package com.truongpham.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ts-truong.pham on 6/13/16.
 */
public class TodoItemsDbHelper extends SQLiteOpenHelper {

    private ArrayList<TodoItem> todoItemList;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dataManager";
    private static final String TABLE_DATA = "data";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK = "task";
    private static final String KEY_DUE = "due";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_PRIORIRY = "prioriry";
    private static final String KEY_STATUS = "status";

    public TodoItemsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0,"
                + KEY_TASK + " TEXT," + KEY_DUE + " TEXT,"
                + KEY_NOTES + " TEXT," + KEY_PRIORIRY + " TEXT," + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    public void addData(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK, todoItem.get_task());
        values.put(KEY_DUE, todoItem.get_dueDate().toString());
        values.put(KEY_NOTES, todoItem.get_notes());
        values.put(KEY_PRIORIRY, todoItem.get_priority());
        values.put(KEY_STATUS, todoItem.get_status());
        db.insert(TABLE_DATA, null,values);
        db.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<TodoItem> getData() {
        todoItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String CREATE_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DATA + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TASK + " TEXT," + KEY_DUE + " TEXT,"
                + KEY_NOTES + " TEXT," + KEY_PRIORIRY + " TEXT," + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_DATA_TABLE);

        Cursor cursor = db.query(TABLE_DATA, new String[]{KEY_ID, KEY_TASK,
                        KEY_DUE, KEY_NOTES, KEY_PRIORIRY, KEY_STATUS}, null,
                null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        while (cursor.moveToNext()) {
            TodoItem todoItem = new TodoItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
            todoItem.setiD(cursor.getInt(0));
            todoItemList.add(todoItem);
        }
        cursor.close();
        db.close();
        return todoItemList;
    }

    public void updateData(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK, todoItem.get_task());
        values.put(KEY_DUE, todoItem.get_dueDate().toString());
        values.put(KEY_NOTES, todoItem.get_notes());
        values.put(KEY_PRIORIRY, todoItem.get_priority());
        values.put(KEY_STATUS, todoItem.get_status());
        db.update(TABLE_DATA, values, KEY_ID + "=" + todoItem.getiD(), null);
        db.close();
    }
}

