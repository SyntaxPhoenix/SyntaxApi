package com.syntaxphoenix.syntaxapi.command.arguments;

import java.math.BigDecimal;

import com.syntaxphoenix.syntaxapi.command.ArgumentSerializer;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.BaseArgument;

/**
 * @author Lauriichen
 *
 */
public class BigDecimalArgument extends BaseArgument {

	private BigDecimal value;

	public BigDecimalArgument() {
		this.value = BigDecimal.ZERO.abs();
	}

	public BigDecimalArgument(BigDecimal value) {
		this.value = value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.BIG_DECIMAL;
	}

	@Override
	public Object asObject() {
		return value;
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public String toString() {
		return toString(ArgumentSerializer.DEFAULT);
	}

	@Override
	public String toString(ArgumentSerializer serializer) {
		return serializer.toString(this);
	}

}
