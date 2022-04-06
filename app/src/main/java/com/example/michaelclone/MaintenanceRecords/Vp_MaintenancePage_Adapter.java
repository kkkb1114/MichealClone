package com.example.michaelclone.MaintenanceRecords;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Vp_MaintenancePage_Adapter extends FragmentStateAdapter {

    int pageNum;

    /**
     * 1. 생성자로 페이지 개수를 받고 그 개수만큼 페이지를 생성한다.
     * 2. createFragment()에서 position대로 차례로 지정한 프래그먼트 객체를 반환한다.
     * **/
    public Vp_MaintenancePage_Adapter(@NonNull FragmentActivity fragmentActivity, int pageNum) {
        super(fragmentActivity);
        this.pageNum = pageNum;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MaintenanceFragment();
            case 1:
                return new OtherFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return pageNum;
    }
}
