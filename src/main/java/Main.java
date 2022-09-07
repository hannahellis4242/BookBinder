import book.Book;
import book.Signature;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        final var book = new Book(List.of(Signature.withNumberOfSheets(3).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(3).get()));
        final var pages = book.getTotalNumberOfPages();
        System.out.println("pages : " + pages);
        for (int i = 0; i < pages; ++i) {
            System.out.print(book.getPageNumber(i)+" ");
        }
    }
}