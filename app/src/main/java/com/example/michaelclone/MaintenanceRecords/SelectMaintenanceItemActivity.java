package com.example.michaelclone.MaintenanceRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.michaelclone.DataBase.MainRecord;
import com.example.michaelclone.DataBase.MainRecordItem;
import com.example.michaelclone.DataBase.MainRecordItem_DB;
import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectMaintenanceItemActivity extends AppCompatActivity implements Serializable {

    private Context context;

    // 탭 레이아웃
    private TabLayout tl_maintenanceOther;
    ArrayList<String> tabNameList = new ArrayList<>();

    // 뷰페이저2
    private ViewPager2 vp_maintenanceOther;
    private ApVp_MaintenancePage ad_maintenancePage;

    static public TextView tv_selectionConfirm;
    static public TextView tv_itemCount;
    //static public ArrayList<String> al_itemTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_records);
        context = this;

        // 일단 만약 해당 페이지가 생성되는데 데이터가 초기화 안되어 있으면 초기화 한다. (항목 변경 기능이 아직 안되어 있어서 오롯이 보여주기만을 위한 기능이며 항목 변경 기능 만들면 바꿀 예정)
        if (Data_MaintenanceRecords.al_itemTitleList != null){
            Data_MaintenanceRecords.al_itemTitleList.clear();
        }
        // 뷰 초기화
        setView();
        setMainRecord();
        // 테이블 레이아웃 뷰 추가
        setTabLayout();
        setDatatransferNextPage();

        MainRecordItem_DB mainRecordItem_db = MainRecordItem_DB.getInstance(context, "MainRecord.db", null, 1);
    }

    public void setView(){
        tl_maintenanceOther = findViewById(R.id.tl_maintenanceOther);
        vp_maintenanceOther = findViewById(R.id.vp_maintenanceOther);
        tv_itemCount = findViewById(R.id.tv_itemCount);
        tv_selectionConfirm = findViewById(R.id.tv_selectionConfirm);
    }

    public void setMainRecord(){
        MainRecord mainRecord = new MainRecord(0,
                0,
                null,
                0,
                null,
                null,
                null);
        MainRecord_Data.mainRecordArrayList.add(mainRecord);
    }

    public void setDatatransferNextPage(){
        tv_selectionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectMaintenanceItemActivity.this, MaintenanceOtherRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // 클릭 이벤트를 지정한 후에 막아야 막힌다.
        // 선택 항목이 없으면 클릭을 막는다.
        tv_selectionConfirm.setClickable(false);
    }

    /**
     * 1. ArrayList 객체에 탭에 적용할 문자열들을 add한다.
     * 2. 뷰페이저에서 적용할 어뎁터를 생성하고 뷰페이저에 어뎁터를 set한다.
     * 3. 탭레이아웃과 뷰페이저를 연결하는 TabLayoutMediator라는 객체를 생성하면서 안에 연결할 탭 레이아웃과
     *    뷰페이저2 레이아웃을 넣고 onConfigureTab()에서 탭에 적용할 텍스트를 ArrayList에서 가져와 적용한다.
     * **/
    public void setTabLayout(){
        tabNameList.add(getResources().getString(R.string.maintenanceItems));
        tabNameList.add(getResources().getString(R.string.otherItems));

        ad_maintenancePage = new ApVp_MaintenancePage(this, 2, context);
        vp_maintenanceOther.setAdapter(ad_maintenancePage);

        new TabLayoutMediator(tl_maintenanceOther, vp_maintenanceOther, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNameList.get(position));
            }
        }).attach();
    }

    /*항목을 클릭할때마다 클릭하여 체크된 항목이 0개일경우 선택완료 버튼을 잠궈버리며 1개 이상이면 선택완료로 다음 페이지에
    넘어갈수 있게 했다.
    또한 리사이클러뷰는 뷰를 재활용하기에 체크되있는 상태에서 그냥 스크롤하면 그대로 체크 true상태의 뷰가 딸려올수 있어 수동 체크와 리사이클러뷰를 스크롤해서 자동으로 체크된 자동 체크를
    구분해서 예외처리 한다.*/
    // 0: 항목 선택, 1: 항목 선택 취소
    static public void itemClickChangeCount(Context context, String title, int CheckedItemCount){
        MainRecordItem mainRecordItem = null;
        if (CheckedItemCount == 0){
            // 리사이클러뷰 스크롤로 인한 자동 추가가 아니면 실행
            Data_MaintenanceRecords.al_itemTitleList.add(title);
            tv_itemCount.setText(Data_MaintenanceRecords.al_itemTitleList.size()+context.getResources().getString(R.string.selectionCount));
            SelectMaintenanceItemActivity.tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#80000000")));
            SelectMaintenanceItemActivity.tv_selectionConfirm.setClickable(true);

            // 선택할때마다 바로 집어넣고 취소하면 뺀다.
            mainRecordItem = new MainRecordItem(0,
                    null,
                    title,
                    null,
                    null,
                    0,
                    null,
                    null);
            MainRecord_Data.mainRecordItemArrayList.add(mainRecordItem);
        }else {
            // 리사이클러뷰 스크롤로 인한 지동 추가가 아니면 실행
                Data_MaintenanceRecords.al_itemTitleList.remove(title);
                if (Data_MaintenanceRecords.al_itemTitleList.size() <= 0){
                    tv_itemCount.setText(context.getResources().getString(R.string.PleaseSelectAnItem));
                    SelectMaintenanceItemActivity.tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#1A000000")));
                    SelectMaintenanceItemActivity.tv_selectionConfirm.setClickable(false);
                }else {
                    tv_itemCount.setText(Data_MaintenanceRecords.al_itemTitleList.size()+context.getResources().getString(R.string.selectionCount));
            }

            // 선택할때마다 바로 집어넣고 취소하면 뺀다.
            MainRecord_Data.mainRecordItemArrayList.remove(mainRecordItem);

        }
    }
}