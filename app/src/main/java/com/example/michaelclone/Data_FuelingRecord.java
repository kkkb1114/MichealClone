package com.example.michaelclone;

import java.util.ArrayList;

public class Data_FuelingRecord {

    // 주유
    static String imagePath;
    static int type = 0; //type = 0: 카메라, 1: 앨범
    static String ImageUri;

    // 정비 기타
    static public ArrayList<String> al_itemTitleList = new ArrayList<>();

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
