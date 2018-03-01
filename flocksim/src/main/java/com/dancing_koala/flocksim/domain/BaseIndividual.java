package com.dancing_koala.flocksim.domain;

/**
 * Created by dancing-koala on 27/02/18.
 */

public class BaseIndividual implements IIndividual {

    protected float mX;
    protected float mY;
    protected float mVx;
    protected float mVy;

    public BaseIndividual(float x, float y) {
        mX = x;
        mY = y;
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

    @Override
    public String toString() {
        return "ColorIndividual{" +
                "mX=" + mX +
                ", mY=" + mY +
                ", mVx=" + mVx +
                ", mVy=" + mVy +
                '}';
    }
}
