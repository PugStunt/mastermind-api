package com.pugstunt.mastermind.core.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Result", description = "Result resource representation")
@Data
@JsonInclude(NON_NULL)
public class Result {

	@ApiModelProperty(value = "Number of colors in the right posisition")
	@JsonProperty("exact")
	private Integer exact;
	
	@ApiModelProperty(value = "Number of colors out of position")
	@JsonProperty("near")
	private Integer near;
	
	@ApiModelProperty(value = "The player guess")
	@JsonProperty("guess")
	private String guess;
		
}