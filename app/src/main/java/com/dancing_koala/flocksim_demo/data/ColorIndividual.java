package com.dancing_koala.flocksim_demo.data;


import com.dancing_koala.flocksim.domain.BaseIndividual;

/**
 * Created by dancing-koala on 27/02/18.
 */

public class ColorIndividual extends BaseIndividual {

    private static final int DEFAULT_COLOR = 0xFFFF00FF;


    private int mColor;
    private final float mRadius;

    public ColorIndividual(float x, float y, float radius) {
        super(x, y);
        mRadius = radius;
        mColor = DEFAULT_COLOR;
    }

    public float getRadius() {
        return mRadius;
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
