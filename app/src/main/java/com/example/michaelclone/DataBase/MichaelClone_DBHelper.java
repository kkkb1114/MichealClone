package com.example.michaelclone.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MichaelClone_DBHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "MichaelClone.db";
    public static MichaelClone_DBHelper michaelCloneDbHelper_Instance;
    public static SQLiteDatabase writeableDataBase;
    public static SQLiteDatabase readableDataBase;

    public static synchronized MichaelClone_DBHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        if (michaelCloneDbHelper_Instance == null){
            michaelCloneDbHelper_Instance = new MichaelClone_DBHelper(context.getApplicationContext(), DATABASE_NAME, factory, version);
            writeableDataBase = michaelCloneDbHelper_Instance.getWritableDatabase();
            readableDataBase = michaelCloneDbHelper_Instance.getReadableDatabase();
        }
        return michaelCloneDbHelper_Instance;
    }

    public static void closeMichaelClone_DBHelper(){
        try {
            if (michaelCloneDbHelper_Instance != null){
                writeableDataBase.close();
                writeableDataBase = null;
                readableDataBase.close();
                writeableDataBase = null;
                michaelCloneDbHelper_Instance.close();
                michaelCloneDbHelper_Instance = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 생성자에서 db를 전부 create한다..
    public MichaelClone_DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        try {
            Log.i("테이블 생성", "테이블 생성");
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("CREATE TABLE IF NOT EXISTS MainRecord (_id INTEGER PRIMARY KEY AUTOINCREMENT, carbookRecordRepairMode INTEGER, carbookRecordExpendDate TEXT," +
                    "carbookRecordIsHidden INTEGER, carbookRecordTotalDistance REAL, carbookRecordRegTime TEXT, carbookRecordUpdateTime TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS MainRecordItem (_id INTEGER PRIMARY KEY AUTOINCREMENT, carbookRecordId INTEGER, carbookRecordItemCategoryCode TEXT, carbookRecordItemCategoryName TEXT," +
                    "carbookRecordItemExpenseMemo TEXT, carbookRecordItemExpenseCost REAL, carbookRecordItemIsHidden INTEGER, carbookRecordItemRegTime TEXT, " +
                    "carbookRecordItemUpdateTime TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
