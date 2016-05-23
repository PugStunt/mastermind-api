package com.pugstunt.mastermind.service.bot.slack.handler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerCredits implements SlackHandler {
	
	static Logger logger = LoggerFactory.getLogger(SlackHandlerCredits.class);
	
	private static final String CREDITS = "credits";
	private static final String CREDITS_FILE = "credits.info";
	
	@Override
	public boolean accept(String message) {
		return message.toLowerCase().startsWith(CREDITS);
	}
	
	@Override
	public SlackResponse apply(SlackRequest request) {
		SlackResponse response = new SlackResponse();
		response.setText(loadHelpText());
		return response;
	}

	private String loadHelpText() {
		try {
			final StringBuilder sb = new StringBuilder();
			List<String> readLines = Files.readLines(new File(SlackHandlerHelp.class.getResource(CREDITS_FILE).getPath()),
					Charset.defaultCharset());
			readLines.forEach(line -> sb.append(line).append(System.lineSeparator()));
			return sb.toString();
		} catch (IOException ex) {
			logger.warn("Credits text couln't be loaded, setting text as 'Unavailable'");
			return "Sorry, credits info is unavailable at moment";
		}
	}
	
}
