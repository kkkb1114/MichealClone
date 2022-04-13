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
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ApRv_other extends RecyclerView.Adapter<ApRv_other.ViewHolder> {

    ArrayList<String> ItemTitleList;
    ArrayList<Integer> ItemTypeList;
    HashMap<Integer, Boolean> checkedHashMap = new HashMap<>();
    boolean firstCheck = true;

    Context context;

    public ApRv_other(Context context, ArrayList<String> ItemTitleList, ArrayList<Integer> ItemTypeList) {
        this.context = context;
        this.ItemTitleList = ItemTitleList;
        this.ItemTypeList = ItemTypeList;
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
    }

    @Override
    public int getItemCount() {
        return ItemTypeList.size();
    }


    public void checkCheckBox(ViewHolder holder, int position){
        /*처음 항목 생성해서 checkedHashMap를 처음 세팅할때의 경우
          - 처음에는 ItemTitleList 크기만큼 checkedHashMap에 false값을 넣어준다.
          - 그 후에 크기가 같아지면 position값으로 checkedHashMap에 들어있는 값을 체크하면서 holder의 체크 박스를 세팅해준다.*/
        if (checkedHashMap.size() != ItemTitleList.size()){
            // 처음에는 checkedHashMap에 해당 포지션에 값이 없을 경우 boolean값을 넣어준다.
            checkedHashMap.put(position, false);
        }else {
            // 해당 아이템에 체크박스 상태 확인
            if (checkedHashMap.get(position)){
                holder.cb_other_itemSelect.setChecked(true);
            }else {
                holder.cb_other_itemSelect.setChecked(false);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_other_itemTitle;
        CheckBox cb_other_itemSelect;
        View View_other_itemDividingLine;
        LinearLayout Ln_other_item;

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

            tv_other_itemTitle.setText(ItemTitle);
            cb_setChecked(position);
            Ln_other_item.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Ln_other_item:
                        if (cb_other_itemSelect.isChecked()) {
                            cb_other_itemSelect.setChecked(false);
                        } else {
                            cb_other_itemSelect.setChecked(true);
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
                        SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_other_itemTitle.getText().toString(), 0);
                        cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00D4FF")));

                        // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                        checkedHashMap.replace(position, true);

                        // 단일 항목들 체크
                        singleItemCheck(true);
                    } else {
                        SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_other_itemTitle.getText().toString(), 1);
                        cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));

                        // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                        checkedHashMap.replace(position, false);
                        // 단일 항목들 체크
                        singleItemCheck(false);
                    }
                }
            });
        }

        /*단일 항목을 체크 할때마다 해당 단일 항목을 제외한 나머지 항목은 전부 터치를 못하게 막는다.
        - 처음에 아이템 뷰를 생성할때 해당 뷰들을 전부 ArrayList에 넣고 단일 항목을 클릭하면 처음에 ArrayList에 넣은 뷰와 사용자가 보고있는 뷰는 같은 것이기에
          리스트 안에 있는 뷰들을 블록시키는 것이다.*/
        public void singleItemCheck(boolean checked){
            String itemTitle = tv_other_itemTitle.getText().toString();
            if (itemTitle.equals(context.getResources().getString(R.string.carInsurance))){

            }else if (itemTitle.equals(context.getResources().getString(R.string.trafficFine))){

            }else if (itemTitle.equals(context.getResources().getString(R.string.automobileTax))){

            }
        }
    }
}
