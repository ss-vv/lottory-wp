package com.unison.lottery.weibo.common.cache.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.cache.SchemeCache;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.CommonDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.BetSchemeVoService;
import com.xhcms.lottery.commons.util.SchemeExpireStrategy;
import com.xhcms.lottery.commons.util.SchemeStatusTools;

@Service
public class SchemeCacheImpl extends BaseServiceImpl implements
		SchemeCache {

	private static final Logger log = LoggerFactory
			.getLogger(SchemeCache.class);

	/**是否开启查询缓存*/
	private boolean openQueryCache = true;
	
	@Autowired
	private CommonDao commonDao;

	@Autowired
	private BetSchemeVoService bsProtocol;

	@Autowired
	private SchemeExpireStrategy expireStrategy;
	
	private final String encoding = "iso-8859-1";
	
	@Autowired
	private BetSchemeService betSchemeService;
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return commonDao;
	}
	
	@Override
	public BetScheme getSchemeCache(String schemeCacheKey) {
		log.debug("根据方案缓存的key={},查询方案缓存对象", schemeCacheKey);
		String betSchemeByteArray = commonDao.getString(schemeCacheKey);
		BetScheme betScheme = null;
		if (StringUtils.isNotBlank(betSchemeByteArray)) {
			try {
				long beginTime = System.currentTimeMillis();
				
				byte[] result = betSchemeByteArray.getBytes(encoding);
				betScheme = bsProtocol.getBetScheme(result);
				
				long endTime = System.currentTimeMillis();
				log.debug("\n将二进制方案信息通过protocolBuf转换为方案，key={}, 字节长度={}, 耗时（毫秒）={}",
						new Object[]{schemeCacheKey, result.length, (endTime - beginTime)});
			} catch (Exception e) {
				log.error("通过缓存key={},缓存内容={},转换出现异常:", new Object[] {
						schemeCacheKey, betSchemeByteArray, e });
			}
			log.debug("根据方案缓存的key={},查询方案信息={}", schemeCacheKey, betScheme);
		}
		return betScheme;
	}

	@Override
	public void setSchemeCache(String schemeCacheKey, BetScheme betScheme) {
		if (null != betScheme && betScheme.getId() > 0) {
			byte[] schemeByte = new byte[] {};
			try {
				long beginTime = System.currentTimeMillis();
				schemeByte = bsProtocol.getBetSchemeByte(betScheme);
				
				long endTime = System.currentTimeMillis();
				log.debug("\n通过protocolBuf转换方案对象，key={}, 转换后的字节长度={}, 耗时（毫秒）={}",
						new Object[]{schemeCacheKey, schemeByte.length, (endTime - beginTime)});
			} catch (Exception e) {
				log.error("通过protocolBuf转换方案异常:", e);
			}
			if (schemeByte.length > 0) {
				String result;
				try {
					result = new String(schemeByte, encoding);
					commonDao.set(schemeCacheKey, result);
				} catch (UnsupportedEncodingException e) {
					log.error("编码字节数组，出现异常={}", e);
				}
				boolean isFinalStatus = SchemeStatusTools
						.isFinalStatus(betScheme.getStatus());
				int ttl = 0; // time to live
				if (isFinalStatus) {
					ttl = expireStrategy.getFinalStatusExpireSec();
				} else {
					ttl = expireStrategy.getExpireSec();
				}
				commonDao.expire(schemeCacheKey, ttl);
				log.info("成功设置方案缓存，缓存key={}, 存活时间(秒)={}", schemeCacheKey, ttl);
				
				if(!isFinalStatus || !SchemeStatusTools.isGetAllMatchResult(betScheme.getMatchs())) {
					String showType = schemeCacheKey.split(":")[2];
					int score = Integer.parseInt(showType);
					commonDao.zadd(score, Keys.REAL_SCHEME_CACHE_NOT_FINAL, betScheme.getId()+"");
				}
			}
		} else {
			log.error("方案对象无效，无法缓存，缓存key={}, 方案信息={}", schemeCacheKey, betScheme.getId());
			log.debug("方案对象无效，无法缓存，缓存key={}, 方案信息={}", schemeCacheKey, betScheme);
		}
	}

	@Override
	public void updateSchemeCache(String schemeCacheKey, BetScheme betScheme) {
		log.info("更新方案缓存，key={}", schemeCacheKey);
		if(null != betScheme && betScheme.getId() > 0) {
			setSchemeCache(schemeCacheKey, betScheme);
		}
	}

	public boolean isOpenQueryCache() {
		return openQueryCache;
	}

	public void setOpenQueryCache(boolean openQueryCache) {
		this.openQueryCache = openQueryCache;
	}
}