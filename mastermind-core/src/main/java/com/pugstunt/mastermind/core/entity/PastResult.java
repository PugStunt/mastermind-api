package com.pugstunt.mastermind.core.entity;

import java.io.Serializable;

import lombok.Data;

@Data
@lombok.Builder
public class PastResult implements Serializable {

	private static final long serialVersionUID = -7656840151183583368L;

	private int exact;
	private String guess;
	private int near;
	
}