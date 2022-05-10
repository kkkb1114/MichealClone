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

import java.util.ArrayList;

public class OtherFragment extends Fragment {

    RecyclerView rv_other;
    OtherFragmentRecyclerViewAdapter otherFragmentRecyclerViewAdapter;
    MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter;
    SelectMaintenanceItemActivity selectMaintenanceItemActivity;

    // 기타 항목
    ArrayList<String> ItemTitleList_other = new ArrayList<>();
    ArrayList<Integer> ItemTypeList_other = new ArrayList<>();

    Context context;

    public OtherFragment(MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter, SelectMaintenanceItemActivity selectMaintenanceItemActivity) {
        this.maintenanceRecyclerViewAdapter = maintenanceRecyclerViewAdapter;
        this.selectMaintenanceItemActivity = selectMaintenanceItemActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.vp_fragment_other, container, false);
        setView(view);
        setRecyclerView(rv_other);

        return view;
    }

    public void setView(View view){
        rv_other = view.findViewById(R.id.rv_other);
    }

    public void setRecyclerView(RecyclerView rv_other){
        SettingItemList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        // 기타 항목
        rv_other.setLayoutManager(linearLayoutManager);
        otherFragmentRecyclerViewAdapter = new OtherFragmentRecyclerViewAdapter(context, ItemTitleList_other, ItemTypeList_other, maintenanceRecyclerViewAdapter, selectMaintenanceItemActivity);
        rv_other.setAdapter(otherFragmentRecyclerViewAdapter);
        otherFragmentRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void SettingItemList(){

        // 기타 항목
        String[] OtherItemTitle = {getResourcesString(R.string.highPass), getResourcesString(R.string.tollFee), getResourcesString(R.string.parkingFee),
                getResourcesString(R.string.carWash), getResourcesString(R.string.fuelAdditive), getResourcesString(R.string.carInspection),
                getResourcesString(R.string.vehicleSupplies), getResourcesString(R.string.outdoorProducts), getResourcesString(R.string.indoorProducts),
                getResourcesString(R.string.carInsurance), getResourcesString(R.string.blackBox), getResourcesString(R.string.trafficFine),
                getResourcesString(R.string.automobileTax), getResourcesString(R.string.tinting), getResourcesString(R.string.sheetMetalPainting),
                getResourcesString(R.string.navigation), getResourcesString(R.string.rearCamera), getResourcesString(R.string.carAudio)};

        for (int i=0; i<OtherItemTitle.length-1; i++){
            ItemTitleList_other.add(OtherItemTitle[i]);
            ItemTypeList_other.add(0);
        }
        ItemTypeList_other.add(1);
    }

    public String getResourcesString(int id){
        return getResources().getString(id);
    }
}
