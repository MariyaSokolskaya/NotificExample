package com.example.notificexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.initial);
        mediaPlayer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent toActivityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                toActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //создаем менеджер уведомлений
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //создать канал уведомлений
        NotificationChannel channel = new NotificationChannel("MyChannel", "Music",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        //создать уведомление
        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(this, "MyChannel")
                        .setChannelId("MyChannel")
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Уведомление")
                        .setContentText("Работать!!!")
                        .setSmallIcon(R.mipmap.ic_launcher_round);
        Notification notification = notifBuilder.build();

        //показать уведомление
        notificationManager.notify(58673, notification);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}