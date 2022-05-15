package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MaintenanceOtherRecordRecyclerViewAdapter extends RecyclerView.Adapter<MaintenanceOtherRecordRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> al_itemTitleList;

    public MaintenanceOtherRecordRecyclerViewAdapter(Context context, ArrayList<String> al_itemTitleList) {
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
        holder.tv_maintenanceOtherItemTitle.setText(al_itemTitleList.get(position));
        holder.GoneMaintenanceItemLine(position);

        // editText 글자 가져오기
        holder.setViewAction(position);
    }

    @Override
    public int getItemCount() {
        return al_itemTitleList.size();
    }

    // 천단위 콤마 메소드
    protected String makeStringComma(String data) {
        if (data.length() == 0) {
            return "";
        } else {
            long value = Long.parseLong(data);
            DecimalFormat format = new DecimalFormat("###,###");
            return format.format(value);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_maintenanceOtherItemTitle;
        TextView tv_maintenanceOtherItemMemoCount;
        EditText et_maintenanceOtherItemPrice;
        EditText et_maintenanceOtherItemMemo;
        View View_maintenanceOtherItemLine;
        LinearLayout Ln_maintenanceOtherItemRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
            //setViewAction();
        }

        public void setView() {
            tv_maintenanceOtherItemTitle = itemView.findViewById(R.id.tv_MtOt_ItemTitle);
            tv_maintenanceOtherItemMemoCount = itemView.findViewById(R.id.tv_MtOtItemMemoCount);
            et_maintenanceOtherItemPrice = itemView.findViewById(R.id.et_MtOt_ItemPrice);
            et_maintenanceOtherItemMemo = itemView.findViewById(R.id.et_MtOtItemMemo);
            View_maintenanceOtherItemLine = itemView.findViewById(R.id.View_maintenanceItemLine);
            Ln_maintenanceOtherItemRemove = itemView.findViewById(R.id.Ln_maintenanceOtherItemRemove);
        }

        // 아이템 마지막은 구분선이 계속 생기면 아래 구분선이 2개가 되기때문에 없애준다.
        public void GoneMaintenanceItemLine(int position) {
            if (al_itemTitleList.size() == 1 || position == al_itemTitleList.size() - 1) {
                View_maintenanceOtherItemLine.setVisibility(View.GONE);
            }
        }

        public void setViewAction(int position) {

            // editText 무한 루프를 방지하기 위해 연결을 자유롭게 끊기 위해 따로 만듬
            // 메모 실시간 글자 수 세주기
            TextWatcher textWatcherMemo = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input_MtOtMemo = et_maintenanceOtherItemMemo.getText().toString();
                    tv_maintenanceOtherItemMemoCount.setText(input_MtOtMemo.length() + "/250");

                    // MainRecord_Data의 MainRecordItem 리스트에 메모 삽입
                    MaintenanceOtherRecordActivity.carbookRecordItemExpenseMemoList.set(position, et_maintenanceOtherItemMemo.getText().toString());
                    //CarbookRecord_Data.carbookRecordItemArrayList_insertDB.get(position).carbookRecordItemExpenseMemo = et_MtOtItemMemo.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            // 누적 지출금액 천단위 콤마 적용
            // 누적 지출 금액 입력할때마다 동작
            TextWatcher textWatcherCost = new TextWatcher() {
                String ItemPrice = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(ItemPrice)) {
                        // MainRecord_Data의 MainRecordItem 리스트에 메모 삽입
                        MaintenanceOtherRecordActivity.carbookRecordItemExpenseCostList.set(position, et_maintenanceOtherItemPrice.getText().toString());
                        if (et_maintenanceOtherItemPrice.getText().toString().matches("0")) {
                            // 맨 처음 앞자리가 0이면 더이상 입력 못하게 지운다
                            et_maintenanceOtherItemPrice.setText("");
                        }
                        ItemPrice = makeStringComma(s.toString().replace(",", ""));
                        et_maintenanceOtherItemPrice.setText(ItemPrice);
                        et_maintenanceOtherItemPrice.setSelection(et_maintenanceOtherItemPrice.length());
                        // 아래 방법은 CumulativeMileage 문자열 길이를 뽑아서 해당 길이가 4면 커서를 4만큼만 이동시키겠다 인데 Editable이걸 왜 만드는지 모르겠다.
                        // 위처럼 setSelection()로 다이렉트로 커서 위치 시키면 바로 되는 것을 아래처럼 하는 이유를 조사해서 알기전까진 위 방법으로 할 생각이다.
                    /*Editable editable = et_cumulativeMileage.getText();
                    Selection.setSelection(editable, CumulativeMileage.length());*/
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            et_maintenanceOtherItemMemo.addTextChangedListener(textWatcherMemo);
            et_maintenanceOtherItemPrice.addTextChangedListener(textWatcherCost);
        }
    }
}