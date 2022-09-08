import java.util.Arrays;

public class BookBinder {
    public static void main(String[] args) {
        Command command = new HelpCommand();
        System.out.println(Arrays.stream(args).reduce("- ", (acc, x) -> acc + x + ","));
        if (args.length != 0) {
            final var commandStr = args[0].toLowerCase();
            command = commandStr.equals("book") ? new BookCommand() : command;
            command = commandStr.equals("pages") ? new PagesCommand() : command;
        }
        command.run(args);
    }
}