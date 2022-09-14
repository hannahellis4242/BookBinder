package signature;

import book.Signature;

import java.util.Map;

public class Option {
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
}
