package book.signature;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Option implements Comparable<Option> {
    private final Map<Integer, Integer> signatureCounts;

    public Option(Map<Integer, Integer> signatureCounts) {
        this.signatureCounts = signatureCounts;
    }

    public int[] getSignatureSizes() {
        return signatureCounts.keySet().stream().mapToInt(n -> n).toArray();
    }

    public int getNumberOfSignatureSized(int size) {
        return signatureCounts.getOrDefault(size, 0);
    }

    public int getTotalNumberOfPages() {
        return signatureCounts.entrySet().stream().mapToInt(entry ->
                entry.getValue() * new Signature(entry.getKey()).getTotalNumberOfPages()
        ).sum();
    }

    public int[] toArray(int min, int max) {
        return IntStream.range(min, max + 1).map(this::getNumberOfSignatureSized).toArray();
    }

    public String show() {
        return signatureCounts.entrySet().stream().map(entry ->
                entry.getKey() +
                        " -> " +
                        entry.getValue() +
                        "\n").reduce("", (acc, x) -> acc + x) +
                "pages : " + getTotalNumberOfPages();
    }

    @Override
    public int compareTo(Option other) {
        final var allKeys = Stream.concat(signatureCounts.keySet().stream(), other.signatureCounts.keySet().stream()).toList();
        final int max = allKeys.stream().max(Integer::compareTo).orElse(1);
        final var thisArray = this.toArray(1, max);
        final var otherArray = other.toArray(1, max);
        return Arrays.compare(thisArray, otherArray);
    }
}
