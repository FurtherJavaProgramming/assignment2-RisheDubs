package model;

public class Book {
    private String title;
    private String author;
    private int physicalCopies;
    private double price;
    private int soldCopies;

    // No-argument constructor (add this)
    public Book() {
    }

    // Parameterized constructor
    public Book(String title, String author, int physicalCopies, double price, int soldCopies) {
        this.title = title;
        this.author = author;
        this.physicalCopies = physicalCopies;
        this.price = price;
        this.soldCopies = soldCopies;
    }

    // Getters and setters for each field
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

    public int getPhysicalCopies() {
        return physicalCopies;
    }

    public void setPhysicalCopies(int physicalCopies) {
        this.physicalCopies = physicalCopies;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSoldCopies() {
        return soldCopies;
    }

    public void setSoldCopies(int soldCopies) {
        this.soldCopies = soldCopies;
    }

    @Override
    public String toString() {
        return title + " by " + author + " - Sold: " + soldCopies;
    }
}
