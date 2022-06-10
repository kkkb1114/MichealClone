package com.example.michaelclone.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CarbookRecordItem_DB {

    // 메인 DB가 생성되어 있지 않으면 생성한다.
    public static synchronized CarbookRecordItem_DB getInstance(@Nullable Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        if (MichaelClone_DBHelper.michaelCloneDbHelper_Instance == null) {
            MichaelClone_DBHelper.michaelCloneDbHelper_Instance = MichaelClone_DBHelper.getInstance(context, name, factory, version);
        }
        return new CarbookRecordItem_DB();
    }

    // 데이터베이스 추가하기 insert
    public void MainRecordItemDB_insert(CarbookRecordItem carbookRecordItem) {
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            db.beginTransaction();
            db.execSQL("INSERT INTO carbookRecordItem VALUES (null, " + carbookRecordItem.carbookRecordId + " , " + "'" + carbookRecordItem.carbookRecordItemCategoryCode + "'" + " , "
                    + "'" + carbookRecordItem.carbookRecordItemCategoryName + "'" + ", " + "'" + carbookRecordItem.carbookRecordItemExpenseMemo + "'" + ", "
                    + "'" + carbookRecordItem.carbookRecordItemExpenseCost + "'" + " , " + carbookRecordItem.carbookRecordItemIsHidden + " , " + "'" + carbookRecordItem.carbookRecordItemRegTime + "'"
                    + " , " + "'" + carbookRecordItem.carbookRecordItemUpdateTime + "'" + " );");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
            }
        }
    }

    public void MainRecordItemDB_update(CarbookRecordItem carbookRecordItem, int _id) {
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            db.beginTransaction();
            db.execSQL("UPDATE carbookRecordItem SET _id = " + _id + ", carbookRecordId = " + carbookRecordItem.carbookRecordId + ", "
                    + "carbookRecordItemCategoryCode = " + "'" + carbookRecordItem.carbookRecordItemCategoryCode + "'" + ","
                    + "carbookRecordItemCategoryName = " + "'" + carbookRecordItem.carbookRecordItemCategoryName + "'" + ","
                    + "carbookRecordItemExpenseMemo = " + "'" + carbookRecordItem.carbookRecordItemExpenseMemo + "'" + ","
                    + "carbookRecordItemExpenseCost = " + carbookRecordItem.carbookRecordItemExpenseCost + ","
                    + "carbookRecordItemIsHidden = " + carbookRecordItem.carbookRecordItemIsHidden + ","
                    + "carbookRecordItemRegTime = " + "'" + carbookRecordItem.carbookRecordItemRegTime + "'" + ","
                    + "carbookRecordItemUpdateTime = " + "'" + carbookRecordItem.carbookRecordItemUpdateTime + "'"
                    + " WHERE _id = " + _id);
            // 위 쿼리문 실행 했다는 것을 알림.
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                // 위 쿼리 실행이 끝난후 해당 동작이 끝났다는것을 알림.
                db.endTransaction();
            }
        }
    }

    public void MainRecordItemDB_delete(int _id) {
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("UPDATE carbookRecordItem SET carbookRecordItemIsHidden = " + 1 + " WHERE _id = " + _id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CarbookRecordItem> getMainRecordItemList() {
        try {
            Cursor cursor;
            cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecordItem WHERE carbookRecordItemIsHidden = " + 0, null);
            return getMainRecordItemCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CarbookRecordItem> getMainRecordItemItemArrayList(int mainRecordItemCarbookRecordId) {
        try {
            Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecordItem WHERE carbookRecordId = " + mainRecordItemCarbookRecordId + " AND carbookRecordItemIsHidden = " + 0, null);
            return getMainRecordItemCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<CarbookRecordItem> getMainRecordItemCursor(Cursor cursor) {
        ArrayList<CarbookRecordItem> carbookRecordItemArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            CarbookRecordItem carbookRecordItem = new CarbookRecordItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7),
                    cursor.getString(8));
            carbookRecordItemArrayList.add(carbookRecordItem);
        }
        cursor.close();
        return carbookRecordItemArrayList;
    }
}
