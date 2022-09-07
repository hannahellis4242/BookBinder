import java.util.Optional;

public class Signature {
    private final int numberOfSheets;

    private static Optional<Integer> greaterThanOrNone(int value, int x) {
        return (x > value ? Optional.of(x) : Optional.empty());
    }

    private static Optional<Integer> lessThanOrNone(int value, int x) {
        return (x < value ? Optional.of(x) : Optional.empty());
    }

    public static Optional<Signature> withNumberOfSheets(int numberOfSheets) {
        return greaterThanOrNone(0, numberOfSheets).map(Signature::new);
    }

    private Signature(int numberOfSheets) {
        this.numberOfSheets = numberOfSheets;
    }

    int totalNumberOfPages() {
        return 4 * numberOfSheets;
    }

    Optional<Integer> pageNumber(int index) {
        return lessThanOrNone(totalNumberOfPages(),index);
    }
}
