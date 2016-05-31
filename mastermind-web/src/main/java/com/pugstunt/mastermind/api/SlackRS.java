package com.pugstunt.mastermind.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.service.SlackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api("slack-integration")
@Path("v1/bot/slack")
@SwaggerDefinition(
		info = @Info(
				title = "Mastermind-API Documentation",
				version = "1.0.0"
		),
		tags = {
				@Tag(name = "slack-integration", description="useful things to write an slack-bot"),
				@Tag(name = "botSays", description = "Process slack message")
		},
		basePath = "/api",
		produces = { MediaType.APPLICATION_JSON },
		consumes = { MediaType.APPLICATION_JSON }
)
public class SlackRS {
	
	private final SlackService service;

	@Inject
	public SlackRS(SlackService service) {
		this.service = service;		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Process slack message identifying command game to run",
			notes = "Process slack message identifying command game to run",
			httpMethod = "POST",
			response = SlackResponse.class
	)
	@ApiResponses({
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public SlackResponse botSays(SlackRequest message){
		
		return service.botSays(message);		
	}
	
}
