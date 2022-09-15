package book.signature;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

class SignatureOptionsBuilderTest {
    @Test
    public void test() {
        final int targetNumberOfPages = 40;
        final int minimumSheetsPerSignature = 3;
        final int maximumSheetsPerSignature = 5;
        final var options = new SignatureOptionsBuilder(targetNumberOfPages,
                minimumSheetsPerSignature,
                maximumSheetsPerSignature).build();
        assertThat(options.size(), equalTo(16));
        options.forEach(opt -> assertThat(opt.getTotalNumberOfPages(), greaterThanOrEqualTo(targetNumberOfPages)));

        final var sortedOptions = options.stream().sorted(Option::compareTo).collect(Collectors.toList());
        assertThat(sortedOptions.size(), equalTo(options.size()));
        final var unique = sortedOptions.stream().distinct().collect(Collectors.toList());
        assertThat("obtained options are unique", unique.size(), equalTo(options.size()));
    }
}