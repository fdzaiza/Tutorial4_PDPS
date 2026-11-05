import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PriceAppGUI extends JFrame implements ActionListener {

    private JComboBox<String> itemBox = new JComboBox<String>(new String[] { "SmartPhone", "Laptop", "Car" });
    private JTextField itemNameField = new JTextField();
    private JTextField priceField = new JTextField();
    private JTextField customerNameField = new JTextField();
    private JTextField salaryField = new JTextField();

    private JButton checkButton = new JButton("Check");
    private JButton sampleButton = new JButton("Load Sample");
    private JButton clearButton = new JButton("Clear");

    private JTextArea outputArea = new JTextArea();

    public PriceAppGUI() {
        setTitle("Price App");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Item type:"));
        formPanel.add(itemBox);
        formPanel.add(new JLabel("Item name / Car brand:"));
        formPanel.add(itemNameField);
        formPanel.add(new JLabel("Price (RM):"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Customer name:"));
        formPanel.add(customerNameField);
        formPanel.add(new JLabel("Customer salary (RM):"));
        formPanel.add(salaryField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkButton);
        buttonPanel.add(sampleButton);
        buttonPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        outputArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        checkButton.addActionListener(this);
        sampleButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkButton) {
            checkFromInput();
        } else if (e.getSource() == sampleButton) {
            showSample();
        } else if (e.getSource() == clearButton) {
            outputArea.setText("");
        }
    }

    private void checkFromInput() {
        String itemName = itemNameField.getText().trim();
        String customerName = customerNameField.getText().trim();
        double price;
        double salary;

        if (itemName.equals("") || customerName.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter the item name and customer name.");
            return;
        }

        try {
            price = Double.parseDouble(priceField.getText().trim());
            salary = Double.parseDouble(salaryField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and salary must be numbers.");
            return;
        }

        Price item;
        int type = itemBox.getSelectedIndex();

        if (type == 0) {
            item = new SmartPhone("SmartPhone", itemName, price);
        } else if (type == 1) {
            item = new Laptop("Laptop", itemName, price);
        } else {
            item = new Car(itemName, price);
        }

        Customer customer = new Customer(customerName, salary);
        showResult(item, customer, type == 2);
    }

    private void showSample() {
        SmartPhone sp = new SmartPhone("SmartPhone", "Iphone 14 ProMax", 5500.00);
        Laptop lp = new Laptop("Laptop", "Lenovo Yoga", 6000.00);
        Car c = new Car("BMW", 200000.00);

        Customer c1 = new Customer("Sally", 3000.00);
        Customer c2 = new Customer("Aaron", 8000.00);
        Customer c3 = new Customer("Simon", 10000.00);

        showResult(sp, c1, false);
        showResult(lp, c2, false);
        showResult(c, c3, true);
    }

    private void showResult(Price item, Customer customer, boolean isCar) {
        String text = item.toString() + "\n";
        String who = "The customer's name is " + customer.getName()
                + " with salary RM" + customer.getSalary();

        if (isCar) {
            if (customer.getSalary() > 8000) {
                text = text + who + " can buy this car. Your salary is higher than the price's car.\n\n";
            } else {
                text = text + who + " cannot buy this car. Your salary is lower than the price's car.\n\n";
            }
        } else {
            if (customer.getSalary() > 4000) {
                text = text + who + " can buy this product.\n"
                        + "Your salary is higher than the price's product.\n\n";
            } else {
                text = text + who + " cannot buy this product.\n"
                        + "The product price is more than your salary.\n\n";
            }
        }

        outputArea.append(text);
    }

    public static void main(String[] args) {
        PriceAppGUI gui = new PriceAppGUI();
        gui.setVisible(true);
    }
}
