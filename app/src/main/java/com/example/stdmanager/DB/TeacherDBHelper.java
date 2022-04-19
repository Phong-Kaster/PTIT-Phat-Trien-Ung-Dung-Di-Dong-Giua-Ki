package com.example.stdmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stdmanager.models.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherDBHelper extends  SQLiteOpenHelper {
    private static final String TABLE_NAME = "TEACHER";
    private static final String TAG = "SQLite";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    public TeacherDBHelper(Context context) {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ( " +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s TEXT, " +
                        "%s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD);
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    // If Teacher table has no data
    // default, Insert 2 records.
    public void createDefaultTeachersIfNeed()  {
        int count = this.getTeachersCount();
        if(count == 0 ) {
            Teacher Teacher1 = new Teacher(1, "Nguyễn Bích Thủy", "123456");
            Teacher Teacher2 = new Teacher(2, "Lê Văn Hiền", "123456");
            Teacher Teacher3 = new Teacher(3, "Trần Huy Hoàng", "123456");
            this.addTeacher(Teacher1);
            this.addTeacher(Teacher2);
            this.addTeacher(Teacher3);
        }
    }


    public void addTeacher(Teacher Teacher) {
        Log.i(TAG, "TeacherDBHelper.addTeacher ... " + Teacher.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, Teacher.getId());
        values.put(COLUMN_NAME, Teacher.getName());
        values.put(COLUMN_PASSWORD, Teacher.getPassword());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);

        // Closing database connection
        db.close();
    }


    public Teacher getTeacher(int id) {
        Log.i(TAG, "TeacherDBHelper.getTeacher ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_PASSWORD }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        try {
            Log.i(TAG, "TeacherDBHelper.getTeacher ... " + cursor.getString(2));
            Teacher Teacher = new Teacher(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            return Teacher;
        } catch(Exception ex){
            return null;
        }

    }


    public List<Teacher> getAllTeachers() {
        Log.i(TAG, "TeacherDBHelper.getAllTeachers ... " );

        List<Teacher> TeacherList = new ArrayList<Teacher>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Teacher Teacher = new Teacher();
                Teacher.setId(Integer.parseInt(cursor.getString(0)));
                Teacher.setName(cursor.getString(1));
                Teacher.setPassword(cursor.getString(2));
                // Adding Teacher to list
                TeacherList.add(Teacher);
            } while (cursor.moveToNext());
        }

        // return Teacher list
        return TeacherList;
    }

    public int getTeachersCount() {
        Log.i(TAG, "TeacherDBHelper.getTeachersCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateTeacher(Teacher Teacher) {
        Log.i(TAG, "TeacherDBHelper.updateTeacher ... "  + Teacher.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, Teacher.getId());
        values.put(COLUMN_NAME, Teacher.getName());
        values.put(COLUMN_PASSWORD, Teacher.getPassword());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(Teacher.getId())});
    }

    public void deleteTeacher(Teacher Teacher) {
        Log.i(TAG, "TeacherDBHelper.updateTeacher ... " + Teacher.getName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(Teacher.getId()) });
        db.close();
    }


    public void deleteAndCreatTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultTeachersIfNeed();
    }

}

