package com.syntaxphoenix.syntaxapi.random;

public class MurmurHashGenerator extends RandomNumberGenerator {

	private long iterations = 0L;
	private long seed;

	public MurmurHashGenerator() {
		this.seed = System.currentTimeMillis();
	}

	public MurmurHashGenerator(long seed) {
		this.seed = seed;
	}
	
	/*
	 * 
	 */
	
	public void setSeed(long seed) {
		this.seed = seed;
		this.iterations = 0L;
	}
	
	public long getSeed() {
		return seed;
	}
	
	/*
	 * 
	 */

	@Override
	public void setCompressedState(long state) {
		seed = state & -1;
		iterations = state >> 32 & -1;
	}

	@Override
	public long getCompressedState() {
		return seed | iterations << 32;
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
		if(iterations == Long.MAX_VALUE)
			iterations = 0L;
		int output = generateInt(seed, iterations++);
		return output;
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

	public static int generateInt(long seed, long input) {
		long num = input * 3432918353L;
		num = (num << 15 | num >> 17);
		num *= 461845907L;
		long num2 = seed ^ num;
		num2 = (num2 << 13 | num2 >> 19);
		num2 = num2 * 5L + 3864292196L;
		num2 ^= 2834544218L;
		num2 ^= num2 >> 16;
		num2 *= 2246822507L;
		num2 ^= num2 >> 13;
		num2 *= 3266489909L;
		return (int) (num2 ^ num2 >> 16);
	}

}
