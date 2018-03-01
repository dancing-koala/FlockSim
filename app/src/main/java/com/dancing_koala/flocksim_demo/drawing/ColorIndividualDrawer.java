package com.dancing_koala.flocksim_demo.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.dancing_koala.flocksim_demo.data.ColorIndividual;

/**
 * Created by dancing-koala on 01/03/18.
 */

public class ColorIndividualDrawer implements IIndividualDrawer<ColorIndividual> {

    private final Paint mFlockPaint;
    private boolean mDrawTail;

    public ColorIndividualDrawer() {
        mFlockPaint = new Paint();
    }

    public void setDrawTail(boolean drawTail) {
        mDrawTail = drawTail;
    }

    @Override
    public void drawIndividual(ColorIndividual individual, Canvas canvas) {
        mFlockPaint.setColor(individual.getColor());
        canvas.drawCircle(
                individual.getX(),
                individual.getY(),
                individual.getRadius(),
                mFlockPaint
        );

        if (mDrawTail) {
            mFlockPaint.setColor(Color.MAGENTA);
            canvas.drawLine(
                    individual.getX(),
                    individual.getY(),
                    individual.getX() - individual.getVx(),
                    individual.getY() - individual.getVy(),
                    mFlockPaint
            );
        }
    }
}
