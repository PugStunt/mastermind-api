package com.pugstunt.mastermind.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.service.bot.slack.SlackService;

@Path("v1/bot/slack")
public class SlackRS {
	
	private final SlackService service;

	@Inject
	public SlackRS(SlackService service) {
		this.service = service;		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SlackResponse botSays(SlackRequest message){
		
		return service.botSays(message);		
	}
}
