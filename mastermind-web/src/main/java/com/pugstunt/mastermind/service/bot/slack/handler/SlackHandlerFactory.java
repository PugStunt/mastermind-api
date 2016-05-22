package com.pugstunt.mastermind.service.bot.slack.handler;

import java.util.List;
import java.util.function.Predicate;

import com.google.inject.Inject;

public class SlackHandlerFactory {

	private final List<SlackHandler> handlers;
	private final SlackHandler handlerDefault = new SlackHandlerDefault();

	@Inject
	public SlackHandlerFactory(List<SlackHandler> handlers) {
		this.handlers = handlers;
	}
	
	public SlackHandler getHandlerFor(String message) {
		
		return handlers.stream()
				.filter(new SlackHandlerFilter(message))
				.findFirst()
				.orElse(handlerDefault);
		
	}
	
	private class SlackHandlerFilter implements Predicate<SlackHandler> {
		
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
