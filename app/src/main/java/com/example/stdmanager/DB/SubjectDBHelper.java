package com.example.stdmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Subject;
import com.example.stdmanager.models.Teacher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SubjectDBHelper extends SQLiteOpenHelper
{
    private static final String TABLE_NAME = "SUBJECT";
    private static final String TAG = "SUBJECT SQLite";
    private static final String COLUMN_mamh = "MAMH";
    private static final String COLUMN_tenmh = "TENMH";
    private static final String COLUMN_hocky = "HOCKY";
    private static final String COLUMN_namhoc = "NAMHOC";
    private static final String COLUMN_heso = "HESO";


    public SubjectDBHelper(Context context)
    {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    // Creating Tables
    /*
    1 . MaMH   int
    2 . TenMH  text
    3 . Hocky  int
    4 . Namhoc text
    5 . Heso   int

     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s TEXT , %s INTEGER  )",
                                TABLE_NAME, COLUMN_mamh,                       COLUMN_tenmh,COLUMN_hocky,COLUMN_namhoc,COLUMN_heso);
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existe

        // Create tables again
//        onCreate(db);
    }

    public boolean AddSubject(Subject subject) {
        Log.i(TAG, "AddSubject " + subject.getTenMH());
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_tenmh, subject.getTenMH());
            values.put(COLUMN_heso, subject.getHeSo());
            values.put(COLUMN_hocky, subject.getHocKy());
            values.put(COLUMN_namhoc,subject.getNamHoc());
            // Inserting Row
            db.insert(TABLE_NAME, null, values);
            // Closing database connection
            db.close();
            return true;
        }catch(Exception e )
        {
            e.printStackTrace();
            return false;
        }

    }

    public boolean update(Subject subject) {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        /*Step 2*/
        int id = subject.getMaMH();

        String tenMH = subject.getTenMH();
        int hocKy = subject.getHocKy();
        int heSo = subject.getHeSo();
        String namHoc = subject.getNamHoc();

        /*Step 3*/
        ContentValues values = new ContentValues();
        values.put(COLUMN_tenmh, tenMH);
        values.put(COLUMN_hocky, hocKy);
        values.put(COLUMN_heso, heSo);
        values.put(COLUMN_namhoc, namHoc);



        /*Step 4*/
        try{
            sqLiteDatabase.update(TABLE_NAME, values, COLUMN_mamh + " = ?",
                    new String[]{String.valueOf(id)});
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }


    public boolean deleteSubject(Subject subject)
    {
        /*Step 1*/
        try{
            int id = subject.getMaMH();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            /*Step 2*/
            sqLiteDatabase.delete(TABLE_NAME, COLUMN_mamh + " = ?",
                    new String[]{ String.valueOf(id) } );
            sqLiteDatabase.close();
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }



        // If Subject table has no data
    // default, Insert 3 records.

    public void createDefaultSubject()  {

            Subject subject1 = new Subject(0,"Toan",1,2,"2021-2022");
            Subject subject2 = new Subject(0,"Van",1,2,"2021-2022");
            Subject subject3 = new Subject(0,"Anh",1,2,"2021-2022");
            this.AddSubject(subject1);
            this.AddSubject(subject2);
            this.AddSubject(subject3);


    }

    public ArrayList<Subject> getAllSubjects() {
        Log.i(TAG, "SubjectDBHelper.getAllTeachers ... " );

        ArrayList<Subject> subjectList = new ArrayList<Subject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{
                    Subject subject = new Subject();
                    subject.setMaMH(Integer.parseInt(cursor.getString(0)));
                    subject.setTenMH(cursor.getString(1));
                    subject.setHocKy(Integer.parseInt(cursor.getString(2)));
                    subject.setNamHoc(cursor.getString(3));
                    subject.setHeSo(Integer.parseInt(cursor.getString(4)));
                    subjectList.add(subject);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "SubjectDBHelper.getAllSubjects error ");
                }

            } while (cursor.moveToNext());
        }

        // return Subject list
        return subjectList;
    }

    public SQLiteDatabase open()
    {
        return this.getWritableDatabase();
    }

    public void deleteAndCreateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultSubject();
    }




}
