package pl.edu.agh.library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new Book("Władca Pierścieni", "J.R.R. Tolkien", "9788324404568"));
        library.addBook(new Book("Hobbit", "J.R.R. Tolkien", "9788324401123"));

        LibraryService libraryService = new LibraryService(library);

        System.out.println(libraryService.borrowBookWithConfirmation("9788324404568"));
        libraryService.notifyAboutOverdue("9788324404568");

        System.out.println("Liczba wszystkich książek: " + library.getTotalBooksCount());
        System.out.println("Liczba dostępnych książek: " + library.getAvailableBooksCount());
    }
}
