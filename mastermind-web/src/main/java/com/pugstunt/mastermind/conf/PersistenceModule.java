package com.pugstunt.mastermind.conf;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisConnectionPool;
import com.lambdaworks.redis.RedisURI;
import com.pugstunt.mastermind.conf.env.ConnectionRedis;
import com.pugstunt.mastermind.store.GameStore;
import com.pugstunt.mastermind.store.redis.RedisGameStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceModule extends AbstractModule {

	static final Logger logger = LoggerFactory.getLogger(PersistenceModule.class);

	@Override
	protected void configure() {
		bind(GameStore.class).to(RedisGameStore.class).in(Scopes.SINGLETON);
	}

	@Provides
	@Singleton
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper;
	}

	@Provides
	@Singleton
	public RedisConnectionPool<RedisConnection<String, String>> pool(ConnectionRedis env) throws Exception {

		final RedisClient redisClient = new RedisClient(RedisURI.create(env.get()));
		final RedisConnectionPool<RedisConnection<String, String>> pool = redisClient.pool();

		logger.info("Redis connection pool created");

		return pool;
	}

}
