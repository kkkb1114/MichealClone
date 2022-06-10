package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.michaelclone.DataBase.CarbookRecordItem_DataBridge;
import com.example.michaelclone.DataBase.CarbookRecord_Data;
import com.example.michaelclone.DataBase.CarbookRecord_DataBridge;
import com.example.michaelclone.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainrecordFragment extends Fragment {

    Context context;
    TabLayout tl_mainhistorypage;
    ArrayList<String> mainhistoryTabNameList = new ArrayList<>();
    // 뷰페이저2
    private ViewPager2 vp_mainhistory;
    private MainRecordPageViewPagerAdapter ad_mainhistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_mainrecordpage, container, false);
        setView(view);
        getMainrecordDataList();
        setTabLayout(new MainRecordPageRecyclerViewAdapter(context));
        return view;
    }

    public void setView(View view) {
        tl_mainhistorypage = view.findViewById(R.id.tl_mainhistorypage);
        vp_mainhistory = view.findViewById(R.id.vp_maintenanceOther);
    }

    public void setTabLayout(MainRecordPageRecyclerViewAdapter mainRecordPageRecyclerViewAdapter) {
        mainhistoryTabNameList.add(getResources().getString(R.string.HistoryPageFullTab));
        mainhistoryTabNameList.add(getResources().getString(R.string.HistoryPageMaintenanceOthers));
        ad_mainhistory = new MainRecordPageViewPagerAdapter(requireActivity(), 2, context, mainRecordPageRecyclerViewAdapter);
        vp_mainhistory.setAdapter(ad_mainhistory);
        new TabLayoutMediator(tl_mainhistorypage, vp_mainhistory, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mainhistoryTabNameList.get(position));
            }
        }).attach();
    }

    // 기록 페이지에 들어오면 DB에 저장된 MainRecord, MainRecordItem데이터를 전부 불러와서 MainRecord_Data클래스안에 메인 페이지 전용 ArrayList에 전부 넣어준다.
    public void getMainrecordDataList() {
        CarbookRecordItem_DataBridge carbookRecordItem_dataBridge = new CarbookRecordItem_DataBridge();
        CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB = carbookRecordItem_dataBridge.MainRecordItemSelect();
        CarbookRecord_DataBridge mainRecordDataBridge = new CarbookRecord_DataBridge();
        CarbookRecord_Data.mainRecordPageArrayList = mainRecordDataBridge.getMainRecordData();
    }
}