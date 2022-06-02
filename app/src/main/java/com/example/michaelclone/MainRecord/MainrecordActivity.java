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
    // (프래그먼트 만들때 MaintenancePageViewPagerAdapter에 현재 엑티비티 자신을 넣어 "this" 프래그먼트에서 항목을 클릭할때 클릭한 아이템 텍스트를 엑티비티의 selectItemTitleList에 add했다.)
    // TODO 이 리스트를 아이템 객체 리스트로 대체해야한다. 1-1
    public static ArrayList<String> resultSelectItemTitleList;
    // 리사이클러뷰에서 선택항목을 return하는 메소드를 만들어 받을까했지만 엑티비티에서 해당 어뎁터까지 2번을 걸쳐 반환을 받아야했기에 복잡해질것을 우려하여 static으로 만들었다.
    // TODO 이 리스트를 항목 선택 화면으로 보내고 항목선택 화면에 생성될때 객체 생성하고 선택 완료를 누르면 해당 리스트를 전부 아이템객체 리스트에 SET및 add를 한다. 1-2
    public static ArrayList<String> beforeSelectItemTitleList;
    // true: 수정모드, false: 기록모드
    public static boolean isModify = false;

    //TODO FrameLayout 위에 툴바 넣을 예정이다. (아직 안넣음.)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrecord);
        context = this;

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