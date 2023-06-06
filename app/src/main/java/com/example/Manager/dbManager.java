package com.example.Manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myfunctions.Record;

import java.util.ArrayList;
import java.util.List;


public class dbManager {
    private Context context;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;
    private static dbManager instance;

    public dbManager(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
        // 创建and/or打开一个数据库
        dbReader = dbOpenHelper.getReadableDatabase();
        dbWriter = dbOpenHelper.getWritableDatabase();
    }

    //getInstance单例
    public static synchronized dbManager getInstance(Context context) {
        if (instance == null) {
            instance = new dbManager(context);
        }
        return instance;
    }

    // 添加到数据库
    public void insert_record(String times, String timeu, String score) {
        //  组装数据
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.TIMES, times);
        cv.put(DBOpenHelper.TIMEU, timeu);
        cv.put(DBOpenHelper.SCORE, score);
        dbWriter.insert(DBOpenHelper.TABLE_NAME, null, cv);
    }

    //  读取数据
    public ArrayList<Record> select_record() {
        Cursor cursor = dbReader.query(DBOpenHelper.TABLE_NAME, null, null, null, null, null, null);
        try {
            ArrayList<Record> RecordList = new ArrayList<Record>();
            while (cursor.moveToNext()) {
                Record r = new Record();
                r.setTimes(cursor.getString(cursor.getColumnIndex(DBOpenHelper.TIMES)));
                r.setTimeu(cursor.getString(cursor.getColumnIndex(DBOpenHelper.TIMEU)));
                r.setScore(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SCORE)));
                RecordList.add(r);
            }
            return RecordList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetDB(){
        dbWriter.execSQL(" DROP TABLE IF EXISTS "+DBOpenHelper.TABLE_NAME);
        dbWriter.execSQL(" CREATE TABLE "+DBOpenHelper.TABLE_NAME+ " ( time_start text,time_used text,score text )");
    }
}

class DBOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QBomb";
    public static final String TABLE_NAME = "record";
    public static final int VERSION = 2;
    public static final String TIMEU = "time_used";
    public static final String TIMES = "time_start";
    public static final String SCORE = "score";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                + TIMES + " text,"
                + TIMEU + " text,"
                + SCORE + " text"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            this.onCreate(sqLiteDatabase);
        }
    }
}

