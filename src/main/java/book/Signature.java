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
        totalNumberOfPages = 4 * numberOfSheets;
        pageSequence = new ArrayList<>(totalNumberOfPages);
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

    public Optional<Integer> pageNumber(int index) {
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
