package pl.edu.agh.library;

public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Tytuł nie może być pusty");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Autor nie może być pusty");
        }
        if (isbn == null || isbn.length() != 13) {
            throw new IllegalArgumentException("Nieprawidłowy format ISBN");
        }

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    // Gettery
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return isAvailable; }

    // Metody zarządzania dostępnością
    public void borrowBook() {
        if (!isAvailable) {
            throw new IllegalStateException("Książka już wypożyczona");
        }
        isAvailable = false;
    }

    public void returnBook() {
        if (isAvailable) {
            throw new IllegalStateException("Książka nie była wypożyczona");
        }
        isAvailable = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
