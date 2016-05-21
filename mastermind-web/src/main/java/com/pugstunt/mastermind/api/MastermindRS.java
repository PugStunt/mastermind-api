package com.pugstunt.mastermind.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.GameResponse;
import com.pugstunt.mastermind.core.domain.GuessRequest;
import com.pugstunt.mastermind.core.domain.NewGameRequest;

@Path("v1/mastermind")
public class MastermindRS {
	
	private GameService gameService;
	
	@Inject
	public MastermindRS(final GameService gameService) {
		this.gameService = gameService;
	}

	@POST
	@Path("/new_game")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newGame(NewGameRequest request) {

		gameService.newGame(request.getUser());
		return "Hello MasterMind - New Game";
	}

	@POST
	@Path("/guess")
	@Produces(MediaType.APPLICATION_JSON)
	public String guess(GuessRequest guessRequest) {

		// TODO Validate keys
		guessRequest.getCode();
		return "Hello MasterMind - New Game";
	}

	@GET
	@Path("/guess_image")
	@Produces(MediaType.APPLICATION_JSON)
	public String guessImage() {
		return "Hello MasterMind - The image for slack use";
	}

}
