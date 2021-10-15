package com.antkumachev.androidlab19;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MyApp extends Application {
    private final ArrayList<String> notes = new ArrayList<>();
    private final ToastHelper toastHelper;

    protected final int ADD_NOTE = 0x211;
    protected final int EDIT_NOTE = 0x212;

    public MyApp() {
        super();
        toastHelper = new ToastHelper(R.layout.toast_layout, this);
        notes.add("First");
        notes.add("Second");
    }

    public void add(String note) {
        notes.add(note);
        toastHelper.show("Note added");
        showNotification(ADD_NOTE, MessageFormat.format("Added note {0}", note));
    }

    public String get(int id) {
        return notes.get(id);
    }

    public void set(int id, String note) {
        notes.set(id, note);
        toastHelper.show("Note edited");
        showNotification(EDIT_NOTE, MessageFormat.format("Edited note {0}", note));
    }

    public int size() {
        return notes.size();
    }

    private void showNotification(int id, String message) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

            RemoteViews view = new RemoteViews(getPackageName(), R.layout.notification);
            view.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
            view.setTextViewText(R.id.title, getString(R.string.app_name));
            view.setTextViewText(R.id.description, message);

            NotificationChannel channel = new NotificationChannel("notes_channel_0", "Notes channel", NotificationManager.IMPORTANCE_DEFAULT);

            Notification notification = new Notification.Builder(this, "notes_channel_0")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentTitle(getString(R.string.app_name))
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setTicker("Записки")
                    .setContent(view)
                    .build();

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            manager.notify(id, notification);
        }
    }
}
