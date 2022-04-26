package com.example.michaelclone.DataBase;

public class MainRecordItem {
        public int carbookRecordId;
        public String carbookRecordItemCategoryCode;
        public String carbookRecordItemCategoryName;
        public String carbookRecordItemExpenseMemo;
        public String carbookRecordItemExpenseCost;
        public int carbookRecordItemIsHidden;
        public String carbookRecordItemRegTime;
        public String carbookRecordItemUpdateTime;


    public MainRecordItem(int carbookRecordId, String carbookRecordItemCategoryCode, String carbookRecordItemCategoryName, String carbookRecordItemExpenseMemo,
                          String carbookRecordItemExpenseCost, int carbookRecordItemIsHidden, String carbookRecordItemRegTime, String carbookRecordItemUpdateTime){
        this.carbookRecordId = carbookRecordId;
        this.carbookRecordItemCategoryCode = carbookRecordItemCategoryCode;
        this.carbookRecordItemCategoryName = carbookRecordItemCategoryName;
        this.carbookRecordItemExpenseMemo = carbookRecordItemExpenseMemo;
        this.carbookRecordItemExpenseCost = carbookRecordItemExpenseCost;
        this.carbookRecordItemIsHidden = carbookRecordItemIsHidden;
        this.carbookRecordItemRegTime = carbookRecordItemRegTime;
        this.carbookRecordItemUpdateTime = carbookRecordItemUpdateTime;
    }

    @Override
    public String toString() {
        return "MainRecordItem{" +
                "carbookRecordId=" + carbookRecordId +
                ", carbookRecordItemCategoryCode='" + carbookRecordItemCategoryCode + '\'' +
                ", carbookRecordItemCategoryName='" + carbookRecordItemCategoryName + '\'' +
                ", carbookRecordItemExpenseMemo='" + carbookRecordItemExpenseMemo + '\'' +
                ", carbookRecordItemExpenseCost='" + carbookRecordItemExpenseCost + '\'' +
                ", carbookRecordItemIsHidden=" + carbookRecordItemIsHidden +
                ", carbookRecordItemRegTime='" + carbookRecordItemRegTime + '\'' +
                ", carbookRecordItemUpdateTime='" + carbookRecordItemUpdateTime + '\'' +
                '}';
    }
}
