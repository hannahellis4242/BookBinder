import book.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookCommand implements Command {
    static class BookTree {
        Map<BookBuilder, BookBuilder> childToParentMap;
        List<BookBuilder> leaves;

        private BookTree(BookBuilder root) {
            childToParentMap = new HashMap<>();
            leaves = new ArrayList<>();
            build(root);
        }
        private void build(BookBuilder parent) {
            if (parent.getChildren().size() == 0) {
                leaves.add(parent);
                return;
            }
            parent.getChildren().forEach(child -> {
                childToParentMap.put(child, parent);
                build(child);
            });
        }
        private BookBuilder findParent(BookBuilder child){
            return childToParentMap.getOrDefault(child, null);
        }
        private List<Integer> buildList(BookBuilder child){
            final var signature = child.getSignature();
            final var parent = findParent(child);
            final var thisList = signature.map(value -> List.of(value.getNumberOfSheets())).orElseGet(ArrayList::new);
            return parent == null ? thisList: Stream.concat(buildList(parent).stream(),thisList.stream()).collect(Collectors.toList());
        }
        List<List<Integer>> getSignatureList(){
            return leaves.stream().map(this::buildList).collect(Collectors.toList());
        }
    }
    public void run(String[] args) {
        try {
            final var pages = Integer.parseInt(args[1]);
            final var book = BookBuilder.build(pages);
            if (book.isEmpty()) {
                System.out.println("no valid book found");
                System.exit(0);
            }
            final var tree = new BookTree(book.get());
            final var signatureList = tree.getSignatureList();
            final var output = signatureList.stream()
                    .map(list->list.stream()
                            .map(Object::toString)
                            .reduce("",(acc, x)->acc+x+" "))
                    .reduce("",(acc, x)->acc+x+"\n");
            System.out.println(output);
        } catch (NumberFormatException e) {
            System.out.println("number of pages argument should be a number");
            System.out.println("run with help to get more information");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("number of pages argument missing");
            System.out.println("run with help to get more information");
        }
    }
}
