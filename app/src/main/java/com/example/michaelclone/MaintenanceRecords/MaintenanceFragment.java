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

public class MaintenanceFragment extends Fragment {

    RecyclerView rv_maintenance;
    MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter;
    Context context;

    public MaintenanceFragment(MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter) {
        this.maintenanceRecyclerViewAdapter = maintenanceRecyclerViewAdapter;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        // 정비 항목
        rv_maintenance.setLayoutManager(linearLayoutManager);
        rv_maintenance.setAdapter(maintenanceRecyclerViewAdapter);
        maintenanceRecyclerViewAdapter.notifyDataSetChanged();
    }

    public String getResourcesString(int id){
        return getResources().getString(id);
    }
}
