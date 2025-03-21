package pl.edu.agh.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Library {
    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Książka o podanym ISBN już istnieje");
        }
        books.put(book.getIsbn(), book);
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    public Book borrowBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Książka nie istnieje");
        }
        book.borrowBook();
        return book;
    }

    public void returnBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Książka nie istnieje");
        }
        book.returnBook();
    }

    public int getTotalBooksCount() {
        return books.size();
    }

    public long getAvailableBooksCount() {
        return books.values().stream().filter(Book::isAvailable).count();
    }
}