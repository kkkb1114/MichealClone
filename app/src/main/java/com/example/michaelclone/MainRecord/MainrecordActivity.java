package com.example.michaelclone.MainRecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.example.michaelclone.DataBase.CarbookRecordItem_DB;
import com.example.michaelclone.DataBase.CarbookRecord_DB;
import com.example.michaelclone.R;

public class MainrecordActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    CarbookRecord_DB carbookRecord_db;
    CarbookRecordItem_DB carbookRecordItem_db;

    MainrecordFragment mainrecordFragment;
    Context context;

    //TODO FrameLayout 위에 툴바 넣을 예정이다. (아직 안넣음.)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrecord);
        context = this;

        setFragment(1);
        setDB();
    }

    public void setDB(){
        carbookRecord_db = CarbookRecord_DB.getInstance(context, "MichaelClone.db", null, 1);
        carbookRecordItem_db = CarbookRecordItem_DB.getInstance(context, "MichaelClone.db", null, 1);
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