package com.mana_wars.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mana_wars.AndroidLauncher;
import com.mana_wars.R;
import com.mana_wars.model.repository.VolleyRepository;

import java.util.Arrays;

public class CloudMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.i("FCM message", "" + remoteMessage.getFrom());

        if (/* Check if data needs to be processed by long running job */ false) {
                /*// For long-running tasks (10 seconds or more) use WorkManager.

                    // [START dispatch_job]
                OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                            .build();
                WorkManager.getInstance().beginWith(work).enqueue();
                    // [END dispatch_job]*/
        } else {

            Log.i("FCM mb", "" + Arrays.deepToString(remoteMessage.getData().keySet().toArray()));
            Log.i("FCM mb", "" + remoteMessage.getNotification());
            if (remoteMessage.getNotification() != null) {
                Intent intent = new Intent(this, AndroidLauncher.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, "Channel ID")
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("fcm")
                                .setContentText(remoteMessage.getNotification().getBody())
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("Channel ID",
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                notificationManager.notify(777 /* ID of notification */, notificationBuilder.build());
            }

        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        //TODO send to server
        VolleyRepository.getInstance(getApplicationContext()).postFCMUserTokenToServer(s);
    }
}
