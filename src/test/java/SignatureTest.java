import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignatureTest {
    private static Stream<Arguments> testPagesParamsProvider() {
        return Stream.of(
                Arguments.of(-1, Optional.empty()),
                Arguments.of(0, Optional.empty()),
                Arguments.of(1, Optional.of(4)),
                Arguments.of(2, Optional.of(8)),
                Arguments.of(3, Optional.of(12)),
                Arguments.of(4, Optional.of(16)),
                Arguments.of(5, Optional.of(20))
        );
    }

    @ParameterizedTest
    @MethodSource("testPagesParamsProvider")
    void whenGivenANumberOfSheetsShouldReturnTheCorrectNumberOfPages(int numberOfSheets, Optional<Integer> expectedPages) {
        var pages = Signature.withNumberOfSheets(numberOfSheets).map(Signature::totalNumberOfPages);
        assertEquals(expectedPages, pages);
    }

    private static Stream<Arguments> testPageParamsProvider() {
        return Stream.of(
                Arguments.of(1, 0, Optional.of(0))
        );
    }

    @ParameterizedTest
    @MethodSource("testPageParamsProvider")
    void whenGivenNumberOfSheetsAndAPageIndexShouldGiveCorrectPageNumber(int sheets, int index, Optional<Integer> expectedPageNumber) {
        var pageNumber = Signature.withNumberOfSheets(sheets).map(s -> s.pageNumber(index));
        assertEquals(expectedPageNumber, pageNumber);
    }
}