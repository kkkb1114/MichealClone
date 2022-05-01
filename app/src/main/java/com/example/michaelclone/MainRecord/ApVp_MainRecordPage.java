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

    //todo 임시 방편으로 뷰 타입 만들어놓음.
    public ApRv_MainRecordPage create_apRv_MainTotalPage(){
        setting_apRv_mainRecordPage();
        Log.i("임시", "111");
        for (int i=0; i<MainRecord_Data.MainRecordPageRecordItemArrayList.size(); i++){
            Log.i("임시", "222");
            Log.i("MainRecord_Data.MainRecordPageRecordArrayList.size()", String.valueOf(MainRecord_Data.MainRecordPageRecordArrayList.size()));
            ViewTypeList.add(2);
        }
        return new ApRv_MainRecordPage(context, ViewTypeList);
    }

    public void setting_apRv_mainRecordPage(){

    }

}
