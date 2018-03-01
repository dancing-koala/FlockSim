package com.dancing_koala.flocksim.domain;

/**
 * Created by dancing-koala on 27/02/18.
 */

public interface IIndividual {

    void moveTo(float x, float y);

    void moveBy(float offsetX, float offsetY);

    float getX();

    float getY();

    void setVelocity(float vx, float vy);

    void addVelocity(float vx, float vy);

    float getVx();

    float getVy();
}
