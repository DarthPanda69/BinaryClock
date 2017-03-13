package de.rub.lafaag.binaryclock.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import de.rub.lafaag.binaryclock.ClockState;
import de.rub.lafaag.binaryclock.R;

/**
 * Created by In_Vino_Veritas on 08.03.2017.
 */

public class BinaryClockFragment extends Fragment {

    private int[] hours = {
            R.id.hours1,
            R.id.hours2,
            R.id.hours4,
            R.id.hours8,
            R.id.hours16
    };
    private int[] minutes = {
            R.id.minutes1,
            R.id.minutes2,
            R.id.minutes4,
            R.id.minutes8,
            R.id.minutes16,
            R.id.minutes32
    };

    /**
     * Returns a new instance of this fragment
     */
    public static BinaryClockFragment newInstance() {
        return new BinaryClockFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_binary_clock, container, false);

        Timer appTimer = new Timer(false);
        appTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                applyClockState(ClockState.getCurrentClockState());
            }
        }, 0, 100);

        return rootView;
    }

    private void applyClockState(final ClockState toApply) {
        final View fragmentView = getView();
        Activity fragmentActivity = getActivity();
        if(fragmentActivity != null && fragmentView != null) {
            fragmentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Apply hours
                    for (int i = 0; i < 5; i++) {
                        if (toApply.getHourState()[i])
                            fragmentView.findViewById(hours[i]).setBackgroundResource(R.color.clockHours);
                        else
                            fragmentView.findViewById(hours[i]).setBackgroundResource(R.color.clockInactive);
                    }
                    //Apply minutes
                    for (int i = 0; i < 6; i++) {
                        if (toApply.getMinuteState()[i])
                            fragmentView.findViewById(minutes[i]).setBackgroundResource(R.color.clockMinutes);
                        else
                            fragmentView.findViewById(minutes[i]).setBackgroundResource(R.color.clockInactive);
                    }
                    if (Build.VERSION.SDK_INT >= 24)
                        ((ProgressBar)fragmentView.findViewById(R.id.seconds)).setProgress(toApply.getMilliseconds() / 100, true);
                    else
                        ((ProgressBar)fragmentView.findViewById(R.id.seconds)).setProgress(toApply.getMilliseconds() / 100);
                }
            });
        }
    }
}
