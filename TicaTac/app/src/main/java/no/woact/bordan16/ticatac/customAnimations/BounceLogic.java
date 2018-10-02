package no.woact.bordan16.ticatac.customAnimations;

import android.view.animation.Interpolator;

/**
 * Created by daniel on 18/04/2018.
 */

/**
 * Class that creates a bounce animation for certain UI elements
 * We implement a interpolator for desired effect.
 */
public class BounceLogic implements Interpolator {

    private double mCorrectAmplitude = 1;
    private double mCorrectFrequency = 10;

    BounceLogic(double amplitude, double frequency) {
        mCorrectAmplitude = amplitude;
        mCorrectFrequency = frequency;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (-1 * Math.pow(Math.E, -input/ mCorrectAmplitude) *
                Math.cos(mCorrectFrequency * input) + 1);
    }
}
