package command;

import book.Book;
import book.Signature;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BookSignatureFinder {
    public static class BookSignatureOptions {
        final private Signature signature;
        private final int minSignatureSize;
        private final int maxSignatureSize;
        private List<BookSignatureOptions> children;

        private void initChildren(int numberOfPages) {
            if (numberOfPages > 0) {
                children = IntStream.range(minSignatureSize, maxSignatureSize + 1)
                        .mapToObj(Signature::new)
                        .map(s -> new BookSignatureOptions(numberOfPages - s.getTotalNumberOfPages(),
                                minSignatureSize,
                                maxSignatureSize,
                                s))
                        .collect(Collectors.toList());
            } else {
                children = new ArrayList<>();
            }
        }

        public BookSignatureOptions(int numberOfPages, int minSignatureSize, int maxSignatureSize) {
            this.minSignatureSize = minSignatureSize;
            this.maxSignatureSize = maxSignatureSize;
            this.signature = null;
            initChildren(numberOfPages);
        }

        private BookSignatureOptions(int numberOfPages,
                                     int minSignatureSize,
                                     int maxSignatureSize,
                                     Signature signature) {
            this.minSignatureSize = minSignatureSize;
            this.maxSignatureSize = maxSignatureSize;
            this.signature = signature;
            initChildren(numberOfPages);
        }

        public Optional<Signature> getSignature() {
            return signature != null ? Optional.of(signature) : Optional.empty();
        }

        public List<BookSignatureOptions> getChildren() {
            return children;
        }
    }

    static class BookTree {
        Map<BookSignatureOptions, BookSignatureOptions> childToParentMap;
        List<BookSignatureOptions> leaves;

        private BookTree(BookSignatureOptions root) {
            childToParentMap = new HashMap<>();
            leaves = new ArrayList<>();
            build(root);
        }

        private void build(BookSignatureOptions parent) {
            if (parent.getChildren().size() == 0) {
                leaves.add(parent);
                return;
            }
            parent.getChildren().forEach(child -> {
                childToParentMap.put(child, parent);
                build(child);
            });
        }

        private BookSignatureOptions findParent(BookSignatureOptions child) {
            return childToParentMap.getOrDefault(child, null);
        }

        private List<Signature> buildList(BookSignatureOptions child) {
            final var signature = child.getSignature();
            final var parent = findParent(child);
            final var thisList = signature.map(List::of).orElseGet(ArrayList::new);
            return parent == null ? thisList : Stream.concat(buildList(parent).stream(), thisList.stream()).collect(Collectors.toList());
        }

        List<Book> getBookList() {
            return leaves.stream()
                    .map(this::buildList)
                    .map(Book::new)
                    .collect(Collectors.toList());
        }
    }
    public String search(int pages,
                       int maxOptions,
                       int minSignatureSize,
                       int maxSignatureSize) {
        var outputBuilder = new StringBuilder();
        outputBuilder.append("Signature options for a book of ").append(pages).append(" pages.\n\n");
        try {
            final var options = new BookSignatureOptions(pages, minSignatureSize, maxSignatureSize);
            final var tree = new BookTree(options);
            final var books = tree.getBookList().stream()
                    .sorted(this::byPagesThenByNumberOfSignatures)
                    .limit(maxOptions)
                    .collect(Collectors.toList());
            var output = books.stream()
                    .map(Book::show)
                    .reduce("", (acc, x) -> acc + x);
            outputBuilder.append(output).append("\n");
        } catch (NumberFormatException e) {
            outputBuilder.append("number of pages argument should be a number\n").append("search with help to get more information\n");
        } catch (IndexOutOfBoundsException e) {
            outputBuilder.append("number of pages argument missing\n").append("search with help to get more information\n");
        }
        return outputBuilder.toString();
    }

    private int byPagesThenByNumberOfSignatures(Book a, Book b) {
        final var value = a.getTotalNumberOfPages() - b.getTotalNumberOfPages();
        if (value == 0) {
            return a.getSignatures().size() - b.getSignatures().size();
        }
        return value;
    }
}
