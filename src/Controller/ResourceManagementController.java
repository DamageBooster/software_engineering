package Controller;

import Model.*;
import Persistance.DisasterDatabaseConn;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class for managing resources in a disaster management system.
 * This class handles resource addition, allocation, status updates, and reporting.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class ResourceManagementController implements Initializable {

    @FXML
    private HBox quantityField;
    @FXML
    private ComboBox<String> resourceTypeComboBox;
    @FXML
    private TableView<Resource> resourceTable;
    @FXML
    private TableColumn<Resource, Integer> resourceIdColumn;
    @FXML
    private TableColumn<Resource, String> typeColumn;
    @FXML
    private TableColumn<Resource, Integer> quantityColumn;
    @FXML
    private TableColumn<Resource, String> statusColumn;
    @FXML
    private TextArea allocationDetailsArea;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox<String> disasterComboBox;

    private DisasterDatabaseConn dbConn;
    
    /**
     * Initializes the controller class.
     * Sets up the resource type combo box, table columns, loads resources, and sets up the disaster combo box.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConn = new DisasterDatabaseConn();
        setupResourceTypeComboBox();
        setupTableColumns();
        loadResources();
        setupDisasterComboBox();
    } 
    
    /**
     * Sets up the resource type combo box with predefined resource types.
     */
    private void setupResourceTypeComboBox() {
        resourceTypeComboBox.setItems(FXCollections.observableArrayList(
            "Medical Supplies", "Food", "Water", "Shelter", "Clothing", "Other"
        ));
    }
    
    /**
     * Sets up the table columns with the appropriate cell value factories.
     */
    private void setupTableColumns() {
        resourceIdColumn.setCellValueFactory(cellData -> 
        new SimpleIntegerProperty(cellData.getValue().getResourceID()).asObject());
    typeColumn.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getType()));
    quantityColumn.setCellValueFactory(cellData -> 
        new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
    statusColumn.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getStatus()));
    }
    
    /**
     * Loads all resources from the database and populates the resource table.
     */
    private void loadResources() {
        try {
            List<Resource> resources = dbConn.getAllResources();
            resourceTable.setItems(FXCollections.observableArrayList(resources));
        } catch (UnsupportedOperationException e) {
            showAlert("Error", "Failed to load resources: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Sets up the disaster combo box with active disaster reports from the database.
     */
    private void setupDisasterComboBox() {
        try {
            List<DisasterReport> activeDisasters = dbConn.getActiveDisasterReports();
            disasterComboBox.setItems(FXCollections.observableArrayList(
                activeDisasters.stream().map(DisasterReport::getDisasterType).toList()
            ));
        } catch (UnsupportedOperationException e) {
            showAlert("Error", "Failed to load active disasters: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the addition of a new resource when the add button is clicked.
     *
     * @param event The action event triggered by clicking the add button.
     */
    @FXML
    private void addResource(ActionEvent event) {
        try {
            String type = resourceTypeComboBox.getValue();
            int quantity = Integer.parseInt(quantityTextField.getText());
            String status = "Available";

            if (type == null || type.isEmpty()) {
                showAlert("Error", "Please select a resource type", Alert.AlertType.ERROR);
                return;
            }

            Resource newResource = new Resource(0, type, quantity, status);
            if (dbConn.addResource(newResource)) {
                showAlert("Success", "Resource added successfully", Alert.AlertType.INFORMATION);
                loadResources();
                clearInputFields();
            } else {
                showAlert("Error", "Failed to add resource", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid quantity. Please enter a number.", Alert.AlertType.ERROR);
        } catch (UnsupportedOperationException e) {
            showAlert("Error", "Adding resource is not supported: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the allocation of resources to a disaster when the allocate button is clicked.
     *
     * @param event The action event triggered by clicking the allocate button.
     */
    @FXML
    private void allocateResources(ActionEvent event) {
        Resource selectedResource = resourceTable.getSelectionModel().getSelectedItem();
        String selectedDisaster = disasterComboBox.getValue();
        if (selectedResource == null || selectedDisaster == null) {
            showAlert("Error", "Please select a resource and a disaster", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Assuming you have a method to get disaster ID from its type
            int disasterId = dbConn.getDisasterIdByType(selectedDisaster);
            boolean success = dbConn.allocateResourceToDisaster(selectedResource.getResourceID(), disasterId, selectedResource.getQuantity());
            if (success) {
                showAlert("Success", "Resource allocated successfully", Alert.AlertType.INFORMATION);
                loadResources();
                updateAllocationDetails(selectedResource, selectedDisaster);
            } else {
                showAlert("Error", "Failed to allocate resource", Alert.AlertType.ERROR);
            }
        } catch (UnsupportedOperationException e) {
            showAlert("Error", "Resource allocation is not supported: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Handles updating the status of a selected resource when the update status button is clicked.
     *
     * @param event The action event triggered by clicking the update status button.
     */
    @FXML
    private void updateResourceStatus(ActionEvent event) {
        Resource selectedResource = resourceTable.getSelectionModel().getSelectedItem();
        if (selectedResource == null) {
            showAlert("Error", "Please select a resource to update", Alert.AlertType.ERROR);
            return;
        }

        try {
            String newStatus = promptForNewStatus();
            if (newStatus != null) {
                selectedResource.setStatus(newStatus);
                if (dbConn.updateResource(selectedResource)) {
                    showAlert("Success", "Resource status updated successfully", Alert.AlertType.INFORMATION);
                    loadResources();
                } else {
                    showAlert("Error", "Failed to update resource status", Alert.AlertType.ERROR);
                }
            }
        } catch (UnsupportedOperationException e) {
            showAlert("Error", "Updating resource status is not supported: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Generates and displays a report of all resources in the allocation details area.
     *
     * @param event The action event triggered by clicking the generate report button.
     */
    @FXML
    private void generateResourceReport(ActionEvent event) {
        // Implement report generation logic here
        String report = "Resource Report\n\n";
        for (Resource resource : resourceTable.getItems()) {
            report += resource.toString() + "\n";
        }
        allocationDetailsArea.setText(report.toString());
    }
    
    /**
     * Updates the allocation details area with information about a resource allocation.
     *
     * @param resource The resource that was allocated.
     * @param disaster The disaster to which the resource was allocated.
     */
    private void updateAllocationDetails(Resource resource, String disaster) {
         String details = "Allocated " + resource.getQuantity() + " " + resource.getType() + " to " + disaster;
        allocationDetailsArea.setText(details);
    }

    /**
     * Clears all input fields in the resource management form.
     */
    private void clearInputFields() {
        resourceTypeComboBox.getSelectionModel().clearSelection();
        quantityTextField.clear();
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
     * Prompts the user to select a new status for a resource.
     *
     * @return The selected status, or null if the user cancels the operation.
     */
    private String promptForNewStatus() {
        List<String> choices = List.of("Available", "In Use", "Depleted");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Available", choices);
        dialog.setTitle("Update Resource Status");
        dialog.setHeaderText("Select new status for the resource");
        dialog.setContentText("Status:");
        return dialog.showAndWait().orElse(null);
    }

    /**
     * Handles navigation back to the response menu.
     *
     * @param event The action event triggered by clicking the back button.
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
