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

import com.example.michaelclone.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Ap_MaintenanceOtherRecord extends RecyclerView.Adapter<Ap_MaintenanceOtherRecord.ViewHolder> {

    Context context;
    ArrayList<String> al_itemTitleList;

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
    }

    @Override
    public int getItemCount() {
        return al_itemTitleList.size();
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
            setViewAction();
        }

        public void setView(){
            tv_MtOt_ItemTitle = itemView.findViewById(R.id.tv_MtOt_ItemTitle);
            tv_MtOtItemMemoCount = itemView.findViewById(R.id.tv_MtOtItemMemoCount);
            et_MtOt_ItemPrice = itemView.findViewById(R.id.et_MtOt_ItemPrice);
            et_MtOtItemMemo = itemView.findViewById(R.id.et_MtOtItemMemo);
            View_maintenanceItemLine = itemView.findViewById(R.id.View_maintenanceItemLine);
        }

        public void addItemData(int position){

        }

        // 아이템 마지막은 구분선이 계속 생기면 아래 구분선이 2개가 되기때문에 없애준다.
        public void GoneMaintenanceItemLine(int position){
            if (al_itemTitleList.size() == 1 || position == al_itemTitleList.size()-1){
                View_maintenanceItemLine.setVisibility(View.GONE);
            }
        }

        public void setViewAction(){
            // 메모 실시간 글자 수 세주기
            et_MtOtItemMemo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String input_MtOtMemo = et_MtOtItemMemo.getText().toString();
                    tv_MtOtItemMemoCount.setText(input_MtOtMemo.length()+"/250");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // 누적 지출금액 천단위 콤마 적용
            et_MtOt_ItemPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    String Mileage = v.getText().toString();
                    /**
                     * 텍스트 두번 가져오면 ,가 포함되서 Long.parseLong가 안되는거다. 그래서 두번째 계산에서 터짐
                     * **/
                    long cumulativeMileage =  Long.parseLong(Mileage.replace(",", ""));
                    DecimalFormat decimalFormat = new DecimalFormat("###,###");
                    String calculatedCumulativeMileage = decimalFormat.format(cumulativeMileage);
                    et_MtOt_ItemPrice.setText(calculatedCumulativeMileage);
                    return false;
                }
            });

            // 누적 지출 금액 입력할때마다 동작
            et_MtOt_ItemPrice.addTextChangedListener(new TextWatcher() {
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

                }
            });
        }
    }
}
