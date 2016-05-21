package com.pugstunt.mastermind.api;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.GuessRequest;
import com.pugstunt.mastermind.core.domain.NewGameRequest;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.transformers.ColorTransformer;
import com.pugstunt.mastermind.transformers.GuessTransformer;
import com.pugstunt.mastermind.transformers.NewGameTransformer;

@Path("v1/")
public class MastermindRS {
	
	private final GameService gameService;
	
	@Inject
	public MastermindRS(final GameService gameService) {
		this.gameService = gameService;
	}

	@POST
	@Path("/new_game")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newGame(NewGameRequest request) {

		final GameEntry game = gameService.newGame(request.getUser());
		
		System.out.println("RESULT=" + game.getAnswer());
		
		return Response.ok(new NewGameTransformer().apply(game)).build();
	}

	@POST
	@Path("/guess")
	@Produces(MediaType.APPLICATION_JSON)
	public Response guess(GuessRequest guessRequest) {

		String gameKey = guessRequest.getGameKey();
		
		List<Color> guess = guessRequest.getCode()
			.chars()
			.mapToObj(new ColorTransformer())
			.collect(toList());
		
		return Response.ok(new GuessTransformer().apply(gameService.checkGuess(gameKey, guess))).build();
	}

}
