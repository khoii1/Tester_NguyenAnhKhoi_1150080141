package com.hcmunre.organization.ui;

import java.sql.SQLException;

import com.hcmunre.organization.model.Organization;
import com.hcmunre.organization.service.OrganizationService;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * JavaFX UI for Organization Form
 * Provides form for creating and managing organizations
 */
public class OrganizationForm extends Application {
    private OrganizationService service;
    private Organization savedOrganization;
    
    // UI Components
    private TextField txtOrgName;
    private TextField txtAddress;
    private TextField txtPhone;
    private TextField txtEmail;
    private Button btnSave;
    private Button btnBack;
    private Button btnDirector;
    private Label lblMessage;

    @Override
    public void start(Stage primaryStage) {
        service = new OrganizationService();
        
        primaryStage.setTitle("Organization Management");
        
        // Create form layout
        GridPane grid = createFormLayout();
        
        // Create scene
        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Create form layout with all components
     */
    private GridPane createFormLayout() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Title
        Label titleLabel = new Label("Organization Form");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        grid.add(titleLabel, 0, 0, 2, 1);

        // Organization Name (Required)
        Label lblOrgName = new Label("Organization Name*:");
        grid.add(lblOrgName, 0, 1);
        
        txtOrgName = new TextField();
        txtOrgName.setPromptText("Enter organization name (3-255 characters)");
        txtOrgName.setPrefWidth(300);
        grid.add(txtOrgName, 1, 1);

        // Address
        Label lblAddress = new Label("Address:");
        grid.add(lblAddress, 0, 2);
        
        txtAddress = new TextField();
        txtAddress.setPromptText("Enter address");
        txtAddress.setPrefWidth(300);
        grid.add(txtAddress, 1, 2);

        // Phone
        Label lblPhone = new Label("Phone:");
        grid.add(lblPhone, 0, 3);
        
        txtPhone = new TextField();
        txtPhone.setPromptText("Enter phone (9-12 digits)");
        txtPhone.setPrefWidth(300);
        grid.add(txtPhone, 1, 3);

        // Email
        Label lblEmail = new Label("Email:");
        grid.add(lblEmail, 0, 4);
        
        txtEmail = new TextField();
        txtEmail.setPromptText("Enter email");
        txtEmail.setPrefWidth(300);
        grid.add(txtEmail, 1, 4);

        // Message Label
        lblMessage = new Label();
        lblMessage.setStyle("-fx-font-weight: bold;");
        grid.add(lblMessage, 0, 5, 2, 1);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        btnSave = new Button("Save");
        btnSave.setPrefWidth(100);
        btnSave.setOnAction(e -> handleSave());
        
        btnBack = new Button("Back");
        btnBack.setPrefWidth(100);
        btnBack.setOnAction(e -> handleBack());
        
        btnDirector = new Button("Director");
        btnDirector.setPrefWidth(100);
        btnDirector.setDisable(true); // Initially disabled
        btnDirector.setOnAction(e -> handleDirector());
        
        buttonBox.getChildren().addAll(btnSave, btnBack, btnDirector);
        grid.add(buttonBox, 0, 6, 2, 1);

        return grid;
    }

    /**
     * Handle Save button click
     */
    private void handleSave() {
        try {
            // Clear previous message
            lblMessage.setText("");
            lblMessage.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
            
            // Get values from form
            String orgName = txtOrgName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            
            // Create organization object
            Organization organization = new Organization();
            organization.setOrgName(orgName);
            organization.setAddress(address.isEmpty() ? null : address);
            organization.setPhone(phone.isEmpty() ? null : phone);
            organization.setEmail(email.isEmpty() ? null : email);
            
            // Save organization
            savedOrganization = service.saveOrganization(organization);
            
            // Show success message
            lblMessage.setText("Save successfully");
            lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            
            // Enable Director button
            btnDirector.setDisable(false);
            
        } catch (OrganizationService.ValidationException e) {
            // Show validation error
            lblMessage.setText(e.getMessage());
            lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            savedOrganization = null;
            btnDirector.setDisable(true);
            
        } catch (SQLException e) {
            // Show database error
            lblMessage.setText("Database error: " + e.getMessage());
            lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            savedOrganization = null;
            btnDirector.setDisable(true);
        }
    }

    /**
     * Handle Back button click
     */
    private void handleBack() {
        // Clear all fields
        txtOrgName.clear();
        txtAddress.clear();
        txtPhone.clear();
        txtEmail.clear();
        lblMessage.setText("");
        savedOrganization = null;
        btnDirector.setDisable(true);
        
        // In a real application, this would navigate to previous screen
        System.out.println("Back button clicked - returning to previous screen");
    }

    /**
     * Handle Director button click
     */
    private void handleDirector() {
        if (savedOrganization != null) {
            // Open Director form and pass saved organization
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Director Management");
            alert.setHeaderText("Organization Information");
            
            // Build detailed information string
            StringBuilder content = new StringBuilder();
            content.append("Organization Details:\n\n");
            content.append("ID: ").append(savedOrganization.getOrgId()).append("\n");
            content.append("Name: ").append(savedOrganization.getOrgName()).append("\n");
            content.append("Address: ").append(savedOrganization.getAddress() != null ? savedOrganization.getAddress() : "N/A").append("\n");
            content.append("Phone: ").append(savedOrganization.getPhone() != null ? savedOrganization.getPhone() : "N/A").append("\n");
            content.append("Email: ").append(savedOrganization.getEmail() != null ? savedOrganization.getEmail() : "N/A").append("\n");
            content.append("Created Date: ").append(savedOrganization.getCreatedDate()).append("\n\n");
            content.append("Director form will open for this organization.");
            
            alert.setContentText(content.toString());
            alert.showAndWait();
            
            // In a real application, this would open the Director form
            System.out.println("Opening Director form for organization: " + savedOrganization);
        }
    }

    /**
     * Get the saved organization (for testing purposes)
     */
    public Organization getSavedOrganization() {
        return savedOrganization;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
