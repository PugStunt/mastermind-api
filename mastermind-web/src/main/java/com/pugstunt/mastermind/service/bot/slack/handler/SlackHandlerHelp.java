package com.pugstunt.mastermind.service.bot.slack.handler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.io.Files;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerHelp implements SlackHandler {

	private static final String HELP = "help";

	@Override
	public boolean accept(String message) {

		return message.toLowerCase().startsWith(HELP);
	}

	@Override
	public SlackResponse apply(SlackRequest request) {

		StringBuilder sb = new StringBuilder();
		try {
			List<String> readLines = Files.readLines(new File(SlackHandlerHelp.class.getResource("help.txt").getPath()),
					Charset.defaultCharset());
			readLines.forEach(line -> sb.append(line).append(System.lineSeparator()));
		} catch (IOException e) {
		}
		return new SlackResponse(sb.toString());
	}

}
