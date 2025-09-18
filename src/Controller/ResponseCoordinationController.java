package Controller;

import Model.Agency;
import Model.AgencyAssignment;
import Model.DisasterReport;
import Model.Resource;
import Persistance.DisasterDatabaseConn;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class for coordinating disaster response efforts.
 * This class manages the assignment of agencies to disasters, tracks agency assignments,
 * and provides functionality for strategic decision-making and reporting.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class ResponseCoordinationController implements Initializable {

    @FXML
    private TableColumn<DisasterReport, Integer> disasterIdColumn;
    @FXML
    private TableColumn<DisasterReport, String> disasterTypeColumn;
    @FXML
    private TableColumn<DisasterReport, String> locationColumn;
    @FXML
    private TableColumn<DisasterReport, Integer> severityColumn;
    @FXML
    private TableColumn<DisasterReport, String> statusColumn;
    @FXML
    private HBox agencySelector;
    @FXML
    private TableView<AgencyAssignment> agencyAssignmentTable;
    @FXML
    private TableColumn<AgencyAssignment, String> agencyColumn;
    @FXML
    private TableColumn<AgencyAssignment, String> assignedDisasterColumn;
    @FXML
    private TableColumn<AgencyAssignment, String> assignmentStatusColumn;
    @FXML
    private TextArea strategicDecisionArea;
    @FXML
    private TableView<DisasterReport> disasterTable;
    @FXML
    private ComboBox<Agency> agencyComboBox;
    @FXML
    private ComboBox<DisasterReport> disasterComboBox;
    @FXML
    private TableColumn<AgencyAssignment, Integer> assignmentIdColumn;
    @FXML
    private TableColumn<AgencyAssignment, LocalDateTime> assignmentDateColumn;
    
    private ObservableList<AgencyAssignment> agencyAssignments = FXCollections.observableArrayList();;
    private DisasterDatabaseConn dbManager;
    private ObservableList<DisasterReport> disasterReports;
    private ObservableList<Resource> assignedResources;

    /**
     * Initializes the controller class.
     * Sets up the database connection, initializes UI components, and loads initial data.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbManager = new DisasterDatabaseConn();
        disasterReports = FXCollections.observableArrayList();
        assignedResources = FXCollections.observableArrayList();
        
        setupDisasterTableColumns();
        setupAgencyAssignmentTableColumns();
        populateAgencyComboBox();
        populateDisasterComboBox();
        loadActiveDisasterReports();
        loadAgencyAssignments();
    }  
    
    /**
     * Sets up the columns for the disaster table.
     */
    private void setupDisasterTableColumns() {
        disasterIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getReportID()).asObject());
        disasterTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisasterType()));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        severityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSeverity()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
    }
    
    /**
     * Sets up the columns for the agency assignment table.
     */
    private void setupAgencyAssignmentTableColumns() {
        assignmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentID"));
        agencyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAgencyName()));
        assignedDisasterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisasterType()));
        assignmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentDate"));
        assignmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    /**
     * Populates the agency combo box with available agencies.
     */
    private void populateAgencyComboBox() {
        List<Agency> agencies = dbManager.getAllAgencies();
        agencyComboBox.setItems(FXCollections.observableArrayList(agencies));
        agencyComboBox.setConverter(new StringConverter<Agency>() {
            @Override
            public String toString(Agency agency) {
                return agency != null ? agency.getName() : "";
            }

            @Override
            public Agency fromString(String string) {
                return null;
            }
        });
    }
    /**
     * Populates the disaster combo box with active disaster reports.
     */
    private void populateDisasterComboBox() {
        List<DisasterReport> activeDisasters = dbManager.getActiveDisasterReports();
        disasterComboBox.setItems(FXCollections.observableArrayList(activeDisasters));
        disasterComboBox.setConverter(new StringConverter<DisasterReport>() {
            @Override
            public String toString(DisasterReport disaster) {
                return disaster != null ? disaster.getDisasterType() + " - " + disaster.getLocation() : "";
            }

            @Override
            public DisasterReport fromString(String string) {
                return null;
            }
        });
    }
    /**
     * Loads and displays all agency assignments.
     */
    private void loadAgencyAssignments() {
        if (agencyAssignments == null) {
            agencyAssignments = FXCollections.observableArrayList();
        }
        agencyAssignments.clear();
        agencyAssignments.addAll(dbManager.getAllAgencyAssignments());
        agencyAssignmentTable.setItems(agencyAssignments);
    }
    /**
     * Loads and displays active disaster reports.
     */
    private void loadActiveDisasterReports() {
        try {
            List<DisasterReport> activeReports = dbManager.getActiveDisasterReports();
            disasterReports.setAll(activeReports);
            disasterTable.setItems(disasterReports);
        } catch (UnsupportedOperationException e) {
            showAlert("Error", "Failed to load active disaster reports: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /**
     * Assigns a selected agency to a selected disaster.
     *
     * @param event The action event triggered by the assign button.
     */
    @FXML
    private void assignAgencyToDisaster(ActionEvent event) {
        DisasterReport selectedDisaster = disasterComboBox.getValue();
        Agency selectedAgency = agencyComboBox.getValue();

        if (selectedDisaster == null || selectedAgency == null) {
            showAlert("Error", "Please select both a disaster and an agency.", Alert.AlertType.ERROR);
            return;
        }

        try {
            boolean success = dbManager.assignAgencyToDisaster(selectedAgency.getId(), selectedDisaster.getReportID());
            if (success) {
                showAlert("Success", "Agency assigned successfully.", Alert.AlertType.INFORMATION);
                loadAgencyAssignments();
            } else {
                showAlert("Error", "Failed to assign agency.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Agency assignment failed: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Loads agency assignments for a specific disaster.
     *
     * @param disasterId The ID of the disaster to load assignments for.
     */
    private void loadAgencyAssignments(int disasterId) {
        List<AgencyAssignment> assignments = dbManager.getAgenciesAssignedToDisaster(disasterId);
        // Update your UI with the assignments (e.g., populate a TableView)
    }
    
    /**
     * Saves a strategic decision entered by the user.
     *
     * @param event The action event triggered by the save button.
     */
    @FXML
    private void saveStrategicDecision(ActionEvent event) {
//        String decision = strategicDecisionArea.getText();
//        if (decision.isEmpty()) {
//            showAlert("Error", "Please enter a strategic decision.");
//            return;
//        }
//        // In a real system, you'd save this to a database
//        showAlert("Success", "Strategic decision saved.");
    }
    
    /**
     * Generates and displays a report of current disaster responses.
     *
     * @param event The action event triggered by the generate report button.
     */
    @FXML
    private void generateReport(ActionEvent event) {
        // This would typically generate a more comprehensive report
        StringBuilder report = new StringBuilder("Disaster Response Report\n\n");
        for (DisasterReport disaster : disasterReports) {
            report.append(disaster.toString()).append("\n");
        }
        showAlert("Report", report.toString(), Alert.AlertType.INFORMATION);
    }

    /**
     * Updates the status of ongoing response efforts.
     *
     * @param event The action event triggered by the update button.
     */
    @FXML
    private void updateResponseEfforts(ActionEvent event) {
//         // This would typically involve updating the status of resources or disasters
//        showAlert("Info", "Response efforts updated.");
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
     * Navigates back to the response menu.
     *
     * @param event The action event triggered by the back button.
     */
    @FXML
    private void goBackAction(ActionEvent event) {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ResponseMenu.fxml"));
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
