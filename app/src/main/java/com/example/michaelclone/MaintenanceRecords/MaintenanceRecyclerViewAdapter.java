package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MaintenanceRecyclerViewAdapter extends RecyclerView.Adapter<MaintenanceRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> itemTitleList;
    ArrayList<String> itemDistanceList;
    ArrayList<String> itemLifeSpanList;
    ArrayList<Integer> itemTypeList;
    HashMap<Integer, Boolean> checkedHashMap_maintenance = new HashMap<>();
    Context context;
    boolean setChecked = false; // checkedHashMap_maintenance 처음에 초기화 했는지 확인 변수
    SelectMaintenanceItemActivity selectMaintenanceItemActivity;
    boolean maintenanceSingleItemboolean = false;
    OtherFragmentRecyclerViewAdapter otherFragmentRecyclerViewAdapter;

    /**
     * 1. ItemTypeList을 기준으로 항목을 추가할지 새 항목 추가 버튼을 추가할지 정해지며 | 0: 항목, 1: 추가버튼이다.
     **/
    public MaintenanceRecyclerViewAdapter(Context context, ArrayList<String> ItemTitleList, ArrayList<String> ItemDistanceList, ArrayList<String> ItemLifeSpanList,
                                          ArrayList<Integer> ItemTypeList, SelectMaintenanceItemActivity selectMaintenanceItemActivity,
                                          OtherFragmentRecyclerViewAdapter otherFragmentRecyclerViewAdapter) {
        this.itemTitleList = ItemTitleList;
        this.itemDistanceList = ItemDistanceList;
        this.itemLifeSpanList = ItemLifeSpanList;
        this.itemTypeList = ItemTypeList;
        this.context = context;
        this.selectMaintenanceItemActivity = selectMaintenanceItemActivity;
        this.otherFragmentRecyclerViewAdapter = otherFragmentRecyclerViewAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        // 0: 항목 레이아웃, 1: 항목 추가 버튼
        if (itemTypeList.get(position) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.maintenance_records_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.add_item, parent, false);
        }
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // itemTypeList.get(position)이 1이면 항목추가 버튼 뷰가 보이기에 해당 조건문을 걸었다.
        if (itemTypeList.get(position) == 0) {
            bindView(position, holder);
            checkCheckBox(position, holder);
            holder.itemView.setOnClickListener(checkCheckBoxOnClickListener(holder));


            // 기타 목록에서 단일 항목 클릭 여부 확인
            if (otherFragmentRecyclerViewAdapter.getIsItemBlock()) {
                itemBlock(holder);
            }
            Log.i("getIsItemBlock", String.valueOf(otherFragmentRecyclerViewAdapter.getIsItemBlock()));
        }
    }

    //ArrayList<Integer> checkList = new ArrayList<>();
    ArrayList<String> checkList = new ArrayList<>();

    // SelectMaintenanceItemActivity에서 확인 버튼 누를때 체크한 항목 리스트를 넘기기 위한 메소드
    public ArrayList<String> getCheckList() {
        return checkList;
    }

    // itemView를 클릭시 checkList에 해당 position값이 있는지 체크하고 있다면 제거하고 없다면 집어넣는다.
    public void checkItemClick(String itemTitle) {
        if (checkList.contains(itemTitle)) {
            checkList.remove(itemTitle);
        } else {
            checkList.add(itemTitle);
        }
    }

    /*
     * 1. 처음에 항목들의 체크 상태를 저장하는 해쉬맵에 항목 수만큼 false를 넣는다.
     * 2. 다 넣은 후에는 리사이클러뷰가 뷰를 재활용할때마다 바인드를 거치면서 포지션과 해쉬맵에 들어있는 포지션 키값을 비교하여
     *    체크를 boolean 값을 체크한다.
     * */
    View.OnClickListener checkCheckBoxOnClickListener(ViewHolder holder) {
        return v -> {

            // 아이템 뷰를 클릭시 체크박스가 false면 true, true면 false로 변경 후
            if (!holder.cb_maintenance_itemSelect.isChecked()) {
                holder.cb_maintenance_itemSelect.setChecked(true);
                Data_MaintenanceRecords.al_itemTitleList.add(holder.tv_maintenance_itemTitle.getText().toString());
                selectMaintenanceItemActivity.tv_itemCount.setText(Data_MaintenanceRecords.al_itemTitleList.size() + context.getResources().getString(R.string.selectionCount));

                if (Data_MaintenanceRecords.al_itemTitleList.size() > 0) {
                    selectMaintenanceItemActivity.tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#80000000")));
                    selectMaintenanceItemActivity.tv_selectionConfirm.setClickable(true);
                }
            } else {
                holder.cb_maintenance_itemSelect.setChecked(false);
                Data_MaintenanceRecords.al_itemTitleList.remove(holder.tv_maintenance_itemTitle.getText().toString());
                if (Data_MaintenanceRecords.al_itemTitleList.size() <= 0) {
                    selectMaintenanceItemActivity.tv_itemCount.setText(context.getResources().getString(R.string.PleaseSelectAnItem));
                    selectMaintenanceItemActivity.tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#1A000000")));
                    selectMaintenanceItemActivity.tv_selectionConfirm.setClickable(false);
                } else {
                    selectMaintenanceItemActivity.tv_itemCount.setText(Data_MaintenanceRecords.al_itemTitleList.size() + context.getResources().getString(R.string.selectionCount));
                    selectMaintenanceItemActivity.tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#80000000")));
                    selectMaintenanceItemActivity.tv_selectionConfirm.setClickable(true);
                }
            }

            // 이미 bindView에서 먼저 해당 메소드를 실행해 놓았기에 딱히 클릭할떄마다 해당 메소드를 또 동작 할 필요는 없을 듯 하다.
            // cb_setChecked(position, holder);
            checkItemClick(holder.tv_maintenance_itemTitle.getText().toString());
        };
    }

    // 체크박스 상태 변화시 동작
    public void cb_setChecked(int position, ViewHolder holder) {
        holder.cb_maintenance_itemSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                    checkedHashMap_maintenance.replace(position, true);
                    holder.cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00D4FF")));
                } else {
                    // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                    checkedHashMap_maintenance.replace(position, false);
                    holder.cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                }
            }
        });
    }


    public void checkCheckBox(int position, ViewHolder holder) {
        // 처음에 checkedHashMap_maintenance에 ItemTitleList크기만큼 false를 넣는다.
        if (!setChecked) { // 체크
            for (int i = 0; i < itemTitleList.size(); i++) {
                checkedHashMap_maintenance.put(i, false);
            }
            setChecked = true; // 완료
        }

        // 리사이클러뷰 특성상 뷰를 재활용 할 때 체크박스가 그대로 체크되어있는 것을 방지하기위해 position마다
        // 체크박스 isboolean여부가 들어있는 checkedHashMap_maintenance를 참고하여 체크박스 상태를 세팅한다.
        if (checkedHashMap_maintenance.get(position)) {
            holder.cb_maintenance_itemSelect.setChecked(true);
        } else {
            holder.cb_maintenance_itemSelect.setChecked(false);
        }
    }

    public void bindView(int position, ViewHolder holder) {
        holder.tv_maintenance_itemTitle.setText(itemTitleList.get(position));
        holder.tv_maintenance_itemDistance.setText(itemDistanceList.get(position));
        holder.tv_maintenance_itemMonth.setText(itemLifeSpanList.get(position));

        cb_setChecked(position, holder);
    }

    // 단일 항목을 클릭한 것인지 확인용 메소드
    public void itemBlock(ViewHolder holder) {
        if (maintenanceSingleItemboolean) {
            holder.tv_maintenance_itemTitle.setTextColor(Color.parseColor("#D3D3D3"));
            holder.tv_maintenance_itemDistance.setTextColor(Color.parseColor("#D3D3D3"));
            holder.tv_maintenance_itemMonth.setTextColor(Color.parseColor("#D3D3D3"));
            holder.tv_maintenance_itemSubText01.setTextColor(Color.parseColor("#D3D3D3"));
            holder.tv_maintenance_itemSubText02.setTextColor(Color.parseColor("#D3D3D3"));
            holder.cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#1A000000")));
            holder.view_maintenance_itemDividingLine.setBackgroundColor(Color.parseColor("#1A000000"));
            holder.view_maintenance_itemDividingLine02.setBackgroundColor(Color.parseColor("#1A000000"));
            holder.Ln_maintenance_item.setClickable(false);
            // 체크 박스는 block처리되면 이제까지 체크했던 기록은 전부 없앤다. (어차피 block처리되면 리사이클러뷰가 초기화되기에 checkList에 저장된 데이터도 초기화되서 신경 쓸 필요 없다.)
            holder.cb_maintenance_itemSelect.setChecked(false);
        } else {
            holder.tv_maintenance_itemTitle.setTextColor(Color.parseColor("#000000"));
            holder.tv_maintenance_itemDistance.setTextColor(Color.parseColor("#80000000"));
            holder.tv_maintenance_itemMonth.setTextColor(Color.parseColor("#80000000"));
            holder.tv_maintenance_itemSubText01.setTextColor(Color.parseColor("#80000000"));
            holder.tv_maintenance_itemSubText02.setTextColor(Color.parseColor("#80000000"));
            holder.cb_maintenance_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
            holder.view_maintenance_itemDividingLine.setBackgroundColor(Color.parseColor("#33000000"));
            holder.view_maintenance_itemDividingLine02.setBackgroundColor(Color.parseColor("#33000000"));
            holder.Ln_maintenance_item.setClickable(true);
            // 체크 박스는 block처리되면 이제까지 체크했던 기록은 전부 없앤다. (어차피 block처리되면 리사이클러뷰가 초기화되기에 checkList에 저장된 데이터도 초기화되서 신경 쓸 필요 없다.)
            holder.cb_maintenance_itemSelect.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return itemTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_maintenance_itemTitle;
        TextView tv_maintenance_itemDistance;
        TextView tv_maintenance_itemMonth;
        TextView tv_maintenance_itemSubText01;
        TextView tv_maintenance_itemSubText02;
        CheckBox cb_maintenance_itemSelect;
        View view_maintenance_itemDividingLine;
        View view_maintenance_itemDividingLine02;
        LinearLayout Ln_maintenance_item;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            setView();
        }

        public void setView() {
            tv_maintenance_itemTitle = itemView.findViewById(R.id.tv_maintenance_itemTitle);
            tv_maintenance_itemDistance = itemView.findViewById(R.id.tv_maintenance_itemDistance);
            tv_maintenance_itemMonth = itemView.findViewById(R.id.tv_maintenance_itemMonth);
            tv_maintenance_itemSubText01 = itemView.findViewById(R.id.tv_maintenance_itemSubText01);
            tv_maintenance_itemSubText02 = itemView.findViewById(R.id.tv_maintenance_itemSubText02);
            view_maintenance_itemDividingLine = itemView.findViewById(R.id.View_maintenance_itemDividingLine);
            view_maintenance_itemDividingLine02 = itemView.findViewById(R.id.View_maintenance_itemDividingLine02);
            Ln_maintenance_item = itemView.findViewById(R.id.Ln_maintenance_item);
            cb_maintenance_itemSelect = itemView.findViewById(R.id.cb_maintenance_itemSelect);
            // 아이템 뷰를 클릭시 체크박스 컨트롤을 위해 아예 체크박스 단일 클릭을 막아버렸다. (체크 박스 단일 클릭시 로직이 꼬여서 막음)
            cb_maintenance_itemSelect.setClickable(false);
        }
    }
}
