package com.example.stdmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stdmanager.models.ReportScore;
import com.example.stdmanager.models.ReportTotal;
import com.example.stdmanager.models.Score;
import com.example.stdmanager.models.Student;

import java.util.ArrayList;

public class ScoreDBHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME = "SCORES";
    public static final String TAG = "SCORE SQLite";
    public static final String COLUMN_mahs = "MAHS";
    public static final String COLUMN_mamh = "MAMH";
    public static final String COLUMN_diem = "DIEM";
    private SQLiteDatabase db;

    public ScoreDBHelper(Context context)
    {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    // Creating Tables
    /*
    1 . MAMHS   int
    2 . MAMH  text
    3 . DIEM  int
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ( %s INTEGER, %s INTEGER , %s REAL  )",
                        TABLE_NAME, COLUMN_mahs, COLUMN_mamh,COLUMN_diem);
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void add(Score score) {
        Log.i(TAG, "AddScore HS:" + score.getMaHS() + "|| MH: " + score.getMaMH());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_mahs, score.getMaHS());
        values.put(COLUMN_mamh, score.getMaMH());
        values.put(COLUMN_diem, score.getDiem());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void update(int student, int subject, double score) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COLUMN_mahs, student);
        values.put(COLUMN_mamh, subject);
        values.put(COLUMN_diem, score);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "MAHS=? and MAMH = ?", new String[]{String.valueOf(student), String.valueOf(subject)});
        db.close();
    }

    public ArrayList<Score> getAll() {
        Log.i(TAG, "Score.getAll ... " );

        ArrayList<Score> scores = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{
                    Score score = new Score();
                    score.setMaHS(Integer.parseInt(cursor.getString(0)));
                    score.setMaMH(Integer.parseInt(cursor.getString(1)));
                    score.setDiem(Double.parseDouble(cursor.getString(2)));
                    scores.add(score);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "ScoreDHelper.getAll error ");
                }

            } while (cursor.moveToNext());
        }
        return scores;
    }

    public ArrayList<Score> getStudentAndSubject(String student, String subject) {
        Log.i(TAG, "Score.getAll ... " );

        ArrayList<Score> scores = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE MAHS = '" + student + "' AND MAMH = '" + subject + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{
                    com.example.stdmanager.models.Score score = new com.example.stdmanager.models.Score();
                    score.setMaHS(Integer.parseInt(cursor.getString(0)));
                    score.setMaMH(Integer.parseInt(cursor.getString(1)));
                    score.setDiem(Double.parseDouble(cursor.getString(2)));
                    scores.add(score);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "ScoreDHelper.getAll error ");
                }

            } while (cursor.moveToNext());
        }
        return scores;
    }

    public ArrayList<ReportScore> getReportScore() {
        Log.i(TAG, "Score.getAll ... " );

        ArrayList<ReportScore> scores = new ArrayList<>();
        // Select All Query
        String selectQuery = String.format("SELECT s.id, ROUND(CAST(SUM (sc.DIEM * sj.HESO) AS float)  /  CAST(SUM ( sj.HESO)  AS float ) , 2) AS DIEM " +
                "FROM STUDENT s " +
                "INNER JOIN SCORES sc " +
                "ON sc.MAHS = s.id " +
                "INNER JOIN SUBJECT sj " +
                "ON sj.MAMH = sc.MAMH " +
                "WHERE s.id > 0 " +
                "GROUP BY s.id");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{
                    ReportScore score = new ReportScore();
                    score.setMaHS(Integer.parseInt(cursor.getString(0)));
                    score.setDiem(Double.valueOf(cursor.getString(1)));
                    scores.add(score);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "ScoreDHelper.getAll error ");
                }

            } while (cursor.moveToNext());
        }
        return scores;
    }

    public ArrayList<ReportTotal> getReportCountByScore(int MAMH) {
        Log.i(TAG, "Score.getAll ... " );

        ArrayList<ReportTotal> scores = new ArrayList<>();
        // Select All Query
        String selectQuery = String.format("SELECT sc.DIEM, COUNT(sc.MAHS) AS AMOUNT " +
                "FROM SCORES sc " +
                "WHERE sc.MAMH = %d " +
                "GROUP BY sc.DIEM", MAMH);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try{
                    ReportTotal score = new ReportTotal();
                    score.setName(String.valueOf(cursor.getString(0)));
                    score.setValue(Double.valueOf(cursor.getString(1)));
                    scores.add(score);
                }catch (Exception exception)
                {
                    Log.i(TAG,  "ScoreDHelper.getAll error ");
                }

            } while (cursor.moveToNext());
        }
        return scores;
    }


    private void createDefaultRecords()
    {
        this.add(new Score(8, 1, 5));
        this.add(new Score(6, 1, 5));
        this.add(new Score(6, 2, 10));
        this.add(new Score(6, 3, 8));
        this.add(new Score(8, 2, 1));
        this.add(new Score(2, 3, 7));
        this.add(new Score(1, 2, 4));
        this.add(new Score(3, 1, 4));
        this.add(new Score(7, 1, 1));
        this.add(new Score(4, 2, 10));
        this.add(new Score(1, 2, 10));
        this.add(new Score(2, 2, 3));
        this.add(new Score(3, 2, 3));
        this.add(new Score(7, 2, 7));
        this.add(new Score(7, 3, 3));
        this.add(new Score(8, 3, 10));
        this.add(new Score(5, 2, 7));
        this.add(new Score(2, 1, 3));
        this.add(new Score(1, 1, 10));
        this.add(new Score(4, 1, 2));
        this.add(new Score(4, 3, 9));
        this.add(new Score(5, 3, 2));
        this.add(new Score(5, 1, 5));
        this.add(new Score(3, 3, 7));
    }

    public void deleteAndCreateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultRecords();
    }









}
