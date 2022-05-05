package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.MaintenanceRecords.ApRv_maintenance;
import com.example.michaelclone.MaintenanceRecords.MaintenanceFragment;
import com.example.michaelclone.MaintenanceRecords.OtherFragment;

import java.util.ArrayList;

public class ApVp_MainRecordPage extends FragmentStateAdapter {

    int pageNum;
    ArrayList<Integer> ViewTypeList = new ArrayList<>();

    ApRv_MainRecordPage apRv_mainRecordPage;
    Context context;

    public ApVp_MainRecordPage(@NonNull FragmentActivity fragmentActivity, int pageNum, Context context) {
        super(fragmentActivity);
        this.pageNum = pageNum;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                apRv_mainRecordPage = create_apRv_MainTotalPage();
                return new MainrecordMaintenanceItemsPageFragment(apRv_mainRecordPage);
            case 1:
                return new MainTotalPageFragment(apRv_mainRecordPage);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return pageNum;
    }

    // 각 프래그먼트에 넣을 리사이클러뷰 어뎁터 (일단 통합 어뎁터를 먼저 만들었고 다 만든 후에 추가로 정비/기타 어뎁터 만들 예정)
    public ApRv_MainRecordPage create_apRv_MainTotalPage(){
        setting_apRv_mainRecordPage();
        return new ApRv_MainRecordPage(context);
    }

    public void setting_apRv_mainRecordPage(){

    }

}
