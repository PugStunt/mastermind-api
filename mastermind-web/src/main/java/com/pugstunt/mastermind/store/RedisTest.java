package com.pugstunt.mastermind.store;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;

public class RedisTest {

	public static void main(String[] args) {
		
		RedisURI redisURI = RedisURI.create("redis://localhost:6379");
		
		RedisClient redisClient = new RedisClient(redisURI);
		RedisConnection<String,String> connection = redisClient.connect();
	
		System.out.println("Connected to Redis");
		
		connection.close();
		redisClient.shutdown();
	}
	
}
