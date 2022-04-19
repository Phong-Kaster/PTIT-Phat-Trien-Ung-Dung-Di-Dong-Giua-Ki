package com.example.stdmanager.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.stdmanager.models.Teacher;
import com.example.stdmanager.models.Grade;

import java.util.ArrayList;
import java.util.List;

public class GradeOpenHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "GRADE";
    private static final  String REFERENCED_TABLE_NAME = "TEACHER";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TEACHER_ID = "teacherId";

    public GradeOpenHelper(@Nullable Context context) {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("" +
                "CREATE TABLE %s(" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT, " +
                "%s INTEGER REFERENCES ["+REFERENCED_TABLE_NAME+"](id) ON DELETE CASCADE NOT NULL  )",
                TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_TEACHER_ID);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    /**
     * @author Phong-Kaster
     * @param grade | object | the grade which is added to TABLE_GRADE
     */
    public void create(Grade grade)
    {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        /*Step 2*/
        String name = grade.getName();
        int teacherId = grade.getTeacherId();


        /*Step 3*/
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_TEACHER_ID, teacherId);

        /*Step 4*/
        sqLiteDatabase.insert(TABLE_NAME,null, contentValues);
        sqLiteDatabase.close();
    }

    /**
     * @author Phong-Kaster
     * @param grade | object | the grade which is deleted from TABLE_GRADE
     */
    public void delete(Grade grade)
    {
        /*Step 1*/
        int id = grade.getId();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        /*Step 2*/
        sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{ String.valueOf(id)} );
        sqLiteDatabase.close();
    }

    public void update(Grade grade)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int id = grade.getId();
        String name = grade.getName();
        int teacherId = grade.getTeacherId();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TEACHER_ID, teacherId);

        // updating row
        sqLiteDatabase.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{ String.valueOf(id) } );
    }

    public ArrayList<Grade> retrieveAllGrades()
    {
        /*Step 1*/
        ArrayList<Grade> objects = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        /*Step 2*/
        String query = "SELECT * FROM grade";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        /*Step 3*/
        if( cursor.moveToFirst() )
        {
            do
            {
                Grade grade = new Grade();

                grade.setId( Integer.parseInt( cursor.getString(0) ) );
                grade.setName( cursor.getString(1));
                grade.setTeacherId( Integer.parseInt( cursor.getString(2) ) );

                objects.add(grade);
            }while( cursor.moveToNext() );
        }

        return objects;
    }

    public String retrieveNameById(int id)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT name FROM grade";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String name = "";
        if( cursor.moveToFirst() )
        {
            name = cursor.getString(0);
        }
        return name;
    }

    /**
     *
     * @param teacherId which is teacher Id
     * @return grade id which teacher having @id is host
     */
    public String retriveIdByTeachId(String teacherId)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = String.format("SELECT g.id FROM grade g WHERE g.teacherId = %s", teacherId);

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String id = "";
        if( cursor.moveToFirst() )
        {
            id =  cursor.getString(0);
        }
        return id;
    }

    /**
     * @author Phong-Kaster
     * retrieve number of grade
     * @return int quantity
     */
    private int count()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int quantity = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return quantity;
    }

    /**
     * @author Phong-Kaster
     * create default records if there is nothing in Grade table
     */
    public void createDefaultRecords()
    {
        /*Step 1*/
        int gradeQuantity = this.count();
        if( gradeQuantity != 0)
            return;

        /*Step 2*/
        Grade grade1 = new Grade(1,"D18CQCN01", 1);
        Grade grade2 = new Grade(2,"D18CQCN02", 2);
        Grade grade3 = new Grade(3,"D18CQCN03", 3);

        this.create(grade1);
        this.create(grade2);
        this.create(grade3);
    }

    public void deleteAndCreatTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultRecords();
    }
}
