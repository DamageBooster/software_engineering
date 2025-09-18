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
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * FXML Controller class for the Main Menu view.
 * This class handles navigation to different views of the application.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class MainMenuController implements Initializable {


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
     * Handles the action to navigate to the Disaster Reporting view.
     *
     * @param event The action event triggered by clicking the report disaster button.
     */
    @FXML
    private void mainReport(ActionEvent event) {
        loadView("DisasterReportingView.fxml", "Report Disaster", event, 0);
    }

    /**
     * Handles the action to navigate to the View Disaster tab of the Disaster Reporting view.
     *
     * @param event The action event triggered by clicking the view disaster button.
     */
    @FXML
    private void viewDisaster(ActionEvent event) {
        loadView("DisasterReportingView.fxml", "View Disaster", event, 1);
    }

    /**
     * Handles the action to navigate to the Login view.
     *
     * @param event The action event triggered by clicking the login button.
     */
    @FXML
    private void login(ActionEvent event) {
        loadView("Login.fxml", "Login", event, -1);
    }
    
    /**
     * Handles the action to navigate to the Registration view.
     *
     * @param event The action event triggered by clicking the registration button.
     */
    @FXML
    private void RegistrationButton(ActionEvent event) {
        loadView("Registration.fxml", "Registration", event, -1);
    }
    
    /**
     * Loads a specified view and sets it as the current scene.
     * If the loaded view is a TabPane and a valid tab index is provided, it selects the specified tab.
     *
     * @param fxmlFile The name of the FXML file to load.
     * @param title The title to set for the stage.
     * @param event The action event that triggered this method call.
     * @param tabIndex The index of the tab to select (-1 if not applicable).
     */
    private void loadView(String fxmlFile, String title, ActionEvent event, int tabIndex) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            
            if (tabIndex >= 0 && root instanceof TabPane) {
                TabPane tabPane = (TabPane) root;
                if (tabIndex < tabPane.getTabs().size()) {
                    tabPane.getSelectionModel().select(tabIndex);
                }
            }
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load view");
            alert.setContentText("An error occurred while trying to load the view: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
}
