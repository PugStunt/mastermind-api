package com.pugstunt.mastermind.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.service.ImageService;
import com.pugstunt.mastermind.transformers.GuessTransformer;
import com.pugstunt.mastermind.transformers.NewGameTransformer;

@Path("v1/")
public class MastermindRS {
	
	private final GameService gameService;
	private final ImageService guessImageService;
	
	@Inject
	public MastermindRS(final GameService gameService, final ImageService guessImageService) {
		this.gameService = gameService;
		this.guessImageService = guessImageService;
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
	public Response guess(GuessRequest guessRequest) {

		String gameKey = guessRequest.getGameKey();
		
		String code = guessRequest.getCode();
		List<Color> guess = null;
//			guessRequest
//				.getCode()
//				.chars()
////				.map(new Color)
//				.collect(toList());
		
		return Response.ok(new GuessTransformer().apply(gameService.checkGuess(gameKey, guess))).build();
	}

	@GET
	@Path("/guess_image")
	@Produces(MediaType.APPLICATION_JSON)
	public BufferedImage guessImage(@QueryParam("code") String code) throws IOException {
		return guessImageService.assembleResponseImage(code);
	}

}
