package com.shrinkcom.medicale.model;

public class summarytotalmodel {
    String cart_id;
    String product_id;
    String user_id;
    String product_count;
    String pharmacy_id;
    String product_name;
    String product_description;
    String product_price;
    String product_quantity;
    String price_discounted;
    String product_image;
    String product_indication;
    String product_packing;
    String product_discount;
    String category_id;
    String sub_category_id;
    String created_by;
    String approved_by;
    String brand_id;
    String created_at;
    String status;
    String vendor_type;
    int subtotal;
    String discount;
    int total;

    public summarytotalmodel(String cart_id, String product_id, String user_id, String product_count, String pharmacy_id, String product_name, String product_description, String product_price, String product_quantity, String price_discounted, String product_image, String product_indication, String product_packing, String product_discount, String category_id, String sub_category_id, String created_by, String approved_by, String brand_id, String created_at, String status, int subtotal, String discount, int total) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.product_count = product_count;
        this.pharmacy_id = pharmacy_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.price_discounted = price_discounted;
        this.product_image = product_image;
        this.product_indication = product_indication;
        this.product_packing = product_packing;
        this.product_discount = product_discount;
        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
        this.created_by = created_by;
        this.approved_by = approved_by;
        this.brand_id = brand_id;
        this.created_at = created_at;
        this.status = status;
        this.vendor_type = vendor_type;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    public String getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(String pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getPrice_discounted() {
        return price_discounted;
    }

    public void setPrice_discounted(String price_discounted) {
        this.price_discounted = price_discounted;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_indication() {
        return product_indication;
    }

    public void setProduct_indication(String product_indication) {
        this.product_indication = product_indication;
    }

    public String getProduct_packing() {
        return product_packing;
    }

    public void setProduct_packing(String product_packing) {
        this.product_packing = product_packing;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendor_type() {
        return vendor_type;
    }

    public void setVendor_type(String vendor_type) {
        this.vendor_type = vendor_type;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
