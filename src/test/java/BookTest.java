import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void testBook(){
        final var book = new Book(List.of(Signature.withNumberOfSheets(2).get(),Signature.withNumberOfSheets(2).get()));
        final var pages = book.getTotalNumberOfPages();
        for(int i =0 ;i<pages;++i){
            System.out.println("page "+i+" should be "+book.getPageNumber(i));
        }
        assertFalse(false);
    }
}