import book.Book;
import book.Signature;

import java.util.List;
import java.util.stream.IntStream;

public class PagesCommand implements Command {
    @Override
    public void run(String[] args) {
        final var book = new Book(List.of(
                new Signature(3),
                new Signature(4),
                new Signature(4),
                new Signature(4),
                new Signature(4),
                new Signature(4),
                new Signature(3)));
        final var pages = book.getTotalNumberOfPages();
        final var output = IntStream.range(0, pages)
                .map(book::getPageNumber)
                .map(i -> i + 1)
                .mapToObj(String::valueOf)
                .reduce("", (acc, x) -> acc + " " + x);
        System.out.println("pages : " + pages);
        System.out.println("pdftk.exe binding.pdf cat " + output + " output out.pdf");
    }
}
