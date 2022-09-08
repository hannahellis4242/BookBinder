package command;

import book.Book;
import book.Signature;
import command.Command;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BookCommand implements Command {
    public static class BookSignatureOptions {
        final private Signature signature;
        private List<BookSignatureOptions> children;

        private void initChildren(int numberOfPages) {
            if (numberOfPages > 0) {
                children = IntStream.range(3, 6)
                        .mapToObj(Signature::new)
                        .map(s -> new BookSignatureOptions(numberOfPages - s.getTotalNumberOfPages(), s))
                        .collect(Collectors.toList());
            } else {
                children = new ArrayList<>();
            }
        }

        public BookSignatureOptions(int numberOfPages) {
            this.signature = null;
            initChildren(numberOfPages);
        }

        private BookSignatureOptions(int numberOfPages, Signature signature) {
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
            final var thisList = signature.map(value -> List.of(value)).orElseGet(ArrayList::new);
            return parent == null ? thisList : Stream.concat(buildList(parent).stream(), thisList.stream()).collect(Collectors.toList());
        }

        List<Book> getBookList() {
            return leaves.stream()
                    .map(this::buildList)
                    .map(Book::new)
                    .collect(Collectors.toList());
        }
    }

    private Optional<Integer> getOptionalArg(int index, String[] args) {
        try {
            return Optional.of(Integer.parseInt(args[index]));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public void run(String[] args) {
        try {
            final var pages = Integer.parseInt(args[1]);
            final var max = getOptionalArg(2, args).orElse(5);
            final var options = new BookSignatureOptions(pages);
            final var tree = new BookTree(options);
            final var books = tree.getBookList();
            var output = books.stream()
                    .sorted(this::byPagesThenByNumberOfSignatures)
                    .limit(max)
                    .map(Book::show)
                    .reduce("", (acc, x) -> acc + x);
            System.out.println(output);
        } catch (NumberFormatException e) {
            System.out.println("number of pages argument should be a number");
            System.out.println("run with help to get more information");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("number of pages argument missing");
            System.out.println("run with help to get more information");
        }
    }

    private int byPagesThenByNumberOfSignatures(Book a, Book b) {
        final var value = a.getTotalNumberOfPages() - b.getTotalNumberOfPages();
        if (value == 0) {
            return a.getSignatures().size() - b.getSignatures().size();
        }
        return value;
    }
}
