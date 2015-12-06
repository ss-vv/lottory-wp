import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = new Jedis("122.226.122.47", 22123);
		try {
			jedis.del("wanglei:list");
			jedis.del("wanglei:SortedSets");
			long time = System.nanoTime();
			for(int i = 0; i<100 ; i++){
				jedis.lpush("wanglei:list", "list"+i);
			}
			System.out.println("list add 100 time = " + (System.nanoTime()/1000000-time/1000000));
			time = System.nanoTime();
			List<String> result= jedis.lrange("wanglei:list", 0, -1);
			System.out.println("list result size = " + result.size() + " , time = " + (System.nanoTime()/1000000-time/1000000));
			
			time = System.nanoTime();
			for(int i = 0; i<100 ; i++){
				jedis.zadd("wanglei:SortedSets", i ,"list"+i);
			}
			System.out.println("wanglei:SortedSets add 100 time = " + (System.nanoTime()/1000000-time/1000000));
			time = System.nanoTime();
			Set<String>setresult= jedis.zrevrange("wanglei:SortedSets", 0, 9);
			System.out.println("wanglei:SortedSets result size = " + setresult.size() + " , time = " + (System.nanoTime()/1000000-time/1000000));
			Iterator<String> it = setresult.iterator();  
			while (it.hasNext()) {  
			  String str = it.next();  
			  System.out.println(str);  
			}  
		} catch (Exception e) {
			System.out.println("error" + e);
		}finally{
			jedis.del("wanglei:list");
			jedis.del("wanglei:SortedSets");
			jedis.disconnect();
		}
	}
}
