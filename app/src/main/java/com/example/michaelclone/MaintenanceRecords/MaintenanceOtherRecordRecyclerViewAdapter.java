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
    boolean isFirstNotifyDataSetChanged = true;
    TextWatcher textWatcherMemo = null;
    TextWatcher textWatcherCost = null;

    public MaintenanceOtherRecordRecyclerViewAdapter(Context context, ArrayList<CarbookRecordItem> carbookRecordItems) {
        this.context = context;
        this.carbookRecordItems = carbookRecordItems;
        Log.i("항목어뎁터111", String.valueOf(carbookRecordItems));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintenance_other_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("항목어뎁터", String.valueOf(carbookRecordItems));
        setViewText(holder, holder.getAdapterPosition());
        holder.goneMaintenanceItemLine(position);
        resetTextWatcher(holder);
        holder.setViewAction(position);
    }

    @Override
    public int getItemCount() {
        return carbookRecordItems.size();
    }

    public void setViewText(ViewHolder holder, int position) {
        if (carbookRecordItems != null) {
            holder.tv_maintenanceOtherItemTitle.setText(carbookRecordItems.get(position).carbookRecordItemCategoryName);
            Log.i("setViewTextcarbookRecordItemExpenseMemo_Position", String.valueOf(position));
            Log.i("setViewTextcarbookRecordItemExpenseMemo", carbookRecordItems.get(position).carbookRecordItemExpenseMemo);
            if (carbookRecordItems.get(position).carbookRecordItemExpenseMemo.equals("")) {
                holder.tv_maintenanceOtherItemMemoCount.setText("0" + context.getString(R.string.ItemMemoCharacterLimit));
                holder.et_maintenanceOtherItemMemo.setText("");
            } else {
                holder.tv_maintenanceOtherItemMemoCount.setText(carbookRecordItems.get(position).carbookRecordItemExpenseMemo.length() + context.getString(R.string.ItemMemoCharacterLimit));
                holder.et_maintenanceOtherItemMemo.setText(carbookRecordItems.get(position).carbookRecordItemExpenseMemo);
            }
            if (carbookRecordItems.get(position).carbookRecordItemExpenseCost.equals("0")) {
                holder.et_maintenanceOtherItemCost.setText("");
            } else {
                holder.et_maintenanceOtherItemCost.setText(stringFormat.makeStringComma(carbookRecordItems.get(position).carbookRecordItemExpenseCost));
            }

        } else {
            holder.tv_maintenanceOtherItemTitle.setText(carbookRecordItems.get(position).carbookRecordItemCategoryName);
        }
    }

    public void resetTextWatcher(ViewHolder holder) {
        if (textWatcherMemo != null) {
            holder.et_maintenanceOtherItemMemo.removeTextChangedListener(textWatcherMemo);
            textWatcherMemo = null;
        }
        if (textWatcherCost != null) {
            holder.et_maintenanceOtherItemCost.removeTextChangedListener(textWatcherCost);
            textWatcherCost = null;
        }
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

        // 아이템 마지막은 구분선이 계속 생기면 아래 구분선이 2개가 되기때문에 없애준다.
        public void goneMaintenanceItemLine(int position) {
            if (carbookRecordItems.size() == 1 || position == carbookRecordItems.size() - 1) {
                View_maintenanceOtherItemLine.setVisibility(View.GONE);
            }
        }

        public void setViewAction(int position) {
            Log.i("setViewAction", "setViewAction");
            // editText 무한 루프를 방지하기 위해 연결을 자유롭게 끊기 위해 따로 만듬
            // 메모 실시간 글자 수 세주기
            textWatcherMemo = new TextWatcher() {
                String input_MtOtMemo = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        // editText 글자 가져오기
                        if (et_maintenanceOtherItemMemo.isFocused()) {
                            if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(input_MtOtMemo)) {
                                input_MtOtMemo = s.toString();
                                tv_maintenanceOtherItemMemoCount.setText(input_MtOtMemo.length() + context.getString(R.string.ItemMemoCharacterLimit));
                            }
                            if (TextUtils.isEmpty(s.toString()) && s.toString().equals("")) {
                                input_MtOtMemo = "";
                                tv_maintenanceOtherItemMemoCount.setText("0" + context.getString(R.string.ItemMemoCharacterLimit));
                            }
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
            et_maintenanceOtherItemMemo.addTextChangedListener(textWatcherMemo);
            et_maintenanceOtherItemMemo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String memo = et_maintenanceOtherItemMemo.getText().toString();
                        if (TextUtils.isEmpty(memo) && memo.equals("")) {
                            memo = "";
                            carbookRecordItems.get(position).carbookRecordItemExpenseMemo = memo;
                        } else {
                            // MainRecord_Data의 MainRecordItem 리스트에 메모 삽입
                            carbookRecordItems.get(position).carbookRecordItemExpenseMemo = memo;
                        }
                    }
                }
            });

            et_maintenanceOtherItemCost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // editText에 포커스가 잡혀있다면 ,를 빼고 포커스가 벗어나면 콤마 메소드를 태워 ,를 넣는다.
                    // editText 글자 가져오기
                    if (hasFocus) {
                        String cost = et_maintenanceOtherItemCost.getText().toString().replace(",", "");
                        et_maintenanceOtherItemCost.setText(cost);
                        Log.i("포커스바뀌기전 ", cost);
                    } else {
                        String cost = et_maintenanceOtherItemCost.getText().toString();
                        if (!cost.equals("") || !cost.equals("0")) {
                            et_maintenanceOtherItemCost.setText(stringFormat.makeStringComma(cost));
                        }
                        if (cost.equals("") || cost.equals("0")) {
                            // edittext에 데이터가 ""이면 그냥 "0"으로 넣는다.
                            if (carbookRecordItems != null) {
                                carbookRecordItems.get(position).carbookRecordItemExpenseCost = "0";
                            }
                        } else {
                            // 숫자가 아닌 문자열이 들어왔을때에 대한 예외처리
                            // (간혹 해외폰에서 숫자키패드 제한을 걸어도 문자 키패드가 나타나는 경우가 있어 걸어놓은 제한)
                            if (cost.matches("^[0-9]*$")) {
                                cost = String.valueOf(Integer.parseInt(cost));
                                // 콤마 찍기 전에 먼저 DB에 넣을 HashMap에 넣고 콤마를 찍는다.
                                if (carbookRecordItems != null) {
                                    // MainRecord_Data의 MainRecordItem 리스트에 메모 삽입
                                    carbookRecordItems.get(position).carbookRecordItemExpenseCost = cost;
                                    Log.i("isFocus_carbookRecordItems.get(position).carbookRecordItemExpenseCost ", String.valueOf(carbookRecordItems.get(position).carbookRecordItemExpenseCost));
                                    Log.i("isFocus_carbookRecordItems.size ", String.valueOf(carbookRecordItems.size() - 1));
                                    Log.i("isFocus_carbookRecordItems.position ", String.valueOf(position));
                                }
                            }
                        }
                        Log.i("포커스바뀌기후 ", cost);
                    }
                }
            });
        }
    }
}
