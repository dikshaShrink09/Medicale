package com.shrinkcom.medicale.model;

public class ShowMyorder {
    String order_id;
    String file;
    String user_id;
    String pharmacy_id;
    String requested_pharmacy;
    String status;
    String created_at;
    String coupon_name;
    String coupon_amount;
    String coupon_type;
    String order_no;
    String order_status;
    String total_amount;
    String user_first_name;
    String user_last_name;
    String user_street_address;
    String user_city;
    String user_postal_code;
    String user_state;
    String user_email;
    String user_phone;
    String product_status;
    String product_count;

    public ShowMyorder(String order_id, String file, String user_id, String pharmacy_id, String requested_pharmacy, String status, String created_at, String coupon_name, String coupon_amount, String coupon_type, String order_no, String order_status, String total_amount, String user_first_name, String user_last_name, String user_street_address, String user_city, String user_postal_code, String user_state, String user_email, String user_phone, String product_status, String product_count) {
        this.order_id = order_id;
        this.file = file;
        this.user_id = user_id;
        this.pharmacy_id = pharmacy_id;
        this.requested_pharmacy = requested_pharmacy;
        this.status = status;
        this.created_at = created_at;
        this.coupon_name = coupon_name;
        this.coupon_amount = coupon_amount;
        this.coupon_type = coupon_type;
        this.order_no = order_no;
        this.order_status = order_status;
        this.total_amount = total_amount;
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_street_address = user_street_address;
        this.user_city = user_city;
        this.user_postal_code = user_postal_code;
        this.user_state = user_state;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.product_status = product_status;
        this.product_count = product_count;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public String getRequested_pharmacy() {
        return requested_pharmacy;
    }

    public void setRequested_pharmacy(String requested_pharmacy) {
        this.requested_pharmacy = requested_pharmacy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_street_address() {
        return user_street_address;
    }

    public void setUser_street_address(String user_street_address) {
        this.user_street_address = user_street_address;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_postal_code() {
        return user_postal_code;
    }

    public void setUser_postal_code(String user_postal_code) {
        this.user_postal_code = user_postal_code;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }
}