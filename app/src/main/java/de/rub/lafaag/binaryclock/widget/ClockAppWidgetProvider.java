package de.rub.lafaag.binaryclock.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;

import de.rub.lafaag.binaryclock.ClockState;
import de.rub.lafaag.binaryclock.R;

/**
 * Created by In_Vino_Veritas on 15.03.2017.
 */

public class ClockAppWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_REFRESH_WIDGET = "de.rub.lafaag.action.REFRESH_WIDGET";

    private int[] hours = {
            R.id.widget_clock_hours1,
            R.id.widget_clock_hours2,
            R.id.widget_clock_hours4,
            R.id.widget_clock_hours8,
            R.id.widget_clock_hours16
    };
    private int[] minutes = {
            R.id.widget_clock_minutes1,
            R.id.widget_clock_minutes2,
            R.id.widget_clock_minutes4,
            R.id.widget_clock_minutes8,
            R.id.widget_clock_minutes16,
            R.id.widget_clock_minutes32
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ACTION_REFRESH_WIDGET)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName clockWidget = new ComponentName(context.getPackageName(), ClockAppWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(clockWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);

            setAlarmToNextMinute(context);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        final ClockState toApply = ClockState.getCurrentClockState();

        for(int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_appwidget);
            for(int j = 0; j < hours.length; j++)
                views.setInt(hours[j], "setBackgroundResource", toApply.getHourState()[j] ? R.color.clockHours : R.color.clockInactive);
            for(int j = 0; j < minutes.length; j++)
                views.setInt(minutes[j], "setBackgroundResource", toApply.getMinuteState()[j] ? R.color.clockMinutes : R.color.clockInactive);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static void setAlarmToNextMinute(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 1);

        Intent alarmIntent = new Intent(ClockAppWidgetProvider.ACTION_REFRESH_WIDGET);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }
}
