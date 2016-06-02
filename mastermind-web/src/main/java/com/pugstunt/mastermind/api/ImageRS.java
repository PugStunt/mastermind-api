package com.pugstunt.mastermind.api;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.pugstunt.mastermind.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api("slack-integration")
@Path("v1/image")
@SwaggerDefinition(
		info = @Info(
				title = "Mastermind-API Documentation",
				version = "1.0.0"
		),
		tags = {
				@Tag(name = "slack-integration", description="useful things to write a slack-bot"),
				@Tag(name = "guessImage", description = "Create an image related to the given color parameter")
		},
		basePath = "/api",
		produces = { MediaType.APPLICATION_JSON },
		consumes = { MediaType.APPLICATION_JSON }
)
public class ImageRS {

	private final ImageService imageService;

	@Inject
	public ImageRS(ImageService imageService) {
		this.imageService = imageService;
	}

	@GET
	@Path("/{code}.png")
	@Produces("image/png")
	@ApiOperation(
			value = "Create an image related to the given color parameter",
			notes = "Create an image related to the given color parameter",
			httpMethod = "GET",
			response = Byte[].class
	)
	@ApiResponses({
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public byte[] guessImage(
			@ApiParam(value = "A string representing the chain of colors of a guess, to be transformed in an image representation") 
			@PathParam("code") String code) throws IOException {

		try {
			return imageService.assembleResponseImage(code);
		} catch (IllegalArgumentException e) {
			return imageService.fallbackImage(code);
		}
	}

}
