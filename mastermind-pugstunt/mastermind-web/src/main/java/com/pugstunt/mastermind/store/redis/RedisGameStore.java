package com.pugstunt.mastermind.store.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.lambdaworks.redis.RedisConnection;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.store.GameStore;

public class RedisGameStore implements GameStore {

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
			connection.setex(game.getGameKey(), TIME_TO_LIVE, mapper.writeValueAsString(game));
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void remove(String gameKey) {
		final RedisConnection<String, String> connection = connSupplier.get();
		connection.del(gameKey);
	}
	
	@Override
	public Optional<GameEntry> findByKey(String gameKey) {
		final RedisConnection<String, String> connection = connSupplier.get();
		String gameEntry = connection.get(gameKey);
		if (gameEntry == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(mapper.readValue(gameEntry, GameEntry.class));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
