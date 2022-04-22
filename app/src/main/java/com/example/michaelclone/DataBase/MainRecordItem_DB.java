package com.example.michaelclone.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MainRecordItem_DB{
    static final String DATABASE_NAME = "MainRecord.db";
    static final String TABLE_NAME = "carbookRecordItem";

    public static MainRecordItem_DB mainRecordItemDB_Instance;

    // 테이블 항목
    public final String COL_1 = "carbookRecordId";
    public final String COL_2 = "carbookRecordItemCategoryCode";
    public final String COL_3 = "carbookRecordItemCategoryName";
    public final String COL_4 = "carbookRecordItemExpenseMemo";
    public final String COL_5 = "carbookRecordItemExpenseCost";
    public final String COL_6 = "carbookRecordItemIsHidden";
    public final String COL_7 = "carbookRecordItemRegTime";
    public final String COL_8 = "carbookRecordItemUpdateTime";

    // 메인 DB가 생성되어 있지 않으면 생성한다.
    public static synchronized MainRecordItem_DB getInstance(@Nullable Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        if (MichaelClone_DBHelper.michaelCloneDbHelper_Instance == null){
            MichaelClone_DBHelper.michaelCloneDbHelper_Instance = MichaelClone_DBHelper.getInstance(context, name, factory, version);
        }
        return mainRecordItemDB_Instance;
    }

    // 데이터베이스 추가하기 insert
    public boolean insertData(int carbookRecordId, String carbookRecordItemCategoryCode, String carbookRecordItemCategoryName, String carbookRecordItemExpenseMemo,
                              String carbookRecordItemExpenseCost, int carbookRecordItemIsHidden, String carbookRecordItemRegTime, String carbookRecordItemUpdateTime){
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            db.beginTransaction();
            db.execSQL("INSERT INTO MainRecordItem VALUES (null, )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
