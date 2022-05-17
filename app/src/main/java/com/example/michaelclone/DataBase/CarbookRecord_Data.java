package com.example.michaelclone.DataBase;

import java.util.ArrayList;

public class CarbookRecord_Data {

    public static ArrayList<CarbookRecord> carbookRecordPageRecordArrayList_getDB = new ArrayList<>();
    public static ArrayList<CarbookRecordItem> mainRecordPageRecordItemArrayList_getDB = new ArrayList<>();

    public static ArrayList<MainRecordPage> mainRecordPageArrayList = new ArrayList<>();

    // 연별 비용 계산 메소드
    public int mainRecordPageYearCostCalculation(String year){
        int Cost = 0;
        // 전체 기록의 비용을 더해서 반환
        for (int i = 0; i < mainRecordPageArrayList.size(); i++){
            if(year.equals(mainRecordPageArrayList.get(i).year)){
                Cost = (int) (Cost + mainRecordPageArrayList.get(i).totalCost);
            }
        }
        return Cost;
    }

    // 월별 비용 계산 메소드 (월총액은 월만 있으면 다른연도의 중복 월까지 계산할것 같지만 MainRecordPageArrayList에 담긴 월 데이터는 연도까지 적혀있기에 타 연도와 중복될일이 없다.)
    public int mainRecordPageMonthCostCalculation(String month){
        int Cost = 0;
        // 전체 기록의 비용을 더해서 반환
        for (int i = 0; i < mainRecordPageArrayList.size(); i++){
            if(month.equals(mainRecordPageArrayList.get(i).month)){
                Cost = (int) (Cost + mainRecordPageArrayList.get(i).totalCost);
            }
        }
        return Cost;
    }

    // sqlite에서 받아온 데이트 넣어주고 새 데이터를 받을 경우 초기화 한 후 다시 받음.
    public void MainRecordPageListClear(){
        carbookRecordPageRecordArrayList_getDB.clear();
        mainRecordPageRecordItemArrayList_getDB.clear();
    }
}
