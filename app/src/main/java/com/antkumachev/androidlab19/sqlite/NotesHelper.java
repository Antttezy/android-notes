package com.antkumachev.androidlab19.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.antkumachev.androidlab19.models.Note;

import java.text.MessageFormat;

public class NotesHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NoteApplicationContext";

    public static final class Notes {
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_CAPTION = "Caption";
        public static final String COLUMN_CONTENT = "Content";
        public static final String COLUMN_TIME = "Time";

        public static String getCreateStatement() {

            return MessageFormat.format(
                    "CREATE TABLE {0} (" +
                            "{1} INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "{2} TEXT," +
                            "{3} TEXT," +
                            "{4} TIME" +
                            ");",
                    TABLE_NAME,
                    BaseColumns._ID,
                    COLUMN_CAPTION,
                    COLUMN_CONTENT,
                    COLUMN_TIME
            );
        }

        public static Note getNote(Cursor cursor) {

            int id = cursor.getColumnIndex(BaseColumns._ID);
            int captionId = cursor.getColumnIndex(COLUMN_CAPTION);
            int contentId = cursor.getColumnIndex(COLUMN_CONTENT);
            int timeId = cursor.getColumnIndex(COLUMN_TIME);

            String caption = cursor.getString(captionId);
            String content = cursor.getString(contentId);
            long time = cursor.getLong(timeId);

            return new Note(caption, content, time);
        }

        public static long insertNote(SQLiteDatabase db, Note note) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_CAPTION, note.getCaption());
            values.put(COLUMN_CONTENT, note.getContent());
            values.put(COLUMN_TIME, note.getCreationTime().getTime());
            return db.insert(TABLE_NAME, null, values);
        }

        public static int updateNote(SQLiteDatabase db, Note note, Cursor cursor) {

            int index = cursor.getColumnIndex(BaseColumns._ID);
            long id = cursor.getInt(index);

            ContentValues values = new ContentValues();
            values.put(COLUMN_CAPTION, note.getCaption());
            values.put(COLUMN_CONTENT, note.getContent());
            values.put(COLUMN_TIME, note.getCreationTime().getTime());
            return db.update(TABLE_NAME, values, MessageFormat.format("{0} = ?", BaseColumns._ID), new String[]{Long.toString(id)});
        }
    }

    public NotesHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Notes.getCreateStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
