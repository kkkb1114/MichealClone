package com.example.michaelclone.DataBase;

import android.util.Log;

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


    // 기록 저장할때 항목 DB의 carbookRecordId에 넣을 id값 추출 메소드
    public int MainRecordSelectLastId(){
        MainRecord_DB mainRecord_db = MainRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return mainRecord_db.getCarbookRecordLastId();
    }

    public void getMainRecordData(){
        MainRecord_DB mainRecord_db = MainRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return mainRecord_db.;
    }
}
