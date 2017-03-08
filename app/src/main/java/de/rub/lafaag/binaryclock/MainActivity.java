package de.rub.lafaag.binaryclock;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView[] hours;
    private TextView[] minutes;

    private ProgressBar seconds;

    private Timer appTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hours = new TextView[5];
        minutes = new TextView[6];

        hours[0] = (TextView) findViewById(R.id.hours1);
        hours[1] = (TextView) findViewById(R.id.hours2);
        hours[2] = (TextView) findViewById(R.id.hours4);
        hours[3] = (TextView) findViewById(R.id.hours8);
        hours[4] = (TextView) findViewById(R.id.hours16);

        minutes[0] = (TextView) findViewById(R.id.minutes1);
        minutes[1] = (TextView) findViewById(R.id.minutes2);
        minutes[2] = (TextView) findViewById(R.id.minutes4);
        minutes[3] = (TextView) findViewById(R.id.minutes8);
        minutes[4] = (TextView) findViewById(R.id.minutes16);
        minutes[5] = (TextView) findViewById(R.id.minutes32);

        seconds = (ProgressBar)findViewById(R.id.seconds);

        appTimer = new Timer(false);
        appTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                applyClockState(ClockState.getCurrentClockState());
            }
        }, 0, 100);
    }

    private void applyClockState(final ClockState toApply) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 5; i++) {
                    if(toApply.getHourState()[i])
                        hours[i].setBackgroundResource(R.color.clockHours);
                    else
                        hours[i].setBackgroundResource(R.color.clockInactive);
                }
                for(int i = 0; i < 6; i++) {
                    if(toApply.getMinuteState()[i])
                        minutes[i].setBackgroundResource(R.color.clockMinutes);
                    else
                        minutes[i].setBackgroundResource(R.color.clockInactive);
                }
                if(Build.VERSION.SDK_INT >= 24)
                    seconds.setProgress(toApply.getMilliseconds() / 100, true);
                else
                    seconds.setProgress(toApply.getMilliseconds() / 100);
            }
        });
    }
}
