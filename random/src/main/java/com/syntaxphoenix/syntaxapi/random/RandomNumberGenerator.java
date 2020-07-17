package com.syntaxphoenix.syntaxapi.random;

public abstract class RandomNumberGenerator {
	
	public abstract void setSeed(long seed);
	public abstract long getSeed();
	
	/*
	 * 
	 */
	
	public abstract void setCompressedState(long state);
	public abstract long getCompressedState();
	
	/*
	 *
	 * 
	 * 
	 */

	public abstract boolean nextBoolean();
	
	/*
	 * 
	 */
	
	public abstract short nextShort();
	public abstract short nextShort(short bound);
	public abstract short nextShort(short min, short max);
	
	/*
	 * 
	 */
	
	public abstract int nextInt();
	public abstract int nextInt(int bound);
	public abstract int nextInt(int min, int max);
	
	/*
	 * 
	 */
	
	public abstract long nextLong();
	public abstract long nextLong(long bound);
	public abstract long nextLong(long min, long max);
	
	/*
	 * 
	 */
	
	public abstract float nextFloat();
	public abstract float nextFloat(float bound);
	public abstract float nextFloat(float min, float max);
	
	/*
	 * 
	 */
	
	public abstract double nextDouble();
	public abstract double nextDouble(double bound);
	public abstract double nextDouble(double min, double max);
	
	/*
	 * 
	 */
	
	protected int next(int bits) {
		return nextInt() >>> (32 - bits);
	}
	
}
