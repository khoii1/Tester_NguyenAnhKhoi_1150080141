package com.hcmunre.organization.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application entry point
 */
public class OrganizationApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        OrganizationForm form = new OrganizationForm();
        form.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
