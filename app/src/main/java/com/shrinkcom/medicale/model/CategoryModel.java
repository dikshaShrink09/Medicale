package com.shrinkcom.medicale.model;

public class CategoryModel {

    private String category_name, category_image_path;

    public CategoryModel(String category_name, String category_image_path) {
        this.category_name = category_name;
        this.category_image_path = category_image_path;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image_path() {
        return category_image_path;
    }

    public void setCategory_image_path(String category_image_path) {
        this.category_image_path = category_image_path;
    }
}
