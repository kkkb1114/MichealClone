package com.example.michaelclone.DataBase;

import android.util.Log;

import java.util.ArrayList;

public class MainRecord_Data {

    public static ArrayList<MainRecord> mainRecordArrayList = new ArrayList<>();
    public static ArrayList<MainRecordItem> mainRecordItemArrayList = new ArrayList<>();

    public static ArrayList<MainRecord> MainRecordPageRecordArrayList = new ArrayList<>();
    public static ArrayList<MainRecordItem> MainRecordPageRecordItemArrayList = new ArrayList<>();

    public static ArrayList<MainRecordPage> MainRecordPageArrayList = new ArrayList<>();

    /*// 비용 계산 메소드
    public int CostCalculation(){
        int Cost = 0;
        // 전체 기록의 비용을 더해서 반환
        for (int i=0; i<MainRecordPageRecordItemArrayList.size(); i++){
            Cost = Cost + Integer.parseInt(MainRecordPageRecordItemArrayList.get(i).carbookRecordItemExpenseCost);
        }
        return Cost;
    }*/

    // sqlite에서 받아온 데이트 넣어주고 새 데이터를 받을 경우 초기화 한 후 다시 받음.
    public void MainRecordPageListClear(){
        MainRecordPageRecordArrayList.clear();
        MainRecordPageRecordItemArrayList.clear();
    }

}
