package com.khelacademy.www.utils;

import java.net.URI;
import java.net.URISyntaxException;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisBullet {
	public static JedisPool getPool() {
		  URI redisURI = null;
		try {
			redisURI = new URI("redis://h:p1f18fb68b02dc68c3d7ccfda566b1bd0242cab7acec000e500ed169e6d887618@ec2-18-211-119-207.compute-1.amazonaws.com:36909");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  JedisPoolConfig poolConfig = new JedisPoolConfig();
		  poolConfig.setMaxTotal(10);
		  poolConfig.setMaxIdle(5);
		  poolConfig.setMinIdle(1);
		  poolConfig.setTestOnBorrow(true);
		  poolConfig.setTestOnReturn(true);
		  poolConfig.setTestWhileIdle(true);
		  JedisPool pool = new JedisPool(poolConfig, redisURI);
		  return pool;
		}

		// In your multithreaded code this is where you'd checkout a connection
		// and then return it to the pool
/*		try (Jedis jedis = pool.getResource()){
		  jedis.set("foo", "bar");
		}*/
}
