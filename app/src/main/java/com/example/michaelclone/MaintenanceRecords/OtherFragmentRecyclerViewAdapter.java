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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michaelclone.R;

import java.util.ArrayList;
import java.util.HashMap;

public class OtherFragmentRecyclerViewAdapter extends RecyclerView.Adapter<OtherFragmentRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> singleItemTitleList = new ArrayList<>();
    ArrayList<String> itemTitleList;
    ArrayList<Integer> itemTypeList;
    HashMap<Integer, Boolean> checkedHashMap_other = new HashMap<>();
    Context context;
    boolean setChecked = false;
    MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter;
    SelectMaintenanceItemActivity selectMaintenanceItemActivity;
    boolean otherSingleItemboolean = false;

    // 시간
    public OtherFragmentRecyclerViewAdapter(Context context, ArrayList<String> ItemTitleList, ArrayList<Integer> ItemTypeList, MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter,
                                            SelectMaintenanceItemActivity selectMaintenanceItemActivity) {
        this.context = context;
        this.itemTitleList = ItemTitleList;
        this.itemTypeList = ItemTypeList;
        this.maintenanceRecyclerViewAdapter = maintenanceRecyclerViewAdapter;
        this.selectMaintenanceItemActivity = selectMaintenanceItemActivity;
    }

    // 단일 항목 클릭시 MaintenanceRecyclerViewAdapter를 새로고침 하기에 새로고침할때 OtherFragmentRecyclerViewAdapter의 단일 항목을 클릭했는지 확인해주는 otherSingleItemboolean를 getIsItemBlock로 통해 확인 후 true면 전부 block, false면 정상 뷰
    public boolean getIsItemBlock(){
        return otherSingleItemboolean;
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("아이템 선택 리사이클러뷰 ㄱㄱ?", "222");
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
            view = layoutInflater.inflate(R.layout.other_records_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.add_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰 타입이 항목 타입이면 0: 항목, 1: 새 항목 추가
        if (itemTypeList.get(position) == 0) {
            bindView(position, holder);
            checkCheckBox(position, holder);
            holder.itemView.setOnClickListener(checkCheckBoxOnClickListener(holder));

            itemBlock(holder);
        }
    }

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

    public void checkCheckBox(int position, ViewHolder holder) {
        // 처음에 checkedHashMap_maintenance에 ItemTitleList크기만큼 false를 넣는다.
        if (!setChecked) { // 체크
            for (int i = 0; i < itemTitleList.size(); i++) {
                checkedHashMap_other.put(i, false);
            }
            setChecked = true; // 완료
        }

        // 리사이클러뷰 특성상 뷰를 재활용 할 때 체크박스가 그대로 체크되어있는 것을 방지하기위해 position마다
        // 체크박스 isboolean여부가 들어있는 checkedHashMap_maintenance를 참고하여 체크박스 상태를 세팅한다.
        if (checkedHashMap_other.get(position)) {
            holder.cb_other_itemSelect.setChecked(true);
        } else {
            holder.cb_other_itemSelect.setChecked(false);
        }
    }

    public void bindView(int position, ViewHolder holder) {
        holder.tv_other_itemTitle.setText(itemTitleList.get(position));

        cb_setChecked(position, holder);
    }

    // 단일 항목을 클릭한 것인지 확인용 메소드
    public void itemBlock(ViewHolder holder) {
        String other_itemTitle = holder.tv_other_itemTitle.getText().toString();
        if (otherSingleItemboolean) {
            if (!other_itemTitle.equals(context.getResources().getString(R.string.carInsurance)) && !other_itemTitle.equals(Data_MaintenanceRecords.maintenanceSingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.trafficFine)) && !other_itemTitle.equals(Data_MaintenanceRecords.maintenanceSingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.automobileTax)) && !other_itemTitle.equals(Data_MaintenanceRecords.maintenanceSingleItemTitle)) {

                holder.tv_other_itemTitle.setTextColor(Color.parseColor("#D3D3D3"));
                holder.cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                holder.View_other_itemDividingLine.setBackgroundColor(Color.parseColor("#D3D3D3"));
                holder.Ln_other_item.setClickable(false);
            } else {
                Toast.makeText(context, other_itemTitle + " " + context.getResources().getString(R.string.selectSingleItemToast), Toast.LENGTH_SHORT).show();
            }

        } else {
            if (!other_itemTitle.equals(context.getResources().getString(R.string.carInsurance)) && !other_itemTitle.equals(Data_MaintenanceRecords.maintenanceSingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.trafficFine)) && !other_itemTitle.equals(Data_MaintenanceRecords.maintenanceSingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.automobileTax)) && !other_itemTitle.equals(Data_MaintenanceRecords.maintenanceSingleItemTitle)) {

                holder.tv_other_itemTitle.setTextColor(Color.parseColor("#000000"));
                holder.cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                holder.View_other_itemDividingLine.setBackgroundColor(Color.parseColor("#33000000"));
                holder.Ln_other_item.setClickable(true);
            }
        }
    }

    public void cb_setChecked(int position, ViewHolder holder) {
        holder.cb_other_itemSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                    checkedHashMap_other.replace(position, true);
                    holder.cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00D4FF")));
                } else {
                    // 체크박스가 변할때마다 onBindViewHolder에서 받아온 position값에 위치한 checkedHashMap boolean값을 변경해준다.
                    checkedHashMap_other.replace(position, false);
                    holder.cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                }
            }
        });
    }

    View.OnClickListener checkCheckBoxOnClickListener(ViewHolder holder) {
        return v -> {
            if (!holder.cb_other_itemSelect.isChecked()) {
                holder.cb_other_itemSelect.setChecked(true);

                Data_MaintenanceRecords.al_itemTitleList.add(holder.tv_other_itemTitle.getText().toString());
                selectMaintenanceItemActivity.tv_itemCount.setText(Data_MaintenanceRecords.al_itemTitleList.size() + context.getResources().getString(R.string.selectionCount));

                // 단일 항목들 체크
                singleItemCheck(true, holder);
            } else {
                holder.cb_other_itemSelect.setChecked(false);
                Data_MaintenanceRecords.al_itemTitleList.remove(holder.tv_other_itemTitle.getText().toString());
                if (Data_MaintenanceRecords.al_itemTitleList.size() <= 0) {
                    selectMaintenanceItemActivity.tv_itemCount.setText(context.getResources().getString(R.string.PleaseSelectAnItem));
                    selectMaintenanceItemActivity.tv_selectionConfirm.setTextColor(ColorStateList.valueOf(Color.parseColor("#1A000000")));
                    selectMaintenanceItemActivity.tv_selectionConfirm.setClickable(false);
                } else {
                    selectMaintenanceItemActivity.tv_itemCount.setText(Data_MaintenanceRecords.al_itemTitleList.size() + context.getResources().getString(R.string.selectionCount));
                }

                // 단일 항목들 체크
                singleItemCheck(false, holder);
            }
        };
    }

    /*단일 항목을 체크 할때마다 해당 단일 항목을 제외한 나머지 항목은 전부 터치를 못하게 막는다.*/
    public void singleItemCheck(boolean Checked, ViewHolder holder) {
        String itemTitle = holder.tv_other_itemTitle.getText().toString();
        if (itemTitle.equals(context.getResources().getString(R.string.carInsurance)) ||
                itemTitle.equals(context.getResources().getString(R.string.trafficFine)) ||
                itemTitle.equals(context.getResources().getString(R.string.automobileTax))) {

            if (Checked) {
                otherSingleItemboolean = true;
                Data_MaintenanceRecords.maintenanceSingleItemTitle = holder.tv_other_itemTitle.getText().toString();
                singleItemTitleList.add(holder.tv_other_itemTitle.getText().toString());

            } else {
                otherSingleItemboolean = false;
                Data_MaintenanceRecords.maintenanceSingleItemTitle = "null";
                singleItemTitleList.remove(holder.tv_other_itemTitle.getText().toString());
            }
            notifyDataSetChanged();
            maintenanceRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return itemTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_other_itemTitle;
        CheckBox cb_other_itemSelect;
        View View_other_itemDividingLine;
        LinearLayout Ln_other_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setView();
        }

        public void setView() {
            tv_other_itemTitle = itemView.findViewById(R.id.tv_other_itemTitle);
            cb_other_itemSelect = itemView.findViewById(R.id.cb_other_itemSelect);
            View_other_itemDividingLine = itemView.findViewById(R.id.View_other_itemDividingLine);
            Ln_other_item = itemView.findViewById(R.id.Ln_other_item);
            // 아이템 뷰를 클릭시 체크박스 컨트롤을 위해 아예 체크박스 단일 클릭을 막아버렸다. (체크 박스 단일 클릭시 로직이 꼬여서 막음)
            cb_other_itemSelect.setClickable(false);
        }
    }
}
