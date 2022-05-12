package com.example.michaelclone.FuelingRecord;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelclone.Data_Record;
import com.example.michaelclone.DialogManager;
import com.example.michaelclone.R;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FuelingRecordActivity extends AppCompatActivity {

    ArrayList<Data_Record> data_RecordArrayList;
    Data_Record data_Record;
    LinearLayout ln_date;
    TextView tv_date;
    TextView fueling_imageCount;
    Context mContext;

    // 이미지 첨부 변수
    RecyclerView rv_fueling;
    VpAp_fueling vpAp_fueling;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<String> typeList = new ArrayList<>(); // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃
    String[] array;

    // 카메라, 앨범 선택 핸들러
    public static ActivityResultLauncher<Intent> mStartForResult;

    // 주유 금액 변수
    ImageView iv_check_putOilPrice;
    ImageView iv_check_fuelVolume;
    TextView tv_fuelingMemoCount;
    EditText et_oilPrice;
    EditText et_putOilPrice;
    EditText et_fuelVolume;
    EditText et_carWash;
    EditText ed_fuelingMemo;
    int putOilPrice = 0;
    int oilPrice = 2038;
    int fuelVolume = 0;
    int carWash = 0;

    // 날짜 구하기 변수
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String date;

    // 주유 금액, 주유량 자동 계산 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fueling_record);

        try {
        mContext = this;
        setView();
        setTextView();
        Automatic_calculation_fueling(); // 주유금액 자동 계산
        set_ViewPager(); // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃
        mStartForResult();
        RequestPermission();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setView(){
        tv_date = findViewById(R.id.tv_date);
        ln_date = findViewById(R.id.ln_date);
        et_oilPrice = findViewById(R.id.et_oilPrice);
        et_putOilPrice = findViewById(R.id.et_putOilPrice);
        fueling_imageCount = findViewById(R.id.fueling_imageCount);
        et_fuelVolume = findViewById(R.id.et_fuelVolume);
        et_carWash = findViewById(R.id.et_carWash);
        iv_check_putOilPrice = findViewById(R.id.iv_check_putOilPrice);
        iv_check_fuelVolume = findViewById(R.id.iv_check_fuelVolume);
        ed_fuelingMemo = findViewById(R.id.ed_fuelingMemo);
        tv_fuelingMemoCount = findViewById(R.id.tv_fuelingMemoCount);;
        rv_fueling = findViewById(R.id.rv_fueling);
    }

    // 상단 날짜 텍스트뷰 동작 세팅
    public void setTextView() {
       /* tv_date.setText(getDate()+getDateDay(mDate));
        DialogManager.calenderDialog calenderDialog = new DialogManager.calenderDialog(mContext, tv_date, this);
        ln_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderDialog.show();
            }
        });*/
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

    // 주유금액 자동 계산
    public void Automatic_calculation_fueling(){
        // 입력된 숫자 값을 디바이스 기준 지역 통화 표현??으로 바꾸었다
        String oilprice_result = NumberFormat.getInstance(Locale.getDefault()).format(oilPrice);
        et_oilPrice.setText(oilprice_result);

        // EditText 입력값 변경후 엔터시 호출
        // 주유 금액
        et_putOilPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String get_putOilPrice = et_putOilPrice.getText().toString();
                if (get_putOilPrice.contains(",")){
                    // 지울것!!
                    Log.i("get_putOilPrice", get_putOilPrice);
                    get_putOilPrice = get_putOilPrice.replaceAll(",", "");
                    // 지울것!!
                    Log.i("get_putOilPrice", get_putOilPrice);
                }
                putOilPrice = Integer.parseInt(get_putOilPrice);
                double result = (double) putOilPrice / (double) oilPrice;
                Log.i("putOilPrice", String.valueOf(putOilPrice));
                Log.i("oilPrice", String.valueOf(oilPrice));
                Log.i("result", String.valueOf(result));
                et_fuelVolume.setText(String.format("%.3f", result));

                String putOilPrice_result = NumberFormat.getInstance(Locale.getDefault()).format(putOilPrice);
                et_putOilPrice.setText(putOilPrice_result);

                iv_check_putOilPrice.setVisibility(View.GONE);
                iv_check_fuelVolume.setVisibility(View.VISIBLE);
                return false;
            }
        });
        
        // EditText 입력값 변경후 엔터시 호출
        // 주유량
        et_fuelVolume.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Integer.parseInt()는 소수 문자열 6.0 같은 것을 못받지만 Double.parseDouble는 전부 받을 수 있기에 Double.parseDouble로 먼저 변환 후 정수로 바꾸었다.
                fuelVolume = (int) Double.parseDouble(et_fuelVolume.getText().toString());
                int result = fuelVolume * oilPrice;
                String putOilPrice_result = NumberFormat.getInstance(Locale.getDefault()).format(result);
                et_putOilPrice.setText(putOilPrice_result);

                iv_check_putOilPrice.setVisibility(View.VISIBLE);
                iv_check_fuelVolume.setVisibility(View.GONE);
                return false;
            }
        });

        // 연료금액
        et_oilPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                oilPrice = Integer.parseInt(et_oilPrice.getText().toString());
                String oilPrice_result = NumberFormat.getInstance(Locale.getDefault()).format(oilPrice);
                et_oilPrice.setText(oilPrice_result);
                return false;
            }
        });

        // 세차비
        et_carWash.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                carWash = Integer.parseInt(et_carWash.getText().toString());
                String carWash_result = NumberFormat.getInstance(Locale.getDefault()).format(carWash);
                et_carWash.setText(carWash_result);
                return false;
            }
        });

        // 메모 실시간 글자 수 세주기
        ed_fuelingMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String input_fuelingMemo = ed_fuelingMemo.getText().toString();
                tv_fuelingMemoCount.setText(input_fuelingMemo.length()+"/250");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // 뷰페이저 세팅
    public void set_ViewPager(){
        typeList.add("0");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_fueling.setLayoutManager(linearLayoutManager);
        vpAp_fueling = new VpAp_fueling(mContext, bitmapArrayList, typeList, fueling_imageCount);
        rv_fueling.setAdapter(vpAp_fueling);
    }

    // registerForActivityResult 코드가 길어 메소드로 만듬
    public void mStartForResult(){
        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result != null){
                            data_Record = new Data_Record();
                            // 지울것!!
                            Log.i("결과", String.valueOf(result.getResultCode()));
                            if (data_Record.getType() == 0 && result.getResultCode() == RESULT_OK) { //todo 카메라
                                Intent intent = result.getData();

                                //cropImage();
                                // 지울것!!
                                Log.i("결과", String.valueOf(intent));
                                // 지울것!!
                                Log.i("data_fuelingRecord.getType()", String.valueOf(data_Record.getType()));
                                // Bundle로 데이터를 입력
                                Bundle extras = result.getData().getExtras();

                                // Bitmap으로 컨버전
                                Bitmap imageBitmap = (Bitmap) extras.get("data");

                                // 이미지뷰에 Bitmap으로 이미지를 입력
                                bitmapArrayList.add(imageBitmap);
                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                for (int i=0; i<bitmapArrayList.size(); i++){
                                    typeList.add("1");
                                    // 지울것!!
                                    Log.i("typeList", typeList.get(i));
                                }
                                if (bitmapArrayList.size() < 5){ // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                    typeList.add("0");
                                }
                                // 리사이클러뷰 새로고침
                                vpAp_fueling.notifyDataSetChanged();
                            }else if (data_Record.getType() == 1 && result.getResultCode() == RESULT_OK){ //todo 앨범

                                // 여러장을 선택 가능하게 해놓았기에 getClipData()에서 가져와야한다.
                                if (result != null){
                                    if (result.getData().getClipData() == null){
                                        Toast.makeText(mContext, "다중선택이 불가한 기기입니다.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        ClipData clipData = result.getData().getClipData();
                                        Log.i("ClipData 개수", String.valueOf(clipData.getItemCount()));
                                        if (clipData.getItemCount() >= 6){ // 선택한 사진이 5장 초과면 제한 안내
                                            Toast.makeText(mContext, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                        }else if (clipData.getItemCount() == 1){ // 선택한 사진 1장이면 getClipData에 있는 이미지 리스트중 인덱스가 0에 있는 사진의 uri를 지정해준다.
                                            if (clipData.getItemCount()+bitmapArrayList.size() > 5){ // 선택한 사진 + 이미 있는 사진 개수가 5개 초과면 제한 안내
                                                Toast.makeText(mContext, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                            }else {
                                                String imagePath = getRealPathFromURI(clipData.getItemAt(0).getUri());
                                                File file = new File(imagePath);
                                                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                                bitmapArrayList.add(bitmap);
                                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                                for (int i=0; i<bitmapArrayList.size(); i++){
                                                    typeList.add("1");
                                                    // 지울것!!
                                                    Log.i("typeList", typeList.get(i));
                                                }
                                                if (bitmapArrayList.size() < 5){ // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                                    typeList.add("0");
                                                }
                                                // 리사이클러뷰 새로고침
                                                vpAp_fueling.notifyDataSetChanged();
                                            }
                                        }else if (clipData.getItemCount() <= 5){
                                            if (clipData.getItemCount()+bitmapArrayList.size() > 5){ // 선택한 사진 + 이미 있는 사진 개수가 5개 초과면 제한 안내
                                                Toast.makeText(mContext, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                            }else {
                                                for (int i=0; i<clipData.getItemCount(); i++){
                                                    String imagePath = getRealPathFromURI(clipData.getItemAt(i).getUri());
                                                    File file = new File(imagePath);
                                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                                    bitmapArrayList.add(bitmap);
                                                    Log.i("clipData.getItemCount", String.valueOf(clipData.getItemAt(i).getUri()));
                                                }
                                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                                for (int i=0; i<bitmapArrayList.size(); i++){
                                                    typeList.add("1");
                                                    // 지울것!!
                                                    Log.i("typeList", typeList.get(i));
                                                }
                                                if (bitmapArrayList.size() < 5){ // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                                    typeList.add("0");
                                                }
                                                // 리사이클러뷰 셋
                                                rv_fueling.setAdapter(vpAp_fueling);
                                                vpAp_fueling.notifyDataSetChanged();
                                            }
                                        }
                                        fueling_imageCount.setText(bitmapArrayList.size()+"/5");
                                    }
                                }
                            }
                        }
                    }
                });
    }

    void cropImage() { //todo 여기서 사용자들이 좀 터진다. 카메라만 관련되면 터지는 것 같다. 해당 라인은 크롭에서 터지는데 이 부분은 좀 알아봐야겠다.
        try {
            PackageManager packageManager = mContext.getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                // 지울것!!
                Log.i("data_fuelingRecord.getImageUri()", data_Record.getImageUri());
                mContext.grantUriPermission("com.android.camera", Uri.parse(data_Record.getImageUri()),
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(Uri.parse(data_Record.getImageUri()), "image/*");

                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(data_Record.getImageUri()));
                mStartForResult.launch(cropIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*// 이미지 파일의 경로를 가져올 메소드
    private File createImageFile() throws IOException{
        // 파일이름을 세팅 및 저장경로 세팅 및 저장경로 세팅
        String Path = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    }*/

    // 앨범 .....
    public String getRealPathFromURI2(Uri uri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }


    public String getRealPathFromURI(Uri uri){
        String fullPath = null;
        final String column = "_data";
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            if (document_id == null){
                for (int i=0; i<cursor.getColumnCount(); i++){
                    if (column.equalsIgnoreCase(cursor.getColumnName(i))){
                        fullPath = cursor.getString(i);
                        break;
                    }
                }
            }else {
                document_id = document_id.substring(document_id.lastIndexOf(".")+1);
                cursor.close();

                final String[] projection = {column};
                try {
                    cursor = mContext.getContentResolver().query(
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null);
                    if (cursor != null){
                        cursor.moveToFirst();
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column));
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            }
        }
        return fullPath;
    }

    public void RequestPermission(){

        // storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if(    shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(mContext, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }
}