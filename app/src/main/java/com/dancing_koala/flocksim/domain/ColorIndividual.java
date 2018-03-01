package com.dancing_koala.flocksim.domain;

/**
 * Created by dancing-koala on 27/02/18.
 */

public class ColorIndividual implements IIndividual {

    private static final int DEFAULT_COLOR = 0xFFFF00FF;

    private float mX;
    private float mY;
    private float mVx;
    private float mVy;

    private int mColor;

    private final float mRadius;

    public ColorIndividual(float x, float y, float radius) {
        mX = x;
        mY = y;
        mRadius = radius;
        mColor = DEFAULT_COLOR;
    }

    @Override
    public void moveTo(float x, float y) {
        mX = x;
        mY = y;
    }

    @Override
    public void moveBy(float offsetX, float offsetY) {
        mX += offsetX;
        mY += offsetY;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    public float getRadius() {
        return mRadius;
    }

    @Override
    public void setVelocity(float vx, float vy) {
        mVx = vx;
        mVy = vy;
    }

    @Override
    public void addVelocity(float vx, float vy) {
        mVx += vx;
        mVy += vy;
    }

    @Override
    public float getVx() {
        return mVx;
    }

    @Override
    public float getVy() {
        return mVy;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public String toString() {
        return "ColorIndividual{" +
                "mX=" + mX +
                ", mY=" + mY +
                ", mVx=" + mVx +
                ", mVy=" + mVy +
                ", mColor=" + mColor +
                ", mRadius=" + mRadius +
                '}';
    }
}
