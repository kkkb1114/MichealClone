package com.example.michaelclone.DataBase;

public class MainRecordPage {
    public int id;
    public int carbookRecordRepairMode;
    public String carbookRecordExpendDate;
    public int carbookRecordIsHidden;
    public double carbookRecordTotalDistance;
    public String carbookRecordRegTime;
    public String carbookRecordUpdateTime;
    public int count;
    public double totalCost;
    public String carbookRecordItemCategoryName;
    public String carbookRecordItemExpenseMemo;
    public String year;
    public String month;

    public MainRecordPage(int id, int carbookRecordRepairMode, String carbookRecordExpendDate, int carbookRecordIsHidden, double carbookRecordTotalDistance, String carbookRecordRegTime, String carbookRecordUpdateTime, int count, double totalCost, String carbookRecordItemCategoryName, String carbookRecordItemExpenseMemo, String month, String year) {
        this.id = id;
        this.carbookRecordRepairMode = carbookRecordRepairMode;
        this.carbookRecordExpendDate = carbookRecordExpendDate;
        this.carbookRecordIsHidden = carbookRecordIsHidden;
        this.carbookRecordTotalDistance = carbookRecordTotalDistance;
        this.carbookRecordRegTime = carbookRecordRegTime;
        this.carbookRecordUpdateTime = carbookRecordUpdateTime;
        this.count = count;
        this.totalCost = totalCost;
        this.carbookRecordItemCategoryName = carbookRecordItemCategoryName;
        this.carbookRecordItemExpenseMemo = carbookRecordItemExpenseMemo;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "MainRecordPage{" +
                "id=" + id +
                ", carbookRecordRepairMode=" + carbookRecordRepairMode +
                ", carbookRecordExpendDate='" + carbookRecordExpendDate + '\'' +
                ", carbookRecordIsHidden=" + carbookRecordIsHidden +
                ", carbookRecordTotalDistance=" + carbookRecordTotalDistance +
                ", carbookRecordRegTime='" + carbookRecordRegTime + '\'' +
                ", carbookRecordUpdateTime='" + carbookRecordUpdateTime + '\'' +
                ", count=" + count +
                ", totalCost=" + totalCost +
                ", carbookRecordItemCategoryName='" + carbookRecordItemCategoryName + '\'' +
                ", carbookRecordItemExpenseMemo='" + carbookRecordItemExpenseMemo + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
