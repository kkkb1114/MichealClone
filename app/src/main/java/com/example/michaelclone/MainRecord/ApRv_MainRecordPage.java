package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.R;

import java.text.DecimalFormat;
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
        if (ViewTypeList.get(position) == 0){
            holder.tv_mainrecordYear.setText(MainRecord_Data.MainRecordPageRecordArrayList.get(0).carbookRecordExpendDate);
            //holder.tv_mainrecordYearCost.setText(String.valueOf(mainRecord_data.CostCalculation()));
        }else if (ViewTypeList.get(position) == 1){
            holder.tv_mainrecordMonth.setText(MainRecord_Data.MainRecordPageRecordArrayList.get(0).carbookRecordExpendDate);
            //holder.tv_mainrecordMonthCost.setText(String.valueOf(mainRecord_data.CostCalculation()));
        }else {

            if (MainRecord_Data.MainRecordPageArrayList.size() > 0){
                // 날짜를 정수로 바꿔 0.01을 곱해도 되긴 하는데 그냥 문자열을 잘라 사이에 .을 붙여 만들었다. (이게 나은 방법인가?)
                String beforeDate = MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordExpendDate.substring(4,8);
                String month = beforeDate.substring(0,2);
                String day = beforeDate.substring(2,4);
                // 누적 거리 , 처리를 위한 DecimalFormat 선언
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                
                for (int i=0; i<MainRecord_Data.MainRecordPageArrayList.size(); i++){
                    // 날짜
                    String date = month+"."+day;
                    // 항목 개수
                    int count = +MainRecord_Data.MainRecordPageArrayList.get(position).count-1;
                    // 항목 타이틀
                    String tv_mainrecordTitle;
                    if (count <= 0){
                        tv_mainrecordTitle = MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordItemCategoryName;
                    }else {
                        tv_mainrecordTitle = MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordItemCategoryName+" 외 "+count+"건";
                    }
                    // 누적 거리에 ,처리와 뒤에 거리 단위, 공백을 추가한 문자열을 위한 부분
                    String Distance = " "+decimalFormat.format((int) MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordTotalDistance)+" "+
                            context.getResources().getString(R.string.km);

                    // 총 금액에 넣을 문자열
                    String totalCost = "₩"+decimalFormat.format((int) MainRecord_Data.MainRecordPageArrayList.get(position).totalCost);

                    holder.tv_mainrecordDate.setText(date);
                    holder.tv_mainrecordTitle.setText(tv_mainrecordTitle);
                    holder.tv_mainrecordDistance.setText(Distance);
                    holder.tv_mainrecordCost.setText(totalCost);
                    holder.tv_mainrecordMemo.setText(String.valueOf(MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordItemExpenseMemo));

                    // 각 기록에 속한 항목 삽입
                    setViewHolderRecyclerView(holder);
                }
            }
        }


    }

    public void setViewHolderRecyclerView(ViewHolder holder){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        holder.rv_mainrecordItem.setAdapter();
        holder.rv_mainrecordItem.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return MainRecord_Data.MainRecordPageRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        int ViewType;
        // 뷰 타입: 0
        TextView tv_mainrecordYear;
        TextView tv_mainrecordYearCost;

        // 뷰 타입: 1
        TextView tv_mainrecordMonth;
        TextView tv_mainrecordMonthCost;

        // 뷰 타입: 2
        TextView tv_mainrecordDate;
        TextView tv_mainrecordTitle;
        TextView tv_mainrecordDistance;
        TextView tv_mainrecordCost;
        TextView tv_mainrecordMemo;
        RecyclerView rv_mainrecordItem;

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
                tv_mainrecordDate = itemView.findViewById(R.id.tv_mainrecordDate);
                tv_mainrecordTitle = itemView.findViewById(R.id.tv_mainrecordTitle);
                tv_mainrecordDistance = itemView.findViewById(R.id.tv_mainrecordDistance);
                tv_mainrecordCost = itemView.findViewById(R.id.tv_mainrecordCost);
                tv_mainrecordMemo = itemView.findViewById(R.id.tv_mainrecordMemo);
                rv_mainrecordItem = itemView.findViewById(R.id.rv_mainrecordItem);
            }
        }
    }
}
