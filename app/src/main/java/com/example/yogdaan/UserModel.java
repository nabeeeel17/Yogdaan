package com.example.yogdaan;

public class UserModel {
    String username ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;
    String userphoneno;
    String userbloodtype;

    public String getImage() {
        return image;
    }

    public String setImage(String image) {
        this.image = image;
        return image;
    }

    String image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUserphoneno() {
        return userphoneno;
    }

    public void setUserphoneno(String userphoneno) {
        this.userphoneno = userphoneno;
    }

    public String getUserbloodtype() {
        return userbloodtype;
    }

    public void setUserbloodtype(String userbloodtype) {
        this.userbloodtype = userbloodtype;
    }
}
