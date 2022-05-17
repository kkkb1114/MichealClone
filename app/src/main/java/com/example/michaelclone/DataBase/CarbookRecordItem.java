package com.example.michaelclone.DataBase;

public class CarbookRecordItem {
    public int _id;
    public int carbookRecordId;
    public String carbookRecordItemCategoryCode;
    public String carbookRecordItemCategoryName;
    public String carbookRecordItemExpenseMemo;
    public String carbookRecordItemExpenseCost;
    public int carbookRecordItemIsHidden;
    public String carbookRecordItemRegTime;
    public String carbookRecordItemUpdateTime;


    public CarbookRecordItem(int _id, int carbookRecordId, String carbookRecordItemCategoryCode, String carbookRecordItemCategoryName, String carbookRecordItemExpenseMemo,
                             String carbookRecordItemExpenseCost, int carbookRecordItemIsHidden, String carbookRecordItemRegTime, String carbookRecordItemUpdateTime) {
        this._id = _id;
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
        return "CarbookRecordItem{" +
                "_id=" + _id +
                ", carbookRecordId=" + carbookRecordId +
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
