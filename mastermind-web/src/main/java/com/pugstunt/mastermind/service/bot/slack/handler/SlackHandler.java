package com.pugstunt.mastermind.service.bot.slack.handler;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

/**
 * Handles the message sent by slack, delegating the process for the proper
 * handler, according to the user request
 * 
 * - New game possbible requests:
 * new game / newgame / start 
 * 
 * - Guess possible requests:
 * guess PRGOBCMP / try PRGOBCMP / maybe PRGOBCMP / PRGOBCMP
 * 
 * - Hint possible requests:
 * hint / suggestion
 * 
 * - Help possible requests:
 * help / -help / --help
 */
public interface SlackHandler {

	boolean accept(String message);

	SlackResponse apply(SlackRequest request);

}
