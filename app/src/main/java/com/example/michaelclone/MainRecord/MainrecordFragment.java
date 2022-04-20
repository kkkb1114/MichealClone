package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.michaelclone.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainrecordFragment extends Fragment {

    Context context;
    TabLayout tl_mainhistorypage;
    ArrayList<String> mainhistoryTabNameList = new ArrayList<>();

    // 뷰페이저2
    private ViewPager2 vp_mainhistory;
    private ApVp_MainRecordPage ad_mainhistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_mainrecordpage, container, false);
        setView(view);
        //setTabLayout();

        return view;
    }

    public void setView(View view){
        tl_mainhistorypage = view.findViewById(R.id.tl_mainhistorypage);
    }

    public void setTabLayout(){
        mainhistoryTabNameList.add(getResources().getString(R.string.HistoryPageFullTab));
        mainhistoryTabNameList.add(getResources().getString(R.string.HistoryPageMaintenanceOthers));

        ad_mainhistory = new ApVp_MainRecordPage(getActivity(), 2, context);
        vp_mainhistory.setAdapter(ad_mainhistory);

        new TabLayoutMediator(tl_mainhistorypage, vp_mainhistory, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mainhistoryTabNameList.get(position));
            }
        }).attach();
    }

}
