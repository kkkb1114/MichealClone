package com.example.michaelclone.MainRecord;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.michaelclone.MaintenanceRecords.ApRv_maintenance;
import com.example.michaelclone.MaintenanceRecords.MaintenanceFragment;
import com.example.michaelclone.MaintenanceRecords.OtherFragment;

import java.util.ArrayList;

public class ApVp_MainRecordPage extends FragmentStateAdapter {

    int pageNum;

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
       /* switch (position){
            case 0:
                apRv_mainRecordPage = create_apRv_maintenance();
                return new MainrecordMaintenanceItemsPageFragment(apRv_mainRecordPage);
            case 1:
                return new MainTotalPageFragment(apRv_mainRecordPage);
            default:
                return null;
        }*/
        return null;
    }

    @Override
    public int getItemCount() {
        return pageNum;
    }

   /* public ApRv_MainRecordPage create_apRv_maintenance(){
        setting_apRv_mainRecordPage();
        return new ApRv_MainRecordPage(context, ItemTitleList_maintenance, ItemDistanceList_maintenance, ItemLifeSpanList_maintenance,
                ItemTypeList_maintenance);
    }*/

    public void setting_apRv_mainRecordPage(){

    }

}
