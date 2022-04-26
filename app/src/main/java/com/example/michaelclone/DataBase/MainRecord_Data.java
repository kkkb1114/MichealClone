package com.example.michaelclone.DataBase;

import java.util.ArrayList;

public class MainRecord_Data {

    public static ArrayList<MainRecord> mainRecordArrayList = new ArrayList<>();
    public static ArrayList<MainRecordItem> mainRecordItemArrayList = new ArrayList<>();

    public static ArrayList<MainRecord> MainRecordPageRecordArrayList = new ArrayList<>();
    public static ArrayList<MainRecordItem> MainRecordPageRecordItemArrayList = new ArrayList<>();

    public static int CostCalculation(){
        int Cost = 0;
        for (int i=0; i<mainRecordItemArrayList.size(); i++){
            if (MainRecordPageRecordArrayList.get(0).carbookRecordExpendDate.split(".")[0].equals("2022")){

            }
            Cost = Cost + Integer.parseInt(mainRecordItemArrayList.get(i).carbookRecordItemExpenseCost);
        }
        return Cost;
    }
    연도, 월별로 나눠서 데이터를 삽입하려하는데 DB에서 몽땅 꺼낸 데이터에서 어떻게 연도만 중복이 되지않게 뺄지가 문제다.
}
