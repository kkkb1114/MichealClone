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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.DataBase.CarbookRecordItem;
import com.example.michaelclone.DataBase.CarbookRecordItem_DataBridge;
import com.example.michaelclone.R;
import com.example.michaelclone.Tools.StringFormat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MaintenanceOtherRecordRecyclerViewAdapter extends RecyclerView.Adapter<MaintenanceOtherRecordRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> al_itemTitleList;
    int carbookRecordId;
    ArrayList<CarbookRecordItem> carbookRecordItems;
    // 툴 클래스
    StringFormat stringFormat = new StringFormat();

    public MaintenanceOtherRecordRecyclerViewAdapter(Context context, ArrayList<String> al_itemTitleList, int carbookRecordId, ArrayList<CarbookRecordItem> carbookRecordItems) {
        this.context = context;
        this.al_itemTitleList = al_itemTitleList;
        this.carbookRecordId = carbookRecordId;
        this.carbookRecordItems = carbookRecordItems;

        // carbookRecordId은 제대로 가져왔다면 0과 같거나 이하일수 없기에 >로 조건을 붙임
        /*if (carbookRecordId > 0){
            carbookRecordItems = getCarbookRecordItems(carbookRecordId);
        }*/
    }

    public ArrayList<CarbookRecordItem> getCarbookRecordItems(int carbookRecordId) {
        CarbookRecordItem_DataBridge carbookRecordItem_dataBridge = new CarbookRecordItem_DataBridge();
        ArrayList<CarbookRecordItem> carbookRecordItems = carbookRecordItem_dataBridge.getMainRecordItemItemData(carbookRecordId);
        if (carbookRecordItems != null) {
            return carbookRecordItems;
        } else {
            return null;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintenance_other_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setViewText(holder, position);
        holder.goneMaintenanceItemLine(position);

        // editText 글자 가져오기
        holder.setViewAction(position);
        Log.i("ㅇㅇㅇ", String.valueOf(al_itemTitleList));
    }

    @Override
    public int getItemCount() {
        return al_itemTitleList.size();
    }

    public void setViewText(ViewHolder holder, int position) {
        if (carbookRecordItems != null) {
            holder.tv_maintenanceOtherItemTitle.setText(al_itemTitleList.get(position));
            if (carbookRecordItems.size() > position) {
                Log.i("수정항목선택", "carbookRecordItems.size(): "+carbookRecordItems.size() +"position: "+ "\n" + position + "\n" + "carbookRecordItems: "+carbookRecordItems);
                holder.tv_maintenanceOtherItemMemoCount.setText(carbookRecordItems.get(position).carbookRecordItemExpenseMemo.length() + context.getString(R.string.ItemMemoCharacterLimit));
                holder.et_maintenanceOtherItemCost.setText(stringFormat.makeStringComma(carbookRecordItems.get(position).carbookRecordItemExpenseCost));
                holder.et_maintenanceOtherItemMemo.setText(carbookRecordItems.get(position).carbookRecordItemExpenseMemo);
            }
        } else {
            holder.tv_maintenanceOtherItemTitle.setText(al_itemTitleList.get(position));
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
            if (al_itemTitleList.size() == 1 || position == al_itemTitleList.size() - 1) {
                View_maintenanceOtherItemLine.setVisibility(View.GONE);
            }
        }

        public void setViewAction(int position) {

            // editText 무한 루프를 방지하기 위해 연결을 자유롭게 끊기 위해 따로 만듬
            // 메모 실시간 글자 수 세주기
            TextWatcher textWatcherMemo = new TextWatcher() {
                String input_MtOtMemo = "";

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        if (s.toString() != null && !TextUtils.isEmpty(s.toString()) && !s.toString().equals(input_MtOtMemo) && et_maintenanceOtherItemMemo != null) {
                            input_MtOtMemo = et_maintenanceOtherItemMemo.getText().toString();
                            tv_maintenanceOtherItemMemoCount.setText(input_MtOtMemo.length() + context.getString(R.string.ItemMemoCharacterLimit));

                            // MainRecord_Data의 MainRecordItem 리스트에 메모 삽입
                            MaintenanceOtherRecordActivity.carbookRecordItemExpenseMemoList.put(position, et_maintenanceOtherItemMemo.getText().toString());
                            //CarbookRecord_Data.carbookRecordItemArrayList_insertDB.get(position).carbookRecordItemExpenseMemo = et_MtOtItemMemo.getText().toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(ItemPrice) && et_maintenanceOtherItemCost != null) {
                        if (et_maintenanceOtherItemCost.getText().toString().matches("0")) {
                            // 맨 처음 앞자리가 0이면 더이상 입력 못하게 지운다
                            et_maintenanceOtherItemCost.setText("");
                        }
                        try {
                            ItemPrice = s.toString().replace(",", "");
                            Log.i("ItemPrice111", ItemPrice);
                            // 숫자가 아닌 문자열이 들어왔을때에 대한 예외처리
                            // (간혹 해외폰에서 숫자키패드 제한을 걸어도 문자 키패드가 나타나는 경우가 있어 걸어놓은 제한)
                            if (ItemPrice.matches("^[0-9]*$")) {
                                ItemPrice = String.valueOf(Integer.parseInt(ItemPrice));
                                // 콤마 찍기 전에 먼저 DB에 넣을 HashMap에 넣고 콤마를 찍는다.
                                if (MaintenanceOtherRecordActivity.carbookRecordItemExpenseCostList != null) {
                                    // MainRecord_Data의 MainRecordItem 리스트에 메모 삽입
                                    MaintenanceOtherRecordActivity.carbookRecordItemExpenseCostList.put(position, ItemPrice);
                                    Log.i("ItemPrice222", ItemPrice);
                                }

                                ItemPrice = stringFormat.makeStringComma(ItemPrice);
                                et_maintenanceOtherItemCost.setText(ItemPrice);
                                et_maintenanceOtherItemCost.setSelection(et_maintenanceOtherItemCost.length());

                            } else {
                                Toast.makeText(context, "숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
                                et_maintenanceOtherItemCost.setText("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
            et_maintenanceOtherItemCost.addTextChangedListener(textWatcherCost);
        }
    }
}
