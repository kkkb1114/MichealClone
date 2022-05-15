package com.example.michaelclone.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CarbookRecord_DB {

    final String TABLE_NAME = "carbookRecord";

    public static synchronized CarbookRecord_DB getInstance(@Nullable Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        if (MichaelClone_DBHelper.michaelCloneDbHelper_Instance == null){
            MichaelClone_DBHelper.michaelCloneDbHelper_Instance = MichaelClone_DBHelper.getInstance(context, name, factory, version);
        }
        return new CarbookRecord_DB();
    }

    public void MainRecordDB_insert(CarbookRecord carbookRecord){
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            // 트랜젝션 시작 (이렇게 트랜젝션을 이용하여 명시적으로 쿼리문을 실행할 것이라 해줘야 쿼리문이 동작하는 도중에 다른 쿼리문에 끼어들일이 없다.)
            db.beginTransaction();
            db.execSQL("INSERT INTO carbookRecord VALUES (null, "+ carbookRecord.carbookRecordRepairMode+" , " +"'"+ carbookRecord.carbookRecordExpendDate+"'"+" , "
                    + carbookRecord.carbookRecordIsHidden+" , "+"'"+ carbookRecord.carbookRecordTotalDistance+"'"+","+"'"+ carbookRecord.carbookRecordRegTime+"'"+","
                    +"'"+ carbookRecord.carbookRecordUpdateTime+"'"+");");
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

    public void MainRecordDB_update(CarbookRecord carbookRecord, int _id){
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            db.beginTransaction();
            db.execSQL("UPDATE carbookRecord SET carbookRecordRepairMode = " + carbookRecord.carbookRecordRepairMode + ", "
                    + "carbookRecordExpendDate = " + "'"+ carbookRecord.carbookRecordExpendDate +"'" + ","
                    + "carbookRecordIsHidden = " + carbookRecord.carbookRecordIsHidden + ","
                    + "carbookRecordTotalDistance = " + "'"+ carbookRecord.carbookRecordTotalDistance +"'" + ","
                    + "carbookRecordRegTime = " + "'"+ carbookRecord.carbookRecordRegTime +"'" + ","
                    + "carbookRecordUpdateTime = " + "'"+ carbookRecord.carbookRecordUpdateTime +"'" + ","
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

    public void MainRecordDB_delete(CarbookRecord carbookRecord, int _id){
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("UPDATE carbookRecord SET carbookRecordIsHidden = "+ carbookRecord.carbookRecordIsHidden +","
                    + "WHERE _id = "+ _id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CarbookRecord> getMainRecordList(){
        try {
            Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecord", null);
            ArrayList<CarbookRecord> CarbookRecords = new ArrayList<>();
            CarbookRecords = getMainRecordCursor(cursor, CarbookRecords);
            return CarbookRecords;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CarbookRecord selectCarbookRecord(int _id){
        try {
            Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecord WHERE _id = "+ _id, null);
            CarbookRecord carbookRecords;
            carbookRecords = getMainRecordCursorSingle(cursor);
            return carbookRecords;
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
            }
            //int lastId = cursor.getInt(0);
            return lastIdList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        // 오토 생성 id값은 1부터 시작하니 0은 에러로 뜻한것으로 지정
        return 0;
    }

    private ArrayList<CarbookRecord> getMainRecordCursor(Cursor cursor, ArrayList<CarbookRecord> CarbookRecords){
        while (cursor.moveToNext()){
            //todo 여기서 cursor.getInt(0)으로 id 값을 받을수 있지 않을까 했는데 안받아져서 일단 뺐다.
            CarbookRecord carbookRecord = new CarbookRecord(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6));

            CarbookRecords.add(carbookRecord);
        }
        cursor.close();
        return CarbookRecords;
    }

    private CarbookRecord getMainRecordCursorSingle(Cursor cursor){
        CarbookRecord carbookRecord = null;
        while (cursor.moveToNext()){
            //todo 여기서 cursor.getInt(0)으로 id 값을 받을수 있지 않을까 했는데 안받아져서 일단 뺐다.
            carbookRecord = new CarbookRecord(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6));
        }
        cursor.close();
        return carbookRecord;
    }

    public ArrayList<MainRecordPage> getMainRecordData(){
        SQLiteDatabase db = MichaelClone_DBHelper.readableDataBase;
        ArrayList<MainRecordPage> mainRecordPageArrayList = new ArrayList<>();
        try {
            db.beginTransaction();
            Cursor cursor = db.rawQuery("SELECT A.*, B.count, B.totalCost, B.carbookRecordItemCategoryName, B.carbookRecordItemExpenseMemo, substr(carbookRecordExpendDate, 0,7) as month," +
                    "substr(carbookRecordExpendDate, 0,5) as year FROM carbookRecord as A JOIN (SELECT carbookRecordId, carbookRecordItemCategoryName, carbookRecordItemExpenseMemo," +
                    "COUNT(*) as 'count', SUM(carbookRecordItemExpenseCost) as 'totalCost' FROM carbookRecordItem GROUP BY carbookRecordId) as B ON (A._id = B.carbookRecordId) ORDER BY carbookRecordExpendDate DESC", null);

            while (cursor.moveToNext()){
                MainRecordPage mainRecordPage = new MainRecordPage(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getDouble(4),
                        cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12));

                mainRecordPageArrayList.add(mainRecordPage);
            }
            cursor.close();
            return mainRecordPageArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return null;
    }
}