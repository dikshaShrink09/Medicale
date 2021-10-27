package com.shrinkcom.medicale.model;

public class PharmacyListModel {


    String id, first_name, pharmacy_name, logo, country, state, city, locality, mobile, vendor_type, vendor_id, last_name, pincode, delivery_address, status, subscription_status;

    public PharmacyListModel(String id, String first_name, String pharmacy_name, String logo, String country, String state, String city, String locality, String mobile, String vendor_type, String vendor_id, String last_name, String pincode, String delivery_address, String status, String subscription_status) {
        this.id = id;
        this.first_name = first_name;
        this.pharmacy_name = pharmacy_name;
        this.logo = logo;
        this.country = country;
        this.state = state;
        this.city = city;
        this.locality = locality;
        this.mobile = mobile;
        this.vendor_type = vendor_type;
        this.vendor_id = vendor_id;
        this.last_name = last_name;
        this.pincode = pincode;
        this.delivery_address = delivery_address;
        this.status = status;
        this.subscription_status = subscription_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPharmacy_name() {
        return pharmacy_name;
    }

    public void setPharmacy_name(String pharmacy_name) {
        this.pharmacy_name = pharmacy_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVendor_type() {
        return vendor_type;
    }

    public void setVendor_type(String vendor_type) {
        this.vendor_type = vendor_type;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubscription_status() {
        return subscription_status;
    }

    public void setSubscription_status(String subscription_status) {
        this.subscription_status = subscription_status;
    }
}



