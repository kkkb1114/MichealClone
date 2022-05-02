package com.example.michaelclone.DataBase;

public class MainRecordPage {
    public int MainRecordPage_carbookRecordCount;
    public String MainRecordPage_carbookRecordFirstName;
    public int MainRecordPage_carbookRecordTotalDistance;
    public int MainRecordPage_carbookRecordTotalCost;

    public MainRecordPage(int mainRecordPage_carbookRecordCount, String mainRecordPage_carbookRecordFirstName, int mainRecordPage_carbookRecordTotalDistance,
                          int mainRecordPage_carbookRecordTotalCost) {
        MainRecordPage_carbookRecordCount = mainRecordPage_carbookRecordCount;
        MainRecordPage_carbookRecordFirstName = mainRecordPage_carbookRecordFirstName;
        MainRecordPage_carbookRecordTotalDistance = mainRecordPage_carbookRecordTotalDistance;
        MainRecordPage_carbookRecordTotalCost = mainRecordPage_carbookRecordTotalCost;
    }

    @Override
    public String toString() {
        return "MainRecordPage{" +
                "MainRecordPage_carbookRecordCount=" + MainRecordPage_carbookRecordCount +
                ", MainRecordPage_carbookRecordFirstName='" + MainRecordPage_carbookRecordFirstName + '\'' +
                ", MainRecordPage_carbookRecordTotalDistance=" + MainRecordPage_carbookRecordTotalDistance +
                ", MainRecordPage_carbookRecordTotalCost=" + MainRecordPage_carbookRecordTotalCost +
                '}';
    }
}
