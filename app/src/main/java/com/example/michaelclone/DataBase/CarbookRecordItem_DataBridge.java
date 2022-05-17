package com.example.michaelclone.DataBase;

import android.util.Log;

import java.util.ArrayList;

public class CarbookRecordItem_DataBridge {

    public void MainRecordItemInsert(CarbookRecordItem carbookRecordItem){
        CarbookRecordItem_DB carbookRecordItem_db = CarbookRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        carbookRecordItem_db.MainRecordItemDB_insert(carbookRecordItem);

        Log.i("CarbookRecordItem", String.valueOf(carbookRecordItem));
    }

    // 메인에 불러오는 기록 항목들 (이름과 가격만 나오는 부분)을 불러오는 sql문을 불러올까 했지만 딱히 기록처럼 지출 금액 총 합등등 계산이 필요한 부분은 없어서 그냥 위의 MainRecordItemSelect()을 써서 전부 불러오기로함.
    public void MainRecordItemUpdate(CarbookRecordItem carbookRecordItem, int id, int CarbookRecordId){
        CarbookRecordItem_DB carbookRecordItem_db = CarbookRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        carbookRecordItem_db.MainRecordItemDB_update(carbookRecordItem, id,CarbookRecordId);
    }

    public ArrayList<CarbookRecordItem> MainRecordItemSelect(){
        CarbookRecordItem_DB carbookRecordItem_db = CarbookRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return carbookRecordItem_db.getMainRecordItemList();
    }

    // 메인에 불러오는 기록 항목들 (이름과 가격만 나오는 부분)을 불러오는 sql문을 불러올까 했지만 딱히 기록처럼 지출 금액 총 합등등 계산이 필요한 부분은 없어서 그냥 위의 MainRecordItemSelect()을 써서 전부 불러오기로함.
    public ArrayList<CarbookRecordItem> getMainRecordItemItemData(int CarbookRecordId){
        CarbookRecordItem_DB carbookRecordItem_db = CarbookRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        return carbookRecordItem_db.getMainRecordItemItemArrayList(CarbookRecordId);
    }

    // 가끔 editText에서 에러터져서 null값이 들어가서 테스트환경에서 그런 데이터를 지우기위해 만든 메소드(원래는 delete를 하지않기에 쓰지 않는다.)
    public void test_delete(int _id){
        CarbookRecordItem_DB carbookRecordItem_db = CarbookRecordItem_DB.getInstance(Main_DataBridge.getMainContext(), "MichaelClone.db", null, 1);
        carbookRecordItem_db.test_delete(_id);
    }
}
