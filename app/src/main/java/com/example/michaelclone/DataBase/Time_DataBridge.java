package com.example.michaelclone.DataBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Time_DataBridge {

    public String getGlobalTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return simpleDateFormat.format(date);
    }

    public String getRealTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }
}
