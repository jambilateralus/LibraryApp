package com.example.suc333l.library.models;

/**
 * Created by suc333l on 10/23/17.
 */

public class MemberInfo {
    private String first_name;
    private String last_name;
    private String registered_year;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRegistered_year() {
        return registered_year.substring(0, 4);
    }

}
