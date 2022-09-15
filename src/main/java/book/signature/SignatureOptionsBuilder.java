package book.signature;

import java.util.ArrayList;
import java.util.List;

public class SignatureOptionsBuilder {
    private record Parameters(int target, int min, int max) {
    }

    private static int[] root() {
        return new int[]{};
    }

    private static boolean accept(Parameters p, int... candidate) {
        final var option = new OptionBuilder().fromArray(p.min, p.max, candidate).build();
        return option.getTotalNumberOfPages() >= p.target;
    }

    private static int[] first(Parameters p, int... candidate) {
        if (candidate.length > p.max - p.min) {
            return null;
        }
        var out = new int[candidate.length + 1];
        System.arraycopy(candidate, 0, out, 0, candidate.length);
        out[candidate.length] = 0;
        return out;
    }

    private static int[] next(Parameters p, int... candidate) {
        var out = candidate.clone();
        out[out.length - 1] += 1;
        final var option = new OptionBuilder().fromArray(p.min, p.max, out).build();
        if (option.getTotalNumberOfPages() > p.target + Signature.getTotalNumberOfPages(p.max)) {
            return null;
        }
        return out;
    }

    private void output(Parameters p, int... candidate) {
        final var option = new OptionBuilder().fromArray(p.min, p.max, candidate).build();
        options.add(option);
    }

    private void backtrack(Parameters p, int... candidate) {
        if (accept(p, candidate)) {
            output(p, candidate);
            return;
        }
        var newCandidate = first(p, candidate);
        while (newCandidate != null) {
            backtrack(p, newCandidate);
            newCandidate = next(p, newCandidate);
        }
    }

    List<Option> options;
    Parameters parameters;

    public SignatureOptionsBuilder(int targetNumberOfPages,
                                   int minSignatureSize,
                                   int maxSignatureSize) {
        parameters = new Parameters(targetNumberOfPages, minSignatureSize, maxSignatureSize);
        options = new ArrayList<>();
    }

    public List<Option> build() {
        if (options.size() == 0) {
            backtrack(parameters, root());
        }
        return options;
    }
}
