package com.example.michaelclone.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            // 트랜젝션 시작 (이렇게 트랜젝션을 이용하여 명시적으로 쿼리문을 실행할 것이라 해줘야 쿼리문이 동작하는 도중에 다른 쿼리문에 끼어들일이 없다.)
            db.beginTransaction();
            db.execSQL("INSERT INTO carbookRecord VALUES (null, "+mainRecord.carbookRecordRepairMode+" , " +"'"+mainRecord.carbookRecordExpendDate+"'"+" , "
                    +mainRecord.carbookRecordIsHidden+" , "+"'"+mainRecord.carbookRecordTotalDistance+"'"+","+"'"+mainRecord.carbookRecordRegTime+"'"+","
                    +"'"+mainRecord.carbookRecordUpdateTime+"'"+");");
            // 위 쿼리문 실행 했다는 것을 알림.
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (db != null){
                // 위 쿼리 실행이 끝난후 해당 동작이 끝났다는것을 알림.
                db.endTransaction();
            }
        }
    }

    public void MainRecordDB_update(MainRecord mainRecord, int _id){
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            db.beginTransaction();
            db.execSQL("UPDATE carbookRecord SET carbookRecordRepairMode = " + mainRecord.carbookRecordRepairMode + ", "
                    + "carbookRecordExpendDate = " + "'"+ mainRecord.carbookRecordExpendDate +"'" + ","
                    + "carbookRecordIsHidden = " + mainRecord.carbookRecordIsHidden + ","
                    + "carbookRecordTotalDistance = " + "'"+ mainRecord.carbookRecordTotalDistance +"'" + ","
                    + "carbookRecordRegTime = " + "'"+ mainRecord.carbookRecordRegTime +"'" + ","
                    + "carbookRecordUpdateTime = " + "'"+ mainRecord.carbookRecordUpdateTime +"'" + ","
                    + "WHERE _id = " + _id);
            // 위 쿼리문 실행 했다는 것을 알림.
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (db != null){
                // 위 쿼리 실행이 끝난후 해당 동작이 끝났다는것을 알림.
                db.endTransaction();
            }
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

    /**
     * - 기록 저장시 항목 데이터 저장할때 넣어줄 기록 id값 추출 쿼리 메소드
     * - 이 메소드는 무조건 carbookRecord를 저장 후에 실행할 예정이기에 carbookRecord중 id값이 가장 마지막인 데이터를 id값만 가지고 나온다.
     * - DESC: 역순을 뜻한다.
     * **/
    public int getCarbookRecordLastId(){
        SQLiteDatabase db = MichaelClone_DBHelper.readableDataBase;
        ArrayList<Integer> lastIdList = new ArrayList<>();
        try {
            db.beginTransaction();
            Cursor cursor = db.rawQuery("SELECT _id FROM carbookRecord ORDER BY ROWID DESC LIMIT 1", null);
            db.setTransactionSuccessful();
            while (cursor.moveToNext()){
                lastIdList.add(cursor.getInt(0));
                Log.i("lastIdList", "22");
            }
            //int lastId = cursor.getInt(0);
            Log.i("lastIdList", String.valueOf(lastIdList.get(0)));
            return lastIdList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        // 오토 생성 id값은 1부터 시작하니 0은 에러로 뜻한것으로 지정
        return 0;
    }

    private ArrayList<MainRecord> getMainRecordCursor(Cursor cursor, ArrayList<MainRecord> mainRecords){
        while (cursor.moveToNext()){
            MainRecord mainRecord = new MainRecord(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5));

            mainRecords.add(mainRecord);
        }
        cursor.close();
        return mainRecords;
    }
}
