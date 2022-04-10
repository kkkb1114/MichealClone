package com.example.michaelclone;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class BaseDialog extends Dialog {
    protected Context mContext;
    public BaseDialog(Context context, int layoutId) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutId);
        this.mContext = context;

        setCancelable(true);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if(window != null) {
            // 백그라운드 투명
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams params = window.getAttributes();
            // 화면에 가득 차도록
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        }

        try{
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            this.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static boolean isShowing = false;
    @Override
    public void show() {
        try{
            if(!isShowing){
                isShowing = true;
                super.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isShowing = false;
    }
}
