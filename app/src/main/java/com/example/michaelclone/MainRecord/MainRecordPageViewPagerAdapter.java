package com.example.michaelclone.MainRecord;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MainRecordPageViewPagerAdapter extends FragmentStateAdapter {

    int pageNum;
    MainRecordPageRecyclerViewAdapter mainRecordPageRecyclerViewAdapter;
    Context context;

    public MainRecordPageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int pageNum, Context context, MainRecordPageRecyclerViewAdapter mainRecordPageRecyclerViewAdapter) {
        super(fragmentActivity);
        this.pageNum = pageNum;
        this.context = context;
        this.mainRecordPageRecyclerViewAdapter = mainRecordPageRecyclerViewAdapter;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MainrecordMaintenanceItemsPageFragment(mainRecordPageRecyclerViewAdapter);
            case 1:
                return new MainTotalPageFragment(mainRecordPageRecyclerViewAdapter);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return pageNum;
    }
}
