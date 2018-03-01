package com.dancing_koala.flocksim.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dancing_koala.flocksim.domain.ColorIndividual;
import com.dancing_koala.flocksim.domain.DangerZone;
import com.dancing_koala.flocksim.domain.Flock;

import java.util.List;

/**
 * Created by dancing-koala on 27/02/18.
 */

public class FlockingView extends View {

    private static final long DEFAULT_DRAWING_DELAY = 17L; // 17ms ~= 60 FPS

    private boolean mAnimating;
    private Flock<ColorIndividual> mFlock;
    private Runnable mDrawingRunnable;
    private Paint mFlockPaint;
    private IIndividualDrawer mIndividualDrawer;

    public FlockingView(Context context) {
        super(context);
        initAttributes();
    }

    public FlockingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributes();
    }

    public void init(Flock<ColorIndividual> flock, IIndividualDrawer individualDrawer) {
        stop();
        mFlock = flock;
        mIndividualDrawer = individualDrawer;
    }

    private void initAttributes() {
        mFlockPaint = new Paint();
        mFlockPaint.setAntiAlias(true);

        mDrawingRunnable = new Runnable() {
            @Override
            public void run() {
                if (!mAnimating) {
                    return;
                }

                mFlock.makeMove();
                invalidate();

                postDelayed(this, 50L);
            }
        };
    }

    public void start() {
        if (mAnimating) {
            return;
        }
        mAnimating = true;

        mDrawingRunnable.run();
    }

    public void stop() {
        mAnimating = false;
        removeCallbacks(mDrawingRunnable);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mFlock.setFrame(w, h);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mFlock.setDangerZone(event.getX(), event.getY());
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DangerZone dangerZone = mFlock.getDangerZone();
        if (dangerZone.isEnabled()) {
            mFlockPaint.setColor(Color.RED);
            canvas.drawCircle(dangerZone.getX(), dangerZone.getY(), dangerZone.getRadius(), mFlockPaint);

            mFlockPaint.setColor(Color.BLACK);
            canvas.drawCircle(dangerZone.getX(), dangerZone.getY(), dangerZone.getRadius() / 3f, mFlockPaint);
        }

        drawFlock(canvas);
    }

    private void drawFlock(Canvas canvas) {
        if (mIndividualDrawer == null) {
            return;
        }

        for (ColorIndividual individual : mFlock.getIndividuals()) {
            mIndividualDrawer.drawIndividual(individual, canvas);
        }
    }
}
