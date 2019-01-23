package com.example.a96653.practice;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AUDIODB.db";
    public static final String TABLE_NAME = "audioNew";

    public SQLiteHelper(Context context, String  DATABASE_NAME ) {
        super(context, DATABASE_NAME, null, 1);


    }

    public void queryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(byte[] mp, int length){
        SQLiteDatabase database=getWritableDatabase();
        String sql ="INSERT INTO audioNew (mp) VALUES(?) ";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.bindBlob(1,mp);
        statement.executeInsert();
    }



    public Cursor getData( ){
        SQLiteDatabase database=this.getReadableDatabase();

        return database.rawQuery("SELECT mp FROM  audioNew",null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
