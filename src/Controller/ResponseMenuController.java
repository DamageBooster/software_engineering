package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class for the Response Menu.
 * This class handles the navigation between different views and logout functionality.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class ResponseMenuController implements Initializable {

    /**
     * Initializes the controller class.
     * This method is called automatically after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Handles the action when the Resource Management button is clicked.
     * Loads and displays the Resource Management view.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    private void ResourceAction(ActionEvent event) {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ResourceManagementView.fxml"));
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
     * Handles the action when the Response Coordination button is clicked.
     * Loads and displays the Response Coordination view.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    private void ResponseAction(ActionEvent event) {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ResponseCoordinationView.fxml"));
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
     * Handles the action when the Communication Hub button is clicked.
     * Loads and displays the Communication Hub view.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    private void CommAction(ActionEvent event) {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CommunicationHubView.fxml"));
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
     * Handles the logout action.
     * Displays a logout success message and navigates to the login page.
     *
     * @param event The action event triggered by clicking the logout button.
     */
    @FXML
    private void logOutAction(ActionEvent event) {
        try {
            // Show logout success message
            showAlert("Logout", "Logged out successfully", Alert.AlertType.INFORMATION);

            // Load the login page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the login page scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not load login page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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
    
}
