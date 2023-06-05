package com.example.Manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public void insert_record(int times, int timeu, int score) {
        //  组装数据
        ContentValues cv = new ContentValues();
        cv.put(DBOpenHelper.TIMES, times);
        cv.put(DBOpenHelper.TIMEU, timeu);
        cv.put(DBOpenHelper.SCORE, score);
        dbWriter.insert(DBOpenHelper.TABLE_NAME, null, cv);
    }

    //  读取数据
    public void select_record() {
        Cursor cursor = dbReader.query(DBOpenHelper.TABLE_NAME, null, null, null, null, null, null);
        try {
            ArrayList<Record> RecordList = new ArrayList<Record>();
            while (cursor.moveToNext()) {
                Record r = new Record();
                r.setTimes(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.TIMES)));
                r.setTimeu(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.TIMEU)));
                r.setScore(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SCORE)));
                RecordList.add(r);
                System.out.println(r.getScore());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class DBOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QBomb";
    public static final String TABLE_NAME = "record";
    public static final int VERSION = 1;
    public static final String TIMEU = "time_used";
    public static final String TIMES = "time_start";
    public static final String SCORE = "score";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                + TIMES + " integer,"
                + TIMEU + " integer,"
                + SCORE + " integer"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
            this.onCreate(sqLiteDatabase);
        }
    }
}

class Record {
    private int times;
    private int timeu;
    private int score;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getTimeu() {
        return timeu;
    }

    public void setTimeu(int timeu) {
        this.timeu = timeu;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



}