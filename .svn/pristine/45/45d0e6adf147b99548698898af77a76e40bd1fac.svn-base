package com.unison.lottery.weibo.common.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.FavouriteDao;
import com.unison.lottery.weibo.common.nosql.PraiseDao;
import com.unison.lottery.weibo.common.redis.RedisDaoUtils;
import com.unison.lottery.weibo.common.redis.Reference;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.exception.ServiceException;
import com.xhcms.lottery.utils.BeanUtilsTools;

/**
 * 常用OHM的服务方法：
 * <ul>填充类
 * <li> protected <VO, MD> VO fillVO(MD plainModel, Class<VO> voClass)
 * <li> protected <VO,MD> PageResult<VO> fillVOPageResult(PageResult<MD> modelResult, Class<?> voClass)
 * </ul>
 * <ul>列表类
 * <li>protected <MD> PageResult<MD> listSortedSetAsc(final String key, Class<?> modelClass, PageRequest pageRequest)
 * <li>protected <MD> PageResult<MD> listSortedSetDesc(final String key, Class<?> modelClass, PageRequest pageRequest)
 * </ul>
 */
public abstract class BaseServiceImpl {

	protected abstract BaseDao<?> getBaseDao();

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PraiseDao praiseDao;
	
	@Autowired
	private FavouriteDao favouriteDao;
	
	public BaseServiceImpl(){
		BeanUtilsTools.fixBeanUtilsNullProblem();
	}
	
	/**
	 * 分页返回Jedis类型为SortedSet的list
	 * 
	 * @param key
	 * @param pageRequest
	 * @throws 
	 */
	protected PageResult<?> ascListSortedSetByPageRequest(final String key,
			PageRequest pageRequest) {
		long[] indexs = initRange(key, pageRequest);
		Set<?> set = (Set<?>) getBaseDao().zrange(key, indexs[0], indexs[1]);
		return new PageResult<>(pageRequest, new ArrayList<>(set));
	}
	
	protected PageResult<?> descListSortedSetByPageRequest(final String key,
			PageRequest pageRequest) {
		long[] indexs = initRange(key, pageRequest);
		Set<?> set = (Set<?>) getBaseDao().zrevrange(key, indexs[0], indexs[1]);
		return new PageResult<>(pageRequest, new ArrayList<>(set));
	}

	/**
	 * 分页返回Jedis类型为SortedSet的包含score的Tuple
	 * 
	 * @param key
	 * @param pageRequest
	 * @return
	 */
	protected PageResult<Tuple> ascListSortedSetByPageRequestWithScores(
			final String key, PageRequest pageRequest) {
		long[] indexs = initRange(key, pageRequest);
		Set<Tuple> set = getBaseDao().zrangeWithScores(key, indexs[0],
				indexs[1]);
		return new PageResult<Tuple>(pageRequest, new ArrayList<Tuple>(set));
	}
	
	protected PageResult<Tuple> descListSortedSetByPageRequestWithScores(
			final String key, PageRequest pageRequest) {
		long[] indexs = initRange(key, pageRequest);
		Set<Tuple> set = getBaseDao().zrevrangeWithScores(key, indexs[0],
				indexs[1]);
		return new PageResult<Tuple>(pageRequest, new ArrayList<Tuple>(set));
	}

	/**
	 * 计算开始、结束偏移量。
	 * @param key
	 * @param pageRequest
	 * @return
	 */
	private long[] initRange(final String key, PageRequest pageRequest) {
		if(pageRequest == null){
			pageRequest = new PageRequest();
		}
		if(pageRequest.getPageIndex() < 1){
			pageRequest.setPageIndex(1);
		}
			pageRequest.setRecordCount(getBaseDao().zcard(key).intValue());
		
		long start;
		long end;
		start = pageRequest.getPageIndex() * pageRequest.getPageSize() - pageRequest.getPageSize();
		end = (start + pageRequest.getPageSize())-1 ;
		return new long[] {start, end};
	}
	/**
	 * 计算开始、结束偏移量。
	 * @param key
	 * @param pageRequest
	 * @return
	 */
	private long[] initRange(final String key, PageRequest pageRequest, long from, long to) {
		if(pageRequest == null){
			pageRequest = new PageRequest();
		}
		if(pageRequest.getPageIndex() < 1){
			pageRequest.setPageIndex(1);
		}
		pageRequest.setRecordCount(getBaseDao().zcount(key, from, to).intValue());
		
		long start;
		long end;
		start = pageRequest.getPageIndex() * pageRequest.getPageSize() - pageRequest.getPageSize();
		end = (start + pageRequest.getPageSize())-1 ;
		return new long[] {start, end};
	}
	
	/**
	 * 按照约定填充Model对象对应的VO对象，并构造一个 PageResult 对象。
	 * <p>
	 * 约定如下：
	 * 对每个VO类中属性"modelProp"，会查看该类是否有@Model注解，有的话就用"modelPropId"属性去装载
	 * 对应的model对象，并赋值给"modelProp"。
	 * <p>
	 * 类型参数：VO 带关系的值对象类；MD 不带关系的基础model类。
	 * @param modelResult 包含Model对象的列表结果.
	 * @return 包含VO对象的列表结果.
	 */
	protected <VO,MD> PageResult<VO> fillVOPageResult(PageResult<MD> modelResult, Class<?> voClass) {
		List<MD> models = modelResult.getResults();
		PageResult<VO> voResult = new PageResult<VO>();
		copyPageResult(modelResult, voResult);
		List<VO> voResults = fillVOResults(models, voClass);
		voResult.setResults(voResults);
		return voResult;
	}
	
	private void copyPageResult(PageResult<?> source, PageResult<?> dest){
		dest.setDesc(source.getDesc());
		dest.setPageRequest(source.getPageRequest());
		dest.setResultCode(source.getResultCode());
		dest.setSuccess(source.isSuccess());
		dest.setTotalResults(source.getTotalResults());
	}
	
	@SuppressWarnings("unchecked")
	private <VO,MD> List<VO> fillVOResults(List<MD> models, Class<?> voClass){
		List<VO> voList = new LinkedList<>();
		for (MD plainModel : models){
			voList.add((VO)fillVO(plainModel, voClass));
		}
		return voList;
	}
	
	/**
	 * 填充带有 @Reference 注解的VO属性"prop"，对应的id属性名为"propId"。</br>
	 * 递归填充带有 @Reference 注解的VO属性。
	 * 
	 * @param plainModel
	 * @param voClass
	 * @return null 如果 plainModel 为null。
	 */
	protected <VO, MD> VO fillVO(MD plainModel, Class<VO> voClass){
		try {
			VO vo = voClass.newInstance();
			BeanUtils.copyProperties(vo, plainModel);
			
			List<Field> referencedFields = collectReferencedFields(voClass); 
			for (Field field : referencedFields) {
				Reference ref = field.getAnnotation(Reference.class);
				String idPropName = String.format("%sId", field.getName());
				if (ref != null && StringUtils.isNotBlank(ref.by())){
					idPropName = ref.by();
				}
				long id = RedisDaoUtils.getLongValueWithParent(plainModel, idPropName);
				if (id >= 0){
					Class<?> refClass = field.getType();
					// 对 VO 类进行填充
					if( refClass.getSimpleName().endsWith("VO")){
						Class<?> parent = refClass.getSuperclass();
						if (parent == null){
							logger.error("The parent class of VO is null!");
							continue;
						}
						Object refObj = getBaseDao().get(parent, id);
						if (refObj != null) {
							Object voProperty = fillVO(refObj, refClass);
							BeanUtils.setProperty(vo, field.getName(), voProperty);
						}
					}else{	// 普通 Model Object 只需要取值即可
						Object refObj = getBaseDao().get(refClass, id);
						if (refObj != null) {
							BeanUtils.setProperty(vo, field.getName(), refObj);
						}
					}
				}
			}
			return vo;
		} catch (Exception e) {
			throw new ServiceException("Can not fill vo with model!", e);
		}
	}
	
	private List<Field> collectReferencedFields(Class<?> voClass) {
		Field[] fields = voClass.getDeclaredFields();
		LinkedList<Field> referencedFields = new LinkedList<>();
		for (Field prop : fields) {
			if (prop.isAnnotationPresent(com.unison.lottery.weibo.common.redis.Reference.class)){
				referencedFields.add(prop);
			}
		}
		return referencedFields;
	}
	
	/**
	 * List Sorted Set with PageRequest in score ascending order.
	 * @param key of sorted set
	 * @param pageRequest
	 * @return page result contains models.
	 */
	protected <MD> PageResult<MD> listSortedSetAsc(final String key, 
			Class<MD> modelClass,
			PageRequest pageRequest) {
		long[] indexs = initRange(key, pageRequest);
		Set<String> set = (Set<String>) getBaseDao().zrange(key, indexs[0], indexs[1]);
		List<MD> models = getBaseDao().get(modelClass, set.iterator());
		return new PageResult<>(pageRequest, models);
	}
	
	/**
	 * List Sorted Set with PageRequest in score descending order.
	 * 
	 * @param key of sorted set
	 * @param pageRequest
	 * @return page result contains models.
	 * Note: null models (missing models) will not be added to results.
	 */
	protected <MD> PageResult<MD> listSortedSetDesc(final String key, 
			Class<MD> modelClass,
			PageRequest pageRequest) {
		long[] indexs = initRange(key, pageRequest);
		Set<String> set = (Set<String>) getBaseDao().zrevrange(key, indexs[0], indexs[1]);
		Long totalResults = getBaseDao().zcard(key);
		List<MD> models = getBaseDao().get(modelClass, set.iterator());
		PageResult<MD> pageResult = new PageResult<>(pageRequest, models);
		pageResult.setTotalResults(totalResults);
		return pageResult;
	}
	
	protected <MD> PageResult<MD> listSortedSetDescByScore(final String key, 
			Class<MD> modelClass, PageRequest pageRequest,
			RecentDateType recentDateType) {
		long now = System.currentTimeMillis();
		long max = now;
		long min = now - recentDateType.getTime();
		return listSortedSetDescByScore(key, modelClass, pageRequest, min, max);
	}
	protected <MD> PageResult<MD> listSortedSetDescByScore(final String key, 
			Class<MD> modelClass, PageRequest pageRequest,
			long from,long to) {
		long[] indexs = initRange(key, pageRequest,from,to);
		Set<String> set = (Set<String>) getBaseDao().zrevrangeByScoreLimt(key, 
				to+"", (from)+"", 
				Long.valueOf(indexs[0]).intValue(), pageRequest.getPageSize());
		Long totalResults = getBaseDao().zcount(key, from, to);
		List<MD> models = getBaseDao().get(modelClass, set.iterator());
		PageResult<MD> pageResult = new PageResult<>(pageRequest, models);
		pageResult.setTotalResults(totalResults);
		return pageResult;
	}
	
	/**
	 * 判断当前用户是否“收藏”和“赞”过此微博
	 * @param uid	当前用户ID
	 * @param weiboMsgVO
	 * @return
	 */
	public WeiboMsgVO checkFavoriateAndLike(long uid, WeiboMsgVO weiboMsgVO){
		if(uid < 1 || weiboMsgVO == null){
			return weiboMsgVO;
		}
		Set<String> likes = praiseDao.list(uid);
		Set<String> favoriates = favouriteDao.loadAll(uid, 0, -1);
		if(likes == null || likes.isEmpty()){
			return weiboMsgVO;
		}
		if(likes != null && likes.contains(weiboMsgVO.getId()+"")){
			weiboMsgVO.setLike(true);
		}
		if(favoriates != null && favoriates.contains(weiboMsgVO.getId()+"")){
			weiboMsgVO.setFavourited(true);
		}
		return weiboMsgVO;
	}
	
	public PageResult<WeiboMsgVO> checkFavoriateAndLike(long uid, PageResult<WeiboMsgVO> result){
		if(uid < 1 || result == null || result.getResults().isEmpty()){
			return result;
		}
		Set<String> likes = praiseDao.list(uid);
		Set<String> favoriates = favouriteDao.loadAll(uid, 0, -1);
		if(likes == null || likes.isEmpty()){
			return result;
		}
		for(WeiboMsgVO msg : result.getResults()){
			if(likes != null && likes.contains(msg.getId()+"")){
				msg.setLike(true);
			}
			if(favoriates != null && favoriates.contains(msg.getId()+"")){
				msg.setFavourited(true);
			}
		}
		return result;
	}
}
