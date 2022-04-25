package com.example.michaelclone.MainRecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.michaelclone.DataBase.MainRecordItem;
import com.example.michaelclone.DataBase.MainRecordItem_DB;
import com.example.michaelclone.DataBase.MainRecordItem_DataBridge;
import com.example.michaelclone.DataBase.MainRecord_DB;
import com.example.michaelclone.DataBase.Main_DataBridge;
import com.example.michaelclone.DataBase.MichaelClone_DBHelper;
import com.example.michaelclone.DataBase.Time_DataBridge;
import com.example.michaelclone.MaintenanceRecords.MaintenanceOtherRecordFragment;
import com.example.michaelclone.MaintenanceRecords.locationSearchFragment;
import com.example.michaelclone.R;

import java.util.ArrayList;

public class MainrecordActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    MainRecord_DB mainRecord_db;
    MainRecordItem_DB mainRecordItem_db;

    MainrecordFragment mainrecordFragment;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrecord);
        context = this;

        setFragment(1);
        setDB();

        ArrayList<MainRecordItem> mainRecordItems = new MainRecordItem_DB().getMainRecordItemList();

        if(MichaelClone_DBHelper.michaelCloneDbHelper_Instance == null){
            Log.i("111", "null");
        }else {
            Log.i("222", "not null");
            Log.i("333", mainRecordItems.get(0).carbookRecordItemCategoryName);
        }
        //Log.i("123", mainRecordItems.get(0).carbookRecordItemRegTime);
    }

    public void setDB(){
        mainRecord_db = new MainRecord_DB(context, 1);
        mainRecordItem_db = MainRecordItem_DB.getInstance(context, "MichaelClone.db", null, 1);
    }

    public void setFragment(int fragment){
        fragmentManager = getSupportFragmentManager();
        mainrecordFragment = new MainrecordFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragment){
            case 1:
                fragmentTransaction.replace(R.id.fl_mainRecord, mainrecordFragment);
                fragmentTransaction.commit();
                break;
        }
    }

}