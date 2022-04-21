package com.example.michaelclone.MaintenanceRecords;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Data_MaintenanceRecords {

    // 정비 기타
    static public ArrayList<String> al_itemTitleList = new ArrayList<>();

    //static public HashMap
    static public String MaintenanceSingleItemTitle = "null";
    static public boolean MaintenanceSingleItemboolean = false;


    // DB 저장할 기록 데이터 모음
    static public HashMap<String, Integer> al_carbookRecordRepairModeList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordExpendDateList = new HashMap<>();
    static public HashMap<String, Integer> al_carbookRecordIsHiddenList = new HashMap<>();
    static public HashMap<String, Integer> al_carbookRecordTotalDistanceList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordRegTimeList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordUpdateTimeList = new HashMap<>();

    // DB 저장할 정비/기타 데이터 모음
    static public HashMap<String, Integer> al_carbookRecordItemPositionList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordItemCategoryCodeList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordItemCategoryNameList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordItemExpenseMemoList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordItemExpenseCostList = new HashMap<>();
    static public HashMap<String, Integer> al_carbookRecordItemIsHiddenList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordItemRegTimeList = new HashMap<>();
    static public HashMap<String, String> al_carbookRecordItemUpdateTimeList = new HashMap<>();

    //static public HashMap<String, CarbookItem> carbookItemHashMap = new HashMap<>();
    //String , String , Int , String

    //싱글톤




    static public void ItemDataClear(){
        al_carbookRecordItemPositionList.clear();
        al_carbookRecordItemCategoryCodeList.clear();
        al_carbookRecordItemCategoryNameList.clear();
        al_carbookRecordItemExpenseMemoList.clear();
        al_carbookRecordItemExpenseCostList.clear();
        al_carbookRecordItemIsHiddenList.clear();
        al_carbookRecordItemRegTimeList.clear();
        al_carbookRecordItemUpdateTimeList.clear();
    }
}
