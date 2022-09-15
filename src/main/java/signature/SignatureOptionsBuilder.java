package signature;

import book.Signature;

import java.util.ArrayList;
import java.util.List;

public class SignatureOptionsBuilder {
    private static class Parameters {
        public int maxPages;
        public int target;
        public int min;
        public int max;

        Parameters(int target, int min, int max) {
            this.target = target;
            this.min = min;
            this.max = max;
            maxPages = target + new Signature(max).getTotalNumberOfPages();
        }
    }
    private record Candidate(Option option, int lastAdded) {
    }
    private static Candidate root(Parameters params) {
        final var opt = new OptionBuilder().build();
        return new Candidate(opt, -1);
    }
    private static boolean reject(Parameters params, Candidate candidate) {
        //don't reject if it's over unless it's over the target plus the total number of pages in the largest signature size
        return candidate.option().getTotalNumberOfPages() > params.maxPages;
    }
    private static boolean accept(Parameters params, Candidate candidate) {
        //accept a solution the number of pages are greater than or equal to the target
        final int pages = candidate.option().getTotalNumberOfPages();
        return pages >= params.target;
    }
    private static Candidate first(Parameters p, Candidate candidate) {
        final int toAdd = candidate.lastAdded() > 0 ? candidate.lastAdded() + 1:p.min;
        if(toAdd > p.max){
            return null;
        }
        final var option = new OptionBuilder().add(candidate.option()).add(toAdd).build();
        return new Candidate(option, toAdd);
    }
    private static Candidate next(Parameters p, Candidate candidate) {
        final int toAdd = candidate.lastAdded();
        final var option = new OptionBuilder().add(candidate.option()).add(toAdd).build();
        if(option.getTotalNumberOfPages()>p.maxPages){
            return null;
        }
        return new Candidate(option, toAdd);
    }
    private void output(Candidate candidate) {
        options.add(candidate.option());
    }
    void backtrack(Parameters p, Candidate c) {
        if (reject(p, c)) {
            return;
        }
        if (accept(p, c)) {
            output(c);
        }
        Candidate newCandidate = first(p, c);
        while (newCandidate != null) {
            backtrack(p, newCandidate);
            newCandidate = next(p, newCandidate);
        }
    }
    List<Option> options;
    Parameters parameters;

    SignatureOptionsBuilder(int targetNumberOfPages,
                            int minSignatureSize,
                            int maxSignatureSize) {
        parameters = new Parameters(targetNumberOfPages, minSignatureSize, maxSignatureSize);
        options = new ArrayList<>();
    }

    List<Option> build() {
        if (options.size() == 0) {
            backtrack(parameters, root(parameters));
        }
        return options;
    }
}
