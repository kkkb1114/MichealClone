package com.example.michaelclone.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainRecordItem_DB{

    public static MainRecordItem_DB mainRecordItemDB_Instance;
    final String TABLE_NAME = "carbookRecordItem";

    // 메인 DB가 생성되어 있지 않으면 생성한다.
    public static synchronized MainRecordItem_DB getInstance(@Nullable Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        if (MichaelClone_DBHelper.michaelCloneDbHelper_Instance == null){
            MichaelClone_DBHelper.michaelCloneDbHelper_Instance = MichaelClone_DBHelper.getInstance(context, name, factory, version);
        }
        return new MainRecordItem_DB();
    }

    // 데이터베이스 추가하기 insert
    public void MainRecordItemDB_insert(MainRecordItem mainRecordItem){

        Log.e("tewst","mainRecordItem : " + mainRecordItem);
        SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        try {
            db.beginTransaction();
            db.execSQL("INSERT INTO carbookRecordItem VALUES (null, "+mainRecordItem.carbookRecordId+" , " +"'"+ mainRecordItem.carbookRecordItemCategoryCode +"'"+" , "
                    +"'"+ mainRecordItem.carbookRecordItemCategoryName +"'"+  ", " +"'"+ mainRecordItem.carbookRecordItemExpenseMemo +"'"+", "
                    +"'"+ mainRecordItem.carbookRecordItemExpenseCost +"'"+" , "+mainRecordItem.carbookRecordItemIsHidden+" , "+"'"+mainRecordItem.carbookRecordItemRegTime+"'"
                    +" , "+"'"+mainRecordItem.carbookRecordItemUpdateTime +"'"+" );");

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(db != null){
                db.endTransaction();
            }
        }
    }

    public void MainRecordDB_update(MainRecordItem mainRecordItem, int _id, int carbookRecordId){
        try {
            SQLiteDatabase db = MichaelClone_DBHelper.writeableDataBase;
        db.execSQL("UPDATE carbookRecordItem SET carbookRecordItemCategoryCode = " + mainRecordItem.carbookRecordItemCategoryCode + ", "
                + "carbookRecordItemCategoryName = " + "'"+ mainRecordItem.carbookRecordItemCategoryName +"'" + ","
                + "carbookRecordItemExpenseMemo = " + mainRecordItem.carbookRecordItemExpenseMemo + ","
                + "carbookRecordItemExpenseCost = " + mainRecordItem.carbookRecordItemExpenseCost + ","
                + "carbookRecordItemIsHidden = " + "'"+ mainRecordItem.carbookRecordItemIsHidden +"'" + ","
                + "carbookRecordRegTime = " + "'"+ mainRecordItem.carbookRecordItemRegTime +"'" + ","
                + "carbookRecordUpdateTime = " + "'"+ mainRecordItem.carbookRecordItemUpdateTime +"'"
                + "WHERE _id = " + _id + "AND carbookRecordId ="+ carbookRecordId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public ArrayList<MainRecordItem> getMainRecordItemList(){
            /*ArrayList<MainRecordItem> mainRecordItems = new ArrayList<>();
            mainRecordItems = getMainRecordItemArrayList();*/
            return getMainRecordItemArrayList();
    }

    public ArrayList<MainRecordItem> getMainRecordItemArrayList(){
        try{
            //Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM MainRecordItem WHERE carbookRecordId = "+ 0, null);
            Cursor cursor = MichaelClone_DBHelper.readableDataBase.rawQuery("SELECT * FROM carbookRecordItem", null);
            ArrayList<MainRecordItem> mainRecordItemArrayList = new ArrayList<>();
            mainRecordItemArrayList = getMainRecordItemCursor(cursor, mainRecordItemArrayList);
            return mainRecordItemArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<MainRecordItem> getMainRecordItemCursor(Cursor cursor, ArrayList<MainRecordItem> mainRecordItems){
        while(cursor.moveToNext()){
            MainRecordItem mainRecordItem = new MainRecordItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6),
                    cursor.getString(7));

            mainRecordItems.add(mainRecordItem);
        }
        cursor.close();
        return mainRecordItems;
    }

}
