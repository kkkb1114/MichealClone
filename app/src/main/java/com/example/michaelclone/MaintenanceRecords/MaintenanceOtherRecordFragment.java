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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import com.example.michaelclone.Data_FuelingRecord;
import com.example.michaelclone.FuelingRecord.VpAp_fueling;
import com.example.michaelclone.R;

import java.io.File;
import java.util.ArrayList;

public class MaintenanceOtherRecordFragment extends Fragment {
    Context context;

    // 카메라, 앨범 선택 핸들러
    public static ActivityResultLauncher<Intent> mStartForResult;

    // 이미지 첨부 변수
    RecyclerView rv_fueling;
    VpAp_fueling vpAp_fueling;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<String> typeList = new ArrayList<>(); // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃

    Data_FuelingRecord data_fuelingRecord;
    TextView fueling_imageCount;
    Context mContext;

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
        RequestPermission();
        return view;
    }

    public void setView(View view){
        fueling_imageCount = view.findViewById(R.id.fueling_imageCount);
        rv_fueling = view.findViewById(R.id.rv_fueling);
    }


    // 뷰페이저 세팅
    public void set_ViewPager(){
        typeList.add("0");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_fueling.setLayoutManager(linearLayoutManager);
        vpAp_fueling = new VpAp_fueling(mContext, bitmapArrayList, typeList, fueling_imageCount);
        rv_fueling.setAdapter(vpAp_fueling);
    }


    void cropImage() { //todo 여기서 사용자들이 좀 터진다. 카메라만 관련되면 터지는 것 같다. 해당 라인은 크롭에서 터지는데 이 부분은 좀 알아봐야겠다.
        try {
            PackageManager packageManager = mContext.getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                mContext.grantUriPermission("com.android.camera", Uri.parse(data_fuelingRecord.getImageUri()),
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(Uri.parse(data_fuelingRecord.getImageUri()), "image/*");

                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(data_fuelingRecord.getImageUri()));
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
                Toast.makeText(mContext, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }

    // registerForActivityResult 코드가 길어 메소드로 만듬
    public void mStartForResult(){
        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result != null){
                            data_fuelingRecord = new Data_FuelingRecord();
                            if (data_fuelingRecord.getType() == 0 && result.getResultCode() == RESULT_OK) { //todo 카메라
                                Intent intent = result.getData();

                                //cropImage();
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
                                vpAp_fueling.notifyDataSetChanged();
                            }else if (data_fuelingRecord.getType() == 1 && result.getResultCode() == RESULT_OK){ //todo 앨범

                                // 여러장을 선택 가능하게 해놓았기에 getClipData()에서 가져와야한다.
                                if (result != null){
                                    if (result.getData().getClipData() == null){
                                        Toast.makeText(mContext, "다중선택이 불가한 기기입니다.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        ClipData clipData = result.getData().getClipData();
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
                                                }
                                                typeList.clear(); // 이미지 추가 아이템을 맨 뒤로 보내야 하기에 초기화 시켜주고 다시 넣는다.
                                                for (int i=0; i<bitmapArrayList.size(); i++){
                                                    typeList.add("1");
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
}
