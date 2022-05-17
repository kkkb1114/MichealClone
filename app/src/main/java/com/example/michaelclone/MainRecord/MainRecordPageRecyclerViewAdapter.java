package com.example.michaelclone.MainRecord;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.MainRecordPage;
import com.example.michaelclone.DataBase.CarbookRecord_Data;
import com.example.michaelclone.MaintenanceRecords.MaintenanceOtherRecordActivity;
import com.example.michaelclone.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainRecordPageRecyclerViewAdapter extends RecyclerView.Adapter<MainRecordPageRecyclerViewAdapter.ViewHolder> {
    Context context;
    CarbookRecord_Data carbookRecord_data;

    // 2022.05,09
    // 키값을 연, 월로 지정해놓고 데이터를 포지션 값으로 지정하여 연, 월에 데이터가 있을 경우 해당 포지션 값에만 연, 월 뷰를 VISIBLE 하도록 한다.
    HashMap<String, Integer> YearMonthBundleCheckHashMap = new HashMap<>();

    public MainRecordPageRecyclerViewAdapter(Context context) {
        this.context = context;
        carbookRecord_data = new CarbookRecord_Data();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        ViewHolder viewHolder;
        view = layoutInflater.inflate(R.layout.mainrecord_item, parent, false);
        viewHolder = new ViewHolder(view, context, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /**
         * 1. YearMonthBundleCheckHashMap기준으로 해당 해쉬맵에 해당 position의 연, 월이 들어있지 않으면 연, 월은 키값 해당 position값은 value로 넣고 들어있으면 넘어간다.
         * 2. 연, 월 뷰는 해당 position의 연, 월값을 키값으로 YearMonthBundleCheckHashMap에 들어있는 position값과 같으면 VISIBLE이며 다르면 GONE 처리한다.
         *
         **/
        if (CarbookRecord_Data.mainRecordPageArrayList.size() > 0) {

            MainRecordPage page = CarbookRecord_Data.mainRecordPageArrayList.get(position);
            // 날짜를 정수로 바꿔 0.01을 곱해도 되긴 하는데 그냥 문자열을 잘라 사이에 .을 붙여 만들었다. (이게 나은 방법인가?)
            String beforeDate = CarbookRecord_Data.mainRecordPageArrayList.get(position).carbookRecordExpendDate.substring(4, 8);
            String submonth = beforeDate.substring(0, 2);
            String day = beforeDate.substring(2, 4);
            // 누적 거리 , 처리를 위한 DecimalFormat 선언
            DecimalFormat decimalFormat = new DecimalFormat("###,###");

            // 날짜
            String date = submonth + "." + day;
            // 항목 개수
            int count = +CarbookRecord_Data.mainRecordPageArrayList.get(position).count - 1;
            // 항목 타이틀
            String tv_mainrecordTitle;
            if (count <= 0) {
                tv_mainrecordTitle = CarbookRecord_Data.mainRecordPageArrayList.get(position).carbookRecordItemCategoryName;
            } else {
                tv_mainrecordTitle = CarbookRecord_Data.mainRecordPageArrayList.get(position).carbookRecordItemCategoryName + " 외 " + count + "건";
            }
            // 누적 거리에 ,처리와 뒤에 거리 단위, 공백을 추가한 문자열을 위한 부분
            String Distance = " " + decimalFormat.format((int) CarbookRecord_Data.mainRecordPageArrayList.get(position).carbookRecordTotalDistance) + " " +
                    context.getResources().getString(R.string.km);

            // 총 금액에 넣을 문자열
            String totalCost = "₩" + decimalFormat.format((int) CarbookRecord_Data.mainRecordPageArrayList.get(position).totalCost);

            // 월 총 금액
            String monthCost = "₩" + decimalFormat.format(carbookRecord_data.mainRecordPageMonthCostCalculation(CarbookRecord_Data.mainRecordPageArrayList.get(position).month));
            // 연 총 금액
            String yearCost = "₩" + decimalFormat.format(carbookRecord_data.mainRecordPageYearCostCalculation(CarbookRecord_Data.mainRecordPageArrayList.get(position).year));

                if (!YearMonthBundleCheckHashMap.containsKey(CarbookRecord_Data.mainRecordPageArrayList.get(position).year)){
                    YearMonthBundleCheckHashMap.put(CarbookRecord_Data.mainRecordPageArrayList.get(position).year, position);
                }

                if (!YearMonthBundleCheckHashMap.containsKey(CarbookRecord_Data.mainRecordPageArrayList.get(position).month)){
                    YearMonthBundleCheckHashMap.put(CarbookRecord_Data.mainRecordPageArrayList.get(position).month, position);
                }

                if (YearMonthBundleCheckHashMap.get(CarbookRecord_Data.mainRecordPageArrayList.get(position).year) == position){
                    holder.Ln_year.setVisibility(View.VISIBLE);
                    holder.tv_mainrecordYearCost.setText(yearCost);
                    holder.tv_mainrecordYear.setText(CarbookRecord_Data.mainRecordPageArrayList.get(position).year);
                }else {
                    holder.Ln_year.setVisibility(View.GONE);
                }

                if (YearMonthBundleCheckHashMap.get(CarbookRecord_Data.mainRecordPageArrayList.get(position).month) == position){
                    holder.Ln_month.setVisibility(View.VISIBLE);
                    holder.tv_mainrecordMonthCost.setText(monthCost);
                    holder.tv_mainrecordMonth.setText(submonth);
                }else {
                    holder.Ln_month.setVisibility(View.GONE);
                }

            holder.tv_mainrecordDate.setText(date);
            holder.tv_mainrecordTitle.setText(tv_mainrecordTitle);
            holder.tv_mainrecordDistance.setText(Distance);
            holder.tv_mainrecordCost.setText(totalCost);
            holder.tv_mainrecordMemo.setText(String.valueOf(CarbookRecord_Data.mainRecordPageArrayList.get(position).carbookRecordItemExpenseMemo));

            ArrayList<String> nameList = new ArrayList<>();
            ArrayList<String> costList = new ArrayList<>();
            for (int i2 = 0; i2 < CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.size(); i2++) {

                if (CarbookRecord_Data.mainRecordPageArrayList.get(position).id == CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.get(i2).carbookRecordId) {
                    if (!CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.get(i2).carbookRecordItemCategoryName.contains("null") &&
                            !CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.get(i2).carbookRecordItemExpenseCost.contains("null") &&
                            CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.get(i2).carbookRecordItemCategoryName != null &&
                            CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.get(i2).carbookRecordItemExpenseCost != null) {

                        nameList.add(CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.get(i2).carbookRecordItemCategoryName);
                        costList.add(CarbookRecord_Data.mainRecordPageRecordItemArrayList_getDB.get(i2).carbookRecordItemExpenseCost);
                    }
                }
            }

            // 각 기록에 속한 항목 삽입
            setViewHolderRecyclerView(holder, nameList, costList);
        }
        // 기록 클릭시 수정페이지 이동 메소드
        moveRecordItemModifyPage(holder, position);
    }

    public void setViewHolderRecyclerView(ViewHolder holder, ArrayList<String> nameList, ArrayList<String> costList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        MainRecordPageItemRecyclerViewAdapter _mainRecordPageItemRecyclerViewAdapter = new MainRecordPageItemRecyclerViewAdapter(context, nameList, costList);

        // SELECT carbookRecordId, carbookRecordItemCategoryName, carbookRecordItemCategoryCode, carbookRecordItemExpenseCost FROM carbookRecordItem를 sql문으로 사용하여
        // 데이터를 뽑아오고 위에서 뽑은 MainRecordPage에서 기록 자체의 id 값을 가져오니 id와 carbookRecordId의 값이 같을 경우 데이터를 삽입한다. (생각한건 for문안에 for문을 넣는 것)
        holder.rv_mainrecordItem.setAdapter(_mainRecordPageItemRecyclerViewAdapter);
        holder.rv_mainrecordItem.setLayoutManager(linearLayoutManager);
    }

    public void moveRecordItemModifyPage(ViewHolder holder, int position){
        holder.Ln_mainRecordItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MaintenanceOtherRecordActivity.class);
                intent.putExtra("mainRecordItemCarbookRecordId", CarbookRecord_Data.mainRecordPageArrayList.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CarbookRecord_Data.mainRecordPageArrayList.size();
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
        ImageView iv_mainrecordMenuButton;
        LinearLayout Ln_mainRecordItem;

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
            iv_mainrecordMenuButton = itemView.findViewById(R.id.iv_mainrecordMenuButton);
            Ln_mainRecordItem = itemView.findViewById(R.id.Ln_mainRecordItem);
        }
    }
}
