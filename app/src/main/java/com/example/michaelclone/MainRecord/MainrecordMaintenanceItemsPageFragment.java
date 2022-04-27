package com.example.michaelclone.MainRecord;

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

public class MainrecordMaintenanceItemsPageFragment extends Fragment {

    ApRv_MainRecordPage apRv_mainRecordPage;
    RecyclerView rv_mainrecordMaintenanceItemspage;
    Context context;

    public MainrecordMaintenanceItemsPageFragment(ApRv_MainRecordPage apRv_mainRecordPage){
        this.apRv_mainRecordPage = apRv_mainRecordPage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_mainrecord_maintenanceitemspage, container, false);
        setView(view);

        return view;
    }

    public void setView(View view){
        rv_mainrecordMaintenanceItemspage = view.findViewById(R.id.rv_mainrecordMaintenanceItemspage);
    }

    public void setRecyclerView(RecyclerView rv_mainTotalPage){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rv_mainTotalPage.setLayoutManager(linearLayoutManager);
        rv_mainTotalPage.setAdapter(apRv_mainRecordPage);
        apRv_mainRecordPage.notifyDataSetChanged();
    }

}
