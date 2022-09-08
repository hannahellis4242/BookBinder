package command;

import javax.swing.*;

public class OutputLog implements Log {
    private final JTextArea area;

    public OutputLog(JTextArea area) {
        this.area = area;
        area.setText("");
    }

    @Override
    public void add(String msg) {
        final var currentText = area.getText();
        if (currentText.length() > 0) {
            var builder = new StringBuilder();
            builder.append(area.getText());
            builder.append('\n');
            builder.append(msg);
            area.setText(builder.toString());
        } else {
            area.setText(msg + "\n");
        }
    }
}
