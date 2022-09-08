import book.Book;
import book.Signature;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PagesCommand implements Command {
    @Override
    public void run(String[] args) {
        final var signatures = Arrays.stream(args)
                .skip(1)
                .mapToInt(Integer::parseInt)
                .mapToObj(Signature::new)
                .collect(Collectors.toList());
        final var book = new Book(signatures);
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
