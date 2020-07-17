package com.syntaxphoenix.syntaxapi.random;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public enum NumberGeneratorType {

	LINEAR(LinearCongruentialGenerator.class), PERMUTED(PermutedCongruentialGenerator.class),
	MURMUR(MurmurHashGenerator.class);

	private final AbstractReflect NUMBER_GENERATOR;

	private NumberGeneratorType(Class<? extends RandomNumberGenerator> clazz) {
		this.NUMBER_GENERATOR = new Reflect(clazz).searchConstructor("seed", long.class);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends RandomNumberGenerator> getGeneratorClass() {
		return (Class<? extends RandomNumberGenerator>) NUMBER_GENERATOR.getOwner();
	}

	public RandomNumberGenerator create() {
		return (RandomNumberGenerator) NUMBER_GENERATOR.init();
	}

	public RandomNumberGenerator create(long seed) {
		return (RandomNumberGenerator) NUMBER_GENERATOR.init("seed", seed);
	}

}
