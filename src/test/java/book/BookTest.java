package book;

import book.signature.Signature;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class BookTest {
    @Test
    void testBook() {
        final var book = new Book(List.of(new Signature(2),
                new Signature(2)));
        final var pages = book.getTotalNumberOfPages();
        for (int i = 0; i < pages; ++i) {
            System.out.println("page " + i + " should be " + book.getPageNumber(i));
        }
        assertFalse(false);
    }
}