package com.example.suc333l.library.models;

/**
 * Created by suc333l on 11/1/17.
 */

public class ReservedBook {
    private int pk;
    private Book book;
    private String reserved_date;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getReserved_date() {
        return reserved_date;
    }

    public void setReserved_date(String reserved_date) {
        this.reserved_date = reserved_date;
    }
}
