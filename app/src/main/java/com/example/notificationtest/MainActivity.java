package com.example.notificationtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String CHANNEL_NOTIFICATION_TEST = "channel_notification_test";

    Button btnNotificationTest;
    int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotificationTest = findViewById(R.id.btnNotificationTest);

        createChannel();

        // Android 13 and above: ask the user for notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        btnNotificationTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    // notification channels are needed from Android 8 (API 26)
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_NOTIFICATION_TEST, "Notification Test", NotificationManager.IMPORTANCE_DEFAULT);

            manager.createNotificationChannel(channel);
        }
    }

    private void sendNotification() {
        String title = "Notification Test";
        String message = "Notification Test";

        // the intent opens the details activity when the notification is tapped
        Intent intent = new Intent(this, AnnouncementDetailActivity.class);
        intent.putExtra("EXTRA_TITLE", title);
        intent.putExtra("EXTRA_MESSAGE", message);
        intent.putExtra("EXTRA_CATEGORY", "Notification Test");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NOTIFICATION_TEST);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationId, builder.build());
        notificationId++; // a new id for every notification so they do not replace each other

        Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show();
    }
}
