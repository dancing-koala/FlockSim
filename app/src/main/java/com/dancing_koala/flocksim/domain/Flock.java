package com.dancing_koala.flocksim.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dancing-koala on 27/02/18.
 */

public class Flock<T extends IIndividual> {
    private static final String TAG = "Flock";

    private final List<T> mIndividuals;
    private final DangerZone mDangerZone;

    private float mAwarenessDistance;
    private float mSeparationFactor;
    private float mCoherenceFactor;
    private float mDangerFactor;
    private float mMaxVelocity;

    private int mFrameWidth;
    private int mFrameHeight;

    public Flock(FlockParams params, Collection<T> individuals, DangerZone dangerZone) {
        updateParams(params);

        mIndividuals = new ArrayList<>(individuals);
        mDangerZone = dangerZone;
    }

    public void updateParams(FlockParams params) {
        mAwarenessDistance = params.mAwarenessDistance;
        mSeparationFactor = params.mSeparationFactor;
        mCoherenceFactor = params.mCoherenceFactor;
        mDangerFactor = params.mDangerFactor;
        mMaxVelocity = params.mMaxVelocity;
    }

    public List<T> getIndividuals() {
        return mIndividuals;
    }

    public void setFrame(int width, int height) {
        mFrameWidth = width;
        mFrameHeight = height;
    }

    public void setDangerZone(float x, float y) {
        mDangerZone.set(x, y);
        mDangerZone.setEnabled(true);
    }

    public DangerZone getDangerZone() {
        return mDangerZone;
    }

    public void makeMove() {
        for (T individual : mIndividuals) {
            List<T> neighbours = getNeighbours(individual);

            if (!neighbours.isEmpty()) {
                separate(individual, neighbours);
                align(individual, neighbours);
                cohere(individual, neighbours);
            }

            avoidDangerZone(individual);
            adjustVelocity(individual);
            move(individual);
            adjustInframe(individual);
        }
    }

    private List<T> getNeighbours(final T individual) {
        List<T> result = new ArrayList<>();

        for (T neighbour : mIndividuals) {
            if (!neighbour.equals(individual) && distance(individual, neighbour) <= mAwarenessDistance) {
                result.add(neighbour);
            }
        }

        return result;
    }

    private void move(T individual) {
        individual.moveBy(individual.getVx(), individual.getVy());
    }

    private void separate(T individual, List<T> neighbours) {
        float sx = 0f;
        float sy = 0f;

        for (T neighbour : neighbours) {
            float dist = distance(individual, neighbour);
            if (dist < mSeparationFactor) {
                sx += (individual.getX() - neighbour.getX()) / dist;
                sy += (individual.getY() - neighbour.getY()) / dist;
            }
        }

        individual.setVelocity(sx, sy);
    }

    private void align(T individual, List<T> neighbours) {
        float ax = 0f;
        float ay = 0f;

        for (T neighbour : neighbours) {
            ax += neighbour.getVx();
            ay += neighbour.getVy();
        }

        float adx = ax / neighbours.size();
        float ady = ay / neighbours.size();

        individual.addVelocity(adx, ady);
    }

    private void cohere(T individual, List<T> neighbours) {
        float cx = 0f;
        float cy = 0f;

        for (T neighbour : neighbours) {
            cx += neighbour.getX();
            cy += neighbour.getY();
        }

        float cdx = cx / neighbours.size() / individual.getX() / mCoherenceFactor;
        float cdy = cy / neighbours.size() / individual.getY() / mCoherenceFactor;

        individual.addVelocity(cdx, cdy);
    }

    private void avoidDangerZone(T individual) {
        if (!mDangerZone.isEnabled()) {
            return;
        }

        float distance = distance(individual, mDangerZone);

        if (distance >= mDangerZone.getRadius()) {
            return;
        }

        float rejection = mDangerZone.getRadius() - distance;

        float newVx = rejection * Math.signum(individual.getX() - mDangerZone.getX()) * mDangerFactor;
        float newVy = rejection * Math.signum(individual.getY() - mDangerZone.getY()) * mDangerFactor;

        individual.addVelocity(newVx, newVy);
    }

    private void adjustVelocity(T individual) {
        float vx = individual.getVx();
        float vy = individual.getVy();

        float velocity = length(vx, vy);

        if (velocity < mMaxVelocity) {
            return;
        }

        vx *= mMaxVelocity / velocity;
        vy *= mMaxVelocity / velocity;

        individual.setVelocity(vx, vy);
    }

    private void adjustInframe(T individual) {
        float x = individual.getX();
        float y = individual.getY();

        if (x > mFrameWidth) {
            x = mFrameWidth - x;
        } else if (x < 0) {
            x += mFrameWidth;
        }

        if (y > mFrameHeight) {
            y = mFrameHeight - y;
        } else if (y < 0) {
            y += mFrameHeight;
        }

        individual.moveTo(x, y);
    }

    private float distance(T left, T right) {
        return distance(left.getX(), left.getY(), right.getX(), right.getY());
    }

    private float distance(T individual, DangerZone dangerZone) {
        return distance(individual.getX(), individual.getY(), dangerZone.getX(), dangerZone.getY());
    }

    private float distance(float ax, float ay, float bx, float by) {
        float dX = ax - bx;
        float dY = ay - by;

        return (float) Math.sqrt(dX * dX + dY * dY);
    }

    private float length(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static class FlockParams {
        private static final float DEFAULT_SEPARATION_FACTOR = 60f;
        private static final float DEFAULT_COHERENCE_FACTOR = 5f;
        private static final float DEFAULT_DANGER_FACTOR = 10f;
        private static final float DEFAULT_AWARENESS_DISTANCE = 30f * 2f * 2f;
        private static final float DEFAULT_MAX_VELOCITY = 30f;

        private float mSeparationFactor;
        private float mCoherenceFactor;
        private float mDangerFactor;
        private float mAwarenessDistance;
        private float mMaxVelocity;

        public FlockParams() {
            mSeparationFactor = DEFAULT_SEPARATION_FACTOR;
            mCoherenceFactor = DEFAULT_COHERENCE_FACTOR;
            mDangerFactor = DEFAULT_DANGER_FACTOR;
            mAwarenessDistance = DEFAULT_AWARENESS_DISTANCE;
            mMaxVelocity = DEFAULT_MAX_VELOCITY;
        }

        public void separationFactor(float separationFactor) {
            mSeparationFactor = separationFactor;
        }

        public void coherenceFactor(float coherenceFactor) {
            mCoherenceFactor = coherenceFactor;
        }

        public void dangerFactor(float dangerFactor) {
            mDangerFactor = dangerFactor;
        }

        public void awarenessDistance(float awarenessDistance) {
            mAwarenessDistance = awarenessDistance;
        }

        public void maxVelocity(float maxVelocity) {
            mMaxVelocity = maxVelocity;
        }
    }
}
