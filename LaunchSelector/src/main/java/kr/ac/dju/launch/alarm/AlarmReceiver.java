package kr.ac.dju.launch.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import kr.ac.dju.launch.R;
import kr.ac.dju.launch.SplashActivity;

/**
 * Created by kjwon15 on 2014. 5. 19..
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final int REQUEST_CODE = 0;

    public static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 30);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent splashIntent = new Intent(context, SplashActivity.class);

        String tickerText = context.getResources().getString(R.string.notify_ticker_text);
        String title = context.getResources().getString(R.string.notify_title);
        String summary = context.getResources().getString(R.string.notify_summary);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, splashIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification =
                new Notification(R.drawable.ic_launcher, tickerText, System.currentTimeMillis());

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.number = 1;

        notification.setLatestEventInfo(context, title, summary, pendingIntent);
        nm.notify(0, notification);
    }
}
