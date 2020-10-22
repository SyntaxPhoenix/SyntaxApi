package com.syntaxphoenix.syntaxapi.utils.position.grid;

public enum GridType {

	GRID_2D(true),
	GRID_3D;

	private final boolean twoDimensions;

	private GridType() {
		this.twoDimensions = false;
	}

	private GridType(boolean twoDimensions) {
		this.twoDimensions = twoDimensions;
	}

	public boolean is2D() {
		return twoDimensions;
	}

}
