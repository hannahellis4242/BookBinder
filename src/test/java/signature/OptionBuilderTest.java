package signature;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;

class OptionBuilderTest {
    @Test
    void whenAddingIndividualSizes() {
        final var option = new OptionBuilder().add(3).add(4).add(3).build();
        assertThat(Arrays.stream(option.getSignatureSizes()).boxed().toArray(), arrayContaining(3, 4));
        assertThat(option.getNumberOfSignatureSized(3), equalTo(2));
        assertThat(option.getNumberOfSignatureSized(4), equalTo(1));
        assertThat(option.getTotalNumberOfPages(), equalTo(40));
    }

    @Test
    void whenAddingMultiple() {
        final var option = new OptionBuilder().add(3, 2).add(4, 1).build();
        assertThat(Arrays.stream(option.getSignatureSizes()).boxed().toArray(), arrayContaining(3, 4));
        assertThat(option.getNumberOfSignatureSized(3), equalTo(2));
        assertThat(option.getNumberOfSignatureSized(4), equalTo(1));
        assertThat(option.getTotalNumberOfPages(), equalTo(40));
    }

    @Test
    void whenAddingExistingOption() {
        final var existingOption = new OptionBuilder().add(3, 2).build();
        final var option = new OptionBuilder().add(existingOption).add(4).build();
        assertThat(Arrays.stream(option.getSignatureSizes()).boxed().toArray(), arrayContaining(3, 4));
        assertThat(option.getNumberOfSignatureSized(3), equalTo(2));
        assertThat(option.getNumberOfSignatureSized(4), equalTo(1));
        assertThat(option.getTotalNumberOfPages(), equalTo(40));
    }

    @Test
    void whenBuildingFromArray() {
        OptionBuilder builder = new OptionBuilder();
        final var array = new int[]{1, 0, 2, 1, 1};
        builder.fromArray(2, 7, array);
        final var option = builder.build();
        assertThat(Arrays.stream(option.getSignatureSizes()).boxed().toArray(), arrayContaining(2, 4, 5, 6));
        assertThat(option.getNumberOfSignatureSized(1), equalTo(0));
        assertThat(option.getNumberOfSignatureSized(2), equalTo(1));
        assertThat(option.getNumberOfSignatureSized(3), equalTo(0));
        assertThat(option.getNumberOfSignatureSized(4), equalTo(2));
        assertThat(option.getNumberOfSignatureSized(5), equalTo(1));
        assertThat(option.getNumberOfSignatureSized(6), equalTo(1));
        assertThat(option.getNumberOfSignatureSized(7), equalTo(0));
        assertThat(option.getNumberOfSignatureSized(8), equalTo(0));
        assertThat(option.getTotalNumberOfPages(), equalTo(84));
    }
}