package com.pugstunt.mastermind.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.pugstunt.mastermind.core.domain.enums.Color;

public class GameEntry implements Serializable {

	private static final long serialVersionUID = 590416949958651376L;

	private String gameKey;
	private String player;
	private int guesses;
	private List<PastResult> pastResults;
	private boolean solved;
	private List<Color> answer;
	private long startTime;
	
	private GameEntry() {
		super();
	}
	
	public String getGameKey() {
		return gameKey;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public int getGuesses() {
		return guesses;
	}
	
	public List<PastResult> getPastResults() {
		return pastResults;
	}
	
	public boolean isSolved() {
		return solved;
	}
	
	public List<Color> getAnswer() {
		return answer;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public boolean isActive() {
		final long currentTime = new Date().getTime();
		final boolean active = currentTime - getStartTime() < TimeUnit.MINUTES.toMillis(5);
		return active;
	}
	
	public static Builder builder(String gameKey) {
		return new Builder(gameKey);
	}
	
	public static class Builder {
		
		private final String gameKey;
		private String player;
		private int guesses;
		private List<PastResult> pastResults;
		private boolean solved;
		private List<Color> answer;
		private long startTime;
		
		private Builder(String gameKey) {
			this.gameKey = gameKey;
		}
		
		public Builder playerName(String value) {
			player = value;
			return this;
		}
		
		public Builder guessesNumber(int value) {
			guesses = value;
			return this;
		}
		
		public Builder pastResults(List<PastResult> values) {
			pastResults = values;
			return this;
		}
		
		public Builder solved(boolean value) {
			solved = value;
			return this;
		}
		
		public Builder answer(List<Color> values) {
			answer = values;
			return this;
		}
		
		public Builder startTime(long timestamp) {
			startTime = timestamp;
			return this;
		}
		
		public GameEntry build() {
			GameEntry gameEntry = new GameEntry();
			gameEntry.gameKey = gameKey;
			gameEntry.player = player;
			gameEntry.guesses = guesses;
			gameEntry.pastResults = pastResults != null ? pastResults : Lists.newArrayList();
			gameEntry.solved = solved;
			gameEntry.answer = answer;
			gameEntry.startTime = startTime;
			return gameEntry;
		}
		
	}
	
	@Override
	public String toString() {
		return "GameEntry [player=" + player + ", guesses=" + guesses + ", pastResults=" + pastResults + ", solved="
				+ solved + "]";
	}

	@JsonIgnore
	public Optional<PastResult> getLastResult() {
		if(pastResults.isEmpty()){
			return Optional.empty();
		}
		
		return Optional.of(pastResults.get(pastResults.size() - 1));
	}
	
}
