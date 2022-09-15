package book;

import book.signature.Option;
import book.signature.OptionBuilder;
import book.signature.Signature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Book {
    private final List<Signature> signatures;
    private final int pages;
    private final List<Integer> pageNumbers;

    public static Book fromOption(Option option) {
        var signatures = new ArrayList<Signature>();
        for (var size : option.getSignatureSizes()) {
            final var number = option.getNumberOfSignatureSized(size);
            for (int i = 0; i < number; ++i) {
                signatures.add(new Signature(size));
            }
        }
        return new Book(signatures);
    }

    public Book(List<Signature> signatures) {
        this.signatures = signatures;
        pages = signatures.stream().mapToInt(Signature::getTotalNumberOfPages).sum();
        pageNumbers = new ArrayList<>(pages);
    }

    private void calculatePageSequence(List<Signature> signatures) {
        int runningPageNumbers = 0;
        for (Signature signature : signatures) {
            final int pagesInSignature = signature.getTotalNumberOfPages();
            for (int i = 0; i < pagesInSignature; ++i) {
                pageNumbers.add(runningPageNumbers + signature.pageNumber(i).orElse(-1));
            }
            runningPageNumbers += pagesInSignature;
        }
    }

    public int getTotalNumberOfPages() {
        return pages;
    }

    public int getPageNumber(int index) {
        if (pageNumbers.size() == 0) {
            calculatePageSequence(signatures);
        }
        try {
            return pageNumbers.get(index);
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    public IntStream getPageOrderStream() {
        return IntStream.range(0, getTotalNumberOfPages())
                .map(this::getPageNumber)
                .map(i -> i + 1);
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public Option getOption() {
        var builder = new OptionBuilder();
        signatures.forEach(s -> builder.add(s.getNumberOfSheets()));
        return builder.build();
    }
}
