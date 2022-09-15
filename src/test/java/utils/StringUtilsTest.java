package utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


class StringUtilsTest {
    @Test
    void whenGivenANumber() {
        assertThat(StringUtils.isNumeric("234"), is(true));
    }

    @Test
    void whenGivenText() {
        assertThat(StringUtils.isNumeric("hello"), is(false));
    }

    @Test
    void whenUsingPluralWithOneItem() {
        assertThat(StringUtils.plural("thing", 1), is(equalTo("thing")));
    }

    @Test
    void whenUsingPluralWithMoreThanOneItem() {
        assertThat(StringUtils.plural("thing", 2), is(equalTo("things")));
    }
}