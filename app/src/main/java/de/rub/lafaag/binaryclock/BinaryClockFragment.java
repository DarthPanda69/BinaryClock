package de.rub.lafaag.binaryclock;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by In_Vino_Veritas on 08.03.2017.
 */

public class BinaryClockFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private TextView[] hours;
    private TextView[] minutes;

    private ProgressBar seconds;

    private Timer appTimer;

    public BinaryClockFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BinaryClockFragment newInstance(int sectionNumber) {
        BinaryClockFragment fragment = new BinaryClockFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_binary_clock, container, false);

        hours = new TextView[5];
        minutes = new TextView[6];

        hours[0] = (TextView) rootView.findViewById(R.id.hours1);
        hours[1] = (TextView) rootView.findViewById(R.id.hours2);
        hours[2] = (TextView) rootView.findViewById(R.id.hours4);
        hours[3] = (TextView) rootView.findViewById(R.id.hours8);
        hours[4] = (TextView) rootView.findViewById(R.id.hours16);

        minutes[0] = (TextView) rootView.findViewById(R.id.minutes1);
        minutes[1] = (TextView) rootView.findViewById(R.id.minutes2);
        minutes[2] = (TextView) rootView.findViewById(R.id.minutes4);
        minutes[3] = (TextView) rootView.findViewById(R.id.minutes8);
        minutes[4] = (TextView) rootView.findViewById(R.id.minutes16);
        minutes[5] = (TextView) rootView.findViewById(R.id.minutes32);

        seconds = (ProgressBar)rootView.findViewById(R.id.seconds);

        appTimer = new Timer(false);
        appTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                applyClockState(ClockState.getCurrentClockState());
            }
        }, 0, 100);

        return rootView;
    }

    private void applyClockState(final ClockState toApply) {
        getActivity().runOnUiThread(new Runnable() {
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
