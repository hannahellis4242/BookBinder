import book.Book;
import book.Signature;

import java.util.List;
import java.util.stream.IntStream;


public class Main {
    public static void main(String[] args) {
        final var book = new Book(List.of(
                Signature.withNumberOfSheets(3).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(3).get()));
        final var pages = book.getTotalNumberOfPages();
        final var output = IntStream.range(0,pages)
                .map(i->book.getPageNumber(i))
                .map(i->i+1)
                .mapToObj(i->String.valueOf(i))
                .reduce("",(acc,x)->acc+" "+x);
        System.out.println("pages : " + pages);
        System.out.println("pdftk.exe binding.pdf cat "+output+" output out.pdf");
    }
}