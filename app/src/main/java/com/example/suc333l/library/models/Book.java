package com.example.suc333l.library.models;

import java.util.List;

/**
 * Created by suc333l on 10/27/17.
 */

public class Book {
    private int pk;
    private String title;
    private List<Author> authors;
    private Publisher publisher;
    private Category category;
    private int book_id;
    private String isbn;
    private int total_number_of_copies;
    private int available_number_of_copies;
    private boolean is_textbook;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getTotal_number_of_copies() {
        return total_number_of_copies;
    }

    public void setTotal_number_of_copies(int total_number_of_copies) {
        this.total_number_of_copies = total_number_of_copies;
    }

    public int getAvailable_number_of_copies() {
        return available_number_of_copies;
    }

    public void setAvailable_number_of_copies(int available_number_of_copies) {
        this.available_number_of_copies = available_number_of_copies;
    }

    public boolean is_textbook() {
        return is_textbook;
    }

    public void setIs_textbook(boolean is_textbook) {
        this.is_textbook = is_textbook;
    }
}
