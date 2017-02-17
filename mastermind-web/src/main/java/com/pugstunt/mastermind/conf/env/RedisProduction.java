package com.pugstunt.mastermind.conf.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisProduction implements ConnectionRedis {

	static final Logger logger = LoggerFactory.getLogger(ConnectionRedis.class);

	@Override
	public String get() {
		final String connection = System.getenv("redis.connection.string");
		logger.info("redis.connection.string={}", connection);
		return connection;
	}

}
