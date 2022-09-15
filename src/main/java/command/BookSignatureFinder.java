package command;

import book.Book;
import book.signature.Option;
import book.signature.SignatureOptionsBuilder;
import utils.StringUtils;

import java.util.Arrays;

public class BookSignatureFinder {
    private static String show(Book book) {
        var outputBuilder = new StringBuilder();
        final var option = book.getOption();
        outputBuilder.append("A book with ");
        outputBuilder.append(Arrays.stream(option.getSignatureSizes())
                .mapToObj(size -> {
                    final var number = option.getNumberOfSignatureSized(size);
                    return number
                            + StringUtils.plural(" signature", number)
                            + " of " + size
                            + StringUtils.plural(" sheet", size)+" ";
                })
                .reduce((acc, x) -> acc + " and " + x).orElse("no signatures")
        );
        outputBuilder.append("gives ")
                .append(book.getTotalNumberOfPages())
                .append(" pages.")
                .append("\n");
        return outputBuilder.toString();
    }

    public String createReport(int pages,
                               int maxOptions,
                               int minSignatureSize,
                               int maxSignatureSize) {
        var outputBuilder = new StringBuilder();
        outputBuilder.append("Signature options for a book of ").append(pages).append(" pages.\n\n");
        final var output = new SignatureOptionsBuilder(pages, minSignatureSize, maxSignatureSize)
                .build()
                .stream()
                .sorted(this::byPagesThenByNumberOfSignatures)
                .limit(maxOptions)
                .map(Book::fromOption)
                .map(BookSignatureFinder::show)
                .reduce("", (acc, x) -> acc + x);
        outputBuilder.append(output).append("\n");
        return outputBuilder.toString();
    }

    private int byPagesThenByNumberOfSignatures(Option a, Option b) {
        final var value = a.getTotalNumberOfPages() - b.getTotalNumberOfPages();
        if (value == 0) {
            return a.getSignatureSizes().length - a.getSignatureSizes().length;
        }
        return value;
    }
}
