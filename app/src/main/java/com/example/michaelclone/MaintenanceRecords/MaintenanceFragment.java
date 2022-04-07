package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MaintenanceFragment extends Fragment {

    RecyclerView rv_maintenance;
    ApRv_maintenance apRv_maintenance;

    // 정비 항목
    ArrayList<String> ItemTitleList_maintenance = new ArrayList<>();
    ArrayList<String> ItemDistanceList_maintenance = new ArrayList<>();
    ArrayList<String> ItemLifeSpanList_maintenance = new ArrayList<>();
    ArrayList<Integer> ItemTypeList_maintenance = new ArrayList<>();

    Context context;

    public MaintenanceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.vp_fragment_maintenance, container, false);
        setView(view);
        setRecyclerView(rv_maintenance);

        return view;
    }

    public void setView(View view){
        rv_maintenance = view.findViewById(R.id.rv_maintenance);
    }

    public void setRecyclerView(RecyclerView rv_maintenance){
        SettingItemList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        // 정비 항목
        rv_maintenance.setLayoutManager(linearLayoutManager);
        apRv_maintenance = new ApRv_maintenance(context, ItemTitleList_maintenance, ItemDistanceList_maintenance, ItemLifeSpanList_maintenance, ItemTypeList_maintenance);
        rv_maintenance.setAdapter(apRv_maintenance);
        apRv_maintenance.notifyDataSetChanged();
    }

    public void SettingItemList(){

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
        return getResources().getString(id);
    }
}
