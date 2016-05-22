package com.pugstunt.mastermind.store.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.lambdaworks.redis.RedisConnection;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.store.GameStore;

public class RedisGameStore implements GameStore {

	static Logger logger = LoggerFactory.getLogger(RedisGameStore.class);
	
	private static final long TIME_TO_LIVE = TimeUnit.HOURS.toSeconds(1);
	private final Supplier<RedisConnection<String, String>> connSupplier;
	private final ObjectMapper mapper;
	
	@Inject
	public RedisGameStore(Supplier<RedisConnection<String, String>> connection, ObjectMapper mapper) {
		this.connSupplier = connection;
		this.mapper = mapper;
	}
	
	@Override
	public void save(GameEntry game) {
		final RedisConnection<String, String> connection = connSupplier.get();
		try {
			logger.info("Saving game={} using TTL={}", game.getGameKey(), TIME_TO_LIVE);
			connection.setex(game.getGameKey(), TIME_TO_LIVE, mapper.writeValueAsString(game));
		} catch (JsonProcessingException ex) {
			logger.error("An error occured while sving game={}", game.getGameKey(), ex);
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void remove(String gameKey) {
		logger.info("Removing game={}", gameKey);
		final RedisConnection<String, String> connection = connSupplier.get();
		connection.del(gameKey);
	}
	
	@Override
	public Optional<GameEntry> findByKey(String gameKey) {
		logger.info("Looking up for gameKey={}", gameKey);
		final RedisConnection<String, String> connection = connSupplier.get();
		String gameEntry = connection.get(gameKey);
		if (gameEntry == null) {
			logger.info("game={} not found, returning empty value", gameKey);
			return Optional.empty();
		}
		try {
			logger.info("game={} found, parsing result");
			return Optional.of(mapper.readValue(gameEntry, GameEntry.class));
		} catch (Exception ex) {
			logger.error("An error ocurrend while parsing game={}", gameKey, ex);
			throw new RuntimeException(ex);
		}
	}

}
