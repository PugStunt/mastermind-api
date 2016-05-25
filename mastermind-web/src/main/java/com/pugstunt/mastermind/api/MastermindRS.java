package com.pugstunt.mastermind.api;

import java.util.List;

import javax.annotation.Nonnull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.CreateNewGame;
import com.pugstunt.mastermind.core.domain.GameCreated;
import com.pugstunt.mastermind.core.domain.PlayerGuess;
import com.pugstunt.mastermind.core.domain.SolvedGame;
import com.pugstunt.mastermind.core.domain.WrongGuess;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.transformers.NewGameTransformer;
import com.pugstunt.mastermind.transformers.SolvedGameTransformer;
import com.pugstunt.mastermind.transformers.WrongGuessTransformer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api("mastermind")
@Path("v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SwaggerDefinition(
		info = @Info(
				title = "Mastermind-API Documentation",
				version = "1.0.0",
				description = "Game Operations"
		),
		tags = {
				@Tag(name = "mastermind", description="Mastermind Operations"),
				@Tag(name = "new_game", description="Creates a new game"),
				@Tag(name = "guess", description="Check whether player guess is right")
		},
		basePath = "/api",
		produces = { MediaType.APPLICATION_JSON },
		consumes = { MediaType.APPLICATION_JSON }
)
public class MastermindRS {

	private final GameService gameService;
	
	@Inject
	public MastermindRS(@Nonnull GameService gameService) {
		this.gameService = gameService;
	}

	@ApiOperation(
			value = "Creates new game for given player and generates the gamey_key",
			notes = "Start a new game",
			response = GameCreated.class
	)
	@POST
	@Path("/new_game")
	public Response newGame(@ApiParam(value = "Player name field", required = true) CreateNewGame newGame) {

		final GameEntry game = gameService.newGame(newGame.getUser());

		return Response.ok(new NewGameTransformer().apply(game)).build();
	}

	@ApiOperation(
			value = "",
			notes = "This endpoint requires the game_key and code consisting of "
					+ "8 letters of RBGYOPCM (corresponding to Red, Blue, Green,"
					+ "Yellow, Orange, Purple, Cyan, Magenta)",
			response = PlayerGuess.class
	)
	@POST
	@Path("/guess")
	@ApiResponses({
		@ApiResponse(code = 200, message = "When the player solves the game", response = SolvedGame.class),
		@ApiResponse(code = 200, message = "When the player misses the answer", response = WrongGuess.class),
		@ApiResponse(code = 200, message = "Game session expired (5 minutes), start new one")
	})
	public Response guess(@ApiParam(value = "Player guess", required = true) PlayerGuess playerGuess) {

		String gameKey = playerGuess.getGameKey();

		List<Color> guess = Color.from(playerGuess.getCode());

		final GameEntry game = gameService.checkGuess(gameKey, guess);
		if (game.isActive()) {
			if (game.isSolved()) {
				return Response.ok(new SolvedGameTransformer().apply(game)).build();
			}
			return Response.ok(new WrongGuessTransformer().apply(game)).build();
		}
		return Response.ok("Game session expired (5 minutes), start new one").build();
	}

}
