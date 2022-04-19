package com.example.stdmanager.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stdmanager.models.Event;

import java.util.ArrayList;

public class EventDBHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME="EVENT";
    private static final String TAG= "EVENT SQLLite";
    private  static final String COLUMN_idEvent="MASUKIEN";
    private static final String COLUMN_nameEvent="TENSUKIEN";
    private static final String COLUMN_startTime="BATDAU";
    private static final String COLUMN_endTime="KETTHUC";
    private static final String COLUMN_day="NGAYTHANG";
    private static final String COLUMN_place="DIADIEM";

    public EventDBHelper(android.content.Context context)
    {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase litedb) {
        String CREATE_TABLE= String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT , %s TEXT , %s TEXT  )",
                TABLE_NAME, COLUMN_idEvent,COLUMN_nameEvent,COLUMN_startTime,COLUMN_endTime,COLUMN_day,COLUMN_place);
        litedb.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase litedb, int oldVersion, int newVersiopn) {

    }
    public boolean AddEvent(Event event){
        Log.i(TAG,"ADD EVENT " + event.getNameEvent());
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_nameEvent,event.getNameEvent());
        values.put(COLUMN_startTime,event.getStartTime());
        values.put(COLUMN_endTime,event.getEndTime());
        values.put(COLUMN_day,event.getDay());
        values.put(COLUMN_place,event.getPlace());

        try {
            sqLiteDatabase.insert(TABLE_NAME, null,values);
            sqLiteDatabase.close();
            return true;
        }catch(Exception ex){
            return false;
        }

    }
    public void createDefaultEvent(){
        Event event1=new Event(0,"Họp phụ huynh tổng kết học kỳ","07:00","11:00","15/04/2022","Phòng học C2");
        Event event2=new Event(0,"Hội trại 26-3","07:00","17:00","26/03/2022","Phòng học C2");
        Event event3=new Event(0,"Va lung tung 14-2","00:00","24:00","14/02/2022","Phòng học C2");

        this.AddEvent(event1);
        this.AddEvent(event2);
        this.AddEvent(event3);
    }
    public ArrayList<Event> getAllEvents(){
        Log.i(TAG, "getAllEvent " );
        ArrayList<Event> eventList= new ArrayList<Event>();
        String selectQuery="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase database= this.getReadableDatabase();
        Cursor cursor= database.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                try {
                    Event event= new Event();
                    event.setIdEvent(Integer.parseInt(cursor.getString(0)));
                    event.setNameEvent(cursor.getString(1));
                    event.setStartTime(cursor.getString(2));
                    event.setEndTime(cursor.getString(3));
                    event.setDay(cursor.getString(4));
                    event.setPlace(cursor.getString(5));
                    eventList.add(event);
                }catch (Exception exception){
                    Log.i(TAG,"getALLEvent Error");
                }

            }while (cursor.moveToNext());
        }
        return eventList;
    }
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }

    public void deletedAndCreateTable(){
        SQLiteDatabase database =this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(database);
        createDefaultEvent();
    }
    public boolean UpdateEvent(Event event){
        Log.i(TAG,"UPDATE EVENT " + event.getNameEvent());
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_nameEvent,event.getNameEvent());
        values.put(COLUMN_startTime,event.getStartTime());
        values.put(COLUMN_endTime,event.getEndTime());
        values.put(COLUMN_day,event.getDay());
        values.put(COLUMN_place,event.getPlace());

        try {
            sqLiteDatabase.update(TABLE_NAME, values, "MASUKIEN= ?", new String[]{event.getIdEvent()+""});
            sqLiteDatabase.close();
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public boolean DeleteEvent(Event event){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "MASUKIEN= ?", new String[]{event.getIdEvent()+""}) >0;
    }
}
