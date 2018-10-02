package bordan16.woact.no.ticatac.customAnimations;

import android.view.animation.Interpolator;

/**
 * Created by daniel on 04/04/2018.
 */

public class MyBounceInt implements Interpolator {

    private double mAmplitude = 1;
    private double mFrequency = 10;


    MyBounceInt(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (-1 * Math.pow(Math.E, -input/ mAmplitude) *
                Math.cos(mFrequency * input) + 1);
    }
}
