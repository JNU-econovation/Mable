package org.techtown.iot3;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.RequiresApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.graphics.BitmapFactory.*;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager Manager;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "**MABLE 알림**", Toast.LENGTH_SHORT).show();
        Notification.Builder mBuilder = new Notification.Builder(context);

        mBuilder.setSmallIcon(R.drawable.on);
        mBuilder.setContentTitle("MABLE");
        mBuilder.setContentText("약 먹을 시간이다");

        Bitmap LargeIcon = BitmapFactory.decodeResource(null,R.drawable.on);
        mBuilder.setLargeIcon(LargeIcon);

        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mBuilder.setSound(ringtoneUri);

        long[] vibrate = {0,100,200,300};
        mBuilder.setVibrate(vibrate);

        //mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        Manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Manager.createNotificationChannel(new NotificationChannel("default","기본 채널",NotificationManager.IMPORTANCE_DEFAULT));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Manager.notify(1,mBuilder.build());
        }

    }
}