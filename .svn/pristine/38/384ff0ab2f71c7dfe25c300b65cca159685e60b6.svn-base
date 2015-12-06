package com.unison.lottery.weibo.common.nosql.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.redis.ObjectKey;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisDao;
import com.unison.lottery.weibo.common.redis.RedisDaoUtils;
import com.unison.lottery.weibo.common.redis.RedisException;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.common.service.IdGenerator;
import com.unison.lottery.weibo.exception.DaoException;
import com.unison.lottery.weibo.utils.Converter;
import com.unison.lottery.weibo.utils.ConverterImpl;

@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDao<T> {
	protected static Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	Class<T> entityClass;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private IdGenerator idGenerator;
	
	// 各种String与Object之间的转换器。
	private Converter converter = new ConverterImpl();
	
	public BaseDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public String getString(final String key) {
		return (String) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}
	
	@Override
	public Long expire(final String key, final int seconds) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	@Override
	public void set(final String key, final String value) {
		redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				jedis.set(key, value);
				return null;
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#hincrBy(java.lang.String, java.lang.String, long)
	 */
	@Override
	public Long hincrBy(final String key, final String field, final long value) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.hincrBy(key, field, value);
			}
		});
	}
	
	@Override
	public Double zscore(final String key, final String value) {
		return (Double) redisTemplate.doExecute(new RedisCallback() {
			public Double doInRedis(Jedis jedis) {
				return jedis.zscore(key, value);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#zrange(java.lang.String, long, long)
	 */
	@Override
	public LinkedHashSet<String> zrange(final String key, final long start, final long end){
		return (LinkedHashSet<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrange(key, start, end);
			}
		});
	}
	
	@Override
	public LinkedHashSet<String> zrevrange(final String key, final long start, final long end){
		return (LinkedHashSet<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrevrange(key, start, end);
			}
		});
	}
	
	@Override
	public List<String> zrangeList(final String key, final long start, final long end){
		LinkedHashSet<String> lhSet = zrange(key, start, end);
		String[] temp = lhSet.toArray(new String[lhSet.size()]);
		return Arrays.asList(temp);
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#lpush(java.lang.String, java.lang.String)
	 */
	@Override
	public Long lpush(final String key, final String... strings) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.lpush(key, strings);
			}
		});
	}

	@Override
	public List<String> lrange(final String key, final long start, final long end) {
		return (List<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
	}

	@Override
	public Long llen(final String key) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.llen(key);
			}
		});
	}

	public Long lrem(final String key, final int count, final String value) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Long doInRedis(Jedis jedis) {
				return jedis.lrem(key, count, value);
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#hashAdd(java.lang.String, java.lang.Object)
	 */
	@Override
	public void hashAdd(final String key, Object obj){
		Field[] fields = obj.getClass().getDeclaredFields();
		Map<String,String> parameterMap= new HashMap<String, String>();
		for(Field field : fields){
			try {
				if(!field.getName().equals("serialVersionUID")){
					field.setAccessible(true);
					Object value = field.get(obj);
					if (value != null){
						String convertedValue = converter.getAsString(field, value);
						parameterMap.put(field.getName(), convertedValue);
					}
				}
			} catch (Exception e) {
				log.error("hashAdd() , add value error ! Field ={} ,{}",  field.getName(), e);
			}
		}
		final Map<String,String> parameters = parameterMap;
		redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				log.debug("hashAdd key={} , value={}",key,parameters.toString());
				return jedis.hmset(key, parameters);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#zcount(java.lang.String, double, double)
	 */
	@Override
	public Long zcount(final String key, final double min, final double max){
		if(StringUtils.isBlank(key) || min > max){
			log.error("zcount error ! key={} , min={}, max={}", new Object[]{key,min,max});
			return -1l;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#zadd(double, java.lang.String, java.lang.String)
	 */
	@Override
	public Long zadd(final double score , final String key , final String value) throws RedisException{
		if(StringUtils.isBlank(key) || StringUtils.isBlank(value)){
			log.error("key and value can not be blank !");
			return 0L;
		}
		log.info("zadd key={} , value={}",key, value);
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zadd(key,score,value);
			}
		});
	}
	
	@Override
	public Long zadd(final String key, final Map<Double, String> scoreMembers){
		if(StringUtils.isBlank(key) || scoreMembers.isEmpty()){
			log.error("key and value can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zadd(key, scoreMembers);
			}
		});
	}
	
	@Override
	public Long zrank(final String key, final String member) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Long doInRedis(Jedis jedis) {
				return jedis.zrank(key, member);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#hashGet(java.lang.String)
	 */
	@Override
	public T hashGet(final String key){
		if(StringUtils.isBlank(key)){
			log.error("key can not be blank !");
			return null;
		}
		return (T) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return reflectToClass(jedis.hgetAll(key),entityClass );
			}
		});
	}
	
	@Override
	public <DO> DO hashGet(final String key, final Class<DO> dataObjectClass){
		if(StringUtils.isBlank(key)){
			log.error("key can not be blank !");
			return null;
		}
		return (DO) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return reflectToClass(jedis.hgetAll(key),dataObjectClass );
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#hashList(java.lang.String[])
	 */
	@Override
	public List<T> hashList(final String keys[]) {
		if(keys == null){
			log.error("hashList error ! keys can not be blank !");
			return new ArrayList<T>();
		}
		return (List<T>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				List<T> result = new ArrayList<T>();
				for(String key : keys){
					result.add(reflectToClass(jedis.hgetAll(key),entityClass ));
				}
				return result;
			}
		});
	}
	
	@Override
	public List<T> loadFromHash(final Iterator<String> keyIterator) {
		return (List<T>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				List<T> result = new ArrayList<T>();
				String key = null;
				while(keyIterator.hasNext()){
					key = keyIterator.next();
					result.add(reflectToClass(jedis.hgetAll(key), entityClass));
				}
				return result;
			}
		});
	}
	
	@Override
	public <DO> List<DO>  hashList(final String keys[], final Class<DO> dataObjectClass) {
		if(keys == null || keys.length == 0){
			log.info("keys is blank !");
			return new ArrayList<DO>();
		}
		return (List<DO>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				List<DO> result = new ArrayList<DO>();
				for(String key : keys){
					DO d = reflectToClass(jedis.hgetAll(key),dataObjectClass );
					if(d != null){
						result.add(d);	
					}
				}
				return result;
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#delete(java.lang.String)
	 */
	@Override
	public Long delete(final String key){
		if(StringUtils.isBlank(key)){
			log.error("delete error ! key can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Long doInRedis(Jedis jedis) {
				return jedis.del(new String[]{key});
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#delete(java.lang.String[])
	 */
	@Override
	public Long delete(final String keys[]){
		if(keys == null ){
			log.error("delete error ! key can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.del(keys);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#incr(java.lang.String)
	 */
	@Override
	public long incr(final String key) throws RedisException{
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}
	
	/**
	 * 将hashmap转为data对象
	 * @param source
	 * @param dataObjectClass
	 * @param filed 需要转换的属性名称（时间类型）
	 * @return
	 */
	public static <DO> DO reflectToClass(Map<String,String> source, Class<DO> dataObjectClass, Set<String> filed) {
		DO d = null;
		if(null == source || null == dataObjectClass || source.isEmpty()){
			return d;
		}
		try {
			d = dataObjectClass.newInstance();
			for (String key : source.keySet()) {
				if(StringUtils.isNotBlank(source.get(key))){
					if(null != filed && filed.contains(key)){
						Date value = new Date(Long.valueOf(source.get(key)));
						org.apache.commons.beanutils.BeanUtils.setProperty(d, key, value);
					} else {
						org.apache.commons.beanutils.BeanUtils.setProperty(d, key, source.get(key));
					}
				}
			}
		} catch (Exception e) {
			log.error("Can not get reflection info from class!", e);
		}
		return d;
	}
	
	/**
	 * 将hashmap转为data对象
	 * @param source
	 * @param dataObjectClass
	 * @return
	 */
	public static <DO> DO reflectToClass(Map<String,String> source,Class<DO> dataObjectClass) {
		return reflectToClass(source, dataObjectClass,new HashSet<String>());
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#zrem(java.lang.String, java.lang.String)
	 */
	@Override
	public Long zrem(final String key , final String ... members) {
		if(StringUtils.isBlank(key) || members.length == 0){
			log.error("key and value can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrem(key, members);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#zcard(java.lang.String)
	 */
	@Override
	public Long zcard(final String key) {
		if(StringUtils.isBlank(key)){
			log.error("key and value can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zcard(key);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#zrangeWithScores(java.lang.String, long, long)
	 */
	@Override
	public LinkedHashSet<Tuple> zrangeWithScores(final String key, final long start, final long end){
		return (LinkedHashSet<Tuple>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrangeWithScores(key, start, end);
			}
		});
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key,
		    final String min, final String max, final int offset,
		    final int count) {
		return (Set<Tuple>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		});
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key,
		    final String min, final String max) {
		return (Set<Tuple>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}
	
	@Override
	public Set<String> zrangeByScore(final String key, final String min,
		    final String max){
		return (Set<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}
		    
	@Override
	public LinkedHashSet<Tuple> zrevrangeWithScores(final String key, final long start, final long end){
		return (LinkedHashSet<Tuple>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrevrangeWithScores(key, start, end);
			}
		});
	}
	
	@Override
	public Set<String> zrevrangeByScoreLimt(final String key, final long max, final long min,final int offset,final int count){
		return (Set<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}
	
	@Override
	public Set<String> zrevrangeByScoreLimt(final String key, final String max, final String min,final int offset,final int count){
		return (Set<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}
	
	@Override
	public Set<String> zrangeByScoreLimt(final String key, final String min, final String max,final int offset,final int count){
		return (Set<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#hdel(java.lang.String, java.lang.String)
	 */
	@Override
	public Long hdel(final String key , final String ... fields) {
		if(StringUtils.isBlank(key) || fields.length == 0){
			log.error("key and value can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.hdel(key, fields);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.unison.lottery.weibo.common.nosql.impl.BaseDao#hmset(java.lang.String, java.util.Map)
	 */
	@Override
	public String hmset(final String key , final Map<String, String> hash) {
		if(StringUtils.isBlank(key) || hash == null || hash.size() == 0){
			log.error("key and value can not be blank !");
			return null;
		}
		return (String) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				String rs = jedis.hmset(key, hash);
				return rs;
			}
		});
	}
	
	@Override
	public String hget(final String key, final String field) {
		return (String) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}

	@Override
	public Long sadd(final String key, final String... members){
		if(StringUtils.isBlank(key) || members.length <= 0){
			log.error("key and value can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.sadd(key, members);
			}
		});
	}

	@Override
	public Long srem(final String key, final String... members){
		if(StringUtils.isBlank(key) || members.length <= 0){
			log.error("key and value can not be blank !");
			return 0L;
		}
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.srem(key, members);
			}
		});
	}
	
	@Override
	public Set<String> smembers(final String key){
		return (Set<String>) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.smembers(key);
			}
		});
	}
	
	public Boolean sismember(final String key, final String member){
		return (Boolean) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.sismember(key, member);
			}
		});
	}
	
	public Long scard(final String key){
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.scard(key);
			}
		});
	}
	// ---------------------- object nosql mapping implements ---------------
	
	@Override
	public <MD> MD create(MD model){
		//String nextIdKey = composeNextIdKey(model);
		//long id = incr(nextIdKey);
		long id = idGenerator.nextId();
		String idKey = composeIdKey(model, id);
		try {
			String idFieldName = RedisDaoUtils.getIdPropertyName(model);
			BeanUtils.setProperty(model, idFieldName, id);
		} catch (Exception e) {
			throw new DaoException("Can not set id to model!", e);
		}
		hashAdd(idKey, model);
		return model;
	}
	
	@Override
	public <MD> boolean update(MD model){
		long id = RedisDaoUtils.getId(model);
		String idKey = composeIdKey(model, id);
		RedisDao<MD> redisDao = redisTemplate.dao(idKey);
		if (!redisDao.exists()){
			return false;
		}
		hashAdd(idKey, model);
		return true;
	}
	
	@SuppressWarnings("unused")
	private String composeNextIdKey(Object model){
		Class<? extends Object> modelClass = model.getClass();
		ObjectKey ann = modelClass.getAnnotation(ObjectKey.class);
		if (ann == null){
			String modelName = getModelName(model);
			return String.format("global:nextId:%s", modelName);
		}else{
			return ann.nextIdKey();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private String getModelName(Object model) {
		Class clazz = model.getClass();
		return clazz.getSimpleName();
	}

	private String composeIdKey(Object model, long id) {
		String modelName = getModelName(model);
		Class<? extends Object> modelClass = model.getClass();
		ObjectKey ann = modelClass.getAnnotation(ObjectKey.class);
		if (ann == null){
			return String.format("id:%s:%d", modelName, id);
		}else{
			return String.format(ann.key(), id);
		}
	}

	private String composeIdKeyByClass(Class<?> modelClass, String id) {
		ObjectKey ann = modelClass.getAnnotation(ObjectKey.class);
		if (ann == null){
			String modelName = modelClass.getSimpleName();
			return String.format("id:%s:%s", modelName, id);
		}else{
			return String.format(ann.key(), id);
		}
	}

    /**
     * Get object by id from Redis.
     * @param clazz object class
     * @param id object id
     * @return null if not found.
     */
    public <MD> MD get(Class<MD> clazz, long id) {
        return get(clazz, Long.toString(id));
    }

    /**
     * Load the model persisted in Redis looking it up by its id and Class type.
     * 
     * @param <T>
     * @param clazz
     * @param id
     * @return null if not found.
     */
    @SuppressWarnings({"rawtypes"})
    public <MD> MD get(Class<MD> clazz, String id) {
    	long startTime = System.currentTimeMillis();
    	
    	String key = composeIdKeyByClass(clazz, id);
    	RedisDao redisDao = redisTemplate.dao(key);
    	if (!redisDao.exists()){
    		return null;
    	}
        Object newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (Exception e) {
			throw new DaoException("Can not create instance of "+clazz, e);
		}
        Map<String,String> map = redisDao.hgetAll();
        Iterator<Entry<String, String>> iter = map.entrySet().iterator();
        while(iter.hasNext()){
        	Entry<String, String> entry = iter.next();
        	String propName = entry.getKey();
        	String value = entry.getValue();
        	if (StringUtils.isNotEmpty(value)){
        		try {
	        		Field field = RedisDaoUtils.getFieldWithParent(clazz, propName);
	        		Object convertedValue = converter.getAsObject(field, value);
	        		BeanUtils.setProperty(newInstance, propName, convertedValue);
        		} catch (Exception e) {
        			log.error("Can not set property.", e);
        		}
        	}
        }
        
        float elapsed = (System.currentTimeMillis()-startTime)/1000.0f;
        log.info("=== 获取{}耗时{}秒", clazz.getSimpleName()+"|"+id, elapsed);
        return (MD) newInstance;
    }

	@Override
	public <MD> List<MD> get(Class<MD> modelClass, Iterator<String> idIterator) {
		LinkedList<MD> loaded = new LinkedList<MD>();
		while(idIterator.hasNext()){
			MD model = get(modelClass, idIterator.next());
			if (model != null){
				loaded.add(model);
			}
		}
		return loaded;
	}

	@Override
	public void destroy(long id, Class<?> modelClass) {
		String modelKey = composeIdKeyByClass(modelClass, ""+id);
		delete(modelKey);
	}

	@Override
	public void destroy(long id) {
		destroy(id, entityClass);
	}

	@Override
	public T get(String id) {
		return get(entityClass, id);
	}

	@Override
	public T get(long id) {
		return get(entityClass, id);
	}

	public String getGlobalId(final String key) {
		return (String) redisTemplate.doExecute(new RedisCallback() {
			@Override
			public Object doInRedis(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}
	
	@Override
	public List<T> get(Iterator<String> idIterator) {
		return get(entityClass, idIterator);
	}

	@Override
	public long zremrangeByScore(final String key, final String start, final String end) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			@Override
			public Object doInRedis(Jedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}
	
	@Override
	public long zremrangeByRank(final String key, final long start, final long end) {
		return (Long) redisTemplate.doExecute(new RedisCallback() {
			@Override
			public Object doInRedis(Jedis jedis) {
				return jedis.zremrangeByRank(key, start, end);
			}
		});
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void hashAddIncludeInheriteProperty(final String key, Object obj) {
//		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			org.apache.commons.beanutils.Converter redisPropertyConverter=new RedisPropertyConverter();
			ConvertUtils.register(redisPropertyConverter , String.class);
			Map<String,String> allReadableFields = BeanUtils.describe(obj);
			
			log.debug("allReadableFields={}",allReadableFields.toString());
			Map<String,String> parameterMap= new HashMap<String, String>();
			for(Entry<String,String> entry : allReadableFields.entrySet()){
				try {
					if(!entry.getKey().equals("serialVersionUID")&&!entry.getKey().equals("class")){
						if(null!= entry.getValue()){
							parameterMap.put(entry.getKey(), entry.getValue());
						}
					}
				} catch (Exception e) {
					log.error("hashAdd() , add value error ! Field ={} ,value={},exception={}", new Object[]{ entry.getKey(), entry.getValue(),e});
				}
			}
			final Map<String,String> parameters = parameterMap;
			redisTemplate.doExecute(new RedisCallback() {
				public Object doInRedis(Jedis jedis) {
					log.debug("hashAdd key={} , value={}",key,parameters.toString());
					return jedis.hmset(key, parameters);
				}
			});
			
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	@Override
	public <DO> DO hashGetIncludeDateProperty(final String key,
			final Class<DO> dataObjectClass,final Set<String> propertyNamesOfDate) {
		if(StringUtils.isBlank(key)){
			log.error("key can not be blank !");
			return null;
		}
		return (DO) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return reflectToClassIncludeBlankPropertyValues(jedis.hgetAll(key),dataObjectClass,propertyNamesOfDate );
			}
		});
	}
	

	/**
	 * 将hashmap转为data对象,也要处理source中为空值的项
	 * @param source
	 * @param dataObjectClass
	 * @param filed 需要转换的属性名称（时间类型）
	 * @return
	 */
	public static <DO> DO reflectToClassIncludeBlankPropertyValues(Map<String,String> source, Class<DO> dataObjectClass, Set<String> filed) {
		DO d = null;
		if(null == source || null == dataObjectClass || source.isEmpty()){
			return d;
		}
		try {
			d = dataObjectClass.newInstance();
			for (String key : source.keySet()) {
				
					if(null != filed && filed.contains(key)){
						if(StringUtils.isNotBlank(source.get(key))){
							Date value = new Date(Long.valueOf(source.get(key)));
							org.apache.commons.beanutils.BeanUtils.setProperty(d, key, value);
						}
						
					} else {
						org.apache.commons.beanutils.BeanUtils.setProperty(d, key, source.get(key));
					}
				
			}
		} catch (Exception e) {
			log.error("Can not get reflection info from class!", e);
		}
		return d;
	}

	@Override
	public void hashAdd(final String key, final String field, final String value) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(field)
				&& value != null) {
			redisTemplate.doExecute(new RedisCallback() {
				public Object doInRedis(Jedis jedis) {
					log.debug("hashAdd key={} , field={} , value={ " + value + "}", key,
							field);
					return jedis.hset(key, field, value);
				}
			});
		}
	}

	@Override
	public Object eval(final String script, final int keyCount, final String... params) {
		if(StringUtils.isNotBlank(script)&&params!=null){
			return redisTemplate.doExecute(new RedisCallback() {
				
				@Override
				public Object doInRedis(Jedis jedis) {
					return jedis.eval(script, keyCount, params);
				}
			});
		}
		return null;
	}
	
}
