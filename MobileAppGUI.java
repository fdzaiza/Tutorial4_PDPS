import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MobileAppGUI extends JFrame implements ActionListener {

    private JTextField manufacturerField = new JTextField();
    private JTextField modelField = new JTextField();
    private JTextField typeField = new JTextField();
    private JTextField stationField = new JTextField();
    private JTextField frequencyField = new JTextField();

    private JButton smartButton = new JButton("Show SmartPhone");
    private JButton basicButton = new JButton("Show BasicPhone");
    private JButton sampleButton = new JButton("Load Sample");

    private JTextArea outputArea = new JTextArea();

    public MobileAppGUI() {
        setTitle("Mobile Device App");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Manufacturer:"));
        formPanel.add(manufacturerField);
        formPanel.add(new JLabel("Model:"));
        formPanel.add(modelField);
        formPanel.add(new JLabel("Type (SmartPhone only):"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Station:"));
        formPanel.add(stationField);
        formPanel.add(new JLabel("Frequency:"));
        formPanel.add(frequencyField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(smartButton);
        buttonPanel.add(basicButton);
        buttonPanel.add(sampleButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        outputArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        smartButton.addActionListener(this);
        basicButton.addActionListener(this);
        sampleButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sampleButton) {
            showSample();
        } else {
            showFromInput(e.getSource() == smartButton);
        }
    }

    private void showFromInput(boolean isSmartPhone) {
        String type = typeField.getText();
        double frequency;

        try {
            frequency = Double.parseDouble(frequencyField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Frequency must be a number.");
            return;
        }

        MobileDevice device;
        if (isSmartPhone) {
            device = new SmartPhone(manufacturerField.getText(), modelField.getText(), type);
        } else {
            device = new BasicPhone(manufacturerField.getText(), modelField.getText());
        }

        device.setStation(stationField.getText());
        device.setFrequency(frequency);
        showDevice(device, isSmartPhone, type);
    }

    private void showSample() {
        MobileDevice m1 = new SmartPhone("Samsung", "S20", "Galaxy");
        MobileDevice m2 = new BasicPhone("Sonny Ericson", "Walkman");

        m1.setStation("Hot FM");
        m1.setFrequency(97.60);
        m2.setStation("Fly FM");
        m2.setFrequency(109.8);

        showDevice(m1, true, "Galaxy");
        showDevice(m2, false, "");
    }

    private void showDevice(MobileDevice m, boolean isSmartPhone, String type) {
        String text = "";

        if (isSmartPhone) {
            text = "SmartPhone Detail:\n";
        } else {
            text = "Basic Phone Detail:\n";
        }

        text = text + "Manufacturer: " + m.manufacturer + "\n";
        text = text + "Model: " + m.model + "\n";

        if (isSmartPhone) {
            text = text + "Type: " + type + "\n";
        }

        text = text + "Station:" + m.station + "\n";
        text = text + "Frequency:" + m.frequency + "\n\n";

        outputArea.append(text);
    }

    public static void main(String[] args) {
        MobileAppGUI gui = new MobileAppGUI();
        gui.setVisible(true);
    }
}