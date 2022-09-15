import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class StringUtilsTest {
    @Test
    void whenGivenANumber(){
        assertThat(StringUtils.isNumeric("234"),is(true));
    }
    @Test
    void whenGivenText(){
        assertThat(StringUtils.isNumeric("hello"),is(false));
    }
}