package com.syntaxphoenix.syntaxapi.random;

public class PermutedCongruentialGenerator extends RandomNumberGenerator {

    private long state;
    private long seed;

    private long multiplier = 645;
    private long increment = 2583;

    public PermutedCongruentialGenerator() {
        setSeed(System.currentTimeMillis());
    }

    public PermutedCongruentialGenerator(long seed) {
        setSeed(seed);
    }

    /*
     * 
     */

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
        this.state = seed;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    /*
     * 
     */

    @Override
    public void setCompressedState(long state) {
        this.state = state;
    }

    @Override
    public long getCompressedState() {
        return state;
    }

    /*
     * 
     */

    public long getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
        checkVariables();
    }

    /*
     * 
     */

    public long getIncrement() {
        return increment;
    }

    public void setIncrement(long increment) {
        this.increment = increment;
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
        if (max <= min) {
            return min;
        }
        return (short) (min + Math.abs(nextShort() % (max - min)));
    }

    /*
     * 
     */

    @Override
    public int nextInt() {
        long number = state;
        long count = number >> 59;
        state = number * multiplier + increment;
        number ^= number >> 18;
        return (int) (((int) number) >> count | number << (-count & 31));
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
        return (float) ((nextInt() - -2147483648.0) / 4294967295.0);
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
        return (((long) (next(26)) << 27) + next(27)) * 0x1.0p-53;
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
        if (multiplier <= 0) {
            multiplier = 1;
        }
        if (increment < 0) {
            increment = 0;
        }
    }

}
