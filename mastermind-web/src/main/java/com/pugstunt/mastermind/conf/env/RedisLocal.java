package com.pugstunt.mastermind.conf.env;

public class RedisLocal implements ConnectionRedis {

	@Override
	public String get() {
		return "redis://localhost:6379";
	}

}
