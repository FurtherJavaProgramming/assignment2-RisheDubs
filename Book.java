public class Book {
    private String title;
    private String author;
    private int physicalCopies;
    private double price;
    private int soldCopies;

    public Book(String title, String author, int physicalCopies, double price, int soldCopies) {
        this.title = title;
        this.author = author;
        this.physicalCopies = physicalCopies;
        this.price = price;
        this.soldCopies = soldCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPhysicalCopies() {
        return physicalCopies;
    }

    public double getPrice() {
        return price;
    }

    public int getSoldCopies() {
        return soldCopies;
    }
}
