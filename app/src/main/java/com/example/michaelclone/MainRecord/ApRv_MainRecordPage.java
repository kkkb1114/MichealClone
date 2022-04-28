package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.R;

import java.util.ArrayList;

public class ApRv_MainRecordPage extends RecyclerView.Adapter<ApRv_MainRecordPage.ViewHolder> {
    Context context;
    ArrayList<Integer> ViewTypeList;
    MainRecord_Data mainRecord_data;
    public ApRv_MainRecordPage(Context context, ArrayList<Integer> ViewTypeList){
        this.context = context;
        this.ViewTypeList = ViewTypeList;

        mainRecord_data = new MainRecord_Data();
        Log.i("리사이클러뷰 ㄱㄱ?", "111");
        Log.i("리사이클러뷰 ㄱㄱ?", String.valueOf(this.ViewTypeList.get(0)));
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("리사이클러뷰 ㄱㄱ?", "222");
        if (ViewTypeList.get(position) == 0){
            return 0;
        }else if (ViewTypeList.get(position) == 1){
            return 1;
        }else {
            return 2;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("리사이클러뷰 ㄱㄱ?", "333");
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // viewType 0: 연도뷰, 1: 월뷰, 2: 기록뷰
        View view;
        ViewHolder viewHolder;
        if (viewType == 0){
            view = layoutInflater.inflate(R.layout.mainrecordyear_item, parent, false);
            viewHolder = new ViewHolder(view, context, viewType);
        }else if (viewType == 1){
            view = layoutInflater.inflate(R.layout.mainrecordmonth_item, parent, false);
            viewHolder = new ViewHolder(view, context, viewType);
        }else {
            view = layoutInflater.inflate(R.layout.mainrecord_item, parent, false);
            viewHolder = new ViewHolder(view, context, viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("리사이클러뷰 ㄱㄱ?", "444");
        if (ViewTypeList.get(position) == 0){
            holder.tv_mainrecordYear.setText(MainRecord_Data.MainRecordPageRecordArrayList.get(0).carbookRecordExpendDate);
            holder.tv_mainrecordYearCost.setText(String.valueOf(mainRecord_data.CostCalculation()));
        }else if (ViewTypeList.get(position) == 1){
            holder.tv_mainrecordMonth.setText(MainRecord_Data.MainRecordPageRecordArrayList.get(0).carbookRecordExpendDate);
            holder.tv_mainrecordMonthCost.setText(String.valueOf(mainRecord_data.CostCalculation()));
        }else {

        }
    }

    @Override
    public int getItemCount() {
        return MainRecord_Data.MainRecordPageRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        int ViewType;
        TextView tv_mainrecordYear;
        TextView tv_mainrecordYearCost;

        TextView tv_mainrecordMonth;
        TextView tv_mainrecordMonthCost;

        public ViewHolder(@NonNull View itemView, Context context, int ViewType) {
            super(itemView);
            this.ViewType = ViewType;
            this.context = context;
            setView();
        }

        public void setView(){
            if (ViewType == 0){
                tv_mainrecordYear = itemView.findViewById(R.id.tv_mainrecordYear);
                tv_mainrecordYearCost = itemView.findViewById(R.id.tv_mainrecordYearCost);
            }else if (ViewType == 1){
                tv_mainrecordMonth = itemView.findViewById(R.id.tv_mainrecordMonth);
                tv_mainrecordMonthCost = itemView.findViewById(R.id.tv_mainrecordMonthCost);
            }else {

            }
        }
    }
}
