package com.shrinkcom.medicale.model;

public class GeneralCategoryModel {

    private  String sub_cat_id,cat_id,sub_cat_name,createdd_at,sub_cat_photo;

    public GeneralCategoryModel(String sub_cat_id, String cat_id, String sub_cat_name, String createdd_at, String sub_cat_photo) {
        this.sub_cat_id = sub_cat_id;
        this.cat_id = cat_id;
        this.sub_cat_name = sub_cat_name;
        this.createdd_at = createdd_at;
        this.sub_cat_photo = sub_cat_photo;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getCreatedd_at() {
        return createdd_at;
    }

    public void setCreatedd_at(String createdd_at) {
        this.createdd_at = createdd_at;
    }

    public String getSub_cat_photo() {
        return sub_cat_photo;
    }

    public void setSub_cat_photo(String sub_cat_photo) {
        this.sub_cat_photo = sub_cat_photo;
    }
}
