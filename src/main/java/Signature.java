import java.util.Optional;

public class Signature {
    private final int numberOfSheets;

    private static Optional<Integer> positiveOrNone(int x) {
        return (x > 0 ? Optional.of(x) : Optional.empty());
    }

    public static Optional<Signature> withNumberOfSheets(int numberOfSheets) {
        return positiveOrNone(numberOfSheets).map(Signature::new);
    }

    private Signature(int numberOfSheets) {
        this.numberOfSheets = numberOfSheets;
    }

    int totalNumberOfPages() {
        return 4 * numberOfSheets;
    }

    int pageNumber(int index) {
        return 0;
    }
}
