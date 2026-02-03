import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
 * GUI Form để thêm Job Title
 */
public class JobTitleForm extends JFrame {
    private JTextField txtJobTitle;
    private JTextArea txtDescription;
    private JTextField txtSpecificationFile;
    private JButton btnBrowse;
    private JTextArea txtNote;
    private JButton btnSave;
    private JButton btnClear;
    private JButton btnView;
    private JLabel lblStatus;
    
    private File selectedFile;

    public JobTitleForm() {
        initComponents();
        DatabaseManager.initDatabase();
    }

    private void initComponents() {
        setTitle("Job Title Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("Add Job Title");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatus.setForeground(Color.WHITE);
        headerPanel.add(lblStatus, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 245));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Form panel (card style)
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

        // Job Title field (Required)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblJobTitle = new JLabel("Job Title*:");
        lblJobTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblJobTitle.setForeground(new Color(211, 47, 47));
        formCard.add(lblJobTitle, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtJobTitle = new JTextField();
        txtJobTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtJobTitle.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formCard.add(txtJobTitle, gbc);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDescription.setForeground(new Color(97, 97, 97));
        formCard.add(lblDescription, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescription = new JTextArea(3, 20);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189), 1));
        formCard.add(scrollDesc, gbc);

        // Job Specification field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblSpec = new JLabel("Job Specification:");
        lblSpec.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSpec.setForeground(new Color(97, 97, 97));
        formCard.add(lblSpec, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel specPanel = new JPanel(new BorderLayout(5, 0));
        specPanel.setBackground(Color.WHITE);
        
        txtSpecificationFile = new JTextField();
        txtSpecificationFile.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSpecificationFile.setEditable(false);
        txtSpecificationFile.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        specPanel.add(txtSpecificationFile, BorderLayout.CENTER);
        
        btnBrowse = new JButton("Browse");
        btnBrowse.setPreferredSize(new java.awt.Dimension(100, 38));
        btnBrowse.setBackground(new Color(158, 158, 158));
        btnBrowse.setForeground(Color.WHITE);
        btnBrowse.setFocusPainted(false);
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBrowse.setBorder(BorderFactory.createEmptyBorder());
        btnBrowse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBrowse.addActionListener(e -> browseFile());
        specPanel.add(btnBrowse, BorderLayout.EAST);
        
        formCard.add(specPanel, gbc);

        // Note field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblNote = new JLabel("Note:");
        lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNote.setForeground(new Color(97, 97, 97));
        formCard.add(lblNote, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        txtNote = new JTextArea(3, 20);
        txtNote.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);
        txtNote.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scrollNote = new JScrollPane(txtNote);
        scrollNote.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189), 1));
        formCard.add(scrollNote, gbc);

        // Info labels
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblRequired = new JLabel("* Required field | Max: Job Title 100 chars, Description/Note 400 chars, File 1024 KB");
        lblRequired.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblRequired.setForeground(new Color(117, 117, 117));
        formCard.add(lblRequired, gbc);

        contentPanel.add(formCard, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Button panel
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
        btnSave.addActionListener(e -> saveJobTitle());
        buttonPanel.add(btnSave);

        btnClear = new JButton("Clear");
        btnClear.setPreferredSize(new java.awt.Dimension(130, 40));
        btnClear.setBackground(new Color(255, 224, 178));
        btnClear.setForeground(new Color(191, 54, 12));
        btnClear.setFocusPainted(false);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClear.setBorder(BorderFactory.createEmptyBorder());
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> clearForm());
        buttonPanel.add(btnClear);

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

    private void browseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Job Specification File");
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            txtSpecificationFile.setText(selectedFile.getName());
            
            long sizeKB = selectedFile.length() / 1024;
            if (sizeKB > 1024) {
                lblStatus.setText("Warning: File size " + sizeKB + " KB exceeds 1024 KB limit");
                lblStatus.setForeground(new Color(255, 152, 0));
            } else {
                lblStatus.setText("File selected: " + selectedFile.getName() + " (" + sizeKB + " KB)");
                lblStatus.setForeground(Color.WHITE);
            }
        }
    }

    private void saveJobTitle() {
        String jobTitle = txtJobTitle.getText().trim();
        String description = txtDescription.getText().trim();
        String note = txtNote.getText().trim();

        JobTitle job = new JobTitle();
        job.setJobTitle(jobTitle);
        job.setDescription(description.isEmpty() ? null : description);
        job.setNote(note.isEmpty() ? null : note);
        
        // Handle file
        if (selectedFile != null) {
            try {
                byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
                long sizeKB = selectedFile.length() / 1024;
                
                job.setJobSpecification(fileContent);
                job.setSpecificationFilename(selectedFile.getName());
                job.setSpecificationSize((int) sizeKB);
            } catch (IOException e) {
                lblStatus.setText("Error reading file: " + e.getMessage());
                lblStatus.setForeground(new Color(211, 47, 47));
                return;
            }
        }

        if (!job.validate()) {
            lblStatus.setText("Validation failed! Check your input.");
            lblStatus.setForeground(new Color(211, 47, 47));
            txtJobTitle.requestFocus();
            txtJobTitle.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            return;
        }

        if (job.save()) {
            lblStatus.setText("Job Title saved successfully! ID: " + job.getId());
            lblStatus.setForeground(Color.WHITE);
            clearForm();
            
            JOptionPane.showMessageDialog(this,
                "Job Title saved successfully!\n\n" +
                "ID: " + job.getId() + "\n" +
                "Job Title: " + job.getJobTitle() + "\n" +
                "Description: " + (job.getDescription() != null ? job.getDescription() : "N/A") + "\n" +
                "File: " + (job.getSpecificationFilename() != null ? job.getSpecificationFilename() : "N/A"),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Error: Failed to save to database!");
            lblStatus.setForeground(new Color(211, 47, 47));
        }
    }

    private void clearForm() {
        txtJobTitle.setText("");
        txtDescription.setText("");
        txtSpecificationFile.setText("");
        txtNote.setText("");
        txtJobTitle.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        selectedFile = null;
        lblStatus.setText(" ");
        txtJobTitle.requestFocus();
    }

    private void viewAllRecords() {
        JDialog dialog = new JDialog(this, "All Job Titles", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel headerLabel = new JLabel("Job Titles Database");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Job Title", "Description", "File", "Size (KB)", "Note"};
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
        
        // Footer
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
            java.sql.ResultSet rs = stmt.executeQuery("SELECT * FROM job_titles ORDER BY id");
            
            java.util.List<Object[]> list = new java.util.ArrayList<>();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("job_title"),
                    rs.getString("description"),
                    rs.getString("specification_filename"),
                    rs.getObject("specification_size"),
                    rs.getString("note")
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JobTitleForm form = new JobTitleForm();
            form.setVisible(true);
        });
    }
}
