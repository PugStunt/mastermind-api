package com.pugstunt.mastermind.api;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.GuessRequest;
import com.pugstunt.mastermind.core.domain.NewGameRequest;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.service.ImageService;
import com.pugstunt.mastermind.transformers.NewGameTransformer;

@Path("v1/")
public class MastermindRS {

	private GameService gameService;

	private ImageService imageService;

	@Inject
	public MastermindRS(final GameService gameService, ImageService imageService) {
		this.gameService = gameService;
		this.imageService = imageService;
	}

	@POST
	@Path("/new_game")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newGame(NewGameRequest request) {

		final GameEntry game = gameService.newGame(request.getUser());
		return Response.ok(new NewGameTransformer().apply(game)).build();
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
	@Produces("image/png")
	public byte[] guessImage(@QueryParam("code") String code) throws IOException {

		return imageService.assembleResponseImage(code);
	}

}
