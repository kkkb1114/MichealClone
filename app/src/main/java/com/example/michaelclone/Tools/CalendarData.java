package com.example.michaelclone.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarData {

    // 요일 구하기
    public String getDateDay(Date mDate){
        String DAY_OF_WEEK = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayNum){
            case 1:
                DAY_OF_WEEK = " (일)";
                break;
            case 2:
                DAY_OF_WEEK = " (월)";
                break;
            case 3:
                DAY_OF_WEEK = " (화)";
                break;
            case 4:
                DAY_OF_WEEK = " (수)";
                break;
            case 5:
                DAY_OF_WEEK = " (목)";
                break;
            case 6:
                DAY_OF_WEEK = " (금)";
                break;
            case 7:
                DAY_OF_WEEK = " (토)";
                break;
        }

        return DAY_OF_WEEK;
    }

    // 아예 date와 Format 양식 문자열을 받아 바로 반환하도록 하였다.
    public String getDateFormat(Date date, String type){
        SimpleDateFormat mForma = new SimpleDateFormat(type);
        return mForma.format(date);
    }

}
