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

    Context context;

    public OtherFragment(OtherFragmentRecyclerViewAdapter otherFragmentRecyclerViewAdapter) {
        this.otherFragmentRecyclerViewAdapter = otherFragmentRecyclerViewAdapter;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        // 기타 항목
        rv_other.setLayoutManager(linearLayoutManager);
        rv_other.setAdapter(otherFragmentRecyclerViewAdapter);
        otherFragmentRecyclerViewAdapter.notifyDataSetChanged();
    }


    public String getResourcesString(int id){
        return getResources().getString(id);
    }
}
