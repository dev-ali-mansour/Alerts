package dev.alimansour.alerts;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    private Button toastButton, dialogButton, notificationButton;
    private NotificationManager manager;
    private int notificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toastButton = findViewById(R.id.toastButton);
        dialogButton = findViewById(R.id.dialogButton);
        notificationButton = findViewById(R.id.notificationButton);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(0);

        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this,
                        "Welcome To Android by Toast", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 210, 450);
                toast.show();

            }
        });

        dialogButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure?");
            builder.setNegativeButton("Yes", (dialog, which) -> {
                Toast.makeText(this, "User logged out", Toast.LENGTH_LONG).show();
            });
            builder.setPositiveButton("No", null);
            builder.show();
        });

        notificationButton.setOnClickListener(v -> {
            createNotificationChannel();
            Intent intent = new Intent(this, MainActivity.class);
            @SuppressLint("WrongConstant")
            PendingIntent pendingIntent = PendingIntent.
                    getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.app_name));
            builder.setContentTitle("Play music");
            builder.setContentText("Play music file");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.addAction(android.R.drawable.ic_media_play, "Play", pendingIntent);
            builder.addAction(android.R.drawable.ic_media_pause, "Pause", pendingIntent);

            Notification notification = builder.build();

            manager.notify(notificationId++, notification);

        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.app_name), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            manager.createNotificationChannel(channel);
        }
    }
}