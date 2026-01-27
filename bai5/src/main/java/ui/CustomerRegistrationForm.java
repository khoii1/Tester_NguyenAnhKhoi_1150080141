package ui;

import dao.CustomerDAO;
import dao.DatabaseConnection;
import model.Customer;
import util.Validator;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CustomerRegistrationForm extends JFrame {
    private JTextField txtCustomerId;
    private JTextField txtFullName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextArea txtAddress;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JSpinner spinnerDateOfBirth;
    private JRadioButton rbMale, rbFemale, rbOther;
    private JCheckBox chkTerms;
    private JButton btnRegister, btnReset;
    
    private CustomerDAO customerDAO;
    
    public CustomerRegistrationForm() {
        customerDAO = new CustomerDAO();
        
        // Initialize database
        DatabaseConnection.initializeDatabase();
        
        setTitle("ĐĂNG KÝ TÀI KHOẢN KHÁCH HÀNG");
        setSize(650, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with border
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 3),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        outerPanel.setBackground(Color.WHITE);
        
        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("ĐĂNG KÝ TÀI KHOẢN KHÁCH HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null); // Absolute positioning for precise control
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(560, 520));
        
        int yPos = 0;
        int labelWidth = 150;
        int fieldWidth = 380;
        int rowHeight = 60;
        
        // Customer ID
        yPos = addLabelAndField(formPanel, yPos, "Mã Khách Hàng *", 
            txtCustomerId = createTextField("6-10 ký tự, chỉ chữ và số"), 
            labelWidth, fieldWidth);
        
        // Full Name
        yPos = addLabelAndField(formPanel, yPos, "Họ và Tên *", 
            txtFullName = createTextField("Nhập họ tên đầy đủ"), 
            labelWidth, fieldWidth);
        
        // Email
        yPos = addLabelAndField(formPanel, yPos, "Email *", 
            txtEmail = createTextField("ví dụ: nguyenvana@email.com"), 
            labelWidth, fieldWidth);
        
        // Phone Number
        yPos = addLabelAndField(formPanel, yPos, "Số điện thoại *", 
            txtPhone = createTextField("Bắt đầu bằng số 0, 10-12 số"), 
            labelWidth, fieldWidth);
        
        // Address (TextArea)
        JLabel lblAddress = createLabel("Địa chỉ *");
        lblAddress.setBounds(0, yPos, labelWidth, 70);
        formPanel.add(lblAddress);
        
        txtAddress = new JTextArea();
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        txtAddress.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtAddress.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        JScrollPane scrollAddress = new JScrollPane(txtAddress);
        scrollAddress.setBounds(labelWidth, yPos, fieldWidth, 70);
        scrollAddress.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        formPanel.add(scrollAddress);
        
        // Set placeholder for address
        txtAddress.setForeground(Color.GRAY);
        txtAddress.setText("Nhập địa chỉ chi tiết");
        txtAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtAddress.getText().equals("Nhập địa chỉ chi tiết")) {
                    txtAddress.setText("");
                    txtAddress.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtAddress.getText().isEmpty()) {
                    txtAddress.setForeground(Color.GRAY);
                    txtAddress.setText("Nhập địa chỉ chi tiết");
                }
            }
        });
        
        yPos += 80;
        
        // Password
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        yPos = addLabelAndField(formPanel, yPos, "Mật khẩu *", txtPassword, labelWidth, fieldWidth);
        setPlaceholder(txtPassword, "Ít nhất 8 ký tự");
        
        // Confirm Password
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtConfirmPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        yPos = addLabelAndField(formPanel, yPos, "Xác nhận Mật khẩu *", txtConfirmPassword, labelWidth, fieldWidth);
        setPlaceholder(txtConfirmPassword, "Nhập lại mật khẩu");
        
        // Date of Birth
        JLabel lblDOB = createLabel("Ngày sinh");
        lblDOB.setBounds(0, yPos, labelWidth, 35);
        formPanel.add(lblDOB);
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerDateOfBirth = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerDateOfBirth, "MM/dd/yyyy");
        spinnerDateOfBirth.setEditor(dateEditor);
        spinnerDateOfBirth.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spinnerDateOfBirth.setBounds(labelWidth, yPos, fieldWidth, 35);
        formPanel.add(spinnerDateOfBirth);
        
        yPos += 45;
        
        // Gender
        JLabel lblGender = createLabel("Giới tính");
        lblGender.setBounds(0, yPos, labelWidth, 30);
        formPanel.add(lblGender);
        
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        genderPanel.setBackground(Color.WHITE);
        genderPanel.setBounds(labelWidth, yPos, fieldWidth, 30);
        
        rbMale = new JRadioButton("Nam");
        rbFemale = new JRadioButton("Nữ");
        rbOther = new JRadioButton("Khác");
        
        rbMale.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rbFemale.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rbOther.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rbMale.setBackground(Color.WHITE);
        rbFemale.setBackground(Color.WHITE);
        rbOther.setBackground(Color.WHITE);
        
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        genderGroup.add(rbOther);
        
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        genderPanel.add(rbOther);
        formPanel.add(genderPanel);
        
        yPos += 40;
        
        // Terms checkbox
        chkTerms = new JCheckBox("<html>Tôi đồng ý với các điều khoản dịch vụ <font color='red'>*</font></html>");
        chkTerms.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkTerms.setBackground(Color.WHITE);
        chkTerms.setBounds(labelWidth - 20, yPos, 400, 25);
        formPanel.add(chkTerms);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnRegister = new JButton("Đăng ký");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setPreferredSize(new Dimension(130, 40));
        btnRegister.setBackground(new Color(0, 123, 255));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnReset = new JButton("Nhập lại");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReset.setPreferredSize(new Dimension(130, 40));
        btnReset.setBackground(new Color(108, 117, 125));
        btnReset.setForeground(Color.WHITE);
        btnReset.setFocusPainted(false);
        btnReset.setBorderPainted(false);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnReset);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        outerPanel.add(mainPanel);
        add(outerPanel);
        
        // Add action listeners
        btnRegister.addActionListener(e -> handleRegister());
        btnReset.addActionListener(e -> handleReset());
    }
    
    // Helper methods for creating components
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        if (text.contains("*")) {
            label.setText("<html>" + text.replace("*", "<font color='red'>*</font>") + "</html>");
        }
        return label;
    }
    
    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        setPlaceholder(field, placeholder);
        return field;
    }
    
    private void setPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }
    
    private void setPlaceholder(JPasswordField field, String placeholder) {
        field.setEchoChar((char) 0);
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('●');
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0);
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }
    
    private int addLabelAndField(JPanel panel, int yPos, String labelText, JTextField field, int labelWidth, int fieldWidth) {
        JLabel label = createLabel(labelText);
        label.setBounds(0, yPos, labelWidth, 35);
        panel.add(label);
        
        field.setBounds(labelWidth, yPos, fieldWidth, 35);
        panel.add(field);
        
        return yPos + 50;
    }
    
    private int addLabelAndField(JPanel panel, int yPos, String labelText, JPasswordField field, int labelWidth, int fieldWidth) {
        JLabel label = createLabel(labelText);
        label.setBounds(0, yPos, labelWidth, 35);
        panel.add(label);
        
        field.setBounds(labelWidth, yPos, fieldWidth, 35);
        panel.add(field);
        
        return yPos + 50;
    }
    
    private void handleRegister() {
        StringBuilder errors = new StringBuilder();
        
        // Get values
        String customerId = txtCustomerId.getText().trim();
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        // Validate all fields
        String error;
        
        error = Validator.validateCustomerId(customerId);
        if (error != null) errors.append("• ").append(error).append("\n");
        
        error = Validator.validateFullName(fullName);
        if (error != null) errors.append("• ").append(error).append("\n");
        
        error = Validator.validateEmail(email);
        if (error != null) errors.append("• ").append(error).append("\n");
        
        error = Validator.validatePhoneNumber(phone);
        if (error != null) errors.append("• ").append(error).append("\n");
        
        error = Validator.validateAddress(address);
        if (error != null) errors.append("• ").append(error).append("\n");
        
        error = Validator.validatePassword(password);
        if (error != null) errors.append("• ").append(error).append("\n");
        
        error = Validator.validateConfirmPassword(password, confirmPassword);
        if (error != null) errors.append("• ").append(error).append("\n");
        
        // Date of birth (optional)
        LocalDate dob = null;
        Date selectedDate = (Date) spinnerDateOfBirth.getValue();
        if (selectedDate != null) {
            dob = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            error = Validator.validateDateOfBirth(dob);
            if (error != null) errors.append("• ").append(error).append("\n");
        }
        
        // Gender (optional)
        String gender = null;
        if (rbMale.isSelected()) gender = "Nam";
        else if (rbFemale.isSelected()) gender = "Nữ";
        else if (rbOther.isSelected()) gender = "Khác";
        
        // Terms
        error = Validator.validateTermsAcceptance(chkTerms.isSelected());
        if (error != null) errors.append("• ").append(error).append("\n");
        
        // Check for validation errors
        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng sửa các lỗi sau:\n\n" + errors.toString(), 
                "Lỗi Validation", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check database constraints
        try {
            if (customerDAO.isCustomerIdExists(customerId)) {
                JOptionPane.showMessageDialog(this, 
                    "Mã khách hàng đã tồn tại. Vui lòng chọn mã khác.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (customerDAO.isEmailExists(email)) {
                JOptionPane.showMessageDialog(this, 
                    "Email đã được sử dụng. Vui lòng sử dụng email khác.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create customer object and insert
            Customer customer = new Customer(customerId, fullName, email, phone, 
                                            address, password, dob, gender);
            
            if (customerDAO.insertCustomer(customer)) {
                JOptionPane.showMessageDialog(this, 
                    "Đăng ký tài khoản thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                handleReset();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), 
                "Lỗi Database", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleReset() {
        txtCustomerId.setText("");
        txtFullName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        spinnerDateOfBirth.setValue(new Date());
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.clearSelection();
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        rbOther.setSelected(false);
        chkTerms.setSelected(false);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomerRegistrationForm form = new CustomerRegistrationForm();
                form.setVisible(true);
            }
        });
    }
}
