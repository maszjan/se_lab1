package pl.edu.agh.library;

public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    public String borrowBookWithConfirmation(String isbn) {
        try {
            Book book = library.borrowBook(isbn);
            return "Wypożyczono: " + book.getTitle() + " | Autor: " + book.getAuthor();
        } catch (Exception e) {
            return "Błąd podczas wypożyczania: " + e.getMessage();
        }
    }

    public void notifyAboutOverdue(String isbn) {
        library.findBookByIsbn(isbn).ifPresent(book -> {
            // Symulacja powiadomienia
            System.out.println("Powiadomienie o terminie zwrotu dla: " + book.getTitle());
        });
    }
}