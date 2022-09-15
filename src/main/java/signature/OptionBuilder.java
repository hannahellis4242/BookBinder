package signature;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class OptionBuilder {
    Map<Integer, Integer> signatureCounts = new HashMap<>();

    public OptionBuilder add(int size) {
        return add(size, 1);
    }

    public OptionBuilder add(int size, int number) {
        final int count = signatureCounts.getOrDefault(size, 0);
        signatureCounts.put(size, count + number);
        return this;
    }

    private static int getOrDefault(int index, int... arr) {
        if (index < 0 || index >= arr.length) {
            return 0;
        }
        return arr[index];
    }

    OptionBuilder fromArray(int min, int max, int... arr) {
        if (min > max) {
            throw new IllegalArgumentException("minimum value given is larger than the maximum value given");
        }
        IntStream.range(0, max - min + 1)
                .mapToObj(i -> new int[]{i, getOrDefault(i, arr)})
                .filter(xs -> xs[1] != 0)
                .forEach(xs -> add(xs[0] + min, xs[1]));
        return this;
    }

    public OptionBuilder add(Option option) {
        for (int size : option.getSignatureSizes()) {
            add(size, option.getNumberOfSignatureSized(size));
        }
        return this;
    }

    Option build() {
        return new Option(signatureCounts);
    }
}
