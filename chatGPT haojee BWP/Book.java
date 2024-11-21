public class Book {
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private double price;
    private int stockQuantity;
    private boolean isEBookAvailable;

    // Constructors, Getters, and Setters

    public Book(String bookId, String title, String author, String genre, double price, int stockQuantity, boolean isEBookAvailable) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.isEBookAvailable = isEBookAvailable;
    }

    // Getters and Setters
}
