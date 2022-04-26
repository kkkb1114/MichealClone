package com.example.michaelclone.DataBase;

import java.util.ArrayList;

public class MainRecord_DataBridge {

    public void MainRecordInsert(MainRecord mainRecord){
        MainRecord_DB mainRecord_db = MainRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        mainRecord_db.MainRecordDB_insert(mainRecord);
    }

    public ArrayList<MainRecord> MainRecordSelect(){
        MainRecord_DB mainRecord_db = MainRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return mainRecord_db.getMainRecordList();
    }

    public ArrayList<MainRecordItem> MainRecordItemSelect(){
        MainRecordItem_DB mainRecordItem_db = MainRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return mainRecordItem_db.getMainRecordItemList();
    }
}
