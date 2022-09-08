public class BookBinder {
    static String getCommandString(String[] args) {
        return args.length != 0 ? args[0].toLowerCase() : "help";
    }

    static Command getCommand(String str) {
        switch (str) {
            case "book":
                return new BookCommand();
            case "pages":
                return new PagesCommand();
            default:
                return new HelpCommand();
        }
    }

    public static void main(String[] args) {
        getCommand(getCommandString(args)).run(args);
    }
}