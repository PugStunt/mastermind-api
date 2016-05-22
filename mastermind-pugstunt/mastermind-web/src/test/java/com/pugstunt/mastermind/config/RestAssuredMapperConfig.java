package com.pugstunt.mastermind.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.mapper.factory.Jackson2ObjectMapperFactory;

public class RestAssuredMapperConfig {
	
	private static final Jackson2ObjectMapperFactory JACKSON_OBJECT_MAPPER = (klazz, s) -> {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	};

	public static ObjectMapperConfig getConfig() {
		
		return ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(JACKSON_OBJECT_MAPPER);
	}

}
