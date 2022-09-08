package command;

import command.Command;

public class HelpCommand implements Command {
    @Override
    public void run(String[] args) {
        var builder = new StringBuilder();
        builder.append("BookBinder\n-----------------------------------\n");
        builder.append("Tool to aid in book binding\n");
        builder.append("usage : java BookBinder <command> <args>\n");
        builder.append("commands:\n");
        //book command
        {
            builder.append("\tbook : calculates signature options for your book\n");
            builder.append("\t\tusage : java BookBinder book number max\n");
            builder.append("\t\t\tnumber : the number of pages to find a book for\n");
            builder.append("\t\t\tmax (optional) : the maximum number of solutions to find - defaults to 5\n");
        }
        //pages command
        {
            builder.append("\tpages : calculates print order for the given signatures\n");
            builder.append("\t\tusage : java BookBinder pages <number of sheets in first signature> <number of sheets in second signature> ... <number of sheets in last signature>\n");
        }
        System.out.print(builder.toString());
    }
}
