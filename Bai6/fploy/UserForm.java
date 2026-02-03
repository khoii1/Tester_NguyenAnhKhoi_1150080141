import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * UserForm - Giao diện quản lý User theo mẫu NGHIENPHIM
 */
public class UserForm extends JFrame {
    private JTextField txtUsername;
    private JTextField txtFullname;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JTable tableUsers;
    private DefaultTableModel tableModel;
    private Integer selectedUserId = null;
    
    // Dark theme colors - improved contrast
    private final Color DARK_BG = new Color(31, 41, 55);
    private final Color DARKER_BG = new Color(24, 33, 47);
    private final Color SIDEBAR_BG = new Color(28, 37, 50);
    private final Color INPUT_BG = new Color(42, 54, 71);
    private final Color TEXT_COLOR = new Color(226, 232, 240);  // Lighter for better visibility
    private final Color HEADER_COLOR = new Color(241, 245, 249);  // Even lighter for headers
    private final Color LABEL_COLOR = new Color(203, 213, 225);  // Labels

    public UserForm() {
        setTitle("NGHIENPHIM - User Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        DatabaseManager.initDatabase();
        
        initComponents();
        loadUsers();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(DARK_BG);

        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(DARK_BG);

        // Sidebar
        JPanel sidebar = createSidebar();
        mainContainer.add(sidebar, BorderLayout.WEST);

        // Content area
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(DARK_BG);

        // Header with logo and search
        JPanel header = createHeader();
        contentArea.add(header, BorderLayout.NORTH);

        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(DARK_BG);
        tabbedPane.setForeground(TEXT_COLOR);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        // USER EDITION tab
        JPanel editionPanel = createEditionPanel();
        tabbedPane.addTab("USER EDITION", editionPanel);
        
        // USER LIST tab
        JPanel listPanel = createListPanel();
        tabbedPane.addTab("USER LIST", listPanel);
        
        contentArea.add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel footer = createFooter();
        contentArea.add(footer, BorderLayout.SOUTH);

        mainContainer.add(contentArea, BorderLayout.CENTER);
        add(mainContainer);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        String[] menuItems = {"Home", "Video Management", "User Management", "Report Management", "Logout"};

        JLabel management = new JLabel("Management");
        management.setForeground(new Color(148, 163, 184));
        management.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        management.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 0));
        sidebar.add(management);

        for (int i = 0; i < menuItems.length; i++) {
            JButton menuBtn = createMenuButton(menuItems[i], i == 2);
            sidebar.add(menuBtn);
        }

        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JButton createMenuButton(String text, boolean selected) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(selected ? HEADER_COLOR : TEXT_COLOR);
        btn.setBackground(selected ? new Color(42, 54, 71) : SIDEBAR_BG);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(220, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        return btn;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARKER_BG);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel logo = new JLabel("NGHIENPHIM");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(HEADER_COLOR);
        header.add(logo, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setBackground(DARKER_BG);
        
        JTextField searchField = new JTextField("Tim kiem phim...");
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBackground(INPUT_BG);
        searchField.setForeground(new Color(156, 163, 175));
        searchField.setCaretColor(TEXT_COLOR);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        rightPanel.add(searchField);
        
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createEditionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Form Panel with 2 columns
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(DARK_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Username and Password
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(createLabel("Username"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtUsername = createTextField();
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(createLabel("Password"), gbc);
        
        gbc.gridx = 3; gbc.weightx = 1.0;
        txtPassword = createPasswordField();
        formPanel.add(txtPassword, gbc);

        // Row 2: Fullname and Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(createLabel("Fullname"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtFullname = createTextField();
        formPanel.add(txtFullname, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(createLabel("Email"), gbc);
        
        gbc.gridx = 3; gbc.weightx = 1.0;
        txtEmail = createTextField();
        formPanel.add(txtEmail, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 20));
        buttonPanel.setBackground(DARK_BG);

        JButton btnCreate = createActionButton("Create", new Color(59, 130, 246));
        JButton btnUpdate = createActionButton("Update", new Color(16, 185, 129));
        JButton btnDelete = createActionButton("Delete", new Color(239, 68, 68));
        JButton btnReset = createActionButton("Reset", new Color(245, 158, 11));

        btnCreate.addActionListener(e -> createUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnReset.addActionListener(e -> resetForm());

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnReset);

        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columnNames = {"ID", "Username", "Fullname", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableUsers = new JTable(tableModel);
        tableUsers.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableUsers.setRowHeight(35);
        tableUsers.setBackground(INPUT_BG);
        tableUsers.setForeground(Color.WHITE);  // White text in table for clarity
        tableUsers.setGridColor(new Color(55, 65, 81));
        tableUsers.setSelectionBackground(new Color(59, 130, 246));
        tableUsers.setSelectionForeground(Color.WHITE);
        
        tableUsers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableUsers.getTableHeader().setBackground(DARKER_BG);
        tableUsers.getTableHeader().setForeground(Color.WHITE);  // White header text
        
        tableUsers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableUsers.getSelectedRow();
                if (selectedRow >= 0) {
                    fillFormFromTable(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableUsers);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(55, 65, 81), 1));
        scrollPane.getViewport().setBackground(INPUT_BG);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(DARK_BG);
        footer.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel copyright = new JLabel("Copyright © 2022 NGHIENPHIM. All rights reserved.");
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        copyright.setForeground(new Color(148, 163, 184));
        footer.add(copyright);

        return footer;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(LABEL_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(INPUT_BG);
        field.setForeground(Color.WHITE);  // White text for better visibility
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(INPUT_BG);
        field.setForeground(Color.WHITE);  // White text for better visibility
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 38));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void createUser() {
        String username = txtUsername.getText().trim();
        String fullname = txtFullname.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username is required!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sử dụng url field để lưu fullname
        User user = new User(username, email, password, fullname);
        
        if (user.create()) {
            JOptionPane.showMessageDialog(this, 
                "User created successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            resetForm();
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to create user. Username may already exist or email format is invalid.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUser() {
        if (selectedUserId == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a user from the table to update.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = txtUsername.getText().trim();
        String fullname = txtFullname.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username is required!", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = User.findById(selectedUserId);
        if (user != null) {
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setUrl(fullname);
            
            if (user.update()) {
                JOptionPane.showMessageDialog(this, 
                    "User updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                resetForm();
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update user. Username may already exist or email format is invalid.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteUser() {
        if (selectedUserId == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a user from the table to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this user?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            User user = User.findById(selectedUserId);
            if (user != null && user.delete()) {
                JOptionPane.showMessageDialog(this, 
                    "User deleted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                resetForm();
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to delete user.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resetForm() {
        txtUsername.setText("");
        txtFullname.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        selectedUserId = null;
        if (tableUsers != null) {
            tableUsers.clearSelection();
        }
    }

    private void fillFormFromTable(int row) {
        selectedUserId = (Integer) tableModel.getValueAt(row, 0);
        txtUsername.setText((String) tableModel.getValueAt(row, 1));
        txtFullname.setText((String) tableModel.getValueAt(row, 2));
        txtEmail.setText((String) tableModel.getValueAt(row, 3));
        txtPassword.setText("");
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = User.findAll();
        
        for (User user : users) {
            Object[] row = {
                user.getId(),
                user.getUsername(),
                user.getUrl() != null ? user.getUrl() : "",
                user.getEmail() != null ? user.getEmail() : ""
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserForm form = new UserForm();
            form.setVisible(true);
        });
    }
}
