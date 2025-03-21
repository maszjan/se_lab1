package pl.edu.agh.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private Book javaBook;
    private Book cleanCodeBook;

    @BeforeEach
    void setUp() {
        library = new Library();
        javaBook = new Book("Effective Java", "Joshua Bloch", "9780321356680");
        cleanCodeBook = new Book("Clean Code", "Robert C. Martin", "9780132350884");
    }

    @Test
    void shouldAddBookToLibrary() {

        library.addBook(javaBook);

        assertEquals(1, library.getTotalBooksCount());
        assertEquals(1, library.getAvailableBooksCount());
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateBook() {

        library.addBook(javaBook);
        Book duplicateBook = new Book("Java Effective", "Joshua B.", "9780321356680");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> library.addBook(duplicateBook)
        );
        assertEquals("Książka o podanym ISBN już istnieje", exception.getMessage());
    }

    @Test
    void shouldFindBookByISBN() {

        library.addBook(javaBook);
        library.addBook(cleanCodeBook);

        Optional<Book> foundBook = library.findBookByIsbn("9780321356680");

        assertTrue(foundBook.isPresent());
        assertEquals(javaBook, foundBook.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenBookNotFound() {

        library.addBook(javaBook);

        Optional<Book> result = library.findBookByIsbn("nonexistent");

        assertFalse(result.isPresent());
    }

    @Test
    void shouldBorrowBook() {

        library.addBook(javaBook);

        Book borrowed = library.borrowBook(javaBook.getIsbn());

        assertEquals(javaBook, borrowed);
        assertFalse(borrowed.isAvailable());
        assertEquals(0, library.getAvailableBooksCount());
    }

    @Test
    void shouldThrowExceptionWhenBorrowingNonexistentBook() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> library.borrowBook("nonexistent")
        );
        assertEquals("Książka nie istnieje", exception.getMessage());
    }

    @Test
    void shouldReturnBook() {

        library.addBook(javaBook);
        library.borrowBook(javaBook.getIsbn());

        library.returnBook(javaBook.getIsbn());

        assertTrue(javaBook.isAvailable());
        assertEquals(1, library.getAvailableBooksCount());
    }

    @Test
    void shouldThrowExceptionWhenReturningNonexistentBook() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> library.returnBook("nonexistent")
        );
        assertEquals("Książka nie istnieje", exception.getMessage());
    }

    @Test
    void shouldCountTotalBooks() {

        library.addBook(javaBook);
        library.addBook(cleanCodeBook);

        assertEquals(2, library.getTotalBooksCount());
    }

    @Test
    void shouldCountAvailableBooks() {

        library.addBook(javaBook);
        library.addBook(cleanCodeBook);

        library.borrowBook(javaBook.getIsbn());

        assertEquals(1, library.getAvailableBooksCount());
    }
}
