package signature;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;

class OptionBuilderTest {
    @Test
    void whenAddingIndividualSizes() {
        OptionBuilder builder = new OptionBuilder();
        builder.add(3);
        builder.add(4);
        builder.add(3);
        final var option = builder.build();
        assertThat(Arrays.stream(option.getSignatureSizes()).boxed().toArray(), arrayContaining(3, 4));
        assertThat(option.getNumberOfSignatureSized(3), equalTo(2));
        assertThat(option.getNumberOfSignatureSized(4), equalTo(1));
        assertThat(option.getTotalNumberOfPages(), equalTo(40));
    }

    @Test
    void whenAddingMultiple() {
        OptionBuilder builder = new OptionBuilder();
        builder.add(3, 2);
        builder.add(4, 1);
        final var option = builder.build();
        assertThat(Arrays.stream(option.getSignatureSizes()).boxed().toArray(), arrayContaining(3, 4));
        assertThat(option.getNumberOfSignatureSized(3), equalTo(2));
        assertThat(option.getNumberOfSignatureSized(4), equalTo(1));
        assertThat(option.getTotalNumberOfPages(), equalTo(40));
    }

    @Test
    void whenAddingExistingOption() {
        OptionBuilder builder = new OptionBuilder();
        {
            OptionBuilder inner = new OptionBuilder();
            inner.add(3, 2);
            final var existingOption = inner.build();
            builder.add(existingOption);
        }
        builder.add(4, 1);
        final var option = builder.build();
        assertThat(Arrays.stream(option.getSignatureSizes()).boxed().toArray(), arrayContaining(3, 4));
        assertThat(option.getNumberOfSignatureSized(3), equalTo(2));
        assertThat(option.getNumberOfSignatureSized(4), equalTo(1));
        assertThat(option.getTotalNumberOfPages(), equalTo(40));
    }
}