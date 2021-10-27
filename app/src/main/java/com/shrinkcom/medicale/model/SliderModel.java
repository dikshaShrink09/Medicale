package com.shrinkcom.medicale.model;

public class SliderModel {
    private String imgUrl;

    // empty constructor which is
    // required when using Firebase.
    public SliderModel() {
    }

    // Constructor
    public SliderModel(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // Getter method.
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method.
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
