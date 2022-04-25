package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.michaelclone.DataBase.MainRecordItem;
import com.example.michaelclone.DataBase.MainRecordItem_DataBridge;
import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.DataBase.Time_DataBridge;
import com.example.michaelclone.DialogManager;
import com.example.michaelclone.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MaintenanceOtherRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MaintenanceOtherRecordFragment maintenanceOtherRecordFragment;
    private locationSearchFragment locationSearchFragment;

    TextView maintenanceOtherRecordComplete;

    LinearLayout ln_date;
    TextView tv_date;
    Context mContext;

    // 날짜 구하기 변수
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_other_record);

        try {
            mContext = this;
            setView();
            setActionView();
            setFragment(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setView(){
        tv_date = findViewById(R.id.tv_date);
        ln_date = findViewById(R.id.ln_date);
        maintenanceOtherRecordComplete = findViewById(R.id.maintenanceOtherRecordComplete);
    }

    // 상단 날짜 텍스트뷰 동작 세팅
    public void setActionView() {
        ln_date.setOnClickListener(this);
        maintenanceOtherRecordComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ln_date:
                tv_date.setText(getDate()+getDateDay(mDate));
                DialogManager.calenderDialog calenderDialog = new DialogManager.calenderDialog(mContext, tv_date);
                calenderDialog.show();
                break;
            case R.id.maintenanceOtherRecordComplete:

                MainRecordItem_DataBridge mainRecordItemDataBridge = new MainRecordItem_DataBridge();

                for (int i=0; i<MainRecord_Data.mainRecordItemArrayList.size(); i++){
                    MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryCode = "123";
                    MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemIsHidden = 0;
                    MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordId = 0;
                    MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemRegTime = new Time_DataBridge().getRealTime();
                    MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemUpdateTime = new Time_DataBridge().getRealTime();

                    mainRecordItemDataBridge.MainRecordItemInsert(new MainRecordItem(MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordId,
                            MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryCode,
                            MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryName,
                            MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemExpenseMemo,
                            MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemExpenseCost,
                            MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemIsHidden,
                            new Time_DataBridge().getGlobalTime(),
                            new Time_DataBridge().getRealTime()));
                }

                for (int i=0; i<MainRecord_Data.mainRecordItemArrayList.size(); i++){
                    Log.i("기록 완료 입력 데이터 체크", MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryName);
                    Log.i("기록 완료 입력 데이터 체크", MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemExpenseMemo);
                    Log.i("기록 완료 입력 데이터 체크", MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemExpenseCost);
                    Log.i("기록 완료 입력 데이터 체크", MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryCode);
                    Log.i("기록 완료 입력 데이터 체크", String.valueOf(MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemIsHidden));
                    Log.i("기록 완료 입력 데이터 체크", MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemRegTime);
                    Log.i("기록 완료 입력 데이터 체크", MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemUpdateTime);
                }
                break;
        }
    }

    // 현재 시간 구하기
    public String getDate(){
        mNow = System.currentTimeMillis(); // 디바이스 기준 표준 시간 적용
        mDate = new Date(mNow);            // Date 객체에 디바이스 표준 시간 적용

        return mFormat.format(mDate);      // SimpleDateFormat에 적용된 양식으로 시간값 문자열 반환
    }

    public String getDateDay(Date mDate){
        String DAY_OF_WEEK = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayNum){
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

    public void setFragment(int fragment){
        fragmentManager = getSupportFragmentManager();
        maintenanceOtherRecordFragment = new MaintenanceOtherRecordFragment();
        locationSearchFragment = new locationSearchFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragment){
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

}