package com.example.michaelclone.DataBase;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class MainRecord_Data {

    public static ArrayList<MainRecord> mainRecordArrayList = new ArrayList<>();
    public static ArrayList<MainRecordItem> mainRecordItemArrayList = new ArrayList<>();

    public static ArrayList<MainRecord> MainRecordPageRecordArrayList = new ArrayList<>();
    public static ArrayList<MainRecordItem> MainRecordPageRecordItemArrayList = new ArrayList<>();

    public static ArrayList<MainRecordPage> MainRecordPageArrayList = new ArrayList<>();

    // 연별 비용 계산 메소드
    public int MainRecordPageYearCostCalculation(String year){
        int Cost = 0;
        // 전체 기록의 비용을 더해서 반환
        for (int i=0; i < MainRecordPageArrayList.size(); i++){
            if(year.equals(MainRecordPageArrayList.get(i).year)){
                Cost = (int) (Cost + MainRecordPageArrayList.get(i).totalCost);
            }
        }
        return Cost;
    }

    // 월별 비용 계산 메소드
    public int MainRecordPageMonthCostCalculation(String month){
        int Cost = 0;
        // 전체 기록의 비용을 더해서 반환
        for (int i=0; i < MainRecordPageArrayList.size(); i++){
            if(month.equals(MainRecordPageArrayList.get(i).month)){
                Cost = (int) (Cost + MainRecordPageArrayList.get(i).totalCost);
            }
        }
        return Cost;
    }

    // sqlite에서 받아온 데이트 넣어주고 새 데이터를 받을 경우 초기화 한 후 다시 받음.
    public void MainRecordPageListClear(){
        MainRecordPageRecordArrayList.clear();
        MainRecordPageRecordItemArrayList.clear();
    }

}
