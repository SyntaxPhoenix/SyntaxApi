package com.syntaxphoenix.syntaxapi.json;

public enum ValueType {

	// JSON
	JSON,
	ARRAY(JSON),
	OBJECT(JSON),

	// Primitives
	NULL,
	STRING,
	NUMBER,
	BOOLEAN,
	BYTE(NUMBER),
	SHORT(NUMBER),
	INTEGER(NUMBER),
	LONG(NUMBER),
	BIG_INTEGER(NUMBER),
	FLOAT(NUMBER),
	DOUBLE(NUMBER),
	BIG_DECIMAL(NUMBER);

	private final ValueType parent;

	private ValueType() {
		this(null);
	}

	private ValueType(ValueType parent) {
		this.parent = parent;
	}

	public ValueType getParent() {
		return parent;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public boolean isPrimitive() {
		return parent != null ? parent != JSON : this != JSON;
	}

	public boolean isJson() {
		return !isPrimitive();
	}

	public boolean hasType(JsonValue<?> value) {
		ValueType type = value.getType();
		return parent != null ? (parent == type ? true : this == type) : this == type;
	}

}
