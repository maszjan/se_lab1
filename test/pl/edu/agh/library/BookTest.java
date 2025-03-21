package pl.edu.agh.library;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldCreateBookWithValidData() {

        String title = "Clean Code";
        String author = "Robert C. Martin";
        String isbn = "9780132350884";

        Book book = new Book(title, author, isbn);

        assertAll(
                () -> assertEquals(title, book.getTitle()),
                () -> assertEquals(author, book.getAuthor()),
                () -> assertEquals(isbn, book.getIsbn()),
                () -> assertTrue(book.isAvailable())
        );
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithEmptyTitle() {

        String author = "Robert C. Martin";
        String isbn = "9780132350884";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Book("", author, isbn)
        );
        assertEquals("Tytuł nie może być pusty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithEmptyAuthor() {

        String title = "Clean Code";
        String isbn = "9780132350884";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Book(title, "", isbn)
        );
        assertEquals("Autor nie może być pusty", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1234567890, Nieprawidłowy format ISBN",
            "'', Nieprawidłowy format ISBN"
    })
    void shouldThrowExceptionWhenCreatingWithInvalidISBN(String isbn, String expectedMessage) {

        String title = "Clean Code";
        String author = "Robert C. Martin";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Book(title, author, isbn)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithNullISBN() {

        String title = "Clean Code";
        String author = "Robert C. Martin";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Book(title, author, null)
        );
        assertEquals("Nieprawidłowy format ISBN", exception.getMessage());
    }

    @Test
    void shouldBorrowBook() {

        Book book = new Book("Effective Java", "Joshua Bloch", "9780321356680");
        assertTrue(book.isAvailable(), "Książka powinna być dostępna na początku");

        book.borrowBook();

        assertFalse(book.isAvailable(), "Książka nie powinna być dostępna po wypożyczeniu");
    }

    @Test
    void shouldReturnBook() {

        Book book = new Book("Effective Java", "Joshua Bloch", "9780321356680");
        book.borrowBook();
        assertFalse(book.isAvailable(), "Książka nie powinna być dostępna po wypożyczeniu");

        book.returnBook();

        assertTrue(book.isAvailable(), "Książka powinna być dostępna po zwrocie");
    }

    @Test
    void shouldThrowExceptionWhenBorrowingAlreadyBorrowedBook() {

        Book book = new Book("Effective Java", "Joshua Bloch", "9780321356680");
        book.borrowBook();


        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                book::borrowBook
        );
        assertEquals("Książka już wypożyczona", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenReturningAvailableBook() {

        Book book = new Book("Effective Java", "Joshua Bloch", "9780321356680");

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                book::returnBook
        );
        assertEquals("Książka nie była wypożyczona", exception.getMessage());
    }

    @Test
    void shouldConsiderBooksWithSameISBNEqual() {

        Book book1 = new Book("Effective Java", "Joshua Bloch", "9780321356680");
        Book book2 = new Book("Effective Java 2", "Joshua Bloch", "9780321356680");
        Book book3 = new Book("Clean Code", "Robert C. Martin", "9780132350884");

        assertEquals(book1, book2, "Książki z tym samym ISBN powinny być równe");
        assertNotEquals(book1, book3, "Książki z różnym ISBN nie powinny być równe");
        assertEquals(book1.hashCode(), book2.hashCode(), "Książki z tym samym ISBN powinny mieć ten sam hashCode");
    }
}
