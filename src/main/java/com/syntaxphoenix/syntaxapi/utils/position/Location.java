package com.syntaxphoenix.syntaxapi.utils.position;

import com.syntaxphoenix.syntaxapi.utils.data.Properties;

/**
 * @author Lauriichen
 *
 */
public abstract class Location extends Properties {
	
	private static final long serialVersionUID = 7451806925430443931L;
	
	/**
	 * 
	 */
	
	public abstract long getNormalizedX();
	public abstract long getNormalizedY();
	public abstract long getNormalizedZ();
	
	public abstract double getX();
	public abstract double getY();
	public abstract double getZ();
	
	public abstract void setX(double x);
	public abstract void setY(double y);
	public abstract void setZ(double z);

}
