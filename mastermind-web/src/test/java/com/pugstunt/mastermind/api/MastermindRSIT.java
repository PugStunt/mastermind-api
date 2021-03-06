package com.pugstunt.mastermind.api;

import static com.google.common.collect.Lists.newArrayList;
import static com.jayway.restassured.RestAssured.given;
import static com.pugstunt.mastermind.config.RestAssuredMapperConfig.getConfig;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.ValidatableResponse;
import com.pugstunt.mastermind.core.domain.PlayerGuess;
import com.pugstunt.mastermind.core.domain.CreateNewGame;
import com.pugstunt.mastermind.core.domain.Result;
import com.pugstunt.mastermind.core.domain.enums.Color;

public class MastermindRSIT {
	
	private static final String APPLICATION_CONTEXT = "/mastermind";
	private static final String[] AVAILABLE_COLORS = newArrayList(Color.values())
		.stream()
		.map(color -> String.valueOf(color.getValue()))
		.collect(toList())
		.toArray(new String[Color.values().length]);
	
	@Before
	public void setup() {
		RestAssured.basePath = APPLICATION_CONTEXT;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.config = RestAssuredConfig.config().objectMapperConfig(getConfig());
	}
	
	@Test
	public void newGame() {
		
		given().
			accept(MediaType.APPLICATION_JSON).
			contentType(MediaType.APPLICATION_JSON).
			body(new CreateNewGame("John Snow")).
		when().
			post("/v1/new_game").
		then().
			statusCode(HttpStatus.SC_OK).
			body("code_length", equalTo(AVAILABLE_COLORS.length)).
			body("colors", contains(AVAILABLE_COLORS)).
			body("game_key", allOf(notNullValue(), any(String.class))).
			body("num_guesses", equalTo(0)).
			body("past_results", emptyCollectionOf(Result.class)).
			body("solved", is(false));
	}
	
	@Test
	public void firstGuess() {
		
		final String gameKey = getGameKey();
		final PlayerGuess request = new PlayerGuess();
		request.setCode(newArrayList(AVAILABLE_COLORS).stream().reduce(new String(), (a, b) -> a.concat(b)));
		request.setGameKey(gameKey);
		
		ValidatableResponse response = given().
			accept(MediaType.APPLICATION_JSON).
			contentType(MediaType.APPLICATION_JSON).
			body(request).
		when().
			post("/v1/guess").
		then().
			statusCode(HttpStatus.SC_OK).
			body("code_length", equalTo(AVAILABLE_COLORS.length)).
			body("colors", contains(AVAILABLE_COLORS)).
			body("game_key", equalTo(gameKey)).
			body("num_guesses", equalTo(1)).
			body("past_results[0].exact", is(both(greaterThanOrEqualTo(0)).and(lessThanOrEqualTo(AVAILABLE_COLORS.length)))).
			body("past_results[0].near", is(both(greaterThanOrEqualTo(0)).and(lessThanOrEqualTo(AVAILABLE_COLORS.length)))).
			body("past_results[0].guess", equalTo(request.getCode()));
		
		final Integer exact = response.extract().path("past_results[0].exact");
		
		if (exact.equals(AVAILABLE_COLORS.length)) {
			response.body("solved", is(true));
		} else {
			response.body("solved", is(false));
		}
			
	}
	
	@Test
	public void invalidKey() {
		
		final PlayerGuess request = new PlayerGuess();
		request.setCode("GGGGGGGG");
		request.setGameKey("invalid game key!!");
		
		given().
			accept(MediaType.APPLICATION_JSON).
			contentType(MediaType.APPLICATION_JSON).
			body(request).
		when().
			post("/v1/guess").
		then().
			statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).
			body("status", equalTo("Internal Server Error")).
			body("text", equalTo("No active game"));
	}
	
	@Test
	public void invalidColorGuess() {
		
		final PlayerGuess request = new PlayerGuess();
		request.setCode("AAAAAAAA");
		request.setGameKey(getGameKey());
		
		given().
			accept(MediaType.APPLICATION_JSON).
			contentType(MediaType.APPLICATION_JSON).
			body(request).
		when().
			post("/v1/guess").
		then().
			statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).
			body("status", equalTo("Internal Server Error")).
			body("text", equalTo("Unknown color: 'A'"));
	}
	
	@Test
	public void invalidGuessSize() {
		
		final PlayerGuess request = new PlayerGuess();
		request.setCode("R");
		request.setGameKey(getGameKey());
		
		given().
			accept(MediaType.APPLICATION_JSON).
			contentType(MediaType.APPLICATION_JSON).
			body(request).
		when().
			post("/v1/guess").
		then().
			statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).
			body("status", equalTo("Internal Server Error")).
			body("text", equalTo("Invalid size for guess. Expected: 8; Actual: 1"));
	}
	
	@Test
	public void guessNotInformed() {
		
		final PlayerGuess request = new PlayerGuess();
		request.setGameKey(getGameKey());
		
		given().
			accept(MediaType.APPLICATION_JSON).
			contentType(MediaType.APPLICATION_JSON).
			body(request).
		when().
			post("/v1/guess").
		then().
			statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).
			body("status", equalTo("Internal Server Error")).
			body("text", equalTo("Invalid guess"));
	}
	
	private String getGameKey() {
		
		return given().
			accept(MediaType.APPLICATION_JSON).
			contentType(MediaType.APPLICATION_JSON).
			body(new CreateNewGame("John")).
		when().
			post("/v1/new_game").
		then().
			extract().
			path("game_key");
	}
	
	

}
