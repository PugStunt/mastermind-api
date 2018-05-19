package com.pugstunt.mastermind.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pugstunt.mastermind.core.domain.enums.Color;

import lombok.Builder.Default;
import lombok.Data;

@Data
@lombok.Builder
public class GameEntry implements Serializable {

	private static final long serialVersionUID = 590416949958651376L;
	
	@Default
	private List<PastResult> pastResults = new ArrayList<>();
	private final String gameKey;
	private String playerName;
	private int guessNumber;
	private boolean solved;
	private List<Color> answer;
	private long startTime;
	
	
	public boolean isActive() {
		final long currentTime = new Date().getTime();
		final boolean active = currentTime - startTime < TimeUnit.MINUTES.toMillis(5);
		return active;
	}
	
	@JsonIgnore
	public Optional<PastResult> getLastResult() {
		if(pastResults.isEmpty()){
			return Optional.empty();
		}	
		return Optional.of(pastResults.get(pastResults.size() - 1));
	}		
	
}
