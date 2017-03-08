package de.rub.lafaag.binaryclock;

import java.util.Calendar;

/**
 * Created by LarsH on 23.02.2017.
 */

public class ClockState {
    private boolean[] hourState, minuteState;
    private int milliseconds;

    public ClockState() {
        hourState = new boolean[5];
        minuteState = new boolean[6];
    }

    public boolean[] getHourState() { return hourState; }
    public boolean[] getMinuteState() { return minuteState; }
    public int getMilliseconds() { return milliseconds; }

    public void setHourState(int index, boolean value) {
        if(index < 5 && index >= 0)
            hourState[index] = value;
    }
    public void setMinuteState(int index, boolean value) {
        if(index < 6 && index >= 0)
            minuteState[index] = value;
    }
    public void setMilliseconds(int value) { milliseconds = value; }

    public static ClockState getCurrentClockState() {
        ClockState result = new ClockState();

        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);

        result.setMilliseconds(c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MILLISECOND));

        for(int i = 4; i >= 0; i--)
            if(hour >= (1 << i)) {
                result.setHourState(i, hour >= (1 << i));
                hour -= (1 << i);
            }

        for(int i = 5; i >= 0; i--)
            if(minutes >= (1 << i)) {
                result.setMinuteState(i, minutes >= (1 << i));
                minutes -= (1 << i);
            }

        return result;
    }
}
