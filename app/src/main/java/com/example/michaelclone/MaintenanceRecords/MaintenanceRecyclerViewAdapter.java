package com.example.michaelclone.MaintenanceRecords;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.michaelclone.DataBase.CarbookRecordItem;
import com.example.michaelclone.MainRecord.MainrecordActivity;
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
    boolean maintenanceSingleItemboolean = false;
    // itemBlock()메소드는 기타항목에서 단일 항목을 클릭했을때만 돌아야하기에 해당 boolean값으로 조건을 건다.
    boolean isExecutionViewHandler = false;
    // 수정용 static CarbookRecordItem리스트
    ArrayList<CarbookRecordItem> carbookRecordItems = MaintenanceOtherRecordActivity.carbookRecordItems;
    // 항목 선택완료시 다음 화면으로 넘길 static 변수
    ArrayList<String> selectItemTitleList = MainrecordActivity.selectItemTitleList;

    /**
     * 1. ItemTypeList을 기준으로 항목을 추가할지 새 항목 추가 버튼을 추가할지 정해지며 | 0: 항목, 1: 추가버튼이다.
     **/
    public MaintenanceRecyclerViewAdapter(Context context, ArrayList<String> ItemTitleList, ArrayList<String> ItemDistanceList, ArrayList<String> ItemLifeSpanList,
                                          ArrayList<Integer> ItemTypeList) {
        this.itemTitleList = ItemTitleList;
        this.itemDistanceList = ItemDistanceList;
        this.itemLifeSpanList = ItemLifeSpanList;
        this.itemTypeList = ItemTypeList;
        this.context = context;
        itemBlockHandler();
        setChecked();
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
        return new ViewHolder(view, context, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // itemTypeList.get(position)이 1이면 항목추가 버튼 뷰가 보이기에 해당 조건문을 걸었다.
        if (itemTypeList.get(position) == 0) {
            bindView(position, holder);
            checkCheckBox(position, holder);
            holder.itemView.setOnClickListener(checkCheckBoxOnClickListener(holder));
            if (isExecutionViewHandler) {
                itemBlock(holder);
                // 돌고나면 다시 bind때 계속 돌지 않도록 막아둔다.
                isExecutionViewHandler = false;
            }
        }
    }

    /**
     * 1. 체크 박스 체크 여부를 결정할 checkedHashMap_maintenance을 초기 세팅한다.
     * 2. 수정용 static 변수 MaintenanceOtherRecordActivity.carbookRecordItems가 null이 아니면 수정모드이기에
     * 아이템 목록중 MaintenanceOtherRecordActivity.carbookRecordItems이 가지고 있는 문자열만
     * checkedHashMap_maintenance값을 true로 바꾸고 SelectMaintenanceItemActivity.selectItemTitleList에 해당 문자열을 넣는다.
     **/
    public void setChecked() {
        // 처음에 checkedHashMap_maintenance에 ItemTitleList크기만큼 false를 넣는다.
        for (int i = 0; i < itemTitleList.size(); i++) {
            checkedHashMap_maintenance.put(i, false);
        }

        Log.i("carbookRecordItems", String.valueOf(carbookRecordItems));
        /**
         * 1. carbookRecordItemTitleList에 carbookRecordItems의 carbookRecordItemCategoryName값을 전부 담아
         * 2. itemTitleList를 기준으로 for문을 돌려 carbookRecordItemTitleList에 문자열이 있다면 해당 회차는 checkedHashMap_maintenance에 true값을 넣는다.
         * **/
        if (carbookRecordItems != null) {
            ArrayList<String> carbookRecordItemTitleList = new ArrayList<>();
            for (int i = 0; i < selectItemTitleList.size(); i++) {
                //carbookRecordItemTitleList.add(carbookRecordItems.get(i).carbookRecordItemCategoryName);
                carbookRecordItemTitleList.add(selectItemTitleList.get(i));
            }

            for (int i = 0; i < itemTitleList.size(); i++) {
                if (carbookRecordItemTitleList.contains(itemTitleList.get(i))) {
                    checkedHashMap_maintenance.put(i, true);
                }
            }
            Log.i("SelectMaintenanceItemActivity.selectItemTitleList", String.valueOf(carbookRecordItemTitleList));
            Log.i("SelectMaintenanceItemActivity.selectItemTitleList", String.valueOf(checkedHashMap_maintenance));
        }
    }

    ArrayList<String> checkList = new ArrayList<>();

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

            // 아이템 한개를 선택할때 선택 아이템 개수가 0개일수는 없으니 추가 했을떄의 예외처리만 있다.
            // 아이템 뷰를 클릭시 체크박스가 false면 true, true면 false로 변경 후
            if (!holder.cb_maintenance_itemSelect.isChecked()) {
                holder.cb_maintenance_itemSelect.setChecked(true);
                selectItemTitleList.add(holder.tv_maintenance_itemTitle.getText().toString());
                if (SelectMaintenanceItemActivity.viewHandler != null) {
                    SelectMaintenanceItemActivity.viewHandler.obtainMessage(1, holder.tv_maintenance_itemTitle.getText().toString()).sendToTarget();
                }

                // 아이템 한개를 선택 취소할때 선택한 아이템 개수가 0개면 if문 첫번쨰를 타고 선택한 아이템 개수가 1개 이상이면 else문을 탄다.
            } else {
                holder.cb_maintenance_itemSelect.setChecked(false);
                selectItemTitleList.remove(holder.tv_maintenance_itemTitle.getText().toString());
                if (SelectMaintenanceItemActivity.viewHandler != null) {
                    SelectMaintenanceItemActivity.viewHandler.obtainMessage(2, holder.tv_maintenance_itemTitle.getText().toString()).sendToTarget();
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

        // 리사이클러뷰 특성상 뷰를 재활용 할 때 체크박스가 그대로 체크되어있는 것을 방지하기위해 position마다
        // 체크박스 isboolean여부가 들어있는 checkedHashMap_maintenance를 참고하여 체크박스 상태를 세팅한다.
        Log.i("checkedHashMap_maintenance111", String.valueOf(checkedHashMap_maintenance.get(position)));
        if (checkedHashMap_maintenance.get(position)) {
            holder.cb_maintenance_itemSelect.setChecked(true);
        } else {
            holder.cb_maintenance_itemSelect.setChecked(false);
        }
        Log.i("checkedHashMap_maintenance222", String.valueOf(checkedHashMap_maintenance.get(position)));
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
            //holder.cb_maintenance_itemSelect.setChecked(true);
            // block하면서 체크했던 기록 전부 없앤다.
            if (selectItemTitleList.contains(holder.tv_maintenance_itemTitle.getText().toString())) {
                selectItemTitleList.remove(holder.tv_maintenance_itemTitle.getText().toString());
            }

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
            //holder.cb_maintenance_itemSelect.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return itemTypeList.size();
    }


    //todo 여기서 핸들러를 만들어 기타 항목에서 단일 항목 선택시 원격으로 블록처리 할 예정이다.
    public static Handler viewHandler = null;

    private void itemBlockHandler() {
        try {
            viewHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    try {
                        Bundle bundle = msg.getData();
                        boolean singleItemCheck = bundle.getBoolean("singleItemCheck");
                        if (singleItemCheck) {
                            maintenanceSingleItemboolean = true;
                        } else {
                            maintenanceSingleItemboolean = false;
                        }
                        // itemBlock()를 실행 시킬지 여부 (이 조건 안걸면 리사이클러뷰 bind때마다 계속 돈다.)
                        isExecutionViewHandler = true;
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int viewType;

        public ViewHolder(@NonNull View itemView, Context context, int viewType) {
            super(itemView);
            this.context = context;
            this.viewType = viewType;
            setView();
        }

        public void setView() {
            // 0: 항목 레이아웃, 1: 항목 추가 버튼
            if (viewType == 0) {
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
}
