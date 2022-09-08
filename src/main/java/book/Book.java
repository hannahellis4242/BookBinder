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
    }

    private void calculatePageSequence(List<Signature> signatures) {
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
        if(pageNumbers.size() == 0){
            calculatePageSequence(signatures);
        }
        try {
            return pageNumbers.get(index);
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public String show() {
        var outputBuilder = new StringBuilder();
        outputBuilder.append("A signature of ");
        for (Signature signature : signatures) {
            outputBuilder.append(signature.getNumberOfSheets());
            outputBuilder.append(" ");
        }
        outputBuilder.append("gives ");
        outputBuilder.append(pages);
        outputBuilder.append(" pages.");
        outputBuilder.append("\n");
        return outputBuilder.toString();
    }
}
