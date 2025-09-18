package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Model.*;
import Persistance.DisasterDatabaseConn;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class for the Disaster Reporting view.
 * This class handles the functionality for reporting disasters, viewing disaster reports,
 * and navigating back to the main menu.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class DisasterReportingController implements Initializable {

    @FXML
    private ComboBox<String> disasterTypeComboBox;
    @FXML
    private TextField locationField;
    @FXML
    private Slider severitySlider;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TableView<DisasterReport> disasterTable;
    @FXML
    private TableColumn<DisasterReport, Integer> reportIdColumn;
    @FXML
    private TableColumn<DisasterReport, String> disasterTypeColumn;
    @FXML
    private TableColumn<DisasterReport, String> locationColumn;
    @FXML
    private TableColumn<DisasterReport, Integer> severityColumn;
    @FXML
    private TableColumn<DisasterReport, String> statusColumn;
    @FXML
    private TableColumn<DisasterReport, LocalDateTime> timestampColumn;

    /**
     * Initializes the controller class.
     */
    
    private DisasterDatabaseConn dbConn;
    
    /**
     * Initializes the controller class.
     * Sets up the disaster type combo box, table columns, and loads existing disaster reports.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConn = new DisasterDatabaseConn();
        setupDisasterTypeComboBox();
        setupTableColumns();
        loadDisasterReports();
    }  
    
    /**
     * Sets up the disaster type combo box with predefined disaster types.
     */
    private void setupDisasterTypeComboBox() {
        disasterTypeComboBox.setItems(FXCollections.observableArrayList(
            "Earthquake", "Flood", "Hurricane", "Wildfire", "Tsunami", "Other"
        ));
    }
    
    /**
     * Sets up the table columns with the appropriate property value factories.
     */
    private void setupTableColumns() {
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        disasterTypeColumn.setCellValueFactory(new PropertyValueFactory<>("disasterType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        severityColumn.setCellValueFactory(new PropertyValueFactory<>("severity"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
    }
    
    /**
     * Handles the submission of a new disaster report.
     * Validates input, creates a new DisasterReport object, and adds it to the database.
     *
     * @param event The action event triggered by clicking the submit button.
     */
    @FXML
    private void submitReport(ActionEvent event) {
        String disasterType = disasterTypeComboBox.getValue();
        String location = locationField.getText();
        int severity = (int) severitySlider.getValue();
        String description = descriptionArea.getText();

        if (disasterType == null || location.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Please fill all fields", Alert.AlertType.ERROR);
            return;
        }
        DisasterReport report = new DisasterReport(0, disasterType, location, severity, description, "Active", LocalDateTime.now());
        
        if (dbConn.addDisasterReport(report)) {
            showAlert("Success", "Disaster report submitted successfully", Alert.AlertType.INFORMATION);
            clearInputFields();
            loadDisasterReports();
        } else {
            showAlert("Error", "Failed to submit disaster report", Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Refreshes the list of disaster reports in the table view.
     *
     * @param event The action event triggered by clicking the refresh button.
     */
    @FXML
    private void refreshDisasterList(ActionEvent event) {
        loadDisasterReports();
    }
    
    /**
     * Displays the details of the selected disaster report.
     *
     * @param event The action event triggered by clicking the view details button.
     */
    @FXML
    private void viewDisasterDetails(ActionEvent event) {
        DisasterReport selectedDisaster = disasterTable.getSelectionModel().getSelectedItem();
        if (selectedDisaster != null) {
            showAlert("Disaster Details", selectedDisaster.toString(), Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Please select a disaster to view details", Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Loads all disaster reports from the database and populates the table view.
     */
    private void loadDisasterReports() {
        List<DisasterReport> reports = dbConn.getAllDisasterReports();
        disasterTable.setItems(FXCollections.observableArrayList(reports));
    }
    
    /**
     * Clears all input fields in the disaster reporting form.
     */
    private void clearInputFields() {
        disasterTypeComboBox.getSelectionModel().clearSelection();
        locationField.clear();
        severitySlider.setValue(1);
        descriptionArea.clear();
    }
    
    /**
     * Displays an alert dialog with the specified title, content, and alert type.
     *
     * @param title The title of the alert dialog.
     * @param content The content message of the alert dialog.
     * @param alertType The type of the alert (e.g., INFORMATION, ERROR).
     */
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Navigates back to the main menu.
     *
     * @param event The action event triggered by clicking the main menu button.
     */
    @FXML
    private void MainMenuButton(ActionEvent event) {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenu.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the main menu scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not load main menu: " + e.getMessage(), Alert.AlertType.ERROR);
            // Handle the exception (e.g., show an error dialog)
        }
    }
    
    /**
     * Alternative method to navigate back to the main menu.
     *
     * @param event The action event triggered by clicking the alternative main menu button.
     */
    @FXML
    private void MainMenuButton2(ActionEvent event) {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenu.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the main menu scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not load main menu: " + e.getMessage(), Alert.AlertType.ERROR);
            // Handle the exception (e.g., show an error dialog)
        }
    }
}
