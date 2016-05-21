package com.pugstunt.mastermind.store;

import java.util.Optional;

import com.pugstunt.mastermind.core.entity.GameEntry;

public interface GameStore {

	void save(GameEntry game);
	
	Optional<GameEntry> findByKey(String gameKey);
	
}
