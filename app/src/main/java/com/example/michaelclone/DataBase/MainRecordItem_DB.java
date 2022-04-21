package com.example.michaelclone.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MainRecordItem_DB extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "MainRecord.db";
    static final String TABLE_NAME = "carbookRecordItem";

    // 테이블 항목
    public final String COL_1 = "carbookRecordId";
    public final String COL_2 = "carbookRecordItemCategoryCode";
    public final String COL_3 = "carbookRecordItemCategoryName";
    public final String COL_4 = "carbookRecordItemExpenseMemo";
    public final String COL_5 = "carbookRecordItemExpenseCost";
    public final String COL_6 = "carbookRecordItemIsHidden";
    public final String COL_7 = "carbookRecordItemRegTime";
    public final String COL_8 = "carbookRecordItemUpdateTime";

    public MainRecordItem_DB(@Nullable Context context, int version) {
        super(context, DATABASE_NAME,null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(carbookRecordId INTEGER, " +
                "carbookRecordItemCategoryCode TEXT, " +
                "carbookRecordItemCategoryName TEXT, " +
                "carbookRecordItemExpenseMemo TEXT, " +
                "carbookRecordTotalDistance REAL DEFAULT 0.0," +
                "carbookRecordItemIsHidden INTEGER DEFAULT 0," +
                "carbookRecordItemRegTime TEXT," +
                "carbookRecordItemUpdateTime, TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    // 데이터베이스 추가하기 insert
    public boolean insertData(int carbookRecordId, String carbookRecordItemCategoryCode, String carbookRecordItemCategoryName, String carbookRecordItemExpenseMemo,
                              double carbookRecordTotalDistance, int carbookRecordItemIsHidden, String carbookRecordItemRegTime, String carbookRecordItemUpdateTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,carbookRecordId);
        contentValues.put(COL_2,carbookRecordItemCategoryCode);
        contentValues.put(COL_3,carbookRecordItemCategoryName);
        contentValues.put(COL_4,carbookRecordItemExpenseMemo);
        contentValues.put(COL_5,carbookRecordTotalDistance);
        contentValues.put(COL_6,carbookRecordItemIsHidden);
        contentValues.put(COL_7,carbookRecordItemRegTime);
        contentValues.put(COL_8,carbookRecordItemUpdateTime);
        long result = db.insert(TABLE_NAME, null, contentValues);
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

    //데이터베이스 수정하기 (항목 테이블은 기록 테이블 id, 항목 분류 코드를 이용하며 항목 코드는 바)
    public boolean updateData(String carbookRecordId, String carbookRecordItemCategoryCode, String phone, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,carbookRecordId);
        contentValues.put(COL_2,carbookRecordItemCategoryCode);
        contentValues.put(COL_3,phone);
        contentValues.put(COL_4,address);
        db.update(TABLE_NAME,contentValues,"carbookRecordId = ? AND carbookRecordItemCategoryCode = ?", new String[] { carbookRecordId , carbookRecordItemCategoryCode});
        return true;
    }
}
