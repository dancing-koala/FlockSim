package com.dancing_koala.flocksim_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dancing_koala.flocksim.domain.DangerZone;
import com.dancing_koala.flocksim.domain.Flock;
import com.dancing_koala.flocksim_demo.data.ColorIndividual;
import com.dancing_koala.flocksim_demo.drawing.ColorIndividualDrawer;
import com.dancing_koala.flocksim_demo.drawing.FlockingView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FlockingView mFlockingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ColorIndividual> individuals = randomIndividuals(30);
        Flock<ColorIndividual> flock = new Flock<>(new Flock.FlockParams(), individuals, new DangerZone(200f));
        ColorIndividualDrawer drawer = new ColorIndividualDrawer();
        drawer.setDrawTail(true);

        mFlockingView = findViewById(R.id.flocking_view);
        mFlockingView.init(flock, drawer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFlockingView.start();
    }

    @Override
    protected void onPause() {
        mFlockingView.stop();
        super.onPause();
    }

    private List<ColorIndividual> randomIndividuals(int count) {
        List<ColorIndividual> result = new ArrayList<>();
        Random random = new Random();
        float radius = 30f;

        for (int i = 0; i < count; i++) {

            ColorIndividual individual = new ColorIndividual(
                    random.nextFloat() * 100,
                    random.nextFloat() * 100,
                    radius
            );

            individual.setVelocity(random.nextFloat() * radius * 2f, random.nextFloat() * radius * 2f);
            individual.setColor(Color.BLUE);

            result.add(individual);
        }

        return result;
    }
}
