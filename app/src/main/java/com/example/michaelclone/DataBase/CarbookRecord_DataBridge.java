package com.example.michaelclone.DataBase;

import android.util.Log;

import java.util.ArrayList;

public class CarbookRecord_DataBridge {

    public void MainRecordInsert(CarbookRecord carbookRecord){
        CarbookRecord_DB carbookRecord_db = CarbookRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        carbookRecord_db.MainRecordDB_insert(carbookRecord);
    }

    public ArrayList<CarbookRecord> MainRecordSelect(){
        CarbookRecord_DB carbookRecord_db = CarbookRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return carbookRecord_db.getMainRecordList();
    }

    public CarbookRecord getSelectCarbookRecord(int _id){
        CarbookRecord_DB carbookRecord_db = CarbookRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return carbookRecord_db.selectCarbookRecord(_id);
    }

    // 기록 저장할때 항목 DB의 carbookRecordId에 넣을 id값 추출 메소드
    public int MainRecordSelectLastId(){
        CarbookRecord_DB carbookRecord_db = CarbookRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return carbookRecord_db.getCarbookRecordLastId();
    }

    public ArrayList<MainRecordPage> getMainRecordData(){
        CarbookRecord_DB carbookRecord_db = CarbookRecord_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return carbookRecord_db.getMainRecordData();
    }
}
