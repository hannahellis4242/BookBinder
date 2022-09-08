import book.Book;
import book.Signature;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;


public class BookBinder {
    static void example() {
        final var book = new Book(List.of(
                Signature.withNumberOfSheets(3).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(4).get(),
                Signature.withNumberOfSheets(3).get()));
        final var pages = book.getTotalNumberOfPages();
        final var output = IntStream.range(0, pages)
                .map(i -> book.getPageNumber(i))
                .map(i -> i + 1)
                .mapToObj(i -> String.valueOf(i))
                .reduce("", (acc, x) -> acc + " " + x);
        System.out.println("pages : " + pages);
        System.out.println("pdftk.exe binding.pdf cat " + output + " output out.pdf");
    }

    static void printHelp() {
        var builder = new StringBuilder();
        builder.append("BookBinder\n-----------------------------------\n");
        builder.append("Tool to aid in book binding\n");
        builder.append("usage : java BookBinder <command> <args>\n");
        builder.append("commands:\n");
        //book command
        {
            builder.append("\tbook : calculates signature options for your book\n");
            builder.append("\t\tusage : java BookBinder book <number of pages>\n");
        }
        //pages command
        {
            builder.append("\tpages : calculates print order for the given signatures\n");
            builder.append("\t\tusage : java BookBinder pages <number of sheets in first signature> <number of sheets in second signature> ... <number of sheets in last signature>\n");
        }
        System.out.print(builder.toString());
    }

    public static void main(String[] args) {
        System.out.println(Arrays.stream(args).reduce("- ",(acc,x)->acc+x+","));
        if (args.length == 0) {
            printHelp();
            System.exit(1);
        }
        final var commandStr = args[0];
        if (commandStr.toLowerCase(Locale.ROOT).equals( "book")) {
            final Command command = new BookCommand();
            command.run(args);
        }
        else if (commandStr == "pages") {

        }
        else {
            printHelp();
        }
    }
}