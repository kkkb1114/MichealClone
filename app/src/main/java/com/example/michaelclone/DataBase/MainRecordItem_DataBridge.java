package com.example.michaelclone.DataBase;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class MainRecordItem_DataBridge {

    public void MainRecordItemInsert(MainRecordItem mainRecordItem){
        MainRecordItem_DB mainRecordItem_db = MainRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        Log.i("carbookRecordItemExpenseMemo", MainRecord_Data.mainRecordItemArrayList.get(0).carbookRecordItemExpenseMemo);
        mainRecordItem_db.MainRecordItemDB_insert(mainRecordItem);
    }
}
