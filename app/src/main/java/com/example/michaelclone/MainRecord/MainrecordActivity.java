package com.example.michaelclone.MainRecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelclone.DataBase.CarbookRecordItem_DB;
import com.example.michaelclone.DataBase.CarbookRecord_DB;
import com.example.michaelclone.DataBase.Main_DataBridge;
import com.example.michaelclone.MaintenanceRecords.MaintenanceOtherRecordActivity;
import com.example.michaelclone.MaintenanceRecords.SelectMaintenanceItemActivity;
import com.example.michaelclone.R;

import java.util.ArrayList;

public class MainrecordActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    CarbookRecord_DB carbookRecord_db;
    CarbookRecordItem_DB carbookRecordItem_db;

    MainrecordFragment mainrecordFragment;
    Context context;
    // 지울것!!
    TextView tv_test;

    // 선택 화면 상단 선택한 항목 리스트
    public static ArrayList<String> beforeSelectItemTitleList;
    // true: 수정모드, false: 기록모드
    public static boolean isModify = false;

    //TODO FrameLayout 위에 툴바 넣을 예정이다. (아직 안넣음.)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrecord);
        context = this;
        Main_DataBridge.setMainContext(getBaseContext());
        // 지울것!!
        tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인페이지 기록하기 클릭시 기록 모드로 설정
                isModify = false;
                // 작성 모드로 들어가면 수정모드 기준인 MaintenanceOtherRecordActivity의 carbookRecords, carbookRecordItems변수를 항상 null 초기화시켜준다.
                MaintenanceOtherRecordActivity.carbookRecords = null;
                MaintenanceOtherRecordActivity.carbookRecordItems = null;
                Intent intent = new Intent(context, SelectMaintenanceItemActivity.class);
                startActivity(intent);
            }
        });
        // 지울것!!

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

    public static void createBeforeSelectItemTitleList(){
        beforeSelectItemTitleList = new ArrayList<>();
    }

    public static void removeBeforeSelectItemTitleList(){
        beforeSelectItemTitleList = null;
    }

    private long backpressedTime = 0;
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }
    }
}