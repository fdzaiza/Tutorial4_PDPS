import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class DataSetAppGUI extends JFrame implements ActionListener {

    private DataSet bankData = new DataSet();
    private DataSet coinData = new DataSet();
    private DataSet studentData = new DataSet();

    private String bankList = "";
    private String coinList = "";
    private String studentList = "";

    private JComboBox<String> typeBox;
    private JTextField nameField;
    private JTextField valueField;
    private JButton addButton;
    private JButton sampleButton;
    private JTextArea listArea;
    private JLabel resultLabel;

    private DecimalFormat df = new DecimalFormat("0.00");

    public DataSetAppGUI() {
        setTitle("DataSet App");
        setSize(650, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        String[] types = { "BankAccount", "Coin", "Student" };
        typeBox = new JComboBox<String>(types);
        nameField = new JTextField(10);
        valueField = new JTextField(8);
        addButton = new JButton("Tambah");
        sampleButton = new JButton("Muat Sample");

        topPanel.add(typeBox);
        topPanel.add(new JLabel("Nama:"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Nilai:"));
        topPanel.add(valueField);
        topPanel.add(addButton);
        topPanel.add(sampleButton);

        listArea = new JTextArea();
        listArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listArea);

        resultLabel = new JLabel("Belum ada data.");

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        sampleButton.addActionListener(this);
        typeBox.addActionListener(this);

        showResult();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addData();
        } else if (e.getSource() == sampleButton) {
            loadSample();
        } else if (e.getSource() == typeBox) {
            showResult();
        }
    }

    private void addData() {
        int type = typeBox.getSelectedIndex();
        String name = nameField.getText().trim();
        double value;

        try {
            value = Double.parseDouble(valueField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Sila masukkan nilai yang sah.");
            return;
        }

        if (type == 0) {
            BankAccount acc = new BankAccount(value);
            bankData.add(acc);
            bankList = bankList + acc.getName() + ": " + acc.getMeasure() + "\n";
        } else {
            if (name.equals("")) {
                JOptionPane.showMessageDialog(this, "Sila masukkan nama.");
                return;
            }
            if (type == 1) {
                Coin c = new Coin(value, name);
                coinData.add(c);
                coinList = coinList + c.getName() + ": " + c.getMeasure() + "\n";
            } else {
                Student s = new Student(name, value);
                studentData.add(s);
                studentList = studentList + s.getName() + ": " + s.getMeasure() + "\n";
            }
        }

        nameField.setText("");
        valueField.setText("");
        showResult();
    }

    private void loadSample() {
        bankData = new DataSet();
        coinData = new DataSet();
        studentData = new DataSet();
        bankList = "";
        coinList = "";
        studentList = "";

        BankAccount b1 = new BankAccount(0);
        BankAccount b2 = new BankAccount(10000);
        BankAccount b3 = new BankAccount(2000);
        bankData.add(b1);
        bankData.add(b2);
        bankData.add(b3);
        bankList = b1.getName() + ": " + b1.getMeasure() + "\n"
                 + b2.getName() + ": " + b2.getMeasure() + "\n"
                 + b3.getName() + ": " + b3.getMeasure() + "\n";

        coinData.add(new Coin(0.25, "Quarter"));
        coinData.add(new Coin(0.1, "Dime"));
        coinData.add(new Coin(0.05, "Nickel"));
        coinList = "Quarter: 0.25\nDime: 0.1\nNickel: 0.05\n";

        studentData.add(new Student("Ali", 157.50));
        studentData.add(new Student("Alex", 165.00));
        studentData.add(new Student("Maria", 147.00));
        studentList = "Ali: 157.5\nAlex: 165.0\nMaria: 147.0\n";

        showResult();
    }

    private void showResult() {
        int type = typeBox.getSelectedIndex();
        DataSet data;

        if (type == 0) {
            data = bankData;
            listArea.setText(bankList);
        } else if (type == 1) {
            data = coinData;
            listArea.setText(coinList);
        } else {
            data = studentData;
            listArea.setText(studentList);
        }

        Measurable max = data.getMax();
        if (max == null) {
            resultLabel.setText("Belum ada data.");
        } else {
            resultLabel.setText("Purata: " + df.format(data.getAverage())
                    + "   |   Tertinggi: " + df.format(max.getMeasure())
                    + " (" + max.getName() + ")");
        }
    }

    public static void main(String[] args) {
        DataSetAppGUI gui = new DataSetAppGUI();
        gui.setVisible(true);
    }
}
