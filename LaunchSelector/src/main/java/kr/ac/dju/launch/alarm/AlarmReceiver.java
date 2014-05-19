package kr.ac.dju.launch.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

/**
 * Created by kjwon15 on 2014. 5. 19..
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final int REQUEST_CODE = 0;
    private static final int INTERVAL_DAY = 24 * 60 * 60 * 1000;

    public static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 60 * 1000;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                firstTime, INTERVAL_DAY, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
    }
}
