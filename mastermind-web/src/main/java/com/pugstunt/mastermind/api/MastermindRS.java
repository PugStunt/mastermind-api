package com.pugstunt.mastermind.api;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.pugstunt.mastermind.core.domain.GuessRequest;
import com.pugstunt.mastermind.service.GuessImageService;

@Path("v1/mastermind")
public class MastermindRS {

	@Inject
	private GuessImageService guessImageService;

	@POST
	@Path("/new_game")
	@Produces(MediaType.APPLICATION_JSON)
	public String newGame(@QueryParam("user") String user) {

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
	public BufferedImage guessImage(@QueryParam("code") String code) throws IOException {
		return guessImageService.assembleResponseImage(code);
	}

}
