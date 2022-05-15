package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.michaelclone.DataBase.CarbookRecord;
import com.example.michaelclone.DataBase.CarbookRecordItem;
import com.example.michaelclone.DataBase.CarbookRecordItem_DataBridge;
import com.example.michaelclone.DataBase.CarbookRecord_Data;
import com.example.michaelclone.DataBase.CarbookRecord_DataBridge;
import com.example.michaelclone.DataBase.Time_DataBridge;
import com.example.michaelclone.DialogManager;
import com.example.michaelclone.MainRecord.MainrecordActivity;
import com.example.michaelclone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MaintenanceOtherRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MaintenanceOtherRecordFragment maintenanceOtherRecordFragment;
    private LocationSearchFragment locationSearchFragment;

    TextView maintenanceOtherRecordComplete;

    LinearLayout ln_date;
    TextView tv_date;
    Context mContext;
    ArrayList<String> selectItemTitleList;
    int carbookRecordId = 0;

    // 날짜 구하기 변수
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    SimpleDateFormat mFormat_saveOnly = new SimpleDateFormat("yyyyMMdd");
    String selectDate;

    // editText의 텍스트가 바뀔때마다 실시간으로 변경이 되어야 하기에 해당 동작을 핸들러로 하기에는 위험 부담이 커보였다.
    public static ArrayList<String> carbookRecordItemExpenseMemoList = new ArrayList<>();
    public static ArrayList<String> carbookRecordItemExpenseCostList = new ArrayList<>();


    // 달력 다이얼로그에서 날짜를 정하고 확인 누를때 해당 MaintenanceOtherRecordActivity의 selectDate변수 값을 선택한 날짜로 지정해준다.
    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    // 먼저 더미데이터를 넣은 ArrayList를 만든 후 항목을 담당하는 리사이클러뷰 어뎁터로 자기 자신을 던져서 각 아이템마다 editText 값이 변경될때마다 더미데이터 대신 진짜 텍스트 데이터를 지정해준다.
    // 순서는 어차피 해당 리사이클러뷰 아이템 생성할때 selectItemTitleList와 똑같기에 순서가 틀어질 일은 없다.
    public void setItemDummyData() {
        for (int i = 0; i < selectItemTitleList.size(); i++) {
            carbookRecordItemExpenseMemoList.add("0");
            carbookRecordItemExpenseCostList.add("0");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_other_record);

        try {
            mContext = this;
            Intent intent = getIntent();
            if (intent != null) {
                carbookRecordId = getIntentData(intent);
            }
            setView();
            setFragment(1, getIntentDate());
            setItemDummyData();
            setActionView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setView() {
        tv_date = findViewById(R.id.tv_date);
        ln_date = findViewById(R.id.ln_date);
        maintenanceOtherRecordComplete = findViewById(R.id.maintenanceOtherRecordComplete);
    }

    // 상단 날짜 텍스트뷰 동작 세팅
    public void setActionView() {
        tv_date.setText(getDate() + getDateDay(mDate));
        ln_date.setOnClickListener(this);
        maintenanceOtherRecordComplete.setOnClickListener(this);
    }

    /***
     * 1. 항목 선택에서 항목을 선택하고 넘어온게 아닌 수정모드로 들어온것이라면 null을 반환
     * 2. 해당 구분은 먼저 mainRecordItemCarbookRecordId키값으로 int값을 받아보고 만약 초기값인 0보다 작으면 항목선택에서 넘어온것으로 보고 ArrayList<String>를 반환한다.
     * 3. 그게 아니라면 null을 반환한다.
     */
    public ArrayList<String> getIntentDate() {
        Intent intent = getIntent();
        carbookRecordId = intent.getIntExtra("mainRecordItemCarbookRecordId", 0);
        if(carbookRecordId <= 0){
            selectItemTitleList = (ArrayList<String>) intent.getSerializableExtra("selectItemTitleList");
            return selectItemTitleList;
        }else {
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_date:
                DialogManager.calenderDialog calenderDialog = new DialogManager.calenderDialog(mContext, tv_date, this);
                calenderDialog.show();
                break;
            case R.id.maintenanceOtherRecordComplete:
                try {
                    // 정비 기록 데이터 저장
                    CarbookRecord_DataBridge mainRecordDataBridge = new CarbookRecord_DataBridge();
                    CarbookRecordItem_DataBridge mainRecordItemDataBridge = new CarbookRecordItem_DataBridge();
                    Time_DataBridge time_dataBridge = new Time_DataBridge();
                    String nowTime = time_dataBridge.getRealTime();

                    // 기록 테이블은 하나씩 저장되니 반복문 필요 없다.
                    mainRecordDataBridge.MainRecordInsert(new CarbookRecord(0,
                            0,
                            selectDate,
                            0,
                            maintenanceOtherRecordFragment.getTotalDistance(),
                            nowTime,
                            nowTime));

                    for (int i = 0; i < selectItemTitleList.size(); i++) {
                        mainRecordItemDataBridge.MainRecordItemInsert(new CarbookRecordItem(mainRecordDataBridge.MainRecordSelectLastId(),
                                "123",
                                selectItemTitleList.get(i),
                                carbookRecordItemExpenseMemoList.get(i),
                                carbookRecordItemExpenseCostList.get(i),
                                0,
                                nowTime,
                                nowTime));
                    }
                    Intent intent = new Intent(MaintenanceOtherRecordActivity.this, MainrecordActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    // 현재 시간 구하기
    public String getDate() {
        mNow = System.currentTimeMillis(); // 디바이스 기준 표준 시간 적용
        mDate = new Date(mNow);            // Date 객체에 디바이스 표준 시간 적용

        // 사용자가 달력 날짜를 선택한다고 지정할때를 대비해서 처음에 사용자 선택 날짜를 오늘로 지정할때 미리 오늘 날짜로 연월일을 디비 저장용 해당 기록 지출 날짜로 지정해놓는다.
        selectDate = mFormat_saveOnly.format(mDate);
        return mFormat.format(mDate);      // SimpleDateFormat에 적용된 양식으로 시간값 문자열 반환
    }

    public String getDateDay(Date mDate) {
        String DAY_OF_WEEK = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayNum) {
            case 1:
                DAY_OF_WEEK = " (일)";
                break;
            case 2:
                DAY_OF_WEEK = " (월)";
                break;
            case 3:
                DAY_OF_WEEK = " (화)";
                break;
            case 4:
                DAY_OF_WEEK = " (수)";
                break;
            case 5:
                DAY_OF_WEEK = " (목)";
                break;
            case 6:
                DAY_OF_WEEK = " (금)";
                break;
            case 7:
                DAY_OF_WEEK = " (토)";
                break;
        }

        return DAY_OF_WEEK;
    }

    public void setFragment(int fragment, ArrayList<String> selectItemTitleList) {
        fragmentManager = getSupportFragmentManager();
        maintenanceOtherRecordFragment = new MaintenanceOtherRecordFragment(selectItemTitleList, carbookRecordId);
        locationSearchFragment = new LocationSearchFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragment) {
            case 1:
                //fragmentTransaction.replace(R.id.fl_RecordPage, maintenanceOtherRecordFragment).commitAllowingStateLoss();
                fragmentTransaction.replace(R.id.fl_RecordPage, maintenanceOtherRecordFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.fl_RecordPage, locationSearchFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    public int getIntentData(Intent intent) {
        return intent.getIntExtra("mainRecordItemCarbookRecordId", 0);
    }

}