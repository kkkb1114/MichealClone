package com.example.michaelclone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.example.michaelclone.DataBase.CarbookRecord_Data;
import com.example.michaelclone.MaintenanceRecords.MaintenanceOtherRecordActivity;
import com.example.michaelclone.MaintenanceRecords.MaintenanceOtherRecordFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogManager extends AlertDialog.Builder {

    Context context;

    public DialogManager(Context context) {
        super(context);
        this.context = context;
    }

    public static class calenderDialog extends BaseDialog implements OnDateSelectedListener {
        // 날짜 구하기 변수
        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat mFormat_mmdd = new SimpleDateFormat("MM월 dd일");
        SimpleDateFormat mFormat_yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat mFormat_saveOnly = new SimpleDateFormat("yyyyMMdd");

        MaterialCalendarView materialCalendarView;
        TextView tv_dl_cal_date;
        TextView tv_dl_cal_year;
        TextView tv_date;
        Button bt_confirm;
        Button bt_cancel;
        String NowDate;
        String NowYear;
        MaintenanceOtherRecordActivity maintenanceOtherRecordActivity;

        public calenderDialog(@NonNull Context context, TextView tv_date, MaintenanceOtherRecordActivity maintenanceOtherRecordActivity) {
            super(context, R.layout.dl_calendar);
            this.tv_date = tv_date;
            this.maintenanceOtherRecordActivity = maintenanceOtherRecordActivity;
        }

        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            getDate();
            initView();
            setMaterialCalendarView();
            tv_dl_cal_date.setText(NowDate+getDateDay(mDate));
            tv_dl_cal_year.setText(NowYear);

        }

        public void initView(){
            materialCalendarView = findViewById(R.id.cv);
            tv_dl_cal_year = findViewById(R.id.dl_cal_year);
            tv_dl_cal_date = findViewById(R.id.dl_cal_date);
            bt_confirm = findViewById(R.id.bt_confirm);
            bt_cancel = findViewById(R.id.bt_cancel);
        }

        public void setMaterialCalendarView(){

            /*
            materialCalendarView.setTitleFormatter(new TitleFormatter() {
                @Override
                public CharSequence format(CalendarDay day) {
                    //calendarDay라는 클래스는 LocalDate클래스를 기반으로 만들어진 클래스다.
                    //때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                    // LocalDate로 변환하는 처리가 필요하다.
                    Date inpuText = day.getDate();
                    String[] calendarHeaderElements = inpuText.toString().split("-");
                    StringBuilder calendarHeaderBuilder = new StringBuilder();
                    calendarHeaderBuilder.append(calendarHeaderElements[0])
                            .append(" ")
                            .append(calendarHeaderElements[1]);
                    return calendarHeaderBuilder.toString();
                }
            });*/

            // 시간값 계산
            mNow = System.currentTimeMillis(); // 디바이스 기준 표준 시간 적용

            // 시간값 계산
            materialCalendarView.setCurrentDate(new Date(System.currentTimeMillis()));
            materialCalendarView.setTopbarVisible(true);
            materialCalendarView.setOnDateChangedListener(this);
            materialCalendarView.addDecorator(new TodayDecorator());
            materialCalendarView.addDecorator(new SaturdayDecorator());
            materialCalendarView.addDecorator(new SundayDecorator());

            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = mFormat.format(materialCalendarView.getSelectedDate().getDate());
                    Date date2 = materialCalendarView.getSelectedDate().getDate();
                    tv_date.setText(date+getDateDay(date2));

                    // 기록 확인 클릭시 선택한 날짜를 디비 저장용 해당 기록 지출 날짜로 지정해놓는다.
                    maintenanceOtherRecordActivity.setSelectDate(mFormat_saveOnly.format(materialCalendarView.getSelectedDate().getDate()));
                    // 지울것!!
                    Log.i("getSelectedDate", mFormat_saveOnly.format(materialCalendarView.getSelectedDate().getDate()));
                    dismiss();
                }
            });
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        // MaterialCalendarView 캘린더에서 날짜를 클릭시 이벤트 지정 메소드
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            // 날짜를 클릭할때마다 받아오는 CalendarDay객체를 이용하여 getDate()후 해당 Date객체로 년, 월, 요일 값을 다시 받아서 상단 텍스트뷰에 다시 set한다.
            NowDate = mFormat_mmdd.format(date.getDate());
            NowYear = mFormat_yyyy.format(date.getDate());

            tv_dl_cal_date.setText(NowDate+getDateDay(date.getDate()));
            tv_dl_cal_year.setText(NowYear);
        }


        // 현재 시간 구하기
        public void getDate(){
            mNow = System.currentTimeMillis(); // 디바이스 기준 표준 시간 적용
            mDate = new Date(mNow);            // Date 객체에 디바이스 표준 시간 적용

            NowDate = mFormat_mmdd.format(mDate);// SimpleDateFormat에 적용된 양식으로 시간값 문자열 반환
            NowYear = mFormat_yyyy.format(mDate);
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

        public class TodayDecorator implements DayViewDecorator {
            private final Calendar calendar = Calendar.getInstance();
            private CalendarDay date;

            public TodayDecorator(){

                date = CalendarDay.today();
                date = CalendarDay.from(calendar);
            }

            @Override
            public boolean shouldDecorate(CalendarDay day) {
                day.copyTo(calendar);
                int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
                return date != null && day.equals(date);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.CYAN));
                view.addSpan(new StyleSpan(Typeface.BOLD));
                view.addSpan(new RelativeSizeSpan(1.4f));
            }

        }

        public class SaturdayDecorator implements DayViewDecorator {

            private final Calendar calendar = Calendar.getInstance();

            public SaturdayDecorator(){
            }

            @Override
            public boolean shouldDecorate(CalendarDay day) {
                day.copyTo(calendar);
                int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
                return weekDay == Calendar.SATURDAY;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.BLUE));
            }
        }

        public class SundayDecorator implements DayViewDecorator {

            private final Calendar calendar = Calendar.getInstance();

            public SundayDecorator(){
            }

            @Override
            public boolean shouldDecorate(CalendarDay day) {
                day.copyTo(calendar);
                int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
                return weekDay == Calendar.SUNDAY;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.RED));
            }
        }
    }


    public static class SelectCameraAlbum_Dialog extends BaseDialog implements View.OnClickListener {
        Context context;
        LinearLayout Ln_Camera;
        LinearLayout Ln_Album;
        Data_Record data_Record;

        String rootPath;
        String newPath;
        Uri uri;

        // 날짜 구하기 변수
        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddss");


        public SelectCameraAlbum_Dialog(Context context) {
            super(context, R.layout.select_camer_aalbum_dialog);
            this.context = context;
            setView();
        }

        public void setView(){
            data_Record = new Data_Record();
            Ln_Camera = findViewById(R.id.Ln_Camera);
            Ln_Album = findViewById(R.id.Ln_Album);
            Ln_Camera.setOnClickListener(this);
            Ln_Album.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Ln_Camera :
                    set_CameraAlbum(0); // 카메라 촬영을 통해 이미지를 가져온다.

                    //여기까지 함 카메라 앨범 작업 중 intentCamera
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/MichaelClone/"; // 주차 위치 카메라 촬영시 기기 내부 이미지 저장 위치
                    String fileName = getDate()+".jpg";
                    // 지울것!!
                    Log.i("fileName", fileName);
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/MichaelClone",
                            fileName);
                    newPath = rootPath + fileName;
                    // 사진 보안정책이 바뀌여서 uri 설정 다르게 해줘야됨.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        uri = FileProvider.getUriForFile(context, "com.example.michaelclone.fileprovider", file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    data_Record.setImageUri(String.valueOf(uri)); // 메인 프레그먼트 사용할 uri 문자열로 변환해서 저장
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    intentCamera.putExtra("return-data", true);
                    MaintenanceOtherRecordFragment.mStartForResult.launch(intentCamera);

                    // 지울것!!
                    Log.i("카메라", String.valueOf(data_Record.getType()));
                    dismiss();
                    break;
                case R.id.Ln_Album :

                    set_CameraAlbum(1); // 앨범을 통해 이미지를 가져온다.

                    Intent intentAlbum = new Intent(Intent.ACTION_PICK);
                    intentAlbum.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intentAlbum.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intentAlbum.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    MaintenanceOtherRecordFragment.mStartForResult.launch(intentAlbum);
                    // 지울것!!
                    Log.i("앨범", String.valueOf(data_Record.getType()));
                    dismiss();
                    break;
            }
        }

        // type = 0: 카메라, 1: 앨범
        public void set_CameraAlbum(int type){
            data_Record.setType(type);
        }


        // 현재 시간 구하기
        public String getDate(){
            mNow = System.currentTimeMillis(); // 디바이스 기준 표준 시간 적용
            mDate = new Date(mNow);            // Date 객체에 디바이스 표준 시간 적용

            return mFormat.format(mDate);      // SimpleDateFormat에 적용된 양식으로 시간값 문자열 반환
        }

    }
}
