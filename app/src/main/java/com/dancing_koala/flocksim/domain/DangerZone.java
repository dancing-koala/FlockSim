package com.dancing_koala.flocksim.domain;

/**
 * Created by dancing-koala on 28/02/18.
 */

public class DangerZone {

    private boolean mEnabled;
    private float mX;
    private float mY;
    private float mRadius;

    public DangerZone(float radius) {
        this(0f, 0f, radius);
    }

    public DangerZone(float x, float y, float radius) {
        mX = x;
        mY = y;
        mRadius = radius;
    }

    public void set(float x, float y) {
        mX = x;
        mY = y;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public boolean isEnabled() {
        return mEnabled;
    }
}
