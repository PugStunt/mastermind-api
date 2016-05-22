package com.pugstunt.mastermind.conf.env;

public class RedisHeroku implements ConnectionRedis {

	@Override
	public String get() {
		return System.getenv("redis.connection.string");
	}

}
