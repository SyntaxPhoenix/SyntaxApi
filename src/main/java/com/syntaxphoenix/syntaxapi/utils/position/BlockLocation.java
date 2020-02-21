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
	
	@Override
	public long getNormalizedX() {
		return x;
	}
	
	@Override
	public long getNormalizedY() {
		return y;
	}
	
	@Override
	public long getNormalizedZ() {
		return z;
	}
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public double getZ() {
		return z;
	}
	
	@Override
	public void setX(double x) {
		this.x = (long) x;
	}
	
	@Override
	public void setY(double y) {
		this.y = (long) y;
	}
	
	@Override
	public void setZ(double z) {
		this.z = (long) z;
	}
	
}
