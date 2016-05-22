package com.pugstunt.mastermind.service.bot.slack.handler;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerDefault implements SlackHandler {

	@Override
	public boolean accept(String message) {
		return false;
	}

	@Override
	public SlackResponse apply(SlackRequest request) {
		
		// TODO if is a valid guess
		if(request.getTextWithouTrigger().startsWith("YY")){
			return new SlackHandlerGuess().apply(request);			
		}
		
		return new SlackResponse("Didn't understand captain");
	}

}
