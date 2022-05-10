package com.example.michaelclone.DataBase;

import java.util.ArrayList;

public class CarbookRecord_Data {

    public static ArrayList<CarbookRecord> carbookRecordArrayList_insertDB = new ArrayList<>();
    public static ArrayList<CarbookRecordItem> carbookRecordItemArrayList_insertDB = new ArrayList<>();

    public static ArrayList<CarbookRecord> carbookRecordPageRecordArrayList_getDB = new ArrayList<>();
    public static ArrayList<CarbookRecordItem> MainRecordPageRecordItemArrayList_getDB = new ArrayList<>();

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

    // 월별 비용 계산 메소드 (월총액은 월만 있으면 다른연도의 중복 월까지 계산할것 같지만 MainRecordPageArrayList에 담긴 월 데이터는 연도까지 적혀있기에 타 연도와 중복될일이 없다.)
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
        carbookRecordPageRecordArrayList_getDB.clear();
        MainRecordPageRecordItemArrayList_getDB.clear();
    }

}
