package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.michaelclone.DataBase.MainRecord;
import com.example.michaelclone.DataBase.MainRecordItem;
import com.example.michaelclone.DataBase.MainRecordItem_DataBridge;
import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.DataBase.MainRecord_DataBridge;
import com.example.michaelclone.DataBase.Time_DataBridge;
import com.example.michaelclone.DialogManager;
import com.example.michaelclone.MainRecord.MainrecordActivity;
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
    SimpleDateFormat mFormat_saveOnly = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_other_record);

        try {
            mContext = this;
            setView();
            setFragment(1);
            setActionView();
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
        tv_date.setText(getDate()+getDateDay(mDate));
        ln_date.setOnClickListener(this);
        maintenanceOtherRecordComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ln_date:
                //tv_date.setText(getDate()+getDateDay(mDate));
                DialogManager.calenderDialog calenderDialog = new DialogManager.calenderDialog(mContext, tv_date);
                calenderDialog.show();
                break;
            case R.id.maintenanceOtherRecordComplete:
                try {
                    // 정비 기록 데이터 저장
                    MainRecord_DataBridge mainRecordDataBridge = new MainRecord_DataBridge();
                    Time_DataBridge time_dataBridge = new Time_DataBridge();

                    MainRecord_Data.mainRecordArrayList.get(0).carbookRecordIsHidden = 0;
                    // 솔직히 여기에 넣지 않고 다이렉트로 넣어도 상관없지만 나중에 MainRecord에 변수로 선언 해놓기도 했고 나중에 내가 봤을때는 다이렉트로 넣었을 거라는 생각은
                    // 하지 않을 것 같아 이렇게 넣는다.
                    MainRecord_Data.mainRecordArrayList.get(0).carbookRecordRegTime = new Time_DataBridge().getRealTime();
                    MainRecord_Data.mainRecordArrayList.get(0).carbookRecordUpdateTime = new Time_DataBridge().getRealTime();

                    mainRecordDataBridge.MainRecordInsert(new MainRecord(MainRecord_Data.mainRecordArrayList.get(0)._id,
                            MainRecord_Data.mainRecordArrayList.get(0).carbookRecordRepairMode,
                            MainRecord_Data.mainRecordArrayList.get(0).carbookRecordExpendDate,
                            MainRecord_Data.mainRecordArrayList.get(0).carbookRecordIsHidden,
                            MainRecord_Data.mainRecordArrayList.get(0).carbookRecordTotalDistance,
                            MainRecord_Data.mainRecordArrayList.get(0).carbookRecordRegTime,
                            MainRecord_Data.mainRecordArrayList.get(0).carbookRecordUpdateTime));

                    // 정비 항목 데이터 저장
                    MainRecordItem_DataBridge mainRecordItemDataBridge = new MainRecordItem_DataBridge();
                    for (int i=0; i<MainRecord_Data.mainRecordItemArrayList.size(); i++){
                        MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryCode = "123";
                        MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemIsHidden = 0;
                        MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordId = mainRecordDataBridge.MainRecordSelectLastId();
                        MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemRegTime = time_dataBridge.getRealTime();
                        MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemUpdateTime = time_dataBridge.getRealTime();

                        Log.i("!!!", String.valueOf(mainRecordDataBridge.MainRecordSelectLastId()));

                        mainRecordItemDataBridge.MainRecordItemInsert(new MainRecordItem(MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordId,
                                MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryCode,
                                MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemCategoryName,
                                MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemExpenseMemo,
                                MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemExpenseCost,
                                MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemIsHidden,
                                MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemRegTime,
                                MainRecord_Data.mainRecordItemArrayList.get(i).carbookRecordItemUpdateTime));
                    }
                    // 정비 항목 데이터 저장

                    for (int i=0; i<MainRecord_Data.mainRecordItemArrayList.size(); i++){
                        Log.i("기록 완료 입력 데이터 체크", String.valueOf(MainRecord_Data.mainRecordItemArrayList.get(i)));
                    }

                    Intent intent = new Intent(MaintenanceOtherRecordActivity.this, MainrecordActivity.class);
                    startActivity(intent);

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    // 현재 시간 구하기
    public String getDate(){
        mNow = System.currentTimeMillis(); // 디바이스 기준 표준 시간 적용
        mDate = new Date(mNow);            // Date 객체에 디바이스 표준 시간 적용

        // 사용자가 달력 날짜를 선택한다고 지정할때를 대비해서 처음에 사용자 선택 날짜를 오늘로 지정할때 미리 오늘 날짜로 연월일을 디비 저장용 해당 기록 지출 날짜로 지정해놓는다.
        MainRecord_Data.mainRecordArrayList.get(0).carbookRecordExpendDate = mFormat_saveOnly.format(mDate);
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