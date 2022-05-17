package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainRecordPageItemRecyclerViewAdapter extends RecyclerView.Adapter<MainRecordPageItemRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> nameList;
    ArrayList<String> costList;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public MainRecordPageItemRecyclerViewAdapter(Context context, ArrayList<String> nameList, ArrayList<String> costList) {
        this.context = context;
        this.nameList = nameList;
        this.costList = costList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.mainrecord_item_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cost;
        if (costList.get(position).contains(",")){
            cost = costList.get(position).replace(",", "");
        }else {
            cost = costList.get(position);
        }
        // 항목 금액
        String costResult = "₩" + decimalFormat.format(Integer.parseInt(cost));

        holder.tv_mainrecordRvItemTitle.setText(nameList.get(position));
        holder.tv_mainrecordRvItemCost.setText(costResult);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mainrecordRvItemTitle;
        TextView tv_mainrecordRvItemCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
        }

        public void setView() {
            tv_mainrecordRvItemTitle = itemView.findViewById(R.id.tv_mainrecordRvItemTitle);
            tv_mainrecordRvItemCost = itemView.findViewById(R.id.tv_mainrecordRvItemCost);
        }
    }
}
