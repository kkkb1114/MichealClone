package com.example.michaelclone.MainRecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.michaelclone.DataBase.MainRecordItem_DB;
import com.example.michaelclone.DataBase.MainRecord_DB;
import com.example.michaelclone.MaintenanceRecords.MaintenanceOtherRecordFragment;
import com.example.michaelclone.MaintenanceRecords.locationSearchFragment;
import com.example.michaelclone.R;

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

        mainRecord_db.insertData(0,
                "bbb",
                0,
                0.5,
                "eee",
                "fff");
    }

    public void setDB(){
        mainRecord_db = new MainRecord_DB(context, 1);
        mainRecordItem_db = new MainRecordItem_DB(context, 1);
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