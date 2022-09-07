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
        var pages = Signature.withNumberOfSheets(numberOfSheets).map(Signature::getTotalNumberOfPages);
        assertEquals(expectedPages, pages);
    }

    private static Stream<Arguments> testPageParamsProvider() {
        return Stream.of(
                //out of bound tests
                Arguments.of(1, -1, Optional.empty()),
                Arguments.of(1, 4, Optional.empty()),
                //real values for a one sheet signature
                Arguments.of(1, 0, Optional.of(3)),
                Arguments.of(1, 1, Optional.of(0)),
                Arguments.of(1, 2, Optional.of(1)),
                Arguments.of(1, 3, Optional.of(2)),
                //real values for a two sheet signature
                Arguments.of(2, 0, Optional.of(7)),
                Arguments.of(2, 1, Optional.of(0)),
                Arguments.of(2, 2, Optional.of(1)),
                Arguments.of(2, 3, Optional.of(6)),
                Arguments.of(2, 4, Optional.of(5)),
                Arguments.of(2, 5, Optional.of(2)),
                Arguments.of(2, 6, Optional.of(3)),
                Arguments.of(2, 7, Optional.of(4)),
                //real values for a three sheet signature
                Arguments.of(3, 0, Optional.of(11)),
                Arguments.of(3, 1, Optional.of(0)),
                Arguments.of(3, 2, Optional.of(1)),
                Arguments.of(3, 3, Optional.of(10)),
                Arguments.of(3, 4, Optional.of(9)),
                Arguments.of(3, 5, Optional.of(2)),
                Arguments.of(3, 6, Optional.of(3)),
                Arguments.of(3, 7, Optional.of(8)),
                Arguments.of(3, 8, Optional.of(7)),
                Arguments.of(3, 9, Optional.of(4)),
                Arguments.of(3, 10, Optional.of(5)),
                Arguments.of(3, 11, Optional.of(6)),
                //real values for a four sheet signature
                Arguments.of(4, 0, Optional.of(15)),
                Arguments.of(4, 1, Optional.of(0)),
                Arguments.of(4, 2, Optional.of(1)),
                Arguments.of(4, 3, Optional.of(14)),
                Arguments.of(4, 4, Optional.of(13)),
                Arguments.of(4, 5, Optional.of(2)),
                Arguments.of(4, 6, Optional.of(3)),
                Arguments.of(4, 7, Optional.of(12)),
                Arguments.of(4, 8, Optional.of(11)),
                Arguments.of(4, 9, Optional.of(4)),
                Arguments.of(4, 10, Optional.of(5)),
                Arguments.of(4, 11, Optional.of(10)),
                Arguments.of(4, 12, Optional.of(9)),
                Arguments.of(4, 13, Optional.of(6)),
                Arguments.of(4, 14, Optional.of(7)),
                Arguments.of(4, 15, Optional.of(8)),
                //real values for a five sheet signature
                Arguments.of(5, 0, Optional.of(19)),
                Arguments.of(5, 1, Optional.of(0)),
                Arguments.of(5, 2, Optional.of(1)),
                Arguments.of(5, 3, Optional.of(18)),
                Arguments.of(5, 4, Optional.of(17)),
                Arguments.of(5, 5, Optional.of(2)),
                Arguments.of(5, 6, Optional.of(3)),
                Arguments.of(5, 7, Optional.of(16)),
                Arguments.of(5, 8, Optional.of(15)),
                Arguments.of(5, 9, Optional.of(4)),
                Arguments.of(5, 10, Optional.of(5)),
                Arguments.of(5, 11, Optional.of(14)),
                Arguments.of(5, 12, Optional.of(13)),
                Arguments.of(5, 13, Optional.of(6)),
                Arguments.of(5, 14, Optional.of(7)),
                Arguments.of(5, 15, Optional.of(12)),
                Arguments.of(5, 16, Optional.of(11)),
                Arguments.of(5, 17, Optional.of(8)),
                Arguments.of(5, 18, Optional.of(9)),
                Arguments.of(5, 19, Optional.of(10))
        );
    }

    @ParameterizedTest
    @MethodSource("testPageParamsProvider")
    void whenGivenNumberOfSheetsAndAPageIndexShouldGiveCorrectPageNumber(int sheets, int index, Optional<Integer> expectedPageNumber) {
        var pageNumber = Signature.withNumberOfSheets(sheets).flatMap(s -> s.pageNumber(index));
        assertEquals(expectedPageNumber, pageNumber);
    }
}