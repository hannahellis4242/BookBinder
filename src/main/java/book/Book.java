package book;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private final List<Signature> signatures;
    private final int pages;
    private final List<Integer> pageNumbers;

    public Book(List<Signature> signatures) {
        this.signatures = signatures;
        pages = signatures.stream().mapToInt(Signature::getTotalNumberOfPages).sum();
        pageNumbers = new ArrayList<>(pages);
        int runningPageNumbers = 0;
        for (Signature signature : signatures) {
            final int pagesInSignature = signature.getTotalNumberOfPages();
            for (int i = 0; i < pagesInSignature; ++i) {
                pageNumbers.add(runningPageNumbers + signature.pageNumber(i).get());
            }
            runningPageNumbers += pagesInSignature;
        }
    }

    public int getTotalNumberOfPages() {
        return pages;
    }

    public int getPageNumber(int index) {
        try {
            return pageNumbers.get(index);
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }
}
