package com.shrinkcom.medicale.model;

public class ShowPrescriptionDetaislModel {
    String id,medicine_name,medicine_price,medicine_discount,total_price,price_discounted,user_id,pharmacy_id,accepted_by_pharmacy,accepted_by_admin,order_id
            ,product_count,product_id,product_indication,product_status;

    public ShowPrescriptionDetaislModel(String id, String medicine_name, String medicine_price, String medicine_discount, String total_price, String price_discounted, String user_id, String pharmacy_id, String accepted_by_pharmacy, String accepted_by_admin, String order_id, String product_count, String product_id, String product_indication, String product_status) {
        this.id = id;
        this.medicine_name = medicine_name;
        this.medicine_price = medicine_price;
        this.medicine_discount = medicine_discount;
        this.total_price = total_price;
        this.price_discounted = price_discounted;
        this.user_id = user_id;
        this.pharmacy_id = pharmacy_id;
        this.accepted_by_pharmacy = accepted_by_pharmacy;
        this.accepted_by_admin = accepted_by_admin;
        this.order_id = order_id;
        this.product_count = product_count;
        this.product_id = product_id;
        this.product_indication = product_indication;
        this.product_status = product_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMedicine_price() {
        return medicine_price;
    }

    public void setMedicine_price(String medicine_price) {
        this.medicine_price = medicine_price;
    }

    public String getMedicine_discount() {
        return medicine_discount;
    }

    public void setMedicine_discount(String medicine_discount) {
        this.medicine_discount = medicine_discount;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPrice_discounted() {
        return price_discounted;
    }

    public void setPrice_discounted(String price_discounted) {
        this.price_discounted = price_discounted;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(String pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }

    public String getAccepted_by_pharmacy() {
        return accepted_by_pharmacy;
    }

    public void setAccepted_by_pharmacy(String accepted_by_pharmacy) {
        this.accepted_by_pharmacy = accepted_by_pharmacy;
    }

    public String getAccepted_by_admin() {
        return accepted_by_admin;
    }

    public void setAccepted_by_admin(String accepted_by_admin) {
        this.accepted_by_admin = accepted_by_admin;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_indication() {
        return product_indication;
    }

    public void setProduct_indication(String product_indication) {
        this.product_indication = product_indication;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }
}
