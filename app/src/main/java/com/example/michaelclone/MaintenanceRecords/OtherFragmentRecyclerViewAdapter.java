package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
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
    boolean otherSingleItemboolean = false;
    // 단일 항목 클릭시 해당 단일 항목만 block 처리를 안하기 위한 예외처리용 변수
    String SingleItemTitle = "null";
    // itemBlock()메소드는 기타항목에서 단일 항목을 클릭했을때만 돌아야하기에 해당 boolean값으로 조건을 건다.
    boolean isExecutionViewHandler = false;

    // 시간
    public OtherFragmentRecyclerViewAdapter(Context context, ArrayList<String> ItemTitleList, ArrayList<Integer> ItemTypeList, MaintenanceRecyclerViewAdapter maintenanceRecyclerViewAdapter) {
        this.context = context;
        this.itemTitleList = ItemTitleList;
        this.itemTypeList = ItemTypeList;
        this.maintenanceRecyclerViewAdapter = maintenanceRecyclerViewAdapter;
    }

    // 단일 항목 클릭시 MaintenanceRecyclerViewAdapter를 새로고침 하기에 새로고침할때 OtherFragmentRecyclerViewAdapter의 단일 항목을 클릭했는지 확인해주는 otherSingleItemboolean를 getIsItemBlock로 통해 확인 후 true면 전부 block, false면 정상 뷰
    public boolean getIsItemBlock() {
        return otherSingleItemboolean;
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
            view = layoutInflater.inflate(R.layout.other_records_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.add_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰 타입이 항목 타입이면 0: 항목, 1: 새 항목 추가
        if (itemTypeList.get(position) == 0) {
            bindView(position, holder);
            checkCheckBox(position, holder);
            holder.itemView.setOnClickListener(checkCheckBoxOnClickListener(holder));
            if (isExecutionViewHandler){
                itemBlock(holder);
                // 돌고나면 다시 bind때 계속 돌지 않도록 막아둔다.
                isExecutionViewHandler = false;
            }
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
            if (!other_itemTitle.equals(context.getResources().getString(R.string.carInsurance)) && !other_itemTitle.equals(SingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.trafficFine)) && !other_itemTitle.equals(SingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.automobileTax)) && !other_itemTitle.equals(SingleItemTitle)) {

                holder.tv_other_itemTitle.setTextColor(Color.parseColor("#D3D3D3"));
                holder.cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#D3D3D3")));
                holder.View_other_itemDividingLine.setBackgroundColor(Color.parseColor("#D3D3D3"));
                holder.Ln_other_item.setClickable(false);
                // 체크 박스는 block처리되면 이제까지 체크했던 기록은 전부 없앤다. (어차피 block처리되면 리사이클러뷰가 초기화되기에 checkList에 저장된 데이터도 초기화되서 신경 쓸 필요 없다.)
                holder.cb_other_itemSelect.setChecked(false);
            } else {
                Toast.makeText(context, other_itemTitle + " " + context.getResources().getString(R.string.selectSingleItemToast), Toast.LENGTH_SHORT).show();
            }

        } else {
            if (!other_itemTitle.equals(context.getResources().getString(R.string.carInsurance)) && !other_itemTitle.equals(SingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.trafficFine)) && !other_itemTitle.equals(SingleItemTitle) ||
                    !other_itemTitle.equals(context.getResources().getString(R.string.automobileTax)) && !other_itemTitle.equals(SingleItemTitle)) {

                holder.tv_other_itemTitle.setTextColor(Color.parseColor("#000000"));
                holder.cb_other_itemSelect.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                holder.View_other_itemDividingLine.setBackgroundColor(Color.parseColor("#33000000"));
                holder.Ln_other_item.setClickable(true);
                // 체크 박스는 block처리되면 이제까지 체크했던 기록은 전부 없앤다. (어차피 block처리되면 리사이클러뷰가 초기화되기에 checkList에 저장된 데이터도 초기화되서 신경 쓸 필요 없다.)
                holder.cb_other_itemSelect.setChecked(false);
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
            // 아이템 한개를 선택할때 선택 아이템 개수가 0개일수는 없으니 추가 했을떄의 예외처리만 있다.
            if (!holder.cb_other_itemSelect.isChecked()) {
                holder.cb_other_itemSelect.setChecked(true);
                SelectMaintenanceItemActivity.selectItemTitleList.add(holder.tv_other_itemTitle.getText().toString());
                if (SelectMaintenanceItemActivity.viewHandler != null) {
                    SelectMaintenanceItemActivity.viewHandler.obtainMessage(1, holder.tv_other_itemTitle.getText().toString()).sendToTarget();
                }
                // 단일 항목들 체크
                singleItemCheck(true, holder);

                // 아이템 한개를 선택 취소할때 선택한 아이템 개수가 0개면 if문 첫번쨰를 타고 선택한 아이템 개수가 1개 이상이면 else문을 탄다.
            } else {
                holder.cb_other_itemSelect.setChecked(false);
                SelectMaintenanceItemActivity.selectItemTitleList.remove(holder.tv_other_itemTitle.getText().toString());
                if (SelectMaintenanceItemActivity.viewHandler != null) {
                    SelectMaintenanceItemActivity.viewHandler.obtainMessage(2, holder.tv_other_itemTitle.getText().toString()).sendToTarget();
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
            // MaintenanceRecyclerViewAdapter의 핸들러에 보낼 Message객체 생성
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            if (Checked) {
                otherSingleItemboolean = true;
                SingleItemTitle = holder.tv_other_itemTitle.getText().toString();
                singleItemTitleList.add(holder.tv_other_itemTitle.getText().toString());
                SelectMaintenanceItemActivity.selectItemTitleList.clear();

                // 새로운
                SelectMaintenanceItemActivity.selectItemTitleList.add(holder.tv_other_itemTitle.getText().toString());
                // 단일 항목 선택으로 인한 정비 항목 block
                bundle.putBoolean("singleItemCheck", true);

            } else {
                otherSingleItemboolean = false;
                SingleItemTitle = "null";
                singleItemTitleList.remove(holder.tv_other_itemTitle.getText().toString());

                // 새로운
                SelectMaintenanceItemActivity.selectItemTitleList.remove(holder.tv_other_itemTitle.getText().toString());
                // 단일 항목 선택해제로 인한 정비 항목 block 품
                bundle.putBoolean("singleItemCheck", false);
            }

            // itemBlock()를 실행 시킬지 여부 (이 조건 안걸면 리사이클러뷰 bind때마다 계속 돈다.)
            isExecutionViewHandler = true;
            // 자기 자신 새로 고침
            notifyDataSetChanged();
            // MaintenanceRecyclerViewAdapter 새로 고침을 위한 핸들러 원격 동작
            if (MaintenanceRecyclerViewAdapter.viewHandler != null) {
                message.setData(bundle);
                MaintenanceRecyclerViewAdapter.viewHandler.sendMessage(message);
            }
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
        int viewType;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            setView();
        }

        public void setView() {
            // 0: 항목 레이아웃, 1: 항목 추가 버튼
            if (viewType == 0) {
                tv_other_itemTitle = itemView.findViewById(R.id.tv_other_itemTitle);
                cb_other_itemSelect = itemView.findViewById(R.id.cb_other_itemSelect);
                View_other_itemDividingLine = itemView.findViewById(R.id.View_other_itemDividingLine);
                Ln_other_item = itemView.findViewById(R.id.Ln_other_item);
                // 아이템 뷰를 클릭시 체크박스 컨트롤을 위해 아예 체크박스 단일 클릭을 막아버렸다. (체크 박스 단일 클릭시 로직이 꼬여서 막음)
                cb_other_itemSelect.setClickable(false);
            }
        }
    }
}
