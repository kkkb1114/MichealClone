package com.example.michaelclone.DataBase;

import android.content.Context;

public class Main_DataBridge {

    private static Context mContext;

    public static void setMainContext(Context context){
        mContext = context;
    }

    public static Context getMainContext(){
        return mContext;
    }

}
