package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.CarbookRecordItem;
import com.example.michaelclone.R;
import com.example.michaelclone.Tools.StringFormat;

import java.util.ArrayList;

public class MaintenanceOtherRecordRecyclerViewAdapter extends RecyclerView.Adapter<MaintenanceOtherRecordRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<CarbookRecordItem> carbookRecordItems;
    // 툴 클래스
    StringFormat stringFormat = new StringFormat();
    TextWatcher textWatcherMemo = null;
    TextWatcher textWatcherCost = null;

    public MaintenanceOtherRecordRecyclerViewAdapter(Context context, ArrayList<CarbookRecordItem> carbookRecordItems) {
        this.context = context;
        this.carbookRecordItems = carbookRecordItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintenance_other_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setViewText(holder, holder.getAdapterPosition());
        goneMaintenanceItemLine(holder, position);
        setViewAction(holder, position);
    }

    @Override
    public int getItemCount() {
        return carbookRecordItems.size();
    }

    public void setViewText(ViewHolder holder, int position) {
        if (carbookRecordItems != null) {
            holder.tv_maintenanceOtherItemTitle.setText(carbookRecordItems.get(position).carbookRecordItemCategoryName);

            // position번째 메모가 빈칸이거나 null인경우 빈칸 표시, 메모 개수 0
            String memo;
            if (TextUtils.isEmpty(carbookRecordItems.get(position).carbookRecordItemExpenseMemo)) {
                memo = "";
            } else {
                memo = carbookRecordItems.get(position).carbookRecordItemExpenseMemo;
            }
            holder.tv_maintenanceOtherItemMemoCount.setText(memo.length() + context.getString(R.string.ItemMemoCharacterLimit));
            holder.et_maintenanceOtherItemMemo.setText(memo);

            // position번째 비용이 0원인 경우 빈칸으로 표시
            String cost;
            if (TextUtils.isEmpty(carbookRecordItems.get(position).carbookRecordItemExpenseCost) || carbookRecordItems.get(position).carbookRecordItemExpenseCost.equals("0")) {
                cost = "";
            } else {
                cost = stringFormat.makeStringComma(carbookRecordItems.get(position).carbookRecordItemExpenseCost);
            }
            holder.et_maintenanceOtherItemCost.setText(cost);
        }
    }

    // 아이템 마지막은 구분선이 계속 생기면 아래 구분선이 2개가 되기때문에 없애준다.
    public void goneMaintenanceItemLine(ViewHolder holder, int position) {
        if (carbookRecordItems.size() == 1 || position == carbookRecordItems.size() - 1) {
            holder.View_maintenanceOtherItemLine.setVisibility(View.GONE);
        }
    }

    // 카운트는 실시간이여야하기에 textWatcher에 놓고 데이터 세팅은 포커스에 놔둔다.
    public void setViewAction(ViewHolder holder, int position) {
        // 메모 실시간 글자 수 세주기
        textWatcherMemo = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String input_MtOtMemo = "";
                    // editText 글자 가져오기
                    if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(input_MtOtMemo)) {
                        input_MtOtMemo = s.toString();
                    }
                    holder.tv_maintenanceOtherItemMemoCount.setText(input_MtOtMemo.length() + context.getString(R.string.ItemMemoCharacterLimit));
                    if (carbookRecordItems != null) {
                        carbookRecordItems.get(position).carbookRecordItemExpenseMemo = input_MtOtMemo;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        textWatcherCost = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String input_MtOtCost = "0";
                    // 현재 editText에 적힌 문자가 (null, 문자 길이 0, TextWatcher가 돌기 전의 문자와 현재 문자가 같이 않은 경우) 이 3가지 경우가 아닌 경우에 돈다.
                    if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(input_MtOtCost)) {
                        input_MtOtCost = s.toString().replace(",", "");
                        // 숫자가 아닌 문자열이 들어왔을때에 대한 예외처리
                        // (간혹 해외폰에서 숫자키패드 제한을 걸어도 문자 키패드가 나타나는 경우가 있어 걸어놓은 제한)
                        if (input_MtOtCost.matches("^[0-9]*$")) {
                            input_MtOtCost = String.valueOf(Long.parseLong(input_MtOtCost));
                        }
                    }
                    if (carbookRecordItems != null) {
                        carbookRecordItems.get(position).carbookRecordItemExpenseCost = input_MtOtCost;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        // 카운트는 실시간이여야하기에 textWatcher에 놓고 데이터 세팅은 포커스에 놔둔다.
        holder.et_maintenanceOtherItemMemo.addTextChangedListener(textWatcherMemo);
        holder.et_maintenanceOtherItemCost.addTextChangedListener(textWatcherCost);
        holder.et_maintenanceOtherItemCost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // editText에 포커스가 잡혀있다면 ,를 빼고 포커스가 벗어나면 콤마 메소드를 태워 ,를 넣는다.
                // editText 글자 가져오기
                if (hasFocus) {
                    String cost = holder.et_maintenanceOtherItemCost.getText().toString().replace(",", "");
                    holder.et_maintenanceOtherItemCost.setText(cost);
                } else {
                    String cost = holder.et_maintenanceOtherItemCost.getText().toString();
                    if (!(cost.equals("") || cost.equals("0"))) {
                        holder.et_maintenanceOtherItemCost.setText(stringFormat.makeStringComma(cost));
                    }
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_maintenanceOtherItemTitle;
        TextView tv_maintenanceOtherItemMemoCount;
        EditText et_maintenanceOtherItemCost;
        EditText et_maintenanceOtherItemMemo;
        View View_maintenanceOtherItemLine;
        LinearLayout Ln_maintenanceOtherItemRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
        }

        public void setView() {
            tv_maintenanceOtherItemTitle = itemView.findViewById(R.id.tv_MtOt_ItemTitle);
            tv_maintenanceOtherItemMemoCount = itemView.findViewById(R.id.tv_MtOtItemMemoCount);
            et_maintenanceOtherItemCost = itemView.findViewById(R.id.et_MtOt_ItemPrice);
            et_maintenanceOtherItemMemo = itemView.findViewById(R.id.et_MtOtItemMemo);
            View_maintenanceOtherItemLine = itemView.findViewById(R.id.View_maintenanceItemLine);
            Ln_maintenanceOtherItemRemove = itemView.findViewById(R.id.Ln_maintenanceOtherItemRemove);
        }
    }
}
