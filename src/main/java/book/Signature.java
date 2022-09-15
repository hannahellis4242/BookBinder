package book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Signature {
    private final int numberOfSheets;
    private final int totalNumberOfPages;
    private final List<Integer> pageSequence;

    public Signature(int numberOfSheets) {
        if (numberOfSheets <= 0) {
            throw new IllegalArgumentException("a signature cannot be made from less than one sheet");
        }
        this.numberOfSheets = numberOfSheets;
        totalNumberOfPages = getTotalNumberOfPages(numberOfSheets);
        pageSequence = new ArrayList<>(totalNumberOfPages);
    }

    private void calculatePageSequence() {
        int index = 0;
        int next = totalNumberOfPages - 1;
        while (index < totalNumberOfPages) {
            pageSequence.add(next);
            int step = index % 4;
            if (step % 2 == 0) {
                next = totalNumberOfPages - 1 - next;
            } else if (step % 4 == 1) {
                ++next;
            } else if (step % 4 == 3) {
                --next;
            }
            ++index;
        }
    }

    public int getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public static int getTotalNumberOfPages(int numberOfSheets) {
        return 4 * numberOfSheets;
    }

    public Optional<Integer> pageNumber(int index) {
        if (pageSequence.size() == 0) {
            calculatePageSequence();
        }
        try {
            return Optional.of(pageSequence.get(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public int getNumberOfSheets() {
        return numberOfSheets;
    }
}
