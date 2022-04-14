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
    static public int MaintenanceSingleItemPosition;
    static public boolean MaintenanceSingleItemboolean = false;

    public void al_itemTitleListresetData(){
        al_itemTitleList.clear();
    }

}
