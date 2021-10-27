package com.shrinkcom.medicale.model;

public class ProductModel {
    private String product_price;
    private String Product_name;
    private String product_image_path;
    private String product_offer_price;
    public ProductModel(String product_price, String product_name, String product_image_path, String product_offer_price) {
        this.product_price = product_price;
        Product_name = product_name;
        this.product_image_path = product_image_path;
        this.product_offer_price = product_offer_price;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public String getProduct_image_path() {
        return product_image_path;
    }

    public void setProduct_image_path(String product_image_path) {
        this.product_image_path = product_image_path;
    }

    public String getProduct_offer_price() {
        return product_offer_price;
    }

    public void setProduct_offer_price(String product_offer_price) {
        this.product_offer_price = product_offer_price;
    }


}
