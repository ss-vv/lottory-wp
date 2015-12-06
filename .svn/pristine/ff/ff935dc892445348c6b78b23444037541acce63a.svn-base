package com.unison.lottery.weibo.common.nosql;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.redis.RedisException;


public interface BaseDao<T> {

	/** set string 类型的 key,value */
	void set(final String key, String value);
	
	/** get string 类型的value */
	String getString(final String key);
	
	/**设置key的存活时间*/
	Long expire(final String key, final int seconds);
	
	/**
	 * To increase field of Hash.
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hincrBy(final String key, final String field, final long value);

	/**
	 * Returns the specified range of elements in the sorted set stored at key.
	 * @param key
	 * @param start
	 * @param end
	 * @return 特意使用 LinkedHashSet 以保持原有集合的顺序。
	 */
	LinkedHashSet<String> zrange(final String key, final long start, final long end);
	
	public Set<String> zrangeByScore(final String key, final String min,
		    final String max);
		    
	LinkedHashSet<String> zrevrange(String key, long start, long end);
	
	/**
     * Return the all the elements in the sorted set at key with a score between
     * min and max (including elements with score equal to min or max).
     * <p>
     * The elements having the same score are returned sorted lexicographically
     * as ASCII strings (this follows from a property of Redis sorted sets and
     * does not involve further computation).
     * <p>
     * Using the optional
     * {@link #zrangeByScore(String, double, double, int, int) LIMIT} it's
     * possible to get only a range of the matching elements in an SQL-alike
     * way. Note that if offset is large the commands needs to traverse the list
     * for offset elements and this adds up to the O(M) figure.
     * <p>
     * The {@link #zcount(String, double, double) ZCOUNT} command is similar to
     * {@link #zrangeByScore(String, double, double) ZRANGEBYSCORE} but instead
     * of returning the actual elements in the specified interval, it just
     * returns the number of matching elements.
     * <p>
     * <b>Exclusive intervals and infinity</b>
     * <p>
     * min and max can be -inf and +inf, so that you are not required to know
     * what's the greatest or smallest element in order to take, for instance,
     * elements "up to a given value".
     * <p>
     * Also while the interval is for default closed (inclusive) it's possible
     * to specify open intervals prefixing the score with a "(" character, so
     * for instance:
     * <p>
     * {@code ZRANGEBYSCORE zset (1.3 5}
     * <p>
     * Will return all the values with score > 1.3 and <= 5, while for instance:
     * <p>
     * {@code ZRANGEBYSCORE zset (5 (10}
     * <p>
     * Will return all the values with score > 5 and < 10 (5 and 10 excluded).
     * <p>
     * <b>Time complexity:</b>
     * <p>
     * O(log(N))+O(M) with N being the number of elements in the sorted set and
     * M the number of elements returned by the command, so if M is constant
     * (for instance you always ask for the first ten elements with LIMIT) you
     * can consider it O(log(N))
     * 
     * @see #zrangeByScore(String, double, double)
     * @see #zrangeByScore(String, double, double, int, int)
     * @see #zrangeByScoreWithScores(String, double, double)
     * @see #zrangeByScoreWithScores(String, double, double, int, int)
     * @see #zcount(String, double, double)
     * 
     * @param key
     * @param min
     * @param max
     * @return Multi bulk reply specifically a list of elements in the specified
     *         score range.
     */
	Set<Tuple> zrangeByScoreWithScores(final String key,
		    final String min, final String max, final int offset,
		    final int count);
    
	/**
	 * 用指定
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Set<Tuple> zrangeByScoreWithScores(final String key,
		    final String min, final String max);
		    
	/**
	 * 查询member在key中的下标，如果不存在返回null。
	 */
	Long zrank(final String key, final String member);
	
	Long lpush(final String key, final String... strings);
	
	List<String> lrange(final String key, final long start, final long end);
	
	Long llen(final String key);
	
	Object eval(String script,int keyCount,String... params);
	
	/**
	 * 传入对象保存到hash。约定：属性名同 nosql的字段名。null属性不会被存储。
	 * Date类型属性会自动转换为Epoch时间数字。
	 */
	void hashAdd(final String key, Object obj);

	/**
	 * 添加或修改key field
	 * @param key
	 * @param field
	 * @param obj
	 */
	void hashAdd(final String key,String field,String val);
	/**
	 * 返回sortedSets  key对应的值的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Long zcount(final String key, final double min,
			final double max);

	/**
	 * 添加到sorted sets 
	 * @param score
	 * @param key
	 * @param value
	 * @throws RedisException
	 * @return Integer reply, specifically: 1 if the new element was added 
	 * 0 if the element was already a member of the sorted set and the score 
	 * was updated
	 */
	Long zadd(final double score, final String key,
			final String value) throws RedisException;

	/**
	 * 用key获取hash对应的data
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	T hashGet(final String key);

	/**
	 * 用keys获取hash对应的data list
	 * @param keys
	 * @return
	 * @throws RedisException
	 */
	List<T> hashList(final String keys[]);

	/**
	 * 用keys获取hash对应的data list
	 * @param keys
	 * @param dataObjectClass
	 * @return
	 */
	<DO> List<DO> hashList(String[] keys, Class<DO> dataObjectClass);

	/**
	 * 用key删除
	 * @param key
	 * @throws RedisException
	 */
	Long delete(final String key);

	/**
	 * 用keys[]删除
	 * @param keys
	 * @return 
	 * @throws RedisException
	 */
	Long delete(final String keys[]);

	long incr(final String key) throws RedisException;

	/**
	 * Sorted Sets，从排序集合中删除一个或多个成员
	 * @param key
	 * @param members
	 * @return
	 * @throws RedisException
	 */
	Long zrem(final String key, final String... members);

	/**
	 * Sorted Sets，获取一个排序的集合中的成员数量
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	Long zcard(final String key);

	/**
	 * 指定取值的范围，并返回权重
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	LinkedHashSet<Tuple> zrangeWithScores(final String key,
			final long start, final long end);

	/**
	 * 删除指定Hash字段
	 * @param key
	 * @param fields
	 * @return
	 */
	Long hdel(final String key, final String... fields);

	/**
	 * 给指定的key设置hash值 
	 * @param key
	 * @param hash
	 * @return
	 */
	String hmset(final String key,
			final Map<String, String> hash);

	String hget(final String key, final String field);
	
	/**
	 * 同 zrange，只是返回一个List<String>结果。
	 * @param key
	 * @param start
	 * @param end
	 * @return List<String>结果。
	 */
	List<String> zrangeList(final String key, final long start, final long end);

	/**
	 * 从 hash 中装载指定key的对象。
	 * @param keyIterator key遍历器
	 * @return key对应的所有对象
	 */
	List<T> loadFromHash(final Iterator<String> keyIterator);

	<DO> DO hashGet(String key, Class<DO> dataObjectClass);

	/**
	 * 使用key和value，返回score
	 * @param key
	 * @param value
	 * @return
	 */
	Double zscore(String key, String value);

	Set<String> zrevrangeByScoreLimt(String key, long max, long min,
			int offset, int count);

	Set<String> zrevrangeByScoreLimt(String key, String max, String min,
			int offset, int count);

	Set<String> zrangeByScoreLimt(String key, String max, String min,
			int offset, int count);
	
	Long sadd(final String key, final String... members);
	Long srem(final String key, final String... members);
	
	Set<String> smembers(final String key);
	
	Boolean sismember(final String key, final String member);
	
	Long scard(final String key);
	
	// ------------------ object nosql mapping style api ----------------------
	
	/**
	 * 会设置 model 的id属性为新创建的值。<br/>
	 * 约定：model 必须有 long id 属性。
	 * 
	 * @param model 必须有 long id 属性。
	 * @return 设置了新生成id的model。
	 */
	<MD> MD create(MD model);
	
	/** 删除一个 nosql object. */
	void destroy(long id, Class<?> modelClass);
	
	void destroy(long id);

	<MD> boolean update(MD model);
	
	<MD> MD get(Class<MD> clazz, String id);
	
	T get(String id);
	
	<MD> MD get(Class<MD> clazz, long id);
	
	T get(long id);

	/**
	 * @return null models (missing models) will not be added to results.
	 */
	<MD> List<MD> get(Class<MD> modelClass, Iterator<String> idIterator);
	
	String getGlobalId(final String key);
	
	List<T> get(Iterator<String> idIterator);
	
	Long zadd(String key, Map<Double, String> scoreMembers);

	LinkedHashSet<Tuple> zrevrangeWithScores(String key, long start, long end);
	
	long zremrangeByScore(String key, String start, String end);
	
	long zremrangeByRank(String key, long start, long end);
	
	/**
	 * 将继承的属性也加入redis
	 * @param key
	 * @param obj
	 */
	void hashAddIncludeInheriteProperty(final String key,Object obj) ;
	/**
	 * 获取对象时，也处理Date类型的属性，由于在redis中以Date.getTime()的值存储，所以转换规则是使用new Date(redis中的值)
	 * @param key
	 * @param dataObjectClass
	 * @param propertyNamesOfDate Date类型的属性名集合
	 * @return
	 */
	<DO> DO hashGetIncludeDateProperty(String key,
			Class<DO> dataObjectClass,Set<String> propertyNamesOfDate) ;
}