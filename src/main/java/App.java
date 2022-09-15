import command.BookSignatureFinder;
import command.Pages;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.IntStream;

public class App {
    private JPanel mainPanel;
    private JTextArea output;
    private JTextField pagesInput;
    private JButton bookButton;
    private JTable signatureTable;
    private JButton pagesButton;
    private JTextField signatureInput;
    private JButton addButton;
    private JButton clearButton;
    private JButton copyButton;
    private JButton saveButton;
    private JTextField inputFileField;
    private JButton inputFindButton;
    private JTextField outputFileField;
    private JButton outputFindButton;
    private JButton runButton;
    private JTextField maxOptionsInput;
    private JSlider minSlider;
    private JSlider maxSlider;
    private JTextField minOutput;
    private JTextField maxOutput;
    private final DefaultTableModel model;

    public App() {
        model = new DefaultTableModel();
        model.addColumn("sheets");
        signatureTable.setModel(model);

        output.setEditable(false);
        minOutput.setEditable(false);
        maxOutput.setEditable(false);
        //sliders
        {
            maxSlider.setPaintTicks(true);
            minSlider.setPaintTicks(true);
            maxSlider.setPaintLabels(true);
            minSlider.setPaintLabels(true);
            Hashtable<Integer, JLabel> labels = new Hashtable<>();
            labels.put(1, new JLabel("1"));
            for (int i = 5; i <= 20; i += 5) {
                labels.put(i, new JLabel(String.valueOf(i)));
            }
            minSlider.setLabelTable(labels);
            maxSlider.setLabelTable(labels);
        }

        bookButton.setEnabled(false);

        addButton.setEnabled(false);
        clearButton.setEnabled(false);
        pagesButton.setEnabled(false);

        saveButton.setEnabled(false);
        copyButton.setEnabled(false);

        runButton.setEnabled(false);

        setBookListeners();
        setPagesListeners();
        setOutputListeners();
    }

    private void setOutputListeners() {
        saveButton.addActionListener((e) -> {
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                    fileChooser.setFileFilter(filter);
                    fileChooser.setDialogTitle("Save");
                    int userSelection = fileChooser.showSaveDialog(null);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        try {
                            List<String> lines = List.of(output.getText());
                            Path file = Paths.get(fileToSave.getAbsolutePath());
                            Files.write(file, lines, StandardCharsets.UTF_8);
                        } catch (Exception except) {
                            JOptionPane.showMessageDialog(null,
                                    except.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        );
        copyButton.addActionListener((e) -> {
            StringSelection stringSelection = new StringSelection(output.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        final var listener = new DocumentListener() {
            private void updateButtons() {
                final var enabled = output.getText().length() != 0;
                saveButton.setEnabled(enabled);
                copyButton.setEnabled(enabled);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtons();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtons();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtons();
            }
        };
        output.getDocument().addDocumentListener(listener);
    }

    private void updateRunButton() {
        final var enabled = model.getRowCount() != 0
                && inputFileField.getText().length() != 0
                && outputFileField.getText().length() != 0;
        runButton.setEnabled(enabled);
    }

    private void setPagesListeners() {
        //table inputs
        {
            clearButton.addActionListener((e) -> {
                while (model.getRowCount() != 0) {
                    model.removeRow(0);
                }
            });
            addButton.addActionListener((e) -> {
                        final var text = signatureInput.getText();
                        try {
                            final var value = Integer.parseInt(text);
                            model.addRow(new Object[]{value});
                        } catch (NumberFormatException except) {
                            return;
                        }

                        signatureInput.setText("");
                    }
            );
            pagesButton.addActionListener((e) -> {
                final var signatures = IntStream.range(0, model.getRowCount())
                        .map((i) -> (int) model.getValueAt(i, 0))
                        .toArray();
                final var pages = new Pages();
                output.setText(pages.createReport(signatures));
            });
            //signature input and add button
            {
                final var listener = new DocumentListener() {
                    private void updateButton() {
                        addButton.setEnabled(StringUtils.isNumeric(signatureInput.getText()));
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateButton();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateButton();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateButton();
                    }
                };
                signatureInput.getDocument().addDocumentListener(listener);
            }
            //table and buttons
            {
                model.addTableModelListener((e) -> {
                    final var enabled = model.getRowCount() != 0;
                    clearButton.setEnabled(enabled);
                    pagesButton.setEnabled(enabled);
                });
            }
        }
        //process runner inputs
        {
            inputFindButton.addActionListener((event) -> {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "pdf");
                fileChooser.setFileFilter(filter);
                fileChooser.setDialogTitle("Input");
                final int userSelection = fileChooser.showOpenDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    inputFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });
            outputFindButton.addActionListener((event) -> {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "pdf");
                fileChooser.setFileFilter(filter);
                fileChooser.setDialogTitle("Output");
                final int userSelection = fileChooser.showDialog(null, "Choose");
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    outputFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });
            {
                final var listener = new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateRunButton();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateRunButton();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateRunButton();
                    }
                };
                inputFileField.getDocument().addDocumentListener(listener);
                outputFileField.getDocument().addDocumentListener(listener);
            }
            runButton.addActionListener((e) -> {
                final var pages = new Pages();
                final var input = Path.of(inputFileField.getText());
                final var output = Path.of(outputFileField.getText());
                final var signatures = IntStream.range(0, model.getRowCount())
                        .map((i) -> (int) model.getValueAt(i, 0))
                        .toArray();
                try {
                    pages.run(input, output, signatures);
                    JOptionPane.showMessageDialog(mainPanel,
                            "pdf created successfully",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(mainPanel,
                            exception.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException exception) {
                    JOptionPane.showMessageDialog(mainPanel,
                            "could not complete operation",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    private void setBookListeners() {
        bookButton.addActionListener((e) -> {
            final var pages = Integer.parseInt(pagesInput.getText());
            final var maximum = Integer.parseInt(maxOptionsInput.getText());
            final var text = new BookSignatureFinder().search(pages, maximum, minSlider.getValue(), maxSlider.getValue());
            output.setText(text);
        });
        final var listener = new DocumentListener() {
            private void updateButton() {
                final var enabled = StringUtils.isNumeric(pagesInput.getText())
                        && StringUtils.isNumeric(maxOptionsInput.getText())
                        && minOutput.getText().length() != 0
                        && maxOutput.getText().length() != 0;
                bookButton.setEnabled(enabled);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButton();
            }
        };
        pagesInput.getDocument().addDocumentListener(listener);
        maxOptionsInput.getDocument().addDocumentListener(listener);
        minOutput.getDocument().addDocumentListener(listener);
        maxOutput.getDocument().addDocumentListener(listener);

        minSlider.addChangeListener((e) -> {
            final var value = minSlider.getValue();
            maxSlider.setMinimum(value + 1);
            minOutput.setText(String.valueOf(value));
        });

        maxSlider.addChangeListener((e) -> {
            final var value = maxSlider.getValue();
            minSlider.setMaximum(value - 1);
            maxOutput.setText(String.valueOf(value));
        });
        minSlider.setValue(3);
        maxSlider.setValue(5);
        minOutput.setText(String.valueOf(minSlider.getValue()));
        maxOutput.setText(String.valueOf(maxSlider.getValue()));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BookBinder");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
