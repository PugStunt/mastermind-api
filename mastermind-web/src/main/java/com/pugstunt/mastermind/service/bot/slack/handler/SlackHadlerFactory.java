package com.pugstunt.mastermind.service.bot.slack.handler;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class SlackHadlerFactory {

	// TODO Fisca: resolve essa
	private final List<SlackHandler> handlers = new LinkedList<SlackHandler>();
	private final SlackHandler handlerDefault = new SlackHandlerDefault();

	public SlackHandler getHandlerFor(String message) {
		
		return handlers.stream()
				.filter(new SlackHandlerFilter(message))
				.findFirst()
				.orElse(handlerDefault);
		
	}
	
	public static class SlackHandlerFilter implements Predicate<SlackHandler> {
		
		private final String message;

		public SlackHandlerFilter(final String message) {
			this.message = message;
		}

		@Override
		public boolean test(SlackHandler handler) {
			return handler.accept(message);
		}
		
	}
	

}
