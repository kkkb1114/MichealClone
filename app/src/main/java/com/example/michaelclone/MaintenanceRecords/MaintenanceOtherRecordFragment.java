package com.example.michaelclone.MaintenanceRecords;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.CarbookRecord;
import com.example.michaelclone.DataBase.CarbookRecordItem;
import com.example.michaelclone.DataBase.CarbookRecordItem_DataBridge;
import com.example.michaelclone.DataBase.CarbookRecord_Data;
import com.example.michaelclone.DataBase.CarbookRecord_DataBridge;
import com.example.michaelclone.Data_Record;
import com.example.michaelclone.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MaintenanceOtherRecordFragment extends Fragment implements View.OnClickListener {

    Context context;

    // 선택 항목 리스트
    RecyclerView rv_MtOtRecorditemList;
    MaintenanceOtherRecordRecyclerViewAdapter maintenanceOtherRecordRecyclerViewAdapter;

    // 카메라, 앨범 선택 핸들러
    public static ActivityResultLauncher<Intent> mStartForResult;

    // 이미지 첨부 변수
    RecyclerView rv_MtOtImageList;
    MaintenanceOtherImageViewPagerAdapter maintenanceOtherImageViewPagerAdapter;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<String> typeList = new ArrayList<>(); // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃

    Data_Record data_record;
    TextView MtOt_imageCount;
    EditText et_cumulativeMileage;
    TextView tv_repairShop;
    TextView tv_selfMaintenance;
    TextView tv_addRecordItem;
    TableRow tr_moRcord_location;
    View View_maintenanceLocationLine;
    Spinner sp_changeLocation;
    ArrayList<String> selectItemTitleList;
    int carbookRecordId;
    CarbookRecord carbookRecords = null;
    ArrayList<CarbookRecordItem> carbookRecordItems = null;

    // 카메라 찍을때 처음 일반 촬영하고 크롭으로 넘어가게끔 만들기 위한 변수
    boolean imageCrop = false;

    public MaintenanceOtherRecordFragment(ArrayList<String> selectItemTitleList, int carbookRecordId) {
        this.selectItemTitleList = selectItemTitleList;
        this.carbookRecordId = carbookRecordId;
    }

    public String getTotalDistance() {
        String toTalDistance = et_cumulativeMileage.getText().toString().replace(",", "");
        return toTalDistance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_maintenance_other_record, container, false);

        setView(view);
        getSelectItemDataList();
        mStartForResult();
        set_ViewPager(); // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃
        set_RvSelectItem();
        setViewAction();
        setOnClick();
        RequestPermission();

        return view;
    }

    public void getSelectItemDataList(){
        if (carbookRecordId > 0){
            CarbookRecord_DataBridge mainRecordDataBridge = new CarbookRecord_DataBridge();
            carbookRecords = mainRecordDataBridge.getSelectCarbookRecord(carbookRecordId);

            CarbookRecordItem_DataBridge carbookRecordItem_dataBridge = new CarbookRecordItem_DataBridge();
            carbookRecordItems = carbookRecordItem_dataBridge.getMainRecordItemItemData(carbookRecordId);

            getSelectItemDataSetView();
        }
    }

    public void getSelectItemDataSetView(){
        et_cumulativeMileage.setText(carbookRecords.carbookRecordTotalDistance);
    }

    public void setView(View view) {
        MtOt_imageCount = view.findViewById(R.id.MtOt_imageCount);
        rv_MtOtImageList = view.findViewById(R.id.rv_MtOtImageList);
        rv_MtOtRecorditemList = view.findViewById(R.id.rv_MtOtRecorditemList);
        et_cumulativeMileage = view.findViewById(R.id.et_cumulativeMileage);
        tv_repairShop = view.findViewById(R.id.tv_repairShop);
        tv_selfMaintenance = view.findViewById(R.id.tv_selfMaintenance);
        tr_moRcord_location = view.findViewById(R.id.tr_moRcord_location);
        tv_addRecordItem = view.findViewById(R.id.tv_addRecordItem);
        View_maintenanceLocationLine = view.findViewById(R.id.View_maintenanceLocationLine);
        sp_changeLocation = view.findViewById(R.id.sp_changeLocation);
    }


    // 뷰들 예외처리 모음
    public void setViewAction() {
        // 누적 주행거리 천단위 콤마 적용
        /*et_cumulativeMileage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String Mileage = v.getText().toString();
                *//**
                 * 텍스트 두번 가져오면 ,가 포함되서 Long.parseLong가 안되는거다. 그래서 두번째 계산에서 터짐
                 * **//*
                long cumulativeMileage = Long.parseLong(Mileage.replace(",", ""));
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                String calculatedCumulativeMileage = decimalFormat.format(cumulativeMileage);
                et_cumulativeMileage.setText(calculatedCumulativeMileage);
                return false;
            }
        });*/

        et_cumulativeMileage.addTextChangedListener(textWatcherCumulativeMileage);
    }

    // 텍스트 바뀔때마다 MainRecord_Data.mainRecordArrayList안에 있는 mainRecord에 데이터 삽입
    TextWatcher textWatcherCumulativeMileage = new TextWatcher() {

        String CumulativeMileage = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(CumulativeMileage)) {
                CumulativeMileage = makeStringComma(s.toString().replace(",", ""));
                et_cumulativeMileage.setText(CumulativeMileage);
                et_cumulativeMileage.setSelection(et_cumulativeMileage.length());
                // 아래 방법은 CumulativeMileage 문자열 길이를 뽑아서 해당 길이가 4면 커서를 4만큼만 이동시키겠다 인데 Editable이걸 왜 만드는지 모르겠다.
                // 위처럼 setSelection()로 다이렉트로 커서 위치 시키면 바로 되는 것을 아래처럼 하는 이유를 조사해서 알기전까진 위 방법으로 할 생각이다.
                /*Editable editable = et_cumulativeMileage.getText();
                  Selection.setSelection(editable, CumulativeMileage.length());*/
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // 천단위 콤마 메소드
    protected String makeStringComma(String data) {
        if (data.length() == 0) {
            return "";
        } else {
            long value = Long.parseLong(data);
            DecimalFormat format = new DecimalFormat("###,###");
            return format.format(value);
        }
    }

    public void setOnClick() {
        tv_repairShop.setOnClickListener(this);
        tv_selfMaintenance.setOnClickListener(this);
        tv_addRecordItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 정비 모드 클릭할때마다 MainRecord_Data.mainRecordArrayList에 carbookRecordRepairMode를 지정해준다.
            // 0: 정비소, 1: 자가정비
            case R.id.tv_repairShop:
                /*context.getResources().getDrawable(R.drawable.check) 처럼 안드로이드에서
                drawable과 color를 위해 사용하였던 함수 getDrawable과 getColor를 더 이상 사용할 수 없게 되었다.
                이제부터는 ResourcesCompat를 이용해 주어야 한다.*/

                tv_selfMaintenance.setBackground(context.getResources().getDrawable(R.drawable.bt_round_transparent, null));
                tv_selfMaintenance.setTextColor(context.getResources().getColor(R.color.blackTransparent_20, null));

                // 이게 엑티비티에서는 ContextCompat 이게 먹히는데 프래그먼트에서 안먹힌다. 이유를 알아보자.
                Drawable img = getContext().getResources().getDrawable(R.drawable.check);
                img.setBounds(0, 0, 60, 60);
                tv_repairShop.setCompoundDrawables(img, null, null, null);
                tv_selfMaintenance.setCompoundDrawables(null, null, null, null);
                tv_repairShop.setBackground(context.getResources().getDrawable(R.drawable.bt_round_black, null));
                tv_repairShop.setTextColor(context.getResources().getColor(R.color.white));
                // 정비소 정비이면 위치 뷰를 보이게 꺼낸다.
                tr_moRcord_location.setVisibility(View.VISIBLE);
                View_maintenanceLocationLine.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_selfMaintenance:
             /*   tv_selfMaintenance.setCompoundDrawables();
                tv_selfMaintenance.setBackground();*/

                tv_repairShop.setBackground(context.getResources().getDrawable(R.drawable.bt_round_transparent, null));
                tv_repairShop.setTextColor(context.getResources().getColor(R.color.blackTransparent_20));
                Drawable img2 = getContext().getResources().getDrawable(R.drawable.check);
                img2.setBounds(0, 0, 60, 60);
                tv_selfMaintenance.setCompoundDrawables(img2, null, null, null);
                tv_repairShop.setCompoundDrawables(null, null, null, null);
                tv_selfMaintenance.setBackground(context.getResources().getDrawable(R.drawable.bt_round_black, null));
                tv_selfMaintenance.setTextColor(context.getResources().getColor(R.color.white));
                // 자가 정비이면 위치 뷰를 보이지 않게 숨긴다.
                tr_moRcord_location.setVisibility(View.GONE);
                View_maintenanceLocationLine.setVisibility(View.GONE);
                break;
            case R.id.tv_addRecordItem:
                requireActivity().finish();
                break;
        }
    }

    // 선택 항목 리사이클러뷰 세팅
    public void set_RvSelectItem() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_MtOtRecorditemList.setLayoutManager(linearLayoutManager);
        if (carbookRecordId > 0){
            if(selectItemTitleList == null){
                selectItemTitleList = new ArrayList<>();
            }
            for (int i = 0; i < carbookRecordItems.size(); i++){
                selectItemTitleList.add(carbookRecordItems.get(i).carbookRecordItemCategoryName);
            }
            maintenanceOtherRecordRecyclerViewAdapter = new MaintenanceOtherRecordRecyclerViewAdapter(context, selectItemTitleList);
        }else {
            maintenanceOtherRecordRecyclerViewAdapter = new MaintenanceOtherRecordRecyclerViewAdapter(context, selectItemTitleList);
        }
        rv_MtOtRecorditemList.setAdapter(maintenanceOtherRecordRecyclerViewAdapter);
        maintenanceOtherRecordRecyclerViewAdapter.notifyDataSetChanged();
    }

    // 뷰페이저 세팅
    public void set_ViewPager() {
        typeList.add("0");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_MtOtImageList.setLayoutManager(linearLayoutManager);
        maintenanceOtherImageViewPagerAdapter = new MaintenanceOtherImageViewPagerAdapter(context, bitmapArrayList, typeList, MtOt_imageCount);
        rv_MtOtImageList.setAdapter(maintenanceOtherImageViewPagerAdapter);
    }

    void cropImage() {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                /*context.grantUriPermission("com.android.camera", Uri.parse(data_record.getImageUri()),
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(Uri.parse(data_record.getImageUri()), "image/*");

                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(data_record.getImageUri()));*/
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                Uri pictureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(pictureUri, "image/*");

                cropIntent.putExtra("outputX", 200); //크롭한 이미지 x축 크기
                cropIntent.putExtra("outputY", 200); //크롭한 이미지 y축 크기
                cropIntent.putExtra("aspectX", 1); //크롭 박스의 x축 비율
                cropIntent.putExtra("aspectY", 1); //크롭 박스의 y축 비율
                cropIntent.putExtra("scale", true);
                cropIntent.putExtra("return-data", true);
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
    public String getRealPathFromURI2(Uri uri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }


    public String getRealPathFromURI(Uri uri) {
        String fullPath = null;
        final String column = "_data";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            if (document_id == null) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if (column.equalsIgnoreCase(cursor.getColumnName(i))) {
                        fullPath = cursor.getString(i);
                        break;
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(".") + 1);
                cursor.close();

                final String[] projection = {column};
                try {
                    cursor = context.getContentResolver().query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null);
                    if (cursor != null) {
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

    public void RequestPermission() {

        // storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(context, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }

    public Bitmap EditImageSize(Bitmap image) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        final int pxWidth = size.x;
        final int pxHeight = size.y;

        if (image.getWidth() > image.getHeight()) {
            Matrix mat = new Matrix();
            mat.postRotate(90);
            Bitmap bitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), mat, true);

            image = Bitmap.createScaledBitmap(bitmap, (int) (pxWidth / 5), (int) (pxHeight / 5), false);
        } else {
            image = Bitmap.createScaledBitmap(image, (int) (pxWidth / 5), (int) (pxHeight / 5), false);
        }
        return image;
    }

    // registerForActivityResult 코드가 길어 메소드로 만듬
    public void mStartForResult() {
        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result != null) {
                            data_record = new Data_Record();
                            if (data_record.getType() == 0 && result.getResultCode() == RESULT_OK) { //todo 카메라

                                // 처음 촬영을 하고 크롭을 시킨다.
                                if (!imageCrop) {

                                    cropImage();
                                    imageCrop = true;
                                    // 크롭이 완료되면 다시 크롭 체크 변수를 false로 변경한다.
                                } else {
                                    Intent intent = result.getData();

                                    // Bundle로 데이터를 입력
                                    Bundle extras = result.getData().getExtras();

                                    // Bitmap으로 컨버전
                                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                                    // 이미지뷰에 Bitmap으로 이미지를 입력
                                    bitmapArrayList.add(imageBitmap);
                                    typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                    for (int i = 0; i < bitmapArrayList.size(); i++) {
                                        typeList.add("1");
                                    }
                                    if (bitmapArrayList.size() < 5) { // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                        typeList.add("0");
                                    }
                                    // 리사이클러뷰 새로고침
                                    maintenanceOtherImageViewPagerAdapter.notifyDataSetChanged();
                                    imageCrop = false;
                                }

                            } else if (data_record.getType() == 1 && result.getResultCode() == RESULT_OK) { //todo 앨범

                                // 여러장을 선택 가능하게 해놓았기에 getClipData()에서 가져와야한다.
                                if (result != null) {
                                    if (result.getData().getClipData() == null) {
                                        Toast.makeText(context, "다중선택이 불가한 기기입니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        ClipData clipData = result.getData().getClipData();
                                        if (clipData.getItemCount() >= 6) { // 선택한 사진이 5장 초과면 제한 안내
                                            Toast.makeText(context, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                        } else if (clipData.getItemCount() == 1) { // 선택한 사진 1장이면 getClipData에 있는 이미지 리스트중 인덱스가 0에 있는 사진의 uri를 지정해준다.
                                            if (clipData.getItemCount() + bitmapArrayList.size() > 5) { // 선택한 사진 + 이미 있는 사진 개수가 5개 초과면 제한 안내
                                                Toast.makeText(context, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String imagePath = getRealPathFromURI(clipData.getItemAt(0).getUri());
                                                File file = new File(imagePath);
                                                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                                bitmapArrayList.add(bitmap);
                                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                                for (int i = 0; i < bitmapArrayList.size(); i++) {
                                                    typeList.add("1");
                                                }
                                                if (bitmapArrayList.size() < 5) { // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                                    typeList.add("0");
                                                }
                                                // 리사이클러뷰 새로고침
                                                maintenanceOtherImageViewPagerAdapter.notifyDataSetChanged();
                                            }
                                        } else if (clipData.getItemCount() <= 5) {
                                            if (clipData.getItemCount() + bitmapArrayList.size() > 5) { // 선택한 사진 + 이미 있는 사진 개수가 5개 초과면 제한 안내
                                                Toast.makeText(context, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                for (int i = 0; i < clipData.getItemCount(); i++) {
                                                    String imagePath = getRealPathFromURI(clipData.getItemAt(i).getUri());
                                                    File file = new File(imagePath);
                                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                                    bitmapArrayList.add(EditImageSize(bitmap)); // 여기에 비트맵을 넣고 크기를 편집하고 다시 비트맵을 반환한다.
                                                }
                                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                                for (int i = 0; i < bitmapArrayList.size(); i++) {
                                                    typeList.add("1");
                                                }
                                                if (bitmapArrayList.size() < 5) { // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                                    typeList.add("0");
                                                }
                                                // 리사이클러뷰 셋
                                                rv_MtOtImageList.setAdapter(maintenanceOtherImageViewPagerAdapter);
                                                maintenanceOtherImageViewPagerAdapter.notifyDataSetChanged();
                                            }
                                        }
                                        MtOt_imageCount.setText(bitmapArrayList.size() + "/5");
                                    }
                                }
                            }
                        }
                    }
                });
    }
}
