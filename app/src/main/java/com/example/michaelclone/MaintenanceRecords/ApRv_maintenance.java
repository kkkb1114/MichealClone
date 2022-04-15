package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ApRv_maintenance extends RecyclerView.Adapter<ApRv_maintenance.ViewHolder> {

    ArrayList<String> ItemTitleList;
    ArrayList<String> ItemDistanceList;
    ArrayList<String> ItemLifeSpanList;
    ArrayList<Integer> ItemTypeList;
    HashMap<Integer, Boolean> checkedHashMap_maintenance = new HashMap<>();
    Context context;
    boolean setChecked = false; // checkedHashMap_maintenance 처음에 초기화 했는지 확인 변수

    /**
     * 1. ItemTypeList을 기준으로 항목을 추가할지 새 항목 추가 버튼을 추가할지 정해지며 | 0: 항목, 1: 추가버튼 | 이다.
     *
     * **/
    public ApRv_maintenance(Context context, ArrayList<String> ItemTitleList, ArrayList<String> ItemDistanceList, ArrayList<String> ItemLifeSpanList,
                            ArrayList<Integer> ItemTypeList){
        this.ItemTitleList = ItemTitleList;
        this.ItemDistanceList = ItemDistanceList;
        this.ItemLifeSpanList = ItemLifeSpanList;
        this.ItemTypeList = ItemTypeList;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position){
        // 0: 항목 레이아웃, 1: 항목 추가 버튼
        if (ItemTypeList.get(position) == 0){
            return 0;
        }else {
            return 1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (viewType == 0){
            view = layoutInflater.inflate(R.layout.maintenance_records_item, parent, false);
        }else {
            view = layoutInflater.inflate(R.layout.add_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (ItemTypeList.get(position) == 0){
            holder.bindView(position, ItemTitleList, ItemDistanceList, ItemLifeSpanList);
            checkCheckBox(holder, position);
            // 지울것!!
            Log.i("정비 항목 새로고침", "onBindViewHolder");
        }
    }

    @Override
    public int getItemCount() {
        return ItemTypeList.size();
    }

    /*
    * 1. 처음에 항목들의 체크 상태를 저장하는 해쉬맵에 항목 수만큼 false를 넣는다.
    * 2. 다 넣은 후에는 리사이클러뷰가 뷰를 재활용할때마다 바인드를 거치면서 포지션과 해쉬맵에 들어있는 포지션 키값을 비교하여
    *    체크를 boolean 값을 체크한다.
    * */
    public void checkCheckBox(ViewHolder holder, int position){

        // 처음에 checkedHashMap_maintenance에 ItemTitleList크기만큼 false를 넣는다.
        if (!setChecked){ // 체크
            for (int i=0; i<ItemTitleList.size(); i++){
                checkedHashMap_maintenance.put(i, false);
            }
            setChecked = true; // 완료
        }

        if (checkedHashMap_maintenance.get(position)){
            holder.cb_maintenance_itemSelect.setChecked(true);
        }else {
            holder.cb_maintenance_itemSelect.setChecked(false);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_maintenance_itemTitle;
        TextView tv_maintenance_itemDistance;
        TextView tv_maintenance_itemMonth;
        TextView tv_maintenance_itemSubText01;
        TextView tv_maintenance_itemSubText02;
        CheckBox cb_maintenance_itemSelect;
        View View_maintenance_itemDividingLine;
        View View_maintenance_itemDividingLine02;
        LinearLayout Ln_maintenance_item;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            setView();
        }

        public void setView(){
            tv_maintenance_itemTitle = itemView.findViewById(R.id.tv_maintenance_itemTitle);
            tv_maintenance_itemDistance = itemView.findViewById(R.id.tv_maintenance_itemDistance);
            tv_maintenance_itemMonth = itemView.findViewById(R.id.tv_maintenance_itemMonth);
            tv_maintenance_itemSubText01 = itemView.findViewById(R.id.tv_maintenance_itemSubText01);
            tv_maintenance_itemSubText02 = itemView.findViewById(R.id.tv_maintenance_itemSubText02);
            cb_maintenance_itemSelect = itemView.findViewById(R.id.cb_maintenance_itemSelect);
            View_maintenance_itemDividingLine = itemView.findViewById(R.id.View_maintenance_itemDividingLine);
            View_maintenance_itemDividingLine02 = itemView.findViewById(R.id.View_maintenance_itemDividingLine02);
            Ln_maintenance_item = itemView.findViewById(R.id.Ln_maintenance_item);
        }

        public void bindView(int position, ArrayList<String> ItemTitleList, ArrayList<String> ItemDistanceList
                , ArrayList<String> ItemLifeSpanList){

            // 지울것!!
            Log.i("정비 항목 새로고침", "bindView");
            tv_maintenance_itemTitle.setText(ItemTitleList.get(position));
            tv_maintenance_itemDistance.setText(ItemDistanceList.get(position));
            tv_maintenance_itemMonth.setText(ItemLifeSpanList.get(position));

            cb_setChecked(position);
            Ln_maintenance_item.setOnClickListener(clickListener);
            cb_maintenance_itemSelect.setOnClickListener(clickListener);

            itemBlock();
        }

        public void itemBlock(){
            if (Data_MaintenanceRecords.MaintenanceSingleItemboolean){
                tv_maintenance_itemTitle.setTextColor(Color.parseColor("#D3D3D3"));
                tv_maintenance_itemDistance.setTextColor(Color.parseColor("#D3D3D3"));
                tv_maintenance_itemMonth.setTextColor(Color.parseColor("#D3D3D3"));
                tv_maintenance_itemSubText01.setTextColor(Color.parseColor("#D3D3D3"));
                tv_maintenance_itemSubText02.setTextColor(Color.parseColor("#D3D3D3"));
                cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#1A000000")));
                View_maintenance_itemDividingLine.setBackgroundColor(Color.parseColor("#1A000000"));
                View_maintenance_itemDividingLine02.setBackgroundColor(Color.parseColor("#1A000000"));
                cb_maintenance_itemSelect.setClickable(false);
                Ln_maintenance_item.setClickable(false);
                // 지울것!!
                Log.i("정비 항목 새로고침", "itemBlock");
            }else {
                tv_maintenance_itemTitle.setTextColor(Color.parseColor("#000000"));
                tv_maintenance_itemDistance.setTextColor(Color.parseColor("#80000000"));
                tv_maintenance_itemMonth.setTextColor(Color.parseColor("#80000000"));
                tv_maintenance_itemSubText01.setTextColor(Color.parseColor("#80000000"));
                tv_maintenance_itemSubText02.setTextColor(Color.parseColor("#80000000"));
                cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                View_maintenance_itemDividingLine.setBackgroundColor(Color.parseColor("#33000000"));
                View_maintenance_itemDividingLine02.setBackgroundColor(Color.parseColor("#33000000"));
                cb_maintenance_itemSelect.setClickable(true);
                Ln_maintenance_item.setClickable(true);
            }
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Ln_maintenance_item:
                        if (!cb_maintenance_itemSelect.isChecked()){
                            cb_maintenance_itemSelect.setChecked(true);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_maintenance_itemTitle.getText().toString(), 0);
                        }else {
                            cb_maintenance_itemSelect.setChecked(false);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_maintenance_itemTitle.getText().toString(), 1);
                        }
                        break;
                    case R.id.cb_maintenance_itemSelect:
                        if (cb_maintenance_itemSelect.isChecked()){
                            cb_maintenance_itemSelect.setChecked(true);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_maintenance_itemTitle.getText().toString(), 0);
                        }else {
                            cb_maintenance_itemSelect.setChecked(false);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_maintenance_itemTitle.getText().toString(), 1);
                        }
                        break;
                }
            }
        };

        public void cb_setChecked(int position){
            cb_maintenance_itemSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                        checkedHashMap_maintenance.replace(position, true);
                        cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00D4FF")));

                        // 지울것!!
                        Log.i("정비 항목 새로고침", "cb_setChecked_1");
                    }else {
                        // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                        checkedHashMap_maintenance.replace(position, false);
                        cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));

                        // 지울것!!
                        Log.i("정비 항목 새로고침", "cb_setChecked_2");
                    }
                }
            });
        }
    }
}
