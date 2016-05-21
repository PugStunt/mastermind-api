package com.pugstunt.mastermind.transformers;

import java.util.function.Function;

import com.pugstunt.mastermind.core.domain.Result;
import com.pugstunt.mastermind.core.entity.PastResult;

public class PastResultTransformer implements Function<PastResult, Result> {

	@Override
	public Result apply(PastResult pastResult) {
		final Result result = new Result();
		result.setExact(pastResult.getExact());
		result.setNear(pastResult.getNear());
		result.setGuess(pastResult.getGuess());
		return result;
	}

}