package com.syntaxphoenix.syntaxapi.random;

public class LinearCongruentialGenerator extends RandomNumberGenerator {

	private long seed;
	private int state;
	
	private int aRngConstant = 32543;
	private int cRngConstant = 87454568;
	private int modulu = 2 ^ 31;

	public LinearCongruentialGenerator() {
		setSeed(System.currentTimeMillis());
	}

	public LinearCongruentialGenerator(long seed) {
		setSeed(System.currentTimeMillis());
	}
	
	/*
	 * 
	 */
	
	public void setSeed(long seed) {
		this.seed = seed;
		this.state = Math.toIntExact(seed >> 32);
	}
	
	public long getSeed() {
		return seed;
	}
	
	/*
	 * 
	 */
	
	@Override
	public void setCompressedState(long state) {
		this.state = Math.toIntExact(seed);
	}

	@Override
	public long getCompressedState() {
		return state;
	}

	/*
	 * 
	 */
	
	public int getAConstant() {
		return aRngConstant;
	}
	
	public void setAConstant(int aRngConstant) {
		this.aRngConstant = aRngConstant;
	}
	
	/*
	 * 
	 */
	
	public int getCConstant() {
		return cRngConstant;
	}
	
	public void setCConstant(int cRngConstant) {
		this.cRngConstant = cRngConstant;
	}
	
	/*
	 * 
	 */

	public int getModulu() {
		return modulu;
	}

	public void setModulu(int modulu) {
		this.modulu = modulu;
	}
	
	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public boolean nextBoolean() {
		return next(1) == 0; 
	}
	
	/*
	 * 
	 */

	@Override
	public short nextShort() {
		return (short) next(16);
	}

	@Override
	public short nextShort(short bound) {
		return nextShort((short) 0, bound);
	}

	@Override
	public short nextShort(short min, short max) {
		if(max <= min) {
			return min;
		}
		return (short) (min + Math.abs((int) (nextShort() % (max - min))));
	}
	
	/*
	 * 
	 */

	@Override
	public int nextInt() {
		return state = (aRngConstant * state + cRngConstant) % modulu;
	}

	@Override
	public int nextInt(int bound) {
		return nextInt(0, bound);
	}

	@Override
	public int nextInt(int min, int max) {
		if (max <= min) {
			return min;
		}
		return min + Math.abs(nextInt() % (max - min));
	}

	/*
	 * 
	 */

	@Override
	public long nextLong() {
		return ((long) nextInt()) << 32 | (nextInt() & 0xffffffffL);
	}

	@Override
	public long nextLong(long bound) {
		return nextLong(0L, bound);
	}

	@Override
	public long nextLong(long min, long max) {
		if (max <= min) {
			return min;
		}
		return min + Math.abs(nextLong() % (max - min));
	}

	/*
	 * 
	 */

	@Override
	public float nextFloat() {
		return (float) (((double) nextInt() - -2147483648.0) / 4294967295.0);
	}

	@Override
	public float nextFloat(float bound) {
		return nextFloat(0, bound);
	}

	@Override
	public float nextFloat(float min, float max) {
		if (max <= min) {
			return min;
		}
		return min + Math.abs(nextFloat() % (max - min));
	}

	/*
	 * 
	 */

	@Override
	public double nextDouble() {
		return (((long) (next(26)) << 27) + next(27)) * 1.0D;
	}

	@Override
	public double nextDouble(double bound) {
		return nextDouble(0, bound);
	}

	@Override
	public double nextDouble(double min, double max) {
		if (max <= min) {
			return min;
		}
		return min + Math.abs(nextDouble() % (max - min));
	}
	
}
