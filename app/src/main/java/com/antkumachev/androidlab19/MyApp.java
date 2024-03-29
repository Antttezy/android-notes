package com.antkumachev.androidlab19;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.antkumachev.androidlab19.models.Note;
import com.antkumachev.androidlab19.sqlite.NotesHelper;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MyApp extends Application {
    private final ToastHelper toastHelper;
    private final NotesHelper notesHelper;
    private Cursor cursor;

    protected final int ADD_NOTE = 0x211;
    protected final int EDIT_NOTE = 0x212;

    public MyApp() {
        super();
        toastHelper = new ToastHelper(R.layout.toast_layout, this);
        notesHelper = new NotesHelper(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNotes();
    }

    private void loadNotes () {

        cursor = notesHelper
                .getReadableDatabase()
                .rawQuery(MessageFormat.format(
                        "SELECT * FROM {0}",
                        NotesHelper.Notes.TABLE_NAME),
                        null);
    }

    public void add(Note note) {

        NotesHelper.Notes.insertNote(notesHelper.getWritableDatabase(), note);
        loadNotes();
        toastHelper.show("Note added");
        showNotification(ADD_NOTE, MessageFormat.format("Added note {0}", note.getCaption()));
    }

    public Note get(int id) {

        cursor.moveToPosition(id);
        return NotesHelper.Notes.getNote(cursor);
    }

    public void set(int id, Note note) {

        cursor.moveToPosition(id);
        NotesHelper.Notes.updateNote(notesHelper.getWritableDatabase(), note, cursor);
        loadNotes();
        toastHelper.show("Note edited");
        showNotification(EDIT_NOTE, MessageFormat.format("Edited note {0}", note.getCaption()));
    }

    public int size() {

        return cursor.getCount();
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
