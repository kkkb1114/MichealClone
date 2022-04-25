package com.example.michaelclone.DataBase;

import android.content.Context;

import java.util.ArrayList;

public class MainRecordItem_DataBridge {

    public void MainRecordItemInsert(MainRecordItem mainRecordItem){
        MainRecordItem_DB mainRecordItem_db = MainRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        mainRecordItem_db.MainRecordItemDB_insert(mainRecordItem);
    }

    public void getMainRecordItemList(){

    }

}
