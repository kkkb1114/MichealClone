package com.example.michaelclone.DataBase;

public class MainRecord {
    public int _id;
    public int carbookRecordRepairMode;
    public String carbookRecordExpendDate;
    public int carbookRecordIsHidden;
    public String carbookRecordTotalDistance;
    public String carbookRecordRegTime;
    public String carbookRecordUpdateTime;

    public MainRecord(int _id, int carbookRecordRepairMode, String carbookRecordExpendDate, int carbookRecordIsHidden,
                      String carbookRecordTotalDistance, String carbookRecordRegTime, String carbookRecordUpdateTime){
        this.carbookRecordRepairMode = carbookRecordRepairMode;
        this.carbookRecordExpendDate = carbookRecordExpendDate;
        this.carbookRecordIsHidden = carbookRecordIsHidden;
        this.carbookRecordTotalDistance = carbookRecordTotalDistance;
        this.carbookRecordRegTime = carbookRecordRegTime;
        this.carbookRecordUpdateTime = carbookRecordUpdateTime;
    }

    @Override
    public String toString() {
        return "MainRecord{" +
                "_id=" + _id +
                ", carbookRecordRepairMode=" + carbookRecordRepairMode +
                ", carbookRecordExpendDate='" + carbookRecordExpendDate + '\'' +
                ", carbookRecordIsHidden=" + carbookRecordIsHidden +
                ", carbookRecordTotalDistance='" + carbookRecordTotalDistance + '\'' +
                ", carbookRecordRegTime='" + carbookRecordRegTime + '\'' +
                ", carbookRecordUpdateTime='" + carbookRecordUpdateTime + '\'' +
                '}';
    }
}
