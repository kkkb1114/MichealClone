package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.michaelclone.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MaintenancePageViewPagerAdapter extends FragmentStateAdapter {

    int pageNum;

    // 정비 항목
    ArrayList<String> ItemTitleList_maintenance = new ArrayList<>();
    ArrayList<String> ItemDistanceList_maintenance = new ArrayList<>();
    ArrayList<String> ItemLifeSpanList_maintenance = new ArrayList<>();
    ArrayList<Integer> ItemTypeList_maintenance = new ArrayList<>();
    MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter;
    SelectMaintenanceItemActivity selectMaintenanceItemActivity;
    Context context;

    /**
     * 1. 생성자로 페이지 개수를 받고 그 개수만큼 페이지를 생성한다.
     * 2. createFragment()에서 position대로 차례로 지정한 프래그먼트 객체를 반환한다.
     * **/
    public MaintenancePageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int pageNum, Context context, SelectMaintenanceItemActivity selectMaintenanceItemActivity) {
        super(fragmentActivity);
        this.pageNum = pageNum;
        this.context = context;
        this.selectMaintenanceItemActivity = selectMaintenanceItemActivity;
    }

    Fragment fragment;
    Fragment getFragment(){
        return fragment;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                maintenanceRecyclerViewAdapter = create_apRv_maintenance();
                fragment = new MaintenanceFragment(maintenanceRecyclerViewAdapter);
                return fragment;
            case 1:
                fragment = new OtherFragment(maintenanceRecyclerViewAdapter, selectMaintenanceItemActivity);;
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return pageNum;
    }

    public MaintenanceRecyclerViewAdapter create_apRv_maintenance(){
        setting_apRv_maintenance();
        return new MaintenanceRecyclerViewAdapter(context, ItemTitleList_maintenance, ItemDistanceList_maintenance, ItemLifeSpanList_maintenance,
                ItemTypeList_maintenance, selectMaintenanceItemActivity);
    }

    public void setting_apRv_maintenance(){

        // 정비 항목
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String DistanceUnit = getResourcesString(R.string.DistanceUnit);
        String[] MaintenanceItemTitle = {getResourcesString(R.string.engineOil), getResourcesString(R.string.airconditionerFilter), getResourcesString(R.string.wiperBlade),
                getResourcesString(R.string.driveBelt), getResourcesString(R.string.missionOil), getResourcesString(R.string.Battery),
                getResourcesString(R.string.WarrantyRepair), getResourcesString(R.string.brakeFluid), getResourcesString(R.string.brakePadsAndDiscs),
                getResourcesString(R.string.accidentRepair), getResourcesString(R.string.airCleanerFilter), getResourcesString(R.string.Coolant),
                getResourcesString(R.string.fuelFilter), getResourcesString(R.string.exteriorRepairRestoration), getResourcesString(R.string.generalRepair),
                getResourcesString(R.string.sparkPlug), getResourcesString(R.string.timingBelt), getResourcesString(R.string.Tire),
                getResourcesString(R.string.tirePosition), getResourcesString(R.string.tirePunctureRepair), getResourcesString(R.string.powerSteeringOil),
                getResourcesString(R.string.wheelAlignman)};

        String[] MaintenanceItemDistance = {decimalFormat.format(15000)+DistanceUnit, decimalFormat.format(5000)+DistanceUnit, decimalFormat.format(8000)+DistanceUnit,
                decimalFormat.format(60000)+DistanceUnit, decimalFormat.format(50000)+DistanceUnit, decimalFormat.format(60000)+DistanceUnit, decimalFormat.format(0)+DistanceUnit,
                decimalFormat.format(45000)+DistanceUnit, decimalFormat.format(30000)+DistanceUnit, decimalFormat.format(0)+DistanceUnit, decimalFormat.format(40000)+DistanceUnit,
                decimalFormat.format(40000)+DistanceUnit, decimalFormat.format(60000)+DistanceUnit, decimalFormat.format(0)+DistanceUnit, decimalFormat.format(0)+DistanceUnit,
                decimalFormat.format(100000)+DistanceUnit, decimalFormat.format(80000)+DistanceUnit, decimalFormat.format(60000)+DistanceUnit, decimalFormat.format(10000)+DistanceUnit,
                decimalFormat.format(0)+DistanceUnit, decimalFormat.format(80000)+DistanceUnit, decimalFormat.format(50000)+DistanceUnit};

        String[] MaintenanceItemLifeSpan = {"12", "6", "12",
                "36", "0", "36", "0", "24", "0",
                "0", "24", "24", "0", "0", "0",
                "0", "0", "36", "0", "0", "36",
                "0"};

        for (int i=0; i<MaintenanceItemTitle.length-1; i++){
            ItemTitleList_maintenance.add(MaintenanceItemTitle[i]);
            ItemDistanceList_maintenance.add(MaintenanceItemDistance[i]);
            ItemLifeSpanList_maintenance.add(MaintenanceItemLifeSpan[i]);
            ItemTypeList_maintenance.add(0);
        }
        ItemTypeList_maintenance.add(1);

    }

    public String getResourcesString(int id){
        return context.getResources().getString(id);
    }
}
