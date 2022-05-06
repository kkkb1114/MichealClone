package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.MainRecordPage;
import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ApRv_MainRecordPage extends RecyclerView.Adapter<ApRv_MainRecordPage.ViewHolder> {
    Context context;
    MainRecord_Data mainRecord_data;
    boolean isYear = false;
    boolean isMonth = false;
    boolean isBoolean = true; // isBoolean: 최초에 연, 월을 추가 하고 2번째부터 위 조건을 태우기 위한 boolean
    ArrayList<String> yearList = new ArrayList<>();
    ArrayList<String> monthList = new ArrayList<>();

    public ApRv_MainRecordPage(Context context) {
        this.context = context;

        mainRecord_data = new MainRecord_Data();
        yearList.add(MainRecord_Data.MainRecordPageArrayList.get(0).year);
        monthList.add(MainRecord_Data.MainRecordPageArrayList.get(0).month);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("리사이클러뷰 ㄱㄱ?", "333");
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        ViewHolder viewHolder;
        view = layoutInflater.inflate(R.layout.mainrecord_item, parent, false);
        viewHolder = new ViewHolder(view, context, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (MainRecord_Data.MainRecordPageArrayList.size() > 0) {

            MainRecordPage page = MainRecord_Data.MainRecordPageArrayList.get(position);
            Log.e("test","page : " + page);
            // 날짜를 정수로 바꿔 0.01을 곱해도 되긴 하는데 그냥 문자열을 잘라 사이에 .을 붙여 만들었다. (이게 나은 방법인가?)
            String beforeDate = MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordExpendDate.substring(4, 8);
            String submonth = beforeDate.substring(0, 2);
            String day = beforeDate.substring(2, 4);
            // 누적 거리 , 처리를 위한 DecimalFormat 선언
            DecimalFormat decimalFormat = new DecimalFormat("###,###");

            // 날짜
            String date = submonth + "." + day;
            // 항목 개수
            int count = +MainRecord_Data.MainRecordPageArrayList.get(position).count - 1;
            // 항목 타이틀
            String tv_mainrecordTitle;
            if (count <= 0) {
                tv_mainrecordTitle = MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordItemCategoryName;
            } else {
                tv_mainrecordTitle = MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordItemCategoryName + " 외 " + count + "건";
            }
            // 누적 거리에 ,처리와 뒤에 거리 단위, 공백을 추가한 문자열을 위한 부분
            String Distance = " " + decimalFormat.format((int) MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordTotalDistance) + " " +
                    context.getResources().getString(R.string.km);

            // 총 금액에 넣을 문자열
            String totalCost = "₩" + decimalFormat.format((int) MainRecord_Data.MainRecordPageArrayList.get(position).totalCost);

            // 월 총 금액
            String monthCost = "₩" + decimalFormat.format(mainRecord_data.MainRecordPageMonthCostCalculation(MainRecord_Data.MainRecordPageArrayList.get(position).month));
            // 연 총 금액
            String yearCost = "₩" + decimalFormat.format(mainRecord_data.MainRecordPageYearCostCalculation(MainRecord_Data.MainRecordPageArrayList.get(position).year));

            // isBoolean: 최초에 연, 월을 추가 하고 2번째부터 위 조건을 태우기 위한 boolean
            if (isBoolean) {
                holder.Ln_year.setVisibility(View.VISIBLE);
                holder.tv_mainrecordYearCost.setText(yearCost);
                holder.tv_mainrecordYear.setText(MainRecord_Data.MainRecordPageArrayList.get(position).year);
                if (!yearList.contains(MainRecord_Data.MainRecordPageArrayList.get(position).year)) {
                    yearList.add(MainRecord_Data.MainRecordPageArrayList.get(position).year);
                }

                if (!monthList.contains(MainRecord_Data.MainRecordPageArrayList.get(position).month)) {
                    monthList.add(MainRecord_Data.MainRecordPageArrayList.get(position).month);
                }

                holder.Ln_month.setVisibility(View.VISIBLE);
                holder.tv_mainrecordMonthCost.setText(monthCost);
                holder.tv_mainrecordMonth.setText(submonth);
            }

            for(String year : yearList){
                Log.e("test","year : " + year);
            }
            for(String month : monthList){
                Log.e("test","month : " + month);
            }

            // 연, 월뷰 중복 기록은 숨기는 구간
            if (yearList.contains(MainRecord_Data.MainRecordPageArrayList.get(position).year)) {
                isYear = true;
            } else {
                isYear = false;
            }

            // 연, 월뷰 중복 기록은 숨기는 구간
            if (monthList.contains(MainRecord_Data.MainRecordPageArrayList.get(position).month)) {
                isMonth = true;
            } else {
                isMonth = false;
            }

            // isBoolean: 최초에 연, 월을 추가 하고 2번째부터 위 조건을 태우기 위한 boolean
            if (!isBoolean) {
                if (isYear) {
                    holder.Ln_year.setVisibility(View.GONE);
                } else {
                    holder.Ln_year.setVisibility(View.VISIBLE);
                    holder.tv_mainrecordYearCost.setText(yearCost);
                    holder.tv_mainrecordYear.setText(MainRecord_Data.MainRecordPageArrayList.get(position).year);
                    if (!yearList.contains(MainRecord_Data.MainRecordPageArrayList.get(position).year)) {
                        yearList.add(MainRecord_Data.MainRecordPageArrayList.get(position).year);
                    }
                }

                if (isMonth) {
                    holder.Ln_month.setVisibility(View.GONE);
                } else {
                    holder.Ln_month.setVisibility(View.VISIBLE);
                    holder.tv_mainrecordMonthCost.setText(monthCost);
                    holder.tv_mainrecordMonth.setText(submonth);
                    monthList.add(MainRecord_Data.MainRecordPageArrayList.get(position).month);
                    if (!monthList.contains(MainRecord_Data.MainRecordPageArrayList.get(position).month)) {
                        monthList.add(MainRecord_Data.MainRecordPageArrayList.get(position).month);
                    }
                }
            } else {
                // isBoolean: 최초에 연, 월을 추가 하고 2번째부터 위 조건을 태우기 위한 boolean
                isBoolean = false;
            }

            holder.tv_mainrecordDate.setText(date);
            holder.tv_mainrecordTitle.setText(tv_mainrecordTitle);
            holder.tv_mainrecordDistance.setText(Distance);
            holder.tv_mainrecordCost.setText(totalCost);
            holder.tv_mainrecordMemo.setText(String.valueOf(MainRecord_Data.MainRecordPageArrayList.get(position).carbookRecordItemExpenseMemo));

            ArrayList<String> nameList = new ArrayList<>();
            ArrayList<String> costList = new ArrayList<>();
            for (int i2 = 0; i2 < MainRecord_Data.MainRecordPageRecordItemArrayList.size(); i2++) {

                if (MainRecord_Data.MainRecordPageArrayList.get(position).id == MainRecord_Data.MainRecordPageRecordItemArrayList.get(i2).carbookRecordId) {
                    if (!MainRecord_Data.MainRecordPageRecordItemArrayList.get(i2).carbookRecordItemCategoryName.contains("null") &&
                            !MainRecord_Data.MainRecordPageRecordItemArrayList.get(i2).carbookRecordItemExpenseCost.contains("null") &&
                            MainRecord_Data.MainRecordPageRecordItemArrayList.get(i2).carbookRecordItemCategoryName != null &&
                            MainRecord_Data.MainRecordPageRecordItemArrayList.get(i2).carbookRecordItemExpenseCost != null) {

                        nameList.add(MainRecord_Data.MainRecordPageRecordItemArrayList.get(i2).carbookRecordItemCategoryName);
                        costList.add(MainRecord_Data.MainRecordPageRecordItemArrayList.get(i2).carbookRecordItemExpenseCost);
                    }
                }
            }

            // 각 기록에 속한 항목 삽입
            setViewHolderRecyclerView(holder, nameList, costList);
            Log.i("costList.get(position)", String.valueOf(costList));
            Log.i("nameList.get(position)", String.valueOf(nameList));

        }
    }

    public void setViewHolderRecyclerView(ViewHolder holder, ArrayList<String> nameList, ArrayList<String> costList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ApRv_MainRecordPageItem apRv_mainRecordPageItem = new ApRv_MainRecordPageItem(context, nameList, costList);

        // SELECT carbookRecordId, carbookRecordItemCategoryName, carbookRecordItemCategoryCode, carbookRecordItemExpenseCost FROM carbookRecordItem를 sql문으로 사용하여
        // 데이터를 뽑아오고 위에서 뽑은 MainRecordPage에서 기록 자체의 id 값을 가져오니 id와 carbookRecordId의 값이 같을 경우 데이터를 삽입한다. (생각한건 for문안에 for문을 넣는 것)
        holder.rv_mainrecordItem.setAdapter(apRv_mainRecordPageItem);
        holder.rv_mainrecordItem.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return MainRecord_Data.MainRecordPageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        int ViewType;
        TextView tv_mainrecordYear;
        TextView tv_mainrecordYearCost;
        TextView tv_mainrecordMonth;
        TextView tv_mainrecordMonthCost;
        TextView tv_mainrecordDate;
        TextView tv_mainrecordTitle;
        TextView tv_mainrecordDistance;
        TextView tv_mainrecordCost;
        TextView tv_mainrecordMemo;
        RecyclerView rv_mainrecordItem;
        LinearLayout Ln_year;
        LinearLayout Ln_month;

        public ViewHolder(@NonNull View itemView, Context context, int ViewType) {
            super(itemView);
            this.ViewType = ViewType;
            this.context = context;
            setView();
        }

        public void setView() {
            tv_mainrecordYear = itemView.findViewById(R.id.tv_mainrecordYear);
            tv_mainrecordYearCost = itemView.findViewById(R.id.tv_mainrecordYearCost);
            tv_mainrecordMonth = itemView.findViewById(R.id.tv_mainrecordMonth);
            tv_mainrecordMonthCost = itemView.findViewById(R.id.tv_mainrecordMonthCost);
            tv_mainrecordDate = itemView.findViewById(R.id.tv_mainrecordDate);
            tv_mainrecordTitle = itemView.findViewById(R.id.tv_mainrecordTitle);
            tv_mainrecordDistance = itemView.findViewById(R.id.tv_mainrecordDistance);
            tv_mainrecordCost = itemView.findViewById(R.id.tv_mainrecordCost);
            tv_mainrecordMemo = itemView.findViewById(R.id.tv_mainrecordMemo);
            rv_mainrecordItem = itemView.findViewById(R.id.rv_mainrecordItem);
            Ln_year = itemView.findViewById(R.id.Ln_year);
            Ln_month = itemView.findViewById(R.id.Ln_month);
        }
    }
}
