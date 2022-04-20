package com.example.michaelclone.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MainRecord_DB extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "MainRecord.db";
    static final String TABLE_NAME = "carbookRecord";

    // 테이블 항목
    public final String COL_1 = "_id";
    public final String COL_2 = "carbookRecordRepairMode";
    public final String COL_3 = "carbookRecordExpendDate";
    public final String COL_4 = "carbookRecordIsHidden";
    public final String COL_5 = "carbookRecordTotalDistance";
    public final String COL_6 = "carbookRecordRegTime";
    public final String COL_7 = "carbookRecordUpdateTime";

    public MainRecord_DB(@Nullable Context context, int version) {
        super(context, DATABASE_NAME,null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "carbookRecordRepairMode INTEGER DEFAULT 0, " +
                "carbookRecordExpendDate TEXT, " +
                "carbookRecordIsHidden INTEGER DEFAULT 0, " +
                "carbookRecordTotalDistance REAL DEFAULT 0.0," +
                "carbookRecordRegTime TEXT," +
                "carbookRecordUpdateTime TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    // 데이터베이스 추가하기 insert
    public boolean insertData(int carbookRecordRepairMode, String carbookRecordExpendDate, int carbookRecordIsHidden, double carbookRecordTotalDistance,
                              String carbookRecordRegTime, String carbookRecordUpdateTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,carbookRecordRepairMode);
        contentValues.put(COL_3,carbookRecordExpendDate);
        contentValues.put(COL_4,carbookRecordIsHidden);
        contentValues.put(COL_5,carbookRecordTotalDistance);
        contentValues.put(COL_6,carbookRecordRegTime);
        contentValues.put(COL_7,carbookRecordUpdateTime);
        long result = db.insert(TABLE_NAME, null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    //데이터베이스 항목 읽어오기 Read
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }

    // 데이터베이스 삭제하기
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_id = ? ",new String[]{id});
    }

    //데이터베이스 수정하기
    public boolean updateData(String id, String name, String phone, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone);
        contentValues.put(COL_4,address);
        db.update(TABLE_NAME,contentValues,"_id = ?", new String[] { id });
        return true;
    }
}
