package com.shrinkcom.medicale.model.productmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("sub_category")
    @Expose
    private List<SubCategory> subCategory = null;
    @SerializedName("brands")
    @Expose
    private List<Brand> brands = null;
    @SerializedName("products_list")
    @Expose
    private List<Products> productsList = null;

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

}