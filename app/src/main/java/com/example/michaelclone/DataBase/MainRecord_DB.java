package com.example.michaelclone.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainRecord_DB {

    final String TABLE_NAME = "carbookRecord";

    public static synchronized MainRecord_DB getInstance(@Nullable Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        if (MichaelClone_DBHelper.michaelCloneDbHelper_Instance == null){
            MichaelClone_DBHelper.michaelCloneDbHelper_Instance = MichaelClone_DBHelper.getInstance(context, name, factory, version);
        }
        return new MainRecord_DB();
    }

    public void MainRecordDB_insert(MainRecord mainRecord){
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("INSERT INTO carbookRecord VALUES (null, "+mainRecord.carbookRecordRepairMode+" , " +"'"+mainRecord.carbookRecordExpendDate+"'"+" , "
                    +mainRecord.carbookRecordIsHidden+" , "+"'"+mainRecord.carbookRecordTotalDistance+"'"+","+"'"+mainRecord.carbookRecordRegTime+"'"+","
                    +"'"+mainRecord.carbookRecordUpdateTime+"'"+");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MainRecordDB_update(MainRecord mainRecord, int _id){
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("UPDATE carbookRecord SET carbookRecordRepairMode = " + mainRecord.carbookRecordRepairMode + ", "
                    + "carbookRecordExpendDate = " + "'"+ mainRecord.carbookRecordExpendDate +"'" + ","
                    + "carbookRecordIsHidden = " + mainRecord.carbookRecordIsHidden + ","
                    + "carbookRecordTotalDistance = " + "'"+ mainRecord.carbookRecordTotalDistance +"'" + ","
                    + "carbookRecordRegTime = " + "'"+ mainRecord.carbookRecordRegTime +"'" + ","
                    + "carbookRecordUpdateTime = " + "'"+ mainRecord.carbookRecordUpdateTime +"'" + ","
                    + "WHERE _id = " + _id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MainRecordDB_delete(MainRecord mainRecord, int _id){
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("UPDATE carbookRecord SET carbookRecordIsHidden = "+ mainRecord.carbookRecordIsHidden +","
                    + "WHERE _id = "+ _id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MainRecord> getMainRecordList(){
        try {
            Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecord", null);
            ArrayList<MainRecord> mainRecords = new ArrayList<>();
            mainRecords = getMainRecordCursor(cursor, mainRecords);
            return mainRecords;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<MainRecord> getMainRecordCursor(Cursor cursor, ArrayList<MainRecord> mainRecords){
        while (cursor.moveToNext()){
            //todo 여기서 cursor.getInt(0)으로 id 값을 받을수 있지 않을까 했는데 안받아져서 일단 뺐다.
            MainRecord mainRecord = new MainRecord(cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6));

            mainRecords.add(mainRecord);
        }
        cursor.close();
        return mainRecords;
    }
}
