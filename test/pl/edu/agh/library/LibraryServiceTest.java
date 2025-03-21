package pl.edu.agh.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private Library libraryMock;

    @Mock
    private Book bookMock;

    @InjectMocks
    private LibraryService libraryService;

    private static final String ISBN = "9780321356680";
    private static final String TITLE = "Effective Java";
    private static final String AUTHOR = "Joshua Bloch";

    @BeforeEach
    void setUp() {
        lenient().when(bookMock.getTitle()).thenReturn(TITLE);
        lenient().when(bookMock.getAuthor()).thenReturn(AUTHOR);
    }

    @Test
    void shouldReturnConfirmationMessageWhenBorrowingSucceeds() {

        when(libraryMock.borrowBook(ISBN)).thenReturn(bookMock);


        String result = libraryService.borrowBookWithConfirmation(ISBN);


        assertEquals("Wypożyczono: " + TITLE + " | Autor: " + AUTHOR, result);
        verify(libraryMock).borrowBook(ISBN);
    }

    @Test
    void shouldReturnErrorMessageWhenBorrowingNonExistentBook() {

        doThrow(new IllegalArgumentException("Książka nie istnieje"))
                .when(libraryMock).borrowBook(ISBN);


        String result = libraryService.borrowBookWithConfirmation(ISBN);


        assertEquals("Błąd podczas wypożyczania: Książka nie istnieje", result);
        verify(libraryMock).borrowBook(ISBN);
    }

    @Test
    void shouldReturnErrorMessageWhenBorrowingAlreadyBorrowedBook() {

        doThrow(new IllegalStateException("Książka już wypożyczona"))
                .when(libraryMock).borrowBook(ISBN);

        String result = libraryService.borrowBookWithConfirmation(ISBN);

        assertEquals("Błąd podczas wypożyczania: Książka już wypożyczona", result);
        verify(libraryMock).borrowBook(ISBN);
    }

    @Test
    void shouldNotifyAboutOverdueWhenBookExists() {

        when(libraryMock.findBookByIsbn(ISBN)).thenReturn(Optional.of(bookMock));
        lenient().when(bookMock.getTitle()).thenReturn("Effective Java");

        libraryService.notifyAboutOverdue(ISBN);

        verify(libraryMock).findBookByIsbn(ISBN);
    }

    @Test
    void shouldNotNotifyWhenBookDoesNotExist() {

        when(libraryMock.findBookByIsbn(ISBN)).thenReturn(Optional.empty());

        libraryService.notifyAboutOverdue(ISBN);

        verify(libraryMock).findBookByIsbn(ISBN);
        verifyNoMoreInteractions(bookMock);
    }
}
