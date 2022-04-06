package com.example.michaelclone.MaintenanceRecords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.michaelclone.R;
import com.google.android.material.tabs.TabLayout;

public class SelectMaintenanceItemActivity extends AppCompatActivity {

    private Context context;
    private TabLayout tl_maintenanceOther;

    private ViewPager2 vp_maintenanceOther;
    private Vp_MaintenancePage_Adapter ad_maintenancePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_records);
        context = this;

        // 뷰 초기화
        setView();
        // 테이블 레이아웃 뷰 추가
        setTabLayout();
    }

    public void setView(){
        tl_maintenanceOther = findViewById(R.id.tl_maintenanceOther);
        vp_maintenanceOther = findViewById(R.id.vp_maintenanceOther);
    }
    public void setTabLayout(){
        tl_maintenanceOther.addTab(tl_maintenanceOther.newTab().setCustomView(createTabView_maintenance()));
        tl_maintenanceOther.addTab(tl_maintenanceOther.newTab().setCustomView(createTabView_other()));

        Log.i("tl_maintenanceOther", String.valueOf(tl_maintenanceOther.getTabCount()));
        ad_maintenancePage = new Vp_MaintenancePage_Adapter(this, tl_maintenanceOther.getTabCount());
        vp_maintenanceOther.setAdapter(ad_maintenancePage);
    }


    // 뷰페이저에 들어갈
    public View createTabView_maintenance(){
        View maintenanceTabView = LayoutInflater.from(context).inflate(R.layout.vp_fragment_maintenance, null);

        return maintenanceTabView;
    }

    public View createTabView_other(){
        View otherTabView = LayoutInflater.from(context).inflate(R.layout.vp_fragment_other, null);

        return otherTabView;
    }
}