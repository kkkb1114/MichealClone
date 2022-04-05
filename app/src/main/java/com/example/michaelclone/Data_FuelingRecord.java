package com.example.michaelclone;

public class Data_FuelingRecord {

    static String imagePath;
    static int type = 0; //type = 0: 카메라, 1: 앨범
    static String ImageUri;

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
