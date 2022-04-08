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
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.util.ArrayList;

public class ApRv_other extends RecyclerView.Adapter<ApRv_other.ViewHolder> {

    ArrayList<String> ItemTitleList;
    ArrayList<Integer> ItemTypeList;

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
        if (ItemTypeList.get(position) == 0) {
            holder.bindView(position, ItemTitleList);
        }
    }

    @Override
    public int getItemCount() {
        return ItemTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemTitle;
        CheckBox cb_itemSelect;
        LinearLayout Ln_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
        }

        public void setView() {
            tv_itemTitle = itemView.findViewById(R.id.tv_itemTitle);
            cb_itemSelect = itemView.findViewById(R.id.cb_itemSelect);
            Ln_item = itemView.findViewById(R.id.Ln_item);
        }

        public void bindView(int position, ArrayList<String> ItemTitleList) {

            tv_itemTitle.setText(ItemTitleList.get(position));

            cb_setChecked();
            Ln_item.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Ln_item:
                        if (cb_itemSelect.isChecked()) {
                            cb_itemSelect.setChecked(false);
                        } else {
                            cb_itemSelect.setChecked(true);
                        }
                        break;
                }
            }
        };

        public void cb_setChecked() {
            cb_itemSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00D4FF")));
                        SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_itemTitle.getText().toString(), 0);
                    } else {
                        cb_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                        SelectMaintenanceItemActivity.itemClickChangeCount(context, tv_itemTitle.getText().toString(), 1);
                    }
                }
            });
        }
    }
}
