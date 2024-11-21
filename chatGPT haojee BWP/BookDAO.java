import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        // Code to save book to a database can be added here
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(String bookId) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    // Other methods as needed
}
