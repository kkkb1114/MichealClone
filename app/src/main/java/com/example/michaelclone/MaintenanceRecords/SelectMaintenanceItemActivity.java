package com.example.michaelclone.MaintenanceRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.michaelclone.DataBase.CarbookRecord;
import com.example.michaelclone.DataBase.CarbookRecordItem_DB;
import com.example.michaelclone.DataBase.CarbookRecord_Data;
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
    private MaintenancePageViewPagerAdapter ad_maintenancePage;

    public TextView tv_selectionConfirm;
    public TextView tv_itemCount;
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

        CarbookRecordItem_DB carbookRecordItem_db = CarbookRecordItem_DB.getInstance(context, "MainRecord.db", null, 1);
    }

    public void setView(){
        tl_maintenanceOther = findViewById(R.id.tl_maintenanceOther);
        vp_maintenanceOther = findViewById(R.id.vp_maintenanceOther);
        tv_itemCount = findViewById(R.id.tv_itemCount);
        tv_selectionConfirm = findViewById(R.id.tv_selectionConfirm);
    }

    public void setMainRecord(){
        CarbookRecord carbookRecord = new CarbookRecord(0,
                0,
                null,
                0,
                null,
                null,
                null);
        CarbookRecord_Data.carbookRecordArrayList_insertDB.add(carbookRecord);
    }

    public void setDatatransferNextPage(){
        new Handler().postDelayed(() -> {

        }, 1000);

        tv_selectionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ad_maintenancePage.getFragment(0) instanceof MaintenanceFragment && ad_maintenancePage.getFragment(1) instanceof OtherFragment){
                    Log.e("12312312366666", ((MaintenanceFragment)ad_maintenancePage.getFragment(0)).maintenanceRecyclerViewAdapter.getCheckList().toString());
                    Log.e("12312312355555", ((OtherFragment)ad_maintenancePage.getFragment(1)).otherFragmentRecyclerViewAdapter.getCheckList().toString());
                    Intent intent = new Intent(SelectMaintenanceItemActivity.this, MaintenanceOtherRecordActivity.class);
                    intent.putExtra("selectMaintenanceItemList", ((MaintenanceFragment) ad_maintenancePage.getFragment(0)).maintenanceRecyclerViewAdapter.getCheckList());
                    intent.putExtra("selectOtherItemList", ((OtherFragment) ad_maintenancePage.getFragment(1)).otherFragmentRecyclerViewAdapter.getCheckList());
                    startActivity(intent);
                    finish();
                }
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

        ad_maintenancePage = new MaintenancePageViewPagerAdapter(this, 2, context, this);
        vp_maintenanceOther.setAdapter(ad_maintenancePage);

        new TabLayoutMediator(tl_maintenanceOther, vp_maintenanceOther, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNameList.get(position));
            }
        }).attach();
    }
}