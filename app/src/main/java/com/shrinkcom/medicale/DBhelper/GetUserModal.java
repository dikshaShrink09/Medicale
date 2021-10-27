package com.shrinkcom.medicale.DBhelper;

public class GetUserModal {

    int _id;
    String fullname;
    String street;
    String city;
    String zipcode;
    String state;
    String email;
    String phone_number;

    public GetUserModal(){

    }

    public GetUserModal(int _id, String fullname, String street, String city, String zipcode, String state, String email, String phone_number) {
        this._id = _id;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
        this.email = email;
        this.phone_number = phone_number;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
