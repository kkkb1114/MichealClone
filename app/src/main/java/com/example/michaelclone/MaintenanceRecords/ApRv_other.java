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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ApRv_other extends RecyclerView.Adapter<ApRv_other.ViewHolder> {

    ArrayList<String> singleItemTitleList = new ArrayList<>();
    ArrayList<String> ItemTitleList;
    ArrayList<Integer> ItemTypeList;
    ArrayList<ViewHolder> holderArrayList = new ArrayList<>();
    HashMap<Integer, Boolean> checkedHashMap_other = new HashMap<>();
    Context context;
    boolean setChecked = false;
    ApRv_maintenance apRv_maintenance;

    // 시간
    String strNow;

    public ApRv_other(Context context, ArrayList<String> ItemTitleList, ArrayList<Integer> ItemTypeList, ApRv_maintenance apRv_maintenance) {
        this.context = context;
        this.ItemTitleList = ItemTitleList;
        this.ItemTypeList = ItemTypeList;
        this.apRv_maintenance = apRv_maintenance;
    }

    @Override
    public int getItemViewType(int position) {
        // 0: 항목 레이아웃, 1: 항목 추가 버튼
        if (ItemTypeList.get(position) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.other_records_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.add_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰 타입이 항목 타입이면 0: 항목, 1: 새 항목 추가
        if (ItemTypeList.get(position) == 0){
            holder.bindView(position, ItemTitleList.get(position));
            checkCheckBox(holder, position);
        }
        holderArrayList.add(holder);
    }

    @Override
    public int getItemCount() {
        return ItemTypeList.size();
    }


    public void checkCheckBox(ViewHolder holder, int position){

        // 처음에 checkedHashMap_maintenance에 ItemTitleList크기만큼 false를 넣는다.
        if (!setChecked){ // 체크
            for (int i=0; i<ItemTitleList.size(); i++){
                checkedHashMap_other.put(i, false);
            }
            setChecked = true; // 완료
        }

        /*처음 항목 생성해서 checkedHashMap를 처음 세팅할때의 경우
          - 처음에는 ItemTitleList 크기만큼 checkedHashMap에 false값을 넣어준다.
          - 그 후에 크기가 같아지면 position값으로 checkedHashMap에 들어있는 값을 체크하면서 holder의 체크 박스를 세팅해준다.*/
            // 해당 아이템에 체크박스 상태 확인
            if (checkedHashMap_other.get(position)){
                holder.cb_other_itemSelect.setChecked(true);
            }else {
                holder.cb_other_itemSelect.setChecked(false);
            }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_other_itemTitle;
        CheckBox cb_other_itemSelect;
        View View_other_itemDividingLine;
        LinearLayout Ln_other_item;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
        }

        public void setView() {
            tv_other_itemTitle = itemView.findViewById(R.id.tv_other_itemTitle);
            cb_other_itemSelect = itemView.findViewById(R.id.cb_other_itemSelect);
            View_other_itemDividingLine = itemView.findViewById(R.id.View_other_itemDividingLine);
            Ln_other_item = itemView.findViewById(R.id.Ln_other_item);
        }

        public void bindView(int position, String ItemTitle) {
            this.position = position;
            tv_other_itemTitle.setText(ItemTitle);
            cb_setChecked(position);
            Ln_other_item.setOnClickListener(clickListener);
            cb_other_itemSelect.setOnClickListener(clickListener);

            itemBlock();
        }

        public void itemBlock(){
            String other_itemTitle = tv_other_itemTitle.getText().toString();
            if (Data_MaintenanceRecords.MaintenanceSingleItemboolean){
                if (!other_itemTitle.equals(context.getResources().getString(R.string.carInsurance)) && !other_itemTitle.equals(Data_MaintenanceRecords.MaintenanceSingleItemTitle) ||
                        !other_itemTitle.equals(context.getResources().getString(R.string.trafficFine)) && !other_itemTitle.equals(Data_MaintenanceRecords.MaintenanceSingleItemTitle) ||
                        !other_itemTitle.equals(context.getResources().getString(R.string.automobileTax)) && !other_itemTitle.equals(Data_MaintenanceRecords.MaintenanceSingleItemTitle)){

                    tv_other_itemTitle.setTextColor(Color.parseColor("#D3D3D3"));
                    cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                    View_other_itemDividingLine.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    cb_other_itemSelect.setClickable(false);
                    Ln_other_item.setClickable(false);
                }else {
                    Toast.makeText(context, other_itemTitle +" "+ context.getResources().getString(R.string.selectSingleItemToast), Toast.LENGTH_SHORT).show();
                }

            }else {
                if (!other_itemTitle.equals(context.getResources().getString(R.string.carInsurance)) && !other_itemTitle.equals(Data_MaintenanceRecords.MaintenanceSingleItemTitle) ||
                        !other_itemTitle.equals(context.getResources().getString(R.string.trafficFine)) && !other_itemTitle.equals(Data_MaintenanceRecords.MaintenanceSingleItemTitle) ||
                        !other_itemTitle.equals(context.getResources().getString(R.string.automobileTax)) && !other_itemTitle.equals(Data_MaintenanceRecords.MaintenanceSingleItemTitle)){

                    tv_other_itemTitle.setTextColor(Color.parseColor("#000000"));
                    cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                    View_other_itemDividingLine.setBackgroundColor(Color.parseColor("#33000000"));
                    cb_other_itemSelect.setClickable(true);
                    Ln_other_item.setClickable(true);
                }
            }
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Ln_other_item:
                        if (!cb_other_itemSelect.isChecked()) {
                            cb_other_itemSelect.setChecked(true);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_other_itemTitle.getText().toString(), 0);

                            // 단일 항목들 체크
                            singleItemCheck(true);
                        } else {
                            cb_other_itemSelect.setChecked(false);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_other_itemTitle.getText().toString(), 1);

                            // 단일 항목들 체크
                            singleItemCheck(false);
                        }
                        break;
                    case R.id.cb_other_itemSelect:
                        if (cb_other_itemSelect.isChecked()) {
                            cb_other_itemSelect.setChecked(true);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_other_itemTitle.getText().toString(), 0);

                            // 단일 항목들 체크
                            singleItemCheck(true);
                        } else {
                            cb_other_itemSelect.setChecked(false);
                            SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_other_itemTitle.getText().toString(), 1);

                            // 단일 항목들 체크
                            singleItemCheck(false);
                        }
                        break;
                }
            }
        };


        public void cb_setChecked(int position) {
            cb_other_itemSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00D4FF")));

                        // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                        checkedHashMap_other.replace(position, true);
                    } else {
                        cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));

                        // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                        checkedHashMap_other.replace(position, false);
                    }
                }
            });
        }

        /*단일 항목을 체크 할때마다 해당 단일 항목을 제외한 나머지 항목은 전부 터치를 못하게 막는다.*/
        public void singleItemCheck(boolean Checked){
                String itemTitle = tv_other_itemTitle.getText().toString();
                if (itemTitle.equals(context.getResources().getString(R.string.carInsurance)) ||
                        itemTitle.equals(context.getResources().getString(R.string.trafficFine)) ||
                        itemTitle.equals(context.getResources().getString(R.string.automobileTax))){

                    if (Checked){
                        Data_MaintenanceRecords.MaintenanceSingleItemboolean = true;
                        Data_MaintenanceRecords.MaintenanceSingleItemTitle = tv_other_itemTitle.getText().toString();
                        singleItemTitleList.add(tv_other_itemTitle.getText().toString());
                    }else {
                        Data_MaintenanceRecords.MaintenanceSingleItemboolean = false;
                        Data_MaintenanceRecords.MaintenanceSingleItemTitle = "null";
                        singleItemTitleList.remove(tv_other_itemTitle.getText().toString());
                    }
                    notifyDataSetChanged();
                    apRv_maintenance.notifyDataSetChanged();
                }
        }

        /*public void SelectItemDataSave(){
            Data_MaintenanceRecords.al_carbookRecordItemCategoryCodeList.add("ㅁㄴㅇ");
            Data_MaintenanceRecords.al_carbookRecordItemCategoryNameList.add(tv_other_itemTitle.getText().toString());
            Data_MaintenanceRecords.al_carbookRecordItemExpenseMemoList.add("");
            Data_MaintenanceRecords.al_carbookRecordItemExpenseCostList.add(0.0);
            Data_MaintenanceRecords.al_carbookRecordItemIsHiddenList.add(0);
            Data_MaintenanceRecords.al_carbookRecordItemRegTimeList.add(strNow);
            Data_MaintenanceRecords.al_carbookRecordItemUpdateTimeList.add(strNow);
        }

        public void SelectItemDataRemove(){
            Data_MaintenanceRecords.al_carbookRecordItemCategoryCodeList.remove("ㅁㄴㅇ");
            Data_MaintenanceRecords.al_carbookRecordItemCategoryNameList.remove(tv_other_itemTitle.getText().toString());
            Data_MaintenanceRecords.al_carbookRecordItemExpenseMemoList.remove("");
            Data_MaintenanceRecords.al_carbookRecordItemExpenseCostList.remove(0.0);
            Data_MaintenanceRecords.al_carbookRecordItemIsHiddenList.remove(0);
            Data_MaintenanceRecords.al_carbookRecordItemRegTimeList.remove(strNow);
            Data_MaintenanceRecords.al_carbookRecordItemUpdateTimeList.remove(strNow);
        }*/

        // 현재시간 구하기
        public Date nowTime(){
            Date nowDate = null;
            try {
                // 타임 피커 뜨는 현재 시간
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                strNow = simpleDateFormat.format(date);
                nowDate = simpleDateFormat.parse(strNow);

                Log.i("nowTime", strNow);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nowDate;
        }

    }
}
