package com.example.suc333l.library.models;

import java.util.List;

/**
 * Created by suc333l on 11/1/17.
 */

public class RequestedBookResponse {
    private List<RequestedBook> requestedBooks;

    public List<RequestedBook> getRequestedBooks() {
        return requestedBooks;
    }

    public void setRequestedBooks(List<RequestedBook> requestedBooks) {
        this.requestedBooks = requestedBooks;
    }

    public void addNewBook(RequestedBook requestedBook) {
        this.requestedBooks.add(requestedBook);
    }
}
