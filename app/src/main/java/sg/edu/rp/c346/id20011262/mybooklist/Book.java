package sg.edu.rp.c346.id20011262.mybooklist;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String category;
    private String author;
    private int year;
    private int ratings;

    public Book(int id, String title, String category, String author, int year, int ratings) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.author = author;
        this.year = year;
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public String toString() {
        String starsString = "";
        for(int i = 0; i < ratings; i++){
            starsString += "*";
        }
        return starsString;
    }
}
