package com.example.suc333l.library.models;

/**
 * Created by suc333l on 10/18/17.
 */

public class Category {
    private int pk;
    private String title;

    public Category(int pk, String title) {
        this.pk = pk;
        this.title = title;
    }

    public int getPk() {
        return pk;
    }

    public String getTitle() {
        return title;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
