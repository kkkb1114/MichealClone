package com.example.michaelclone.FuelingRecord;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DialogManager;
import com.example.michaelclone.R;

import java.util.ArrayList;
import java.util.Locale;

public class VpAp_fueling extends RecyclerView.Adapter<VpAp_fueling.ViewHolder> {

    Context context;
    DialogManager.SelectCameraAlbum_Dialog selectCameraAlbum_dialog;
    ArrayList<Bitmap> bitmapArrayList;
    ArrayList<String> typeList;
    TextView fueling_imageCount;

   public VpAp_fueling(Context context, ArrayList<Bitmap> bitmapArrayList, ArrayList<String> typeList, TextView fueling_imageCount){
       this.context = context;
       this.bitmapArrayList = bitmapArrayList;
       this.typeList = typeList;
       this.fueling_imageCount = fueling_imageCount;
   }

   // onCreateViewHolder에서 viewType로 뷰를 구분하기 위해 getItemViewType를 오버라이드함.
    @Override
    public int getItemViewType(int position) {

        if (typeList.get(position).equals("0")) { // 0: 사진 추가 레이아웃
            return 0;
        } else { // 1: 사진 추가된 레이아웃
            return 1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // type이 1이면 이미지 적용된 레이아웃, 0이면 이미지 추가 레이아웃
        // 뷰 아이템 레이아웃 입혀서 생성
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemadd_vp_fueling, parent, false);
                // 자식 클래스 ViewHolder에 생성한 뷰 삽입하면서 객체 생성
                return new ViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vp_fueling, parent, false);
                // 자식 클래스 ViewHolder에 생성한 뷰 삽입하면서 객체 생성
                return new ViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemadd_vp_fueling, parent, false);
        // 자식 클래스 ViewHolder에 생성한 뷰 삽입하면서 객체 생성
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.bindItem(position, holder);
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout fl_fuelingItemadd;
        ImageView iv_fuelingItem;
        ImageView iv_fuelingItemadd;
        LinearLayout ln_fuelingItem_close;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 이미지 추가된 뷰
            iv_fuelingItem = itemView.findViewById(R.id.iv_fuelingItem);
            ln_fuelingItem_close = itemView.findViewById(R.id.ln_fuelingItem_close);

            // 이미지 추가 뷰
            iv_fuelingItemadd = itemView.findViewById(R.id.iv_fuelingItemadd);
            fl_fuelingItemadd = itemView.findViewById(R.id.fl_fuelingItemadd);
        }

        public void bindItem(int position, ViewHolder holder){
            if(typeList.get(position).equals("0")){
                setItemaddClick();
            }else {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();

                Point size = new Point();
                display.getSize(size);
                final int pxWidth = size.x;
                final int pxHeight = size.y;
                iv_fuelingItem.setImageBitmap(setItemImage(bitmapArrayList.get(position), pxWidth, pxHeight));
                setItemClick(position);
            }
        }

        // 아이템 이미지 크기 편집
        public Bitmap setItemImage(Bitmap bitmap, int pxWidth, int pxHeight){
            try {

                if (bitmap.getWidth() > bitmap.getHeight()) {
                    Matrix mat = new Matrix();
                    mat.postRotate(90);
                    Bitmap correctBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);

                   return bitmap = Bitmap.createScaledBitmap(correctBmp, (int) (pxWidth / 2.5), (int) (pxHeight / 2.5), false);
                } else {
                    return bitmap = Bitmap.createScaledBitmap(bitmap, (int) (pxWidth / 2.5), (int) (pxHeight / 2.5), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // 이미지 추가 아이템 클릭시 사진 관련 작업 세팅 메소드
        public void setItemaddClick(){
            fl_fuelingItemadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCameraAlbum_dialog = new DialogManager.SelectCameraAlbum_Dialog(context);
                    selectCameraAlbum_dialog.show();
                }
            });
        }
        // 이미지 제거 클릭시 리스트들 전부 작업
        public void setItemClick(int position){

            ln_fuelingItem_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bitmapArrayList.remove(position);
                    typeList.remove(position);
                    if (bitmapArrayList.size() == 4){ // 취소 버튼을 눌렀을때 이미지 추가버튼이 생성 되어야함.
                        typeList.add("0");
                    }
                    notifyDataSetChanged(); // 리사이클러뷰 어뎁터에서 새로고침
                    fueling_imageCount.setText(bitmapArrayList.size()+"/5");
                }
            });
        }
    }
}
