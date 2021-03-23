package com.syntaxphoenix.syntaxapi.utils.position;

/**
 * @author Lauriichen
 *
 */
public class VectorLocation extends Location {

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

    @Override
    public long getNormalizedX() {
        return (long) x;
    }

    @Override
    public long getNormalizedY() {
        return (long) y;
    }

    @Override
    public long getNormalizedZ() {
        return (long) z;
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
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

}
