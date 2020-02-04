package com.syntaxphoenix.syntaxapi.utils.position;

/**
 * @author Lauriichen
 *
 */
public class BlockLocation extends Location {
	
	private static final long serialVersionUID = 885081752971308094L;
	
	/**
	 * 
	 */
	
	private long x = 0;
	private long y = 0;
	private long z = 0;
	
	/**
	 * 
	 */
	
	public BlockLocation() {
		
	}
	
	public BlockLocation(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockLocation(VectorLocation position) {
		this.x = position.getNormalizedX();
		this.y = position.getNormalizedY();
		this.z = position.getNormalizedZ();
	}
	
	/**
	 * 
	 */
	
	public long getNormalizedX() {
		return x;
	}
	
	public long getNormalizedY() {
		return y;
	}
	
	public long getNormalizedZ() {
		return z;
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
		this.x = (long) x;
	}
	
	public void setY(double y) {
		this.y = (long) y;
	}
	
	public void setZ(double z) {
		this.z = (long) z;
	}
	
}
