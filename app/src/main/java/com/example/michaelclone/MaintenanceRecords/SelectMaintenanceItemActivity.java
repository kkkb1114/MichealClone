package com.example.michaelclone.MaintenanceRecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.michaelclone.DataBase.CarbookRecord;
import com.example.michaelclone.DataBase.CarbookRecordItem;
import com.example.michaelclone.DataBase.CarbookRecordItem_DB;
import com.example.michaelclone.DataBase.CarbookRecord_Data;
import com.example.michaelclone.MainRecord.MainrecordActivity;
import com.example.michaelclone.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectMaintenanceItemActivity extends AppCompatActivity implements Serializable {

    private Context context;

    // 탭 레이아웃
    private TabLayout tl_maintenanceOther;
    ArrayList<String> tabNameList = new ArrayList<>();

    // 뷰페이저2
    private ViewPager2 vp_maintenanceOther;
    private MaintenancePageViewPagerAdapter ad_maintenancePage;

    public TextView tv_selectionConfirm;
    public TextView tv_itemCount;

    boolean isModify = MainrecordActivity.isModify;

    ArrayList<String> selectItemTitleList;

    // 수정모드 구분
    ArrayList<CarbookRecordItem> carbookRecordItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_records);
        context = this;

        // 뷰 초기화
        setView();
        // 테이블 레이아웃 뷰 추가
        setTabLayout();
        setDatatransferNextPage();

        ModifyCheck();

        initHandler();

        CarbookRecordItem_DB carbookRecordItem_db = CarbookRecordItem_DB.getInstance(context, "MainRecord.db", null, 1);
    }

    public void ModifyCheck() {
        if (!isModify) {
            if (MainrecordActivity.selectItemTitleList == null) {
                MainrecordActivity.createSelectItemTitleList();
            }
            // 해당 엑티비티 생성때마다 selectItemTitleList객체 생성
            //MainrecordActivity.createSelectItemTitleList();
            selectItemTitleList = MainrecordActivity.selectItemTitleList;
        } else {
            selectItemTitleList = MainrecordActivity.selectItemTitleList;
            if (selectItemTitleList.size() > 0) {
                tv_itemCount.setText(String.valueOf(selectItemTitleList.size() + context.getResources().getString(R.string.selectionCount)));
                tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#80000000")));
                tv_selectionConfirm.setClickable(true);
            } else {
                if (selectItemTitleList.size() <= 0) {
                    tv_itemCount.setText(context.getResources().getString(R.string.PleaseSelectAnItem));
                    tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#1A000000")));
                    tv_selectionConfirm.setClickable(false);
                } else {
                    tv_itemCount.setText(String.valueOf(selectItemTitleList.size() + context.getResources().getString(R.string.selectionCount)));
                    tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#80000000")));
                    tv_selectionConfirm.setClickable(true);
                }
            }
        }
    }

    public static Handler viewHandler = null;

    private void initHandler() {
        try {
            viewHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    try {
                        switch (msg.what) {
                            case 1:
                                tv_itemCount.setText(String.valueOf(selectItemTitleList.size() + context.getResources().getString(R.string.selectionCount)));
                                if (selectItemTitleList.size() > 0) {
                                    tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#80000000")));
                                    tv_selectionConfirm.setClickable(true);
                                }
                                break;
                            case 2:
                                tv_itemCount.setText(String.valueOf(selectItemTitleList.size() + context.getResources().getString(R.string.selectionCount)));
                                if (selectItemTitleList.size() <= 0) {
                                    tv_itemCount.setText(context.getResources().getString(R.string.PleaseSelectAnItem));
                                    tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#1A000000")));
                                    tv_selectionConfirm.setClickable(false);
                                } else {
                                    tv_itemCount.setText(String.valueOf(selectItemTitleList.size() + context.getResources().getString(R.string.selectionCount)));
                                    tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#80000000")));
                                    tv_selectionConfirm.setClickable(true);
                                }
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setView() {
        tl_maintenanceOther = findViewById(R.id.tl_maintenanceOther);
        vp_maintenanceOther = findViewById(R.id.vp_maintenanceOther);
        tv_itemCount = findViewById(R.id.tv_itemCount);
        tv_selectionConfirm = findViewById(R.id.tv_selectionConfirm);
    }

    public void setDatatransferNextPage() {
        new Handler().postDelayed(() -> {

        }, 1000);

        tv_selectionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (ad_maintenancePage.getFragment(0) instanceof MaintenanceFragment && ad_maintenancePage.getFragment(1) instanceof OtherFragment){}
                // 수정모드면 기록페이지의 핸들러를 작동시키며 작성모드면 인텐트로 리스트객체를 전달한다.
                if (isModify) {
                    Log.i("tv_selectionConfirmrecordDataHandler", String.valueOf(selectItemTitleList));
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("selectItemTitleList", selectItemTitleList);
                    message.setData(bundle);
                    message.what = 1;
                    Log.i("tv_selectionConfirmrecordDataHandlermessage", String.valueOf(message));
                    MaintenanceOtherRecordFragment.recordDataHandler.sendMessage(message);
                } else {
                    Intent intent = new Intent(SelectMaintenanceItemActivity.this, MaintenanceOtherRecordActivity.class);
                    intent.putExtra("selectItemTitleList", selectItemTitleList);
                    startActivity(intent);
                }
                /*// 기록, 수정화면에 들어올떼마다 새로 사용할 변수이기에 완료시 초기화
                MainrecordActivity.removeSelectItemTitleList();*/
                finish();
            }
        });
        // 클릭 이벤트를 지정한 후에 막아야 막힌다.
        // 선택 항목이 없으면 클릭을 막는다.
        tv_selectionConfirm.setClickable(false);
    }

    /**
     * 1. ArrayList 객체에 탭에 적용할 문자열들을 add한다.
     * 2. 뷰페이저에서 적용할 어뎁터를 생성하고 뷰페이저에 어뎁터를 set한다.
     * 3. 탭레이아웃과 뷰페이저를 연결하는 TabLayoutMediator라는 객체를 생성하면서 안에 연결할 탭 레이아웃과
     * 뷰페이저2 레이아웃을 넣고 onConfigureTab()에서 탭에 적용할 텍스트를 ArrayList에서 가져와 적용한다.
     **/
    public void setTabLayout() {
        tabNameList.add(getResources().getString(R.string.maintenanceItems));
        tabNameList.add(getResources().getString(R.string.otherItems));

        ad_maintenancePage = new MaintenancePageViewPagerAdapter(this, 2, context);
        vp_maintenanceOther.setAdapter(ad_maintenancePage);

        new TabLayoutMediator(tl_maintenanceOther, vp_maintenanceOther, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNameList.get(position));
            }
        }).attach();
    }
}