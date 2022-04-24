package com.example.michaelclone.DataBase;

import android.content.Context;

import java.util.ArrayList;

public class MainRecordItem_DataBridge {

    private static ArrayList<MainRecordItem> mainRecordItemArrayList = new ArrayList<>();

    public void MainRecordItemInsert(MainRecordItem mainRecordItem){
        MainRecordItem_DB mainRecordItem_db = MainRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MainRecord.db", null, 1);
        mainRecordItem_db.MainRecordItemDB_insert(mainRecordItem.carbookRecordId,
                mainRecordItem.carbookRecordItemCategoryCode,
                mainRecordItem.carbookRecordItemCategoryName,
                mainRecordItem.carbookRecordItemExpenseMemo,
                mainRecordItem.carbookRecordItemExpenseCost,
                mainRecordItem.carbookRecordItemIsHidden,
                mainRecordItem.carbookRecordItemRegTime,
                mainRecordItem.carbookRecordItemUpdateTime);
    }

    public void getMainRecordItemList(){

    }

}
