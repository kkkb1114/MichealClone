package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
                    String Mileage = et_MtOt_ItemPrice.getText().toString();
                    /**
                     * 텍스트 두번 가져오면 ,가 포함되서 Long.parseLong가 안되는거다. 그래서 두번째 계산에서 터짐
                     * **/
                    if(Mileage.contains("[,]")){
                        Mileage.replaceAll("[,]", "");
                    }
                    long cumulativeMileage = Long.parseLong(et_MtOt_ItemPrice.getText().toString());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###");
                    String calculatedCumulativeMileage = decimalFormat.format(cumulativeMileage);
                    et_MtOt_ItemPrice.setText(calculatedCumulativeMileage);
                    return false;
                }
            });
        }
    }
}
