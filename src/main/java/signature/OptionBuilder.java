package signature;

import java.util.HashMap;
import java.util.Map;

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
