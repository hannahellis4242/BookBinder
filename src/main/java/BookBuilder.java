import book.Signature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookBuilder {
    final private Optional<Signature> signature;
    private List<BookBuilder> children;

    static Optional<BookBuilder> build(int numberOfPages){
        if(numberOfPages <0){
            return Optional.empty();
        }
        return Optional.of(new BookBuilder(numberOfPages,Optional.empty()));
    }
    private BookBuilder(int numberOfPages, Optional<Signature> signature) {
        this.signature = signature;
        if (numberOfPages > 0) {
            children = IntStream.range(3, 5)
                    .mapToObj(i -> Signature.withNumberOfSheets(i))
                    .filter(i -> i.isPresent())
                    .map(i -> i.get())
                    .map(s -> new BookBuilder(numberOfPages - s.getTotalNumberOfPages(), Optional.of(s)))
                    .collect(Collectors.toList());
        }
        else{
            children = new ArrayList<>();
        }
    }

    public Optional<Signature> getSignature() {
        return signature;
    }

    public List<BookBuilder> getChildren() {
        return children;
    }
}
