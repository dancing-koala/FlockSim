package com.dancing_koala.flocksim_demo.drawing;

import android.graphics.Canvas;

import com.dancing_koala.flocksim.domain.IIndividual;

/**
 * Created by dancing-koala on 01/03/18.
 */

public interface IIndividualDrawer<T extends IIndividual> {
    void drawIndividual(T individual, Canvas canvas);
}
