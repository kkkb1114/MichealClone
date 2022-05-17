package com.example.michaelclone.Tools;

import java.text.DecimalFormat;

public class StringFormat {

    // 천단위 콤마 메소드
    public String makeStringComma(String data) {
        if (data.length() == 0) {
            return "";
        } else {
            long value = Long.parseLong(data);
            DecimalFormat format = new DecimalFormat("###,###");
            return format.format(value);
        }
    }

}
