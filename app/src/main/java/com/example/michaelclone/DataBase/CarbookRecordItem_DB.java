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

    public void MainRecordDB_update(CarbookRecordItem carbookRecordItem, int _id, int carbookRecordId) {
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("UPDATE carbookRecordItem SET carbookRecordItemCategoryCode = " + carbookRecordItem.carbookRecordItemCategoryCode + ", "
                    + "carbookRecordItemCategoryName = " + "'" + carbookRecordItem.carbookRecordItemCategoryName + "'" + ","
                    + "carbookRecordItemExpenseMemo = " + carbookRecordItem.carbookRecordItemExpenseMemo + ","
                    + "carbookRecordItemExpenseCost = " + carbookRecordItem.carbookRecordItemExpenseCost + ","
                    + "carbookRecordItemIsHidden = " + "'" + carbookRecordItem.carbookRecordItemIsHidden + "'" + ","
                    + "carbookRecordRegTime = " + "'" + carbookRecordItem.carbookRecordItemRegTime + "'" + ","
                    + "carbookRecordUpdateTime = " + "'" + carbookRecordItem.carbookRecordItemUpdateTime + "'"
                    + "WHERE _id = " + _id + "AND carbookRecordId =" + carbookRecordId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    // 가끔 editText에서 에러터져서 null값이 들어가서 테스트환경에서 그런 데이터를 지우기위해 만든 메소드(원래는 delete를 하지않기에 쓰지 않는다.)
    public void test_delete(int _id) {
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("DELETE FROM carbookRecordItem WHERE _id =" + _id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MainRecordDB_delete(CarbookRecordItem carbookRecordItem, int _id, int carbookRecordId) {
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
            db.execSQL("UPDATE carbookRecordItem SET carbookRecordItemIsHidden = " + carbookRecordItem.carbookRecordItemIsHidden + ","
                    + "WHERE _id = " + _id + "AND carbookRecordId = " + carbookRecordId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CarbookRecordItem> getMainRecordItemList() {
            /*ArrayList<MainRecordItem> mainRecordItems = new ArrayList<>();
            mainRecordItems = getMainRecordItemArrayList();*/
        return getMainRecordItemArrayList();
    }

    public ArrayList<CarbookRecordItem> getMainRecordItemArrayList() {
        try {
            //Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM MainRecordItem WHERE carbookRecordId = "+ 0, null);
            Cursor cursor = null;
            cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecordItem", null);

            ArrayList<CarbookRecordItem> carbookRecordItemArrayList = new ArrayList<>();
            carbookRecordItemArrayList = getMainRecordItemCursor(cursor, carbookRecordItemArrayList);
            return carbookRecordItemArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CarbookRecordItem> getMainRecordItemItemArrayList(int mainRecordItemCarbookRecordId) {
        try {
            Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecordItem WHERE carbookRecordId = " + mainRecordItemCarbookRecordId, null);

            ArrayList<CarbookRecordItem> carbookRecordItemArrayList = new ArrayList<>();
            carbookRecordItemArrayList = getMainRecordItemCursor(cursor, carbookRecordItemArrayList);
            return carbookRecordItemArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<CarbookRecordItem> getMainRecordItemCursor(Cursor cursor, ArrayList<CarbookRecordItem> carbookRecordItems) {
        while (cursor.moveToNext()) {
            //todo 여기서 cursor.getInt(0)으로 id 값을 받을수 있지 않을까 했는데 안받아져서 일단 뺐다.
            CarbookRecordItem carbookRecordItem = new CarbookRecordItem(cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7),
                    cursor.getString(8));

            carbookRecordItems.add(carbookRecordItem);
        }
        cursor.close();
        return carbookRecordItems;
    }

}
