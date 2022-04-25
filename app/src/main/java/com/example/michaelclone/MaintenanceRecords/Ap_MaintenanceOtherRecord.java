package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.MainRecord_Data;
import com.example.michaelclone.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ap_MaintenanceOtherRecord extends RecyclerView.Adapter<Ap_MaintenanceOtherRecord.ViewHolder> {

    Context context;
    ArrayList<String> al_itemTitleList;
    String strNow;
    String ItemPriceResult;

    public Ap_MaintenanceOtherRecord(Context context, ArrayList<String> al_itemTitleList){
        this.context = context;
        this.al_itemTitleList = al_itemTitleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintenance_other_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_MtOt_ItemTitle.setText(al_itemTitleList.get(position));
        holder.GoneMaintenanceItemLine(position);

        // 항목 아이템 EditText 적힐떄마다 해쉬맵에 저장
        getItemEditTextData(holder, position);

        // editText 글자 가져오기
        holder.setViewAction(position);
    }

    @Override
    public int getItemCount() {
        return al_itemTitleList.size();
    }

    public void getItemEditTextData(ViewHolder holder, int position){
        String ApRv_otherHashMapKey = "ApRv_other"+position;
        Data_MaintenanceRecords.al_carbookRecordItemExpenseMemoList.put(ApRv_otherHashMapKey, holder.et_MtOtItemMemo.getText().toString());
        Data_MaintenanceRecords.al_carbookRecordItemExpenseCostList.put(ApRv_otherHashMapKey, holder.et_MtOt_ItemPrice.getText().toString());


    }

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_MtOt_ItemTitle;
        TextView tv_MtOtItemMemoCount;
        EditText et_MtOt_ItemPrice;
        EditText et_MtOtItemMemo;
        View View_maintenanceItemLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
            //setViewAction();
        }

        public void setView(){
            tv_MtOt_ItemTitle = itemView.findViewById(R.id.tv_MtOt_ItemTitle);
            tv_MtOtItemMemoCount = itemView.findViewById(R.id.tv_MtOtItemMemoCount);
            et_MtOt_ItemPrice = itemView.findViewById(R.id.et_MtOt_ItemPrice);
            et_MtOtItemMemo = itemView.findViewById(R.id.et_MtOtItemMemo);
            View_maintenanceItemLine = itemView.findViewById(R.id.View_maintenanceItemLine);
        }
        // 아이템 마지막은 구분선이 계속 생기면 아래 구분선이 2개가 되기때문에 없애준다.
        public void GoneMaintenanceItemLine(int position){
            if (al_itemTitleList.size() == 1 || position == al_itemTitleList.size()-1){
                View_maintenanceItemLine.setVisibility(View.GONE);
            }
        }

        public void setViewAction(int position){

            // editText 무한 루프를 방지하기 위해 연결을 자유롭게 끊기 위해 따로 만듬
            // 메모 실시간 글자 수 세주기
            TextWatcher textWatcherMemo = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input_MtOtMemo = et_MtOtItemMemo.getText().toString();
                    tv_MtOtItemMemoCount.setText(input_MtOtMemo.length()+"/250");

                    // MainRecord_Data의 MainRecordItem 리스트에 메모 삽입
                    MainRecord_Data.mainRecordItemArrayList.get(position).carbookRecordItemExpenseMemo = et_MtOtItemMemo.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            // 누적 지출금액 천단위 콤마 적용
            // 누적 지출 금액 입력할때마다 동작
            TextWatcher textWatcherCost = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (et_MtOt_ItemPrice.getText().toString().matches("0")){
                        // 맨 처음 앞자리가 0이면 더이상 입력 못하게 지운다
                        et_MtOt_ItemPrice.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // 텍스트가 변하고 글자 길이가 3 이상이면 천단위 콤마 로직을 태우고 한번 타면 바로 false로 막는다.
                    String Mileage = s.toString();
                    // 가격 글자가 0이하면 Long.parseLong()에서 터지고 어차피 4글자 이상이여야하니 글자 개수로 조건문 걸어준다.
                    if (!Mileage.equals(ItemPriceResult)){
                            long cumulativeMileage =  Long.parseLong(Mileage.replace(",", ""));
                            // MainRecord_Data의 MainRecordItem 리스트에 지출금액 삽입
                            MainRecord_Data.mainRecordItemArrayList.get(position).carbookRecordItemExpenseCost = String.valueOf(cumulativeMileage);
                            Log.i("cumulativeMileage", String.valueOf(cumulativeMileage));
                            // 가격 글자가 0이하면 Long.parseLong()에서 터지고 어차피 4글자 이상이여야하니 글자 개수로 조건문 걸어준다.
                            DecimalFormat decimalFormat = new DecimalFormat("###,###");
                            String calculatedCumulativeMileage = decimalFormat.format(cumulativeMileage);
                            Log.i("???", calculatedCumulativeMileage);
                            ItemPriceResult = calculatedCumulativeMileage;
                            if (calculatedCumulativeMileage.length() > Mileage.length()){
                                // 잠시 주석처리
                                //et_MtOt_ItemPrice.setText(calculatedCumulativeMileage);
                            }
                    }
                }
            };
            et_MtOtItemMemo.addTextChangedListener(textWatcherMemo);
            et_MtOt_ItemPrice.addTextChangedListener(textWatcherCost);
        }
    }
}
