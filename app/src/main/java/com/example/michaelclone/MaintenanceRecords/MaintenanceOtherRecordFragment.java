package com.example.michaelclone.MaintenanceRecords;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.Data_Record;
import com.example.michaelclone.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MaintenanceOtherRecordFragment extends Fragment implements View.OnClickListener {

    Context context;

    // 선택 항목 리스트
    RecyclerView rv_MtOtRecorditemList;
    Ap_MaintenanceOtherRecord ap_maintenanceOtherRecord;
    ArrayList<String> MaintenanceOtherList = new ArrayList<>();

    // 카메라, 앨범 선택 핸들러
    public static ActivityResultLauncher<Intent> mStartForResult;

    // 이미지 첨부 변수
    RecyclerView rv_MtOtImageList;
    VpAp_maintenanceOther vpAp_maintenanceOther;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<String> typeList = new ArrayList<>(); // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃

    Data_Record data_record;
    TextView MtOt_imageCount;
    EditText et_cumulativeMileage;
    TextView tv_repairShop;
    TextView tv_selfMaintenance;
    TableRow tr_moRcord_location;

    // 카메라 찍을때 처음 일반 촬영하고 크롭으로 넘어가게끔 만들기 위한 변수
    boolean imageCrop = false;

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
        mStartForResult();
        set_ViewPager(); // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃
        set_RvSelectItem();
        setViewAction();
        setOnClick();
        RequestPermission();
        return view;
    }

    public void setView(View view){
        MtOt_imageCount = view.findViewById(R.id.MtOt_imageCount);
        rv_MtOtImageList = view.findViewById(R.id.rv_MtOtImageList);
        rv_MtOtRecorditemList = view.findViewById(R.id.rv_MtOtRecorditemList);
        et_cumulativeMileage = view.findViewById(R.id.et_cumulativeMileage);
        tv_repairShop = view.findViewById(R.id.tv_repairShop);
        tv_selfMaintenance = view.findViewById(R.id.tv_selfMaintenance);
        tr_moRcord_location = view.findViewById(R.id.tr_moRcord_location);
    }

    // 뷰들 예외처리 모음
    public void setViewAction(){
        // 누적 주행거리 천단위 콤마 적용
        et_cumulativeMileage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String Mileage = et_cumulativeMileage.getText().toString();
                /**
                 * 텍스트 두번 가져오면 ,가 포함되서 Long.parseLong가 안되는거다. 그래서 두번째 계산에서 터짐
                 * **/
                if(Mileage.contains("[,]")){
                    Mileage.replaceAll("[,]", "");
                }
                long cumulativeMileage = Long.parseLong(et_cumulativeMileage.getText().toString());
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                String calculatedCumulativeMileage = decimalFormat.format(cumulativeMileage);
                et_cumulativeMileage.setText(calculatedCumulativeMileage);
                return false;
            }
        });
    }

    public void setOnClick(){
        tv_repairShop.setOnClickListener(this);
        tv_selfMaintenance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_repairShop:
                /*context.getResources().getDrawable(R.drawable.check) 처럼 안드로이드에서
                drawable과 color를 위해 사용하였던 함수 getDrawable과 getColor를 더 이상 사용할 수 없게 되었다.
                이제부터는 ResourcesCompat를 이용해 주어야 한다.*/

                tv_selfMaintenance.setBackground(context.getResources().getDrawable(R.drawable.bt_round_transparent, null));
                tv_selfMaintenance.setTextColor(context.getResources().getColor(R.color.blackTransparent_20));

                // 이게 엑티비티에서는 ContextCompat 이게 먹히는데 프래그먼트에서 안먹힌다. 이유를 알아보자.
                Drawable img = getContext().getResources().getDrawable(R.drawable.check);
                img.setBounds(0, 0, 60, 60);
                tv_repairShop.setCompoundDrawables(img, null, null, null);
                tv_selfMaintenance.setCompoundDrawables(null, null, null, null);
                tv_repairShop.setBackground(context.getResources().getDrawable(R.drawable.bt_round_black, null));
                tv_repairShop.setTextColor(context.getResources().getColor(R.color.white));
                // 정비소 정비이면 위치 뷰를 보이게 꺼낸다.
                tr_moRcord_location.setVisibility(View.VISIBLE);
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
                break;
        }
    }

    // 선택 항목 리사이클러뷰 세팅
    public void set_RvSelectItem(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_MtOtRecorditemList.setLayoutManager(linearLayoutManager);
        ap_maintenanceOtherRecord = new Ap_MaintenanceOtherRecord(context, Data_MaintenanceRecords.al_itemTitleList);
        rv_MtOtRecorditemList.setAdapter(ap_maintenanceOtherRecord);
        ap_maintenanceOtherRecord.notifyDataSetChanged();
    }

    // 뷰페이저 세팅
    public void set_ViewPager(){
        typeList.add("0");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_MtOtImageList.setLayoutManager(linearLayoutManager);
        vpAp_maintenanceOther = new VpAp_maintenanceOther(context, bitmapArrayList, typeList, MtOt_imageCount);
        rv_MtOtImageList.setAdapter(vpAp_maintenanceOther);
    }

    void cropImage() { //todo 여기서 사용자들이 좀 터진다. 카메라만 관련되면 터지는 것 같다. 해당 라인은 크롭에서 터지는데 이 부분은 좀 알아봐야겠다.
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                // 지울것!!
                Log.i("data_fuelingRecord.getImageUri()", data_record.getImageUri());
                context.grantUriPermission("com.android.camera", Uri.parse(data_record.getImageUri()),
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(Uri.parse(data_record.getImageUri()), "image/*");

                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(data_record.getImageUri()));
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
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }


    public String getRealPathFromURI(Uri uri){
        String fullPath = null;
        final String column = "_data";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
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
                    cursor = context.getContentResolver().query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if(    shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(context, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }

    public Bitmap EditImageSize(Bitmap image){
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
    public void mStartForResult(){
        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result != null){
                            data_record = new Data_Record();
                            if (data_record.getType() == 0 && result.getResultCode() == RESULT_OK) { //todo 카메라

                                // 처음 촬영을 하고 크롭을 시킨다.
                                if(!imageCrop){

                                    cropImage();
                                    imageCrop = true;
                                // 크롭이 완료되면 다시 크롭 체크 변수를 false로 변경한다.
                                }else {
                                    Intent intent = result.getData();

                                    // Bundle로 데이터를 입력
                                    Bundle extras = result.getData().getExtras();

                                    // Bitmap으로 컨버전
                                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                                    // 이미지뷰에 Bitmap으로 이미지를 입력
                                    bitmapArrayList.add(imageBitmap);
                                    typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                    for (int i=0; i<bitmapArrayList.size(); i++){
                                        typeList.add("1");
                                    }
                                    if (bitmapArrayList.size() < 5){ // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                        typeList.add("0");
                                    }
                                    // 리사이클러뷰 새로고침
                                    vpAp_maintenanceOther.notifyDataSetChanged();
                                    imageCrop = false;
                                }

                            }else if (data_record.getType() == 1 && result.getResultCode() == RESULT_OK){ //todo 앨범

                                // 여러장을 선택 가능하게 해놓았기에 getClipData()에서 가져와야한다.
                                if (result != null){
                                    if (result.getData().getClipData() == null){
                                        Toast.makeText(context, "다중선택이 불가한 기기입니다.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        ClipData clipData = result.getData().getClipData();
                                        if (clipData.getItemCount() >= 6){ // 선택한 사진이 5장 초과면 제한 안내
                                            Toast.makeText(context, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                        }else if (clipData.getItemCount() == 1){ // 선택한 사진 1장이면 getClipData에 있는 이미지 리스트중 인덱스가 0에 있는 사진의 uri를 지정해준다.
                                            if (clipData.getItemCount()+bitmapArrayList.size() > 5){ // 선택한 사진 + 이미 있는 사진 개수가 5개 초과면 제한 안내
                                                Toast.makeText(context, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                            }else {
                                                String imagePath = getRealPathFromURI(clipData.getItemAt(0).getUri());
                                                File file = new File(imagePath);
                                                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                                bitmapArrayList.add(bitmap);
                                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                                for (int i=0; i<bitmapArrayList.size(); i++){
                                                    typeList.add("1");
                                                }
                                                if (bitmapArrayList.size() < 5){ // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                                    typeList.add("0");
                                                }
                                                // 리사이클러뷰 새로고침
                                                vpAp_maintenanceOther.notifyDataSetChanged();
                                            }
                                        }else if (clipData.getItemCount() <= 5){
                                            if (clipData.getItemCount()+bitmapArrayList.size() > 5){ // 선택한 사진 + 이미 있는 사진 개수가 5개 초과면 제한 안내
                                                Toast.makeText(context, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                                            }else {
                                                for (int i=0; i<clipData.getItemCount(); i++){
                                                    String imagePath = getRealPathFromURI(clipData.getItemAt(i).getUri());
                                                    File file = new File(imagePath);
                                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                                    bitmapArrayList.add(EditImageSize(bitmap)); // 여기에 비트맵을 넣고 크기를 편집하고 다시 비트맵을 반환한다.
                                                }
                                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                                for (int i=0; i<bitmapArrayList.size(); i++){
                                                    typeList.add("1");
                                                }
                                                if (bitmapArrayList.size() < 5){ // 이미지 리스트가 5이하면 이미지 추가 버튼 생성
                                                    typeList.add("0");
                                                }
                                                // 리사이클러뷰 셋
                                                rv_MtOtImageList.setAdapter(vpAp_maintenanceOther);
                                                vpAp_maintenanceOther.notifyDataSetChanged();
                                            }
                                        }
                                        MtOt_imageCount.setText(bitmapArrayList.size()+"/5");
                                    }
                                }
                            }
                        }
                    }
                });
    }
}