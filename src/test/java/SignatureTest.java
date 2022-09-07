import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SignatureTest {
    private static Stream<Arguments> testParamsProvider() {
        return Stream.of(
                Arguments.of(-1,null),
                Arguments.of(0,null),
                Arguments.of(1, 4),
                Arguments.of(2,8),
                Arguments.of(3,12),
                Arguments.of(4,16),
                Arguments.of(5,20)
        );
    }
    @ParameterizedTest
    @MethodSource("testParamsProvider")
    void whenGivenANumberOfSheetsShouldReturnTheCorrectNumberOfPages(int numberOfSheets,Integer expectedPages) {
        var pages = Signature.withNumberOfSheets(numberOfSheets).map(Signature::pages);
        if(pages.isPresent()){
            assertEquals(expectedPages,pages.get());
        }
        else{
            assertNull(expectedPages);
        }
    }
}