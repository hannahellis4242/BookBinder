package signature;

import book.Signature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SignatureOptionsBuilder {
    private record Parameters(int target, int min, int max) {}
    private static class Node{
        List<Node> children;
        Option option;
        Node(Parameters parameters){
            option = null;
            children = IntStream.range(parameters.min,parameters.max+1)
                    .mapToObj(i->new OptionBuilder().add(i).build())
                    .map(opt->new Node(parameters,opt))
                    .collect(Collectors.toList());
        }
        private Node(Parameters parameters,Option option) {
            if (option.getTotalNumberOfPages() >= parameters.target) {
                //this is a leaf in the tree, so we don't need to continue any further
                this.option = option;
                children = new ArrayList<>();
            } else {
                //this is not a leaf, so we need to keep going, no need to store the option
                this.option = null;
                children = IntStream.range(parameters.min, parameters.max + 1)
                        .mapToObj(i -> new OptionBuilder().add(option).add(i).build())
                        .map(opt -> new Node(parameters, opt))
                        .collect(Collectors.toList());
            }
        }
        void visit(Consumer<Option> visitor){
            if(option != null){
                visitor.accept(option);
            }
            children.forEach(child->child.visit(visitor));
        }
    }
    List<Option> options;
    Parameters parameters;

    SignatureOptionsBuilder(int targetNumberOfPages,
                            int minSignatureSize,
                            int maxSignatureSize) {
        parameters = new Parameters(targetNumberOfPages, minSignatureSize, maxSignatureSize);
        options = new ArrayList<>();
    }

    List<Option> build() {
        if (options.size() == 0) {
            final var root = new Node(parameters);
            root.visit((option -> options.add(option)));
        }
        return options;
    }
}
