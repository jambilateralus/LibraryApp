package com.example.suc333l.library.models;

/**
 * Created by suc333l on 11/1/17.
 */

public class RequestedBook {
    private int pk;
    private String title;
    private String author;
    private String publisher;
    private String requested_by;
    private String requested_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRequested_date() {
        return requested_date;
    }

    public void setRequested_date(String requested_date) {
        this.requested_date = requested_date;
    }
}
