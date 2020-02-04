package com.syntaxphoenix.syntaxapi.utils.position;

/**
 * @author Lauriichen
 *
 */
public class VectorLocation extends Location {
	
	private static final long serialVersionUID = 885085547971308094L;
	
	/**
	 * 
	 */
	
	private double x = 0;
	private double y = 0;
	private double z = 0;
	
	/**
	 * 
	 */
	
	public VectorLocation() {
		
	}
	
	public VectorLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public VectorLocation(BlockLocation location) {
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
	}
	
	/**
	 * 
	 */
	
	public long getNormalizedX() {
		return (long) x;
	}
	
	public long getNormalizedY() {
		return (long) y;
	}
	
	public long getNormalizedZ() {
		return (long) z;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
}
