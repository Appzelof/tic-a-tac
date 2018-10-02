package no.woact.bordan16.ticatac.engines;

import android.content.Context;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

import java.nio.channels.AsynchronousCloseException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by daniel on 12/04/2018.
 */

/**
 * This class handle`s simple logic for start and stop time.
 */
public class TimerEngine {

    private Chronometer chronometer;

    public TimerEngine(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public void startTimer(){
        //Starting the chronometer.
        chronometer.start();
    }

    public void stopTimer(){
        //Resets the chronometer by setting a new base for our chronometer.
        chronometer.setBase(SystemClock.elapsedRealtime());
        //Stops the chronometer
        chronometer.stop();


    }

}
