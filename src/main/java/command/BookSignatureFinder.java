package command;

import book.Book;
import book.signature.Option;
import book.signature.SignatureOptionsBuilder;

public class BookSignatureFinder {
    public String search(int pages,
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
                .map(Book::show)
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
