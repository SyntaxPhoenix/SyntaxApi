package com.syntaxphoenix.syntaxapi.random;

public class LinearCongruentialGenerator extends RandomNumberGenerator {

	private long seed;
	private int state;
	
	private int multiplier = 87454568;
	private int increment = 3214561;
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
	
	public int getMultipliert() {
		return multiplier;
	}
	
	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
		checkVariables();
	}
	
	/*
	 * 
	 */
	
	public int getIncrement() {
		return increment;
	}
	
	public void setIncrement(int increment) {
		this.increment = increment;
		checkVariables();
	}
	
	/*
	 * 
	 */

	public int getModulu() {
		return modulu;
	}

	public void setModulu(int modulu) {
		this.modulu = modulu;
		checkVariables();
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
		return state = ((multiplier * state) + increment) % modulu;
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
		return min + (nextFloat() * (max - min));
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
		return min + (nextDouble() * (max - min));
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	
	private void checkVariables() {
		if(modulu <= 0)
			modulu = 1;
		if(multiplier <= 0)
			multiplier = 1;
		if(increment < 0)
			increment = 0;
		if(multiplier > modulu)
			multiplier = modulu != 1 ? modulu - 1 : 1;
		if(increment > modulu)
			increment = modulu - 1;
	}
	
}
