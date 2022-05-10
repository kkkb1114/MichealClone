package com.example.michaelclone.MainRecord;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MainRecordPageViewPagerAdapter extends FragmentStateAdapter {

    int pageNum;
    ArrayList<Integer> ViewTypeList = new ArrayList<>();

    MainRecordPageRecyclerViewAdapter _mainRecordPageRecyclerViewAdapter;
    Context context;

    public MainRecordPageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int pageNum, Context context) {
        super(fragmentActivity);
        this.pageNum = pageNum;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        _mainRecordPageRecyclerViewAdapter = create_apRv_MainTotalPage();
        switch (position){
            case 0:
                return new MainrecordMaintenanceItemsPageFragment(_mainRecordPageRecyclerViewAdapter);
            case 1:
                return new MainTotalPageFragment(_mainRecordPageRecyclerViewAdapter);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return pageNum;
    }

    // 각 프래그먼트에 넣을 리사이클러뷰 어뎁터 (일단 통합 어뎁터를 먼저 만들었고 다 만든 후에 추가로 정비/기타 어뎁터 만들 예정)
    public MainRecordPageRecyclerViewAdapter create_apRv_MainTotalPage(){
        setting_apRv_mainRecordPage();
        return new MainRecordPageRecyclerViewAdapter(context);
    }

    public void setting_apRv_mainRecordPage(){

    }

}
