import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotepadClone extends JFrame implements ActionListener {

    JTextArea textArea;
    JFileChooser fileChooser;

    public NotepadClone() {
        // Frame setup
        setTitle("Simple Notepad");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Text area
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        String[] fileItems = {"New", "Open", "Save", "Exit"};
        for (String item : fileItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(this);
            fileMenu.add(menuItem);
        }

        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        String[] editItems = {"Cut", "Copy", "Paste", "Select All"};
        for (String item : editItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(this);
            editMenu.add(menuItem);
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        // File chooser
        fileChooser = new JFileChooser();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                textArea.setText("");
                break;

            case "Open":
                int openVal = fileChooser.showOpenDialog(this);
                if (openVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        textArea.read(reader, null);
                    } catch (IOException ex) {
                        showError("Error opening file.");
                    }
                }
                break;

            case "Save":
                int saveVal = fileChooser.showSaveDialog(this);
                if (saveVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        textArea.write(writer);
                    } catch (IOException ex) {
                        showError("Error saving file.");
                    }
                }
                break;

            case "Exit":
                System.exit(0);
                break;

            case "Cut":
                textArea.cut();
                break;

            case "Copy":
                textArea.copy();
                break;

            case "Paste":
                textArea.paste();
                break;

            case "Select All":
                textArea.selectAll();
                break;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NotepadClone::new);
    }
}
