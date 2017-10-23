package com.example.suc333l.library.models;

/**
 * Created by suc333l on 10/14/17.
 */

public class TokenRequest {

    private String username;
    private  String password;

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
