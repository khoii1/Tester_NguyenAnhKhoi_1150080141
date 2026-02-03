import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * GUI Form để thêm Organization Unit
 */
public class OrganizationUnitForm extends JFrame {
    private JTextField txtUnitId;
    private JTextField txtName;
    private JTextArea txtDescription;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnView;
    private JLabel lblStatus;

    public OrganizationUnitForm() {
        initComponents();
        DatabaseManager.initDatabase();
    }

    private void initComponents() {
        setTitle("Organization Unit Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Header Panel with gradient effect
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("Add Organization Unit");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        // Status label in header
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatus.setForeground(Color.WHITE);
        headerPanel.add(lblStatus, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel with card design
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 245));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Form panel with white background (card style)
        JPanel formCard = new JPanel();
        formCard.setBackground(Color.WHITE);
        formCard.setLayout(new GridBagLayout());
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Unit Id field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblUnitId = new JLabel("Unit ID:");
        lblUnitId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUnitId.setForeground(new Color(97, 97, 97));
        formCard.add(lblUnitId, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUnitId = new JTextField();
        txtUnitId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUnitId.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formCard.add(txtUnitId, gbc);

        // Name field (Required)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel lblName = new JLabel("Name*:");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblName.setForeground(new Color(211, 47, 47));
        formCard.add(lblName, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formCard.add(txtName, gbc);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDescription.setForeground(new Color(97, 97, 97));
        formCard.add(lblDescription, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescription = new JTextArea(6, 20);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scrollPane = new JScrollPane(txtDescription);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189), 1));
        formCard.add(scrollPane, gbc);

        // Info labels
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblRequired = new JLabel("* Required field");
        lblRequired.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblRequired.setForeground(new Color(211, 47, 47));
        formCard.add(lblRequired, gbc);

        gbc.gridy = 4;
        JLabel lblOrg = new JLabel("This unit will be added under Organization");
        lblOrg.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblOrg.setForeground(new Color(117, 117, 117));
        formCard.add(lblOrg, gbc);

        contentPanel.add(formCard, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Button panel with modern design
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        btnSave = new JButton("Save");
        btnSave.setPreferredSize(new java.awt.Dimension(130, 40));
        btnSave.setBackground(new Color(200, 230, 201));
        btnSave.setForeground(new Color(27, 94, 32));
        btnSave.setFocusPainted(false);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBorder(BorderFactory.createEmptyBorder());
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> saveOrganizationUnit());
        buttonPanel.add(btnSave);

        btnCancel = new JButton("Clear");
        btnCancel.setPreferredSize(new java.awt.Dimension(130, 40));
        btnCancel.setBackground(new Color(255, 224, 178));
        btnCancel.setForeground(new Color(191, 54, 12));
        btnCancel.setFocusPainted(false);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBorder(BorderFactory.createEmptyBorder());
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> clearForm());
        buttonPanel.add(btnCancel);

        btnView = new JButton("View All");
        btnView.setPreferredSize(new java.awt.Dimension(130, 40));
        btnView.setBackground(new Color(187, 222, 251));
        btnView.setForeground(new Color(13, 71, 161));
        btnView.setFocusPainted(false);
        btnView.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnView.setBorder(BorderFactory.createEmptyBorder());
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.addActionListener(e -> viewAllRecords());
        buttonPanel.add(btnView);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveOrganizationUnit() {
        String unitId = txtUnitId.getText().trim();
        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();

        // Create organization unit
        OrganizationUnit orgUnit = new OrganizationUnit();
        if (!unitId.isEmpty()) {
            orgUnit.setUnitId(unitId);
        }
        orgUnit.setName(name);
        orgUnit.setDescription(description.isEmpty() ? null : description);

        // Validate and save
        if (!orgUnit.validate()) {
            lblStatus.setText("❌ Error: Name is required!");
            lblStatus.setForeground(Color.RED);
            txtName.requestFocus();
            txtName.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            return;
        }

        if (orgUnit.save()) {
            lblStatus.setText("✅ Organization Unit saved successfully! ID: " + orgUnit.getUnitId());
            lblStatus.setForeground(new Color(76, 175, 80));
            clearForm();
            
            // Show confirmation dialog
            JOptionPane.showMessageDialog(this,
                "Organization Unit saved successfully!\n\n" +
                "Unit ID: " + orgUnit.getUnitId() + "\n" +
                "Name: " + orgUnit.getName() + "\n" +
                "Description: " + (orgUnit.getDescription() != null ? orgUnit.getDescription() : "N/A"),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("❌ Error: Failed to save to database!");
            lblStatus.setForeground(Color.RED);
        }
    }

    private void clearForm() {
        txtUnitId.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtName.setBorder(UIManager.getBorder("TextField.border"));
        lblStatus.setText(" ");
        txtName.requestFocus();
    }

    private void viewAllRecords() {
        JDialog dialog = new JDialog(this, "All Organization Units", true);
        dialog.setSize(750, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel headerLabel = new JLabel("Organization Units Database");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);

        String[] columnNames = {"Unit ID", "Name", "Description"};
        Object[][] data = getAllRecordsData();

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setGridColor(new Color(224, 224, 224));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.getTableHeader().setForeground(new Color(63, 81, 181));
        table.setSelectionBackground(new Color(197, 202, 233));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        // Footer with count
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(224, 224, 224)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        JLabel lblCount = new JLabel("Total records: " + data.length);
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCount.setForeground(new Color(63, 81, 181));
        footerPanel.add(lblCount);
        dialog.add(footerPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }

    private Object[][] getAllRecordsData() {
        try {
            java.sql.Connection conn = DatabaseManager.getConnection();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery("SELECT * FROM organization_units ORDER BY unit_id");
            
            java.util.List<Object[]> list = new java.util.ArrayList<>();
            while (rs.next()) {
                Object[] row = {
                    rs.getString("unit_id"),
                    rs.getString("name"),
                    rs.getString("description")
                };
                list.add(row);
            }
            
            return list.toArray(new Object[0][]);
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            OrganizationUnitForm form = new OrganizationUnitForm();
            form.setVisible(true);
        });
    }
}
