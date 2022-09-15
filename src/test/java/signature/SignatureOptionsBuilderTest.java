package signature;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SignatureOptionsBuilderTest {
    @Test
    public void test() {
        final int targetNumberOfPages =40;
        final int minimumSheetsPerSignature = 3;
        final int maximumSheetsPerSignature = 5;
        final var options = new SignatureOptionsBuilder(targetNumberOfPages,
                minimumSheetsPerSignature,
                maximumSheetsPerSignature).build();
        //assertThat(options.size(), equalTo(2));
        for(final var option:options){
            System.out.println(option.show());
        }
    }
}