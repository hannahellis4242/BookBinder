package command;

import book.Book;
import book.Signature;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Pages {
    public String createReport(int[] sheets) {
        var outputBuilder = new StringBuilder();
        final var signatures = Arrays.stream(sheets)
                .mapToObj(Signature::new)
                .collect(Collectors.toList());
        final var book = new Book(signatures);
        final String output = book.getPageOrderStream()
                .mapToObj(String::valueOf)
                .reduce("", (acc, x) -> acc + " " + x);
        outputBuilder.append("The book has ");
        outputBuilder.append(book.getTotalNumberOfPages());
        outputBuilder.append(" pages.\n");
        outputBuilder.append("The book has the following pages per signature : \n");
        for (int i = 0; i < signatures.size(); i++) {
            final var signature = signatures.get(i);
            outputBuilder.append("signature ");
            outputBuilder.append(i + 1);
            outputBuilder.append(" has ");
            outputBuilder.append(signature.getNumberOfSheets());
            outputBuilder.append(" sheets.\n");
        }
        outputBuilder.append("\n The printed page order goes as follows");
        outputBuilder.append(output);
        outputBuilder.append("\n\nThe following command below can be used with pdftk to create a pdf with pages in the right order\n");
        outputBuilder.append("pdftk.exe <filename> cat " + output + " output out.pdf\n");
        outputBuilder.append("replace <filename> with the name of your file\n");
        return outputBuilder.toString();
    }

    public void run(Path input, Path output, int... signatures) throws IOException, InterruptedException {
        final var book = new Book(Arrays.stream(signatures).mapToObj(Signature::new).collect(Collectors.toList()));
        final var pageOrder = book.getPageOrderStream().toArray();
        List<String> command = new ArrayList<>();
        command.add("pdftk.exe");
        command.add(input.toString());
        command.add("cat");
        for (final var page : pageOrder) {
            command.add(String.valueOf(page));
        }
        command.add("output");
        command.add(output.toString());
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        process.wait();
    }
}
