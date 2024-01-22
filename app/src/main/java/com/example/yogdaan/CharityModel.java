package com.example.yogdaan;

public class CharityModel {
    String Name;
    String address;
    String category;
    String email;
    String mbo;
    String pincode;

    public CharityModel() {
    }

    public CharityModel(String name, String address, String category, String email, String mbo, String pincode) {
        Name = name;
        this.address = address;
        this.category = category;
        this.email = email;
        this.mbo = mbo;
        this.pincode = pincode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMbo() {
        return mbo;
    }

    public void setMbo(String mbo) {
        this.mbo = mbo;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
