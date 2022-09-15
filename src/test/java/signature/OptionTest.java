package signature;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;

class OptionTest {
    static Option option;

    @BeforeAll
    static void setup() {
        Map<Integer, Integer> signatureCounts = new HashMap<>();
        //option is two signatures of size 5
        signatureCounts.put(5, 2);
        //and one signature of size 4
        signatureCounts.put(4, 1);
        option = new Option(signatureCounts);
    }

    @Test
    void whenGettingTheSignatureSizes() {
        final var sizes = Arrays.stream(option.getSignatureSizes()).boxed().toArray();
        assertThat("it should give an array containing 4 and 5", sizes, arrayContaining(4, 5));
    }

    @Test
    void whenGettingTheNumberOfAPParticularSignatureSize() {
        assertThat("it should give 0 when given a signature size that doesn't exist", option.getNumberOfSignatureSized(2), equalTo(0));
        assertThat("it should give 1 when given 4", option.getNumberOfSignatureSized(4), equalTo(1));
        assertThat("it should give 2 when given 5", option.getNumberOfSignatureSized(5), equalTo(2));
    }

    @Test
    void whenGettingTheNumberOfPagesInAnOption() {
        assertThat("it should give 56", option.getTotalNumberOfPages(), equalTo(56));
    }
}