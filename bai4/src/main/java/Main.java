import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main class with GUI for Payment Calculator
 */
public class Main extends JFrame {
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private JCheckBox childCheckBox;
    private JTextField ageField;
    private JTextField paymentField;
    private JButton calculateButton;
    private PaymentCalculator calculator;

    public Main() {
        calculator = new PaymentCalculator();
        setTitle("Calculate the Payment for the Patient");
        setSize(450, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(new Color(200, 200, 200));
        
        // Title label
        JLabel titleLabel = new JLabel("Calculate the Payment for the Patient");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel for checkboxes and input
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null); // Use absolute positioning for precise control
        centerPanel.setBackground(new Color(200, 200, 200));
        centerPanel.setPreferredSize(new Dimension(400, 160));
        
        // Checkboxes
        maleCheckBox = new JCheckBox("Male");
        femaleCheckBox = new JCheckBox("Female");
        childCheckBox = new JCheckBox("Child (0 - 17 years)");
        
        maleCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        femaleCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        childCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        maleCheckBox.setBackground(new Color(200, 200, 200));
        femaleCheckBox.setBackground(new Color(200, 200, 200));
        childCheckBox.setBackground(new Color(200, 200, 200));
        
        // Make checkboxes mutually exclusive
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleCheckBox);
        genderGroup.add(femaleCheckBox);
        genderGroup.add(childCheckBox);
        
        // Set default selection
        maleCheckBox.setSelected(true);
        
        // Position checkboxes
        maleCheckBox.setBounds(20, 10, 100, 25);
        femaleCheckBox.setBounds(20, 40, 100, 25);
        childCheckBox.setBounds(20, 70, 180, 25);
        
        centerPanel.add(maleCheckBox);
        centerPanel.add(femaleCheckBox);
        centerPanel.add(childCheckBox);
        
        // Age input
        JLabel ageLabel = new JLabel("Age (Years)");
        ageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        ageLabel.setBounds(20, 105, 80, 25);
        centerPanel.add(ageLabel);
        
        ageField = new JTextField();
        ageField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        ageField.setBounds(105, 105, 120, 25);
        centerPanel.add(ageField);
        
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        calculateButton.setBounds(235, 105, 100, 25);
        calculateButton.setBackground(new Color(240, 240, 240));
        calculateButton.setFocusPainted(false);
        centerPanel.add(calculateButton);
        
        // Payment result
        JLabel paymentLabel = new JLabel("Payment is");
        paymentLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        paymentLabel.setBounds(20, 140, 80, 25);
        centerPanel.add(paymentLabel);
        
        paymentField = new JTextField();
        paymentField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        paymentField.setEditable(false);
        paymentField.setBackground(Color.WHITE);
        paymentField.setBounds(105, 140, 120, 25);
        centerPanel.add(paymentField);
        
        JLabel euroLabel = new JLabel("euro €");
        euroLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        euroLabel.setBounds(235, 140, 60, 25);
        centerPanel.add(euroLabel);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add action listener to calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePayment();
            }
        });
        
        add(mainPanel);
    }
    
    private void calculatePayment() {
        try {
            // Get age
            String ageText = ageField.getText().trim();
            if (ageText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter age!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int age = Integer.parseInt(ageText);
            
            // Get gender
            PaymentCalculator.Gender gender;
            if (maleCheckBox.isSelected()) {
                gender = PaymentCalculator.Gender.MALE;
            } else if (femaleCheckBox.isSelected()) {
                gender = PaymentCalculator.Gender.FEMALE;
            } else if (childCheckBox.isSelected()) {
                gender = PaymentCalculator.Gender.CHILD;
            } else {
                JOptionPane.showMessageDialog(this, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Calculate payment
            int payment = calculator.calculatePayment(gender, age);
            
            // Display result
            paymentField.setText(String.valueOf(payment));
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for age!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main frame = new Main();
                frame.setVisible(true);
            }
        });
    }
}
