package Controller;

import Model.User;
import Persistance.DisasterDatabaseConn;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class for the Login view.
 * This class handles user authentication and navigation to the response menu or main menu.
 *
 * @author Sagar Bhujel
 * Student ID 12256739
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField loginPasswd;
    @FXML
    private TextField userName;
    
    private DisasterDatabaseConn dbConn;
    @FXML
    private ComboBox<String> roleComboBox;
    
    /**
     * Initializes the controller class.
     * Sets up the database connection and populates the role combo box.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConn = new DisasterDatabaseConn();
        setupRoleComboBox();
        if (loginPasswd == null) {
            System.err.println("loginPasswd is null. Check your FXML file.");
        }
    }  
    
    /**
     * Sets up the role combo box with available roles from the database.
     */
    private void setupRoleComboBox() {
        List<String> roles = dbConn.getAllRoles();
        roleComboBox.getItems().addAll(roles);
        roleComboBox.setPromptText("Select Role");
    }
   
    /**
     * Handles the login action when the login button is clicked.
     * Validates user credentials and navigates to the response menu if successful.
     *
     * @param event The action event triggered by clicking the login button.
     */
    @FXML
    private void loginAction(ActionEvent event) {
        String username = userName.getText();
        String password = loginPasswd.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Error", "Please fill in all fields and select a role.");
            return;
        }

        User user = dbConn.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase(role)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/responseMenu.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) userName.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load response menu.");
            }
        } else {
            showAlert("Error", "Invalid username, password, or role.");
        }
    }

    /**
     * Handles the cancel action when the cancel button is clicked.
     * Navigates back to the main menu.
     *
     * @param event The action event triggered by clicking the cancel button.
     */
    @FXML
    private void cancelAction(ActionEvent event) {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenu.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) userName.getScene().getWindow();

            // Set the main menu scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }
    
    /**
     * Displays an alert dialog with the specified title and content.
     *
     * @param title The title of the alert dialog.
     * @param content The content message of the alert dialog.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}
