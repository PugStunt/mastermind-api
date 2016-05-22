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
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponseAttachment;

public class SlackHandlerHelp implements SlackHandler {

	static final Logger logger = LoggerFactory.getLogger(SlackHandlerHelp.class);
	
	private static final String HELP = "help";
	private static final String HELP_FILE = "help.txt";

	@Override
	public boolean accept(String message) {

		return message.toLowerCase().startsWith(HELP);
	}

	@Override
	public SlackResponse apply(SlackRequest request) {
		SlackResponse response = new SlackResponse();
		
		response.getAttachments().add(SlackResponseAttachment.info(loadHelpText()));		
		
		return response;
	}

	private String loadHelpText() {
		try {
			final StringBuilder sb = new StringBuilder();
			List<String> readLines = Files.readLines(new File(SlackHandlerHelp.class.getResource(HELP_FILE).getPath()),
					Charset.defaultCharset());
			readLines.forEach(line -> sb.append(line).append(System.lineSeparator()));
			return sb.toString();
		} catch (IOException ex) {
			logger.warn("Help text couln't be loaded, setting text as 'Unavailable'");
			return "Sorry, help info is unavailable at moment";
		}
	}

}
