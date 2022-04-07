package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

public class ApRv_maintenance extends RecyclerView.Adapter<ApRv_maintenance.ViewHolder> {

    ArrayList<String> ItemTitleList;
    ArrayList<String> ItemDistanceList;
    ArrayList<String> ItemLifeSpanList;
    ArrayList<Integer> ItemTypeList;

    Context context;

    /**
     * 1. ItemTypeList을 기준으로 항목을 추가할지 새 항목 추가 버튼을 추가할지 정해지며 | 0: 항목, 1: 추가버튼 | 이다.
     *
     * **/

    public ApRv_maintenance(Context context, ArrayList<String> ItemTitleList, ArrayList<String> ItemDistanceList, ArrayList<String> ItemLifeSpanList, ArrayList<Integer> ItemTypeList){
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
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (ItemTypeList.get(position) == 0){
            holder.bindView(position, ItemTitleList, ItemDistanceList, ItemLifeSpanList);
        }
    }

    @Override
    public int getItemCount() {
        return ItemTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_itemTitle;
        TextView tv_itemDistance;
        TextView tv_itemMonth;
        CheckBox cb_itemSelect;
        LinearLayout Ln_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
        }

        public void setView(){
            tv_itemTitle = itemView.findViewById(R.id.tv_itemTitle);
            tv_itemDistance = itemView.findViewById(R.id.tv_itemDistance);
            tv_itemMonth = itemView.findViewById(R.id.tv_itemMonth);
            cb_itemSelect = itemView.findViewById(R.id.cb_itemSelect);
            Ln_item = itemView.findViewById(R.id.Ln_item);
        }

        public void bindView(int position, ArrayList<String> ItemTitleList, ArrayList<String> ItemDistanceList
                , ArrayList<String> ItemLifeSpanList){

            tv_itemTitle.setText(ItemTitleList.get(position));
            tv_itemDistance.setText(ItemDistanceList.get(position));
            tv_itemMonth.setText(ItemLifeSpanList.get(position));

            cb_setChecked();
            Ln_item.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Ln_item:
                        if (cb_itemSelect.isChecked()){
                            cb_itemSelect.setChecked(false);
                        }else {
                            cb_itemSelect.setChecked(true);
                        }
                        break;
                }
            }
        };

        public void cb_setChecked(){
            cb_itemSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        cb_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00D4FF")));
                    }else {
                        cb_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                    }
                }
            });
        }
    }
}
