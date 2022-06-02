package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.michaelclone.DataBase.CarbookRecord;
import com.example.michaelclone.DataBase.CarbookRecordItem;
import com.example.michaelclone.DataBase.CarbookRecordItem_DataBridge;
import com.example.michaelclone.DataBase.CarbookRecord_DataBridge;
import com.example.michaelclone.DataBase.Time_DataBridge;
import com.example.michaelclone.DialogManager;
import com.example.michaelclone.MainRecord.MainrecordActivity;
import com.example.michaelclone.MainRecord.MainrecordFragment;
import com.example.michaelclone.R;
import com.example.michaelclone.Tools.CalendarData;
import com.example.michaelclone.Tools.StringFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MaintenanceOtherRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MaintenanceOtherRecordFragment maintenanceOtherRecordFragment;
    private LocationSearchFragment locationSearchFragment;

    // 수정 모드
    CarbookRecord carbookRecord = null;
    int carbookRecordId = 0;
    boolean isModifyMode = MainrecordActivity.isModify;

    TextView maintenanceOtherRecordComplete;

    LinearLayout ln_date;
    TextView tv_date;
    Context mContext;
    ArrayList<String> selectItemTitleList;

    // 날짜 구하기
    CalendarData calendarData = new CalendarData();
    long mNow;
    Date mDate;
    String selectDate;

    // editText의 텍스트가 바뀔때마다 실시간으로 변경이 되어야 하기에 해당 동작마다 다른 클래스의 핸들러를 타서 add 방식이 괜찮을까 싶어 static으로 했다.
    // TODO 이 해쉬맵들을 전부 지우고 해당 해쉬맵이 put되는곳에 항목 아이템 객체에 set시킨다. 1-3
    public static int carbookRecordRepairMode = 0;
    // 일단 static으로 만들어 놓고 실제 차계부 만들때는 로직을 바꾸는게 좋을 것 같다.
    public static CarbookRecord carbookRecords = null;
    public static ArrayList<CarbookRecordItem> carbookRecordItems = null;
    ArrayList<String> firstRecordSelectItemTitleList = new ArrayList<>();
    // 나중에 항목 수정사항 적용을 위한 기준 리스트
    ArrayList<String> carbookRecordItemsTitleStandardArrayList = new ArrayList<>();


    // 달력 다이얼로그에서 날짜를 정하고 확인 누를때 해당 MaintenanceOtherRecordActivity의 selectDate변수 값을 선택한 날짜로 지정해준다.
    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_other_record);
        try {
            mContext = this;
            // 해당 페이지에서 수정모드와 작성모드를 하나의 조건문으로 구분 지어야하기에 보기 쉽게 boolean값 isModifyMode으로 기준 잡았다.
            if (isModifyMode) {

                // 확인 누르기 전 선택 항목 데이터 리스트 객체 생성
                if (MainrecordActivity.beforeSelectItemTitleList == null) {
                    MainrecordActivity.createBeforeSelectItemTitleList();
                } else {
                    MainrecordActivity.removeBeforeSelectItemTitleList();
                    MainrecordActivity.createBeforeSelectItemTitleList();
                }
                Intent intent = getIntent();
                carbookRecordId = getModifyIntentData(intent);
                CarbookRecord_DataBridge carbookRecord_dataBridge = new CarbookRecord_DataBridge();
                carbookRecord = carbookRecord_dataBridge.getSelectCarbookRecord(carbookRecordId);
                getSelectItemDataList();

                // 처음에 완료 되는 순간 비교에 사용될 기준용 기존 항목 이름 리스트를 만든다.
                for (int i = 0; i < carbookRecordItems.size(); i++) {
                    carbookRecordItemsTitleStandardArrayList.add(carbookRecordItems.get(i).carbookRecordItemCategoryName);
                }
            }else {
                Intent intent = getIntent();
                firstRecordSelectItemTitleList = intent.getStringArrayListExtra("selectItemTitleList");
            }
            setView();
            setFragment(1, getItemSelectIntentDate());
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
        // carbookRecord가 null이 아니면 수정모드 / carbookRecord가 null이면 작성 모드인 것이다.
        if (carbookRecord != null) {
            Date date = getDate(carbookRecord.carbookRecordExpendDate);
            tv_date.setText(getStrDate(date) + calendarData.getDateDay(date));
        } else {
            mNow = System.currentTimeMillis(); // 디바이스 기준 표준 시간 적용
            mDate = new Date(mNow);            // Date 객체에 디바이스 표준 시간 적용
            tv_date.setText(getStrDate(mDate) + calendarData.getDateDay(mDate));
        }
        ln_date.setOnClickListener(this);
        maintenanceOtherRecordComplete.setOnClickListener(this);
    }

    public Date getDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /***
     * 1. 항목 선택에서 항목을 선택하고 넘어온게 아닌 수정모드로 들어온것이라면 null을 반환
     * 2. 해당 구분은 먼저 mainRecordItemCarbookRecordId키값으로 int값을 받아보고 만약 초기값인 0보다 작으면 항목선택에서 넘어온것으로 보고 ArrayList<String>를 반환한다.
     * 3. 그게 아니라면 null을 반환한다.
     */
    public ArrayList<String> getItemSelectIntentDate() {
        if (MainrecordActivity.beforeSelectItemTitleList != null) {
            //selectItemTitleList = MainrecordActivity.beforeSelectItemTitleList;
            Log.i("123123123", String.valueOf(firstRecordSelectItemTitleList));
            selectItemTitleList = firstRecordSelectItemTitleList; // todo 여기 건들임
        }
        return selectItemTitleList;
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

                    // 수정모드면 첫번째, 작성모드면 두번째 조건으로 탄다.
                    if (isModifyMode) { // < 수정 모드 >
                        // 기록 테이블은 하나씩 저장되니 반복문 필요 없다.
                        mainRecordDataBridge.getCarbookRecordUpdate(new CarbookRecord(carbookRecord._id,
                                carbookRecordRepairMode,
                                selectDate,
                                0,
                                maintenanceOtherRecordFragment.getTotalDistance(),
                                nowTime,
                                nowTime), carbookRecord._id);

                        // 수정된 리스트
                        ArrayList<String> carbookRecordItemsTitleModifyArrayList = new ArrayList<>();
                        for (int i=0; i<carbookRecordItems.size(); i++){
                            carbookRecordItemsTitleModifyArrayList.add(carbookRecordItems.get(i).carbookRecordItemCategoryName);
                        }


                        String memoUpdate;
                        String costUpdate;
                        // 데이터 수정 리스트 기준으로 돌려야 삭제까지 가능
                        for (int i = 0; i < carbookRecordItemsTitleStandardArrayList.size(); i++) {
                            if (carbookRecordItems.get(i) == null || carbookRecordItems.get(i).carbookRecordItemExpenseMemo.equals("")) {
                                memoUpdate = "";
                            } else {
                                memoUpdate = carbookRecordItems.get(i).carbookRecordItemExpenseMemo;
                            }
                            if (carbookRecordItems.get(i) == null || carbookRecordItems.get(i).carbookRecordItemExpenseCost.equals("")) {
                                costUpdate = "0";
                            } else {
                                costUpdate = carbookRecordItems.get(i).carbookRecordItemExpenseCost;
                            }
                            // 수정된 데이터 리스트에 i번째 기존 리스트 항목이 있다면 업데이트, 없다면 지운다.
                            if (carbookRecordItemsTitleModifyArrayList.contains(carbookRecordItemsTitleStandardArrayList.get(i))) {
                                mainRecordItemDataBridge.MainRecordItemUpdate(new CarbookRecordItem(carbookRecordItems.get(i)._id, carbookRecordId,
                                        "123",
                                        carbookRecordItems.get(i).carbookRecordItemCategoryName,
                                        memoUpdate,
                                        costUpdate,
                                        0,
                                        nowTime,
                                        nowTime), carbookRecordItems.get(i)._id, carbookRecordItems.get(i).carbookRecordId);
                            } else {
                                mainRecordItemDataBridge.MainRecordItemDelete(carbookRecordItems.get(i)._id);
                            }
                        }

                        String memoInsert;
                        String costInsert;
                        // 수정, 삭제 후에는 수정된 리스트를 기준으로 i번째 기준 리스트 id가 없다면 insert
                        for (int i = 0; i < carbookRecordItemsTitleModifyArrayList.size(); i++) {
                            if (carbookRecordItems.get(i) == null || carbookRecordItems.get(i).carbookRecordItemExpenseMemo.equals("")) {
                                memoInsert = "";
                            } else {
                                memoInsert = carbookRecordItems.get(i).carbookRecordItemExpenseMemo;
                            }
                            if (carbookRecordItems.get(i) == null || carbookRecordItems.get(i).carbookRecordItemExpenseCost.equals("")) {
                                costInsert = "0";
                            } else {
                                costInsert = carbookRecordItems.get(i).carbookRecordItemExpenseCost;
                            }
                            if (!carbookRecordItemsTitleStandardArrayList.contains(carbookRecordItemsTitleModifyArrayList.get(i))) {
                                mainRecordItemDataBridge.MainRecordItemInsert(new CarbookRecordItem(0, carbookRecordId,
                                        "123",
                                        carbookRecordItemsTitleModifyArrayList.get(i),
                                        memoInsert,
                                        costInsert,
                                        0,
                                        nowTime,
                                        nowTime));
                            }
                        }
                        // 수정 체크용 리스트 변수 초기화
                        carbookRecordItemsTitleStandardArrayList = null;
                        carbookRecordItemsTitleModifyArrayList = null;
                    } else { // < 작성 모드 >

                        // 기록 테이블은 하나씩 저장되니 반복문 필요 없다.
                        mainRecordDataBridge.mainRecordInsert(new CarbookRecord(0,
                                carbookRecordRepairMode,
                                selectDate,
                                0,
                                maintenanceOtherRecordFragment.getTotalDistance(),
                                nowTime,
                                nowTime));

                        String memoInsert;
                        String costInsert;
                        Log.i("carbookRecordItemsasdasdsad", String.valueOf(carbookRecordItems.size()));
                        for (int i = 0; i < selectItemTitleList.size(); i++) {
                            if (carbookRecordItems.get(i) == null || carbookRecordItems.get(i).carbookRecordItemExpenseMemo.equals("")) {
                                memoInsert = "";
                            } else {
                                memoInsert = carbookRecordItems.get(i).carbookRecordItemExpenseMemo;
                            }
                            if (carbookRecordItems.get(i) == null || carbookRecordItems.get(i).carbookRecordItemExpenseCost.equals("")) {
                                costInsert = "0";
                            } else {
                                costInsert = carbookRecordItems.get(i).carbookRecordItemExpenseCost;
                            }
                            mainRecordItemDataBridge.MainRecordItemInsert(new CarbookRecordItem(0, mainRecordDataBridge.mainRecordSelectLastId(),
                                    "123",
                                    selectItemTitleList.get(i),
                                    memoInsert,
                                    costInsert,
                                    0,
                                    nowTime,
                                    nowTime));
                        }

                    }
                    // 완료 버튼 클릭시 수정모드 전용 변수를 항상 null로 초기화한다.
                    carbookRecords = null;
                    carbookRecordItems = null;
                    MainrecordActivity.removeBeforeSelectItemTitleList();
                    // 확인 누르는 즉시 모든 동작은 마치고 전 화면 스택을 죄다 지워버린 후 인텐으로 화면을 새로 만들어서 넘긴다.
                    finishAffinity();
                    Intent intent = new Intent(mContext, MainrecordActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    // 현재 시간 구하기
    public String getStrDate(Date date) {

        // 사용자가 달력 날짜를 선택한다고 지정할때를 대비해서 처음에 사용자 선택 날짜를 오늘로 지정할때 미리 오늘 날짜로 연월일을 디비 저장용 해당 기록 지출 날짜로 지정해놓는다.
        selectDate = calendarData.getDateFormat(date, "yyyyMMdd");
        return calendarData.getDateFormat(date, "yyyy.MM.dd");
    }

    public void setFragment(int fragment, ArrayList<String> selectItemTitleList) {
        fragmentManager = getSupportFragmentManager();
        maintenanceOtherRecordFragment = new MaintenanceOtherRecordFragment(selectItemTitleList, carbookRecordId, isModifyMode);
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

    public int getModifyIntentData(Intent intent) {
        return intent.getIntExtra("mainRecordItemCarbookRecordId", 0);
    }

    public void getSelectItemDataList() {
        if (isModifyMode) {
            CarbookRecord_DataBridge mainRecordDataBridge = new CarbookRecord_DataBridge();
            carbookRecords = mainRecordDataBridge.getSelectCarbookRecord(carbookRecordId);
            Log.i("carbookRecords", String.valueOf(carbookRecords));

            CarbookRecordItem_DataBridge carbookRecordItem_dataBridge = new CarbookRecordItem_DataBridge();
            carbookRecordItems = carbookRecordItem_dataBridge.getMainRecordItemItemData(carbookRecordId);

            // 항목 아이템에 적용시킬 리스트 처음 세팅 (어차피 static이라 리사이클러뷰 어뎁터에 넣지 않는다.)
            selectItemTitleList = new ArrayList<>();
            for (int i = 0; i < carbookRecordItems.size(); i++){
                selectItemTitleList.add(carbookRecordItems.get(i).carbookRecordItemCategoryName);
            }
        }
    }
}