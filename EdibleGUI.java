import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EdibleGUI extends JFrame implements ActionListener {

    private JComboBox<String> typeBox;
    private JTextField nameField;
    private JButton eatButton;
    private JButton sampleButton;
    private JButton clearButton;
    private JTextArea outputArea;

    private Person p1 = new Person("Ali");
    private Person p2 = new Person("Sarah");

    public EdibleGUI() {
        setTitle("Edible App");
        setSize(700, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        String[] types = { "Animal", "Apple", "Orange" };
        typeBox = new JComboBox<String>(types);
        nameField = new JTextField(12);
        eatButton = new JButton("Makan");
        sampleButton = new JButton("Muat Sample");
        clearButton = new JButton("Kosongkan");

        topPanel.add(new JLabel("Jenis:"));
        topPanel.add(typeBox);
        topPanel.add(new JLabel("Nama makanan:"));
        topPanel.add(nameField);
        topPanel.add(eatButton);
        topPanel.add(sampleButton);
        topPanel.add(clearButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JLabel noteLabel = new JLabel("Animal dimakan oleh Ali. Apple dan Orange dimakan oleh Sarah.");

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(noteLabel, BorderLayout.SOUTH);

        eatButton.addActionListener(this);
        sampleButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == eatButton) {
            eatFromInput();
        } else if (e.getSource() == sampleButton) {
            loadSample();
        } else if (e.getSource() == clearButton) {
            outputArea.setText("");
        }
    }

    private void eatFromInput() {
        String name = nameField.getText().trim();

        if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "Sila masukkan nama makanan.");
            return;
        }

        int type = typeBox.getSelectedIndex();
        Edible food;

        if (type == 0) {
            food = new Animal(name);
        } else if (type == 1) {
            food = new Apple(name);
        } else {
            food = new Orange(name);
        }

        showEating(food);
        nameField.setText("");
    }

    private void loadSample() {
        Edible[] objects = { new Animal("Chicken"), new Apple("Red Apple"), new Orange("Navel Orange") };

        for (int i = 0; i < objects.length; i++) {
            showEating(objects[i]);
        }
    }

    private void showEating(Edible food) {
        if (food instanceof Animal) {
            outputArea.append(p1.eating(food) + "\n");
        } else {
            outputArea.append(p2.eating(food) + "\n");
        }
    }

    public static void main(String[] args) {
        EdibleGUI gui = new EdibleGUI();
        gui.setVisible(true);
    }
}
