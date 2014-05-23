package kr.ac.dju.launch.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import kr.ac.dju.launch.R;
import kr.ac.dju.launch.SplashActivity;

/**
 * Created by kjwon15 on 2014. 5. 19..
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final int REQUEST_CODE = 0;
    private static final String ACTION_ALARM = "alarm";
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    public static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(ACTION_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 30);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d("Alarm", "Alarm set");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_ALARM)) {
            notifyAlarm(context);
        } else if (action.equals(ACTION_BOOT)) {
            setAlarm(context);
        }
    }

    private void notifyAlarm(Context context) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent splashIntent = new Intent(context, SplashActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, splashIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setTicker(context.getString(R.string.notify_ticker_text))
                .setContentTitle(context.getString(R.string.notify_title))
                .setContentText(context.getString(R.string.notify_summary))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .build();

        nm.notify(0, notification);
    }
}
