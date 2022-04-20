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
    static public ArrayList<String> al_carbookRecordRepairModeList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordExpendDateList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordIsHiddenList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordTotalDistanceList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordRegTimeList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordUpdateTimeList = new ArrayList<>();

    // DB 저장할 정비/기타 데이터 모음
    static public ArrayList<String> al_carbookRecordItemCategoryCodeList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordItemCategoryNameList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordItemExpenseMemoList = new ArrayList<>();
    static public ArrayList<Double> al_carbookRecordItemExpenseCostList = new ArrayList<>();
    static public ArrayList<Integer> al_carbookRecordItemIsHiddenList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordItemRegTimeList = new ArrayList<>();
    static public ArrayList<String> al_carbookRecordItemUpdateTimeList = new ArrayList<>();

}
