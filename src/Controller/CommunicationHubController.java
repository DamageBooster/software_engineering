package Controller;

import Model.Message;
import Model.Notification;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class for the Communication Hub view.
 * This class manages the functionality for sending notifications, alerts, and messages,
 * as well as displaying sent communications in table views.
 *
 * @author Sagar Bhujel
 * Student id 12256739
 */
public class CommunicationHubController implements Initializable {

    @FXML
    private ComboBox<String> recipientTypeComboBox;
    @FXML
    private TextField notificationTitleField;
    @FXML
    private TextArea notificationContentArea;
    @FXML
    private TableView<Notification> sentNotificationsTable;
    @FXML
    private TableColumn<Notification, LocalDateTime> notificationTimestampColumn;
    @FXML
    private TableColumn<Notification, String> notificationRecipientColumn;
    @FXML
    private TableColumn<Notification, String> notificationTitleColumn;
    @FXML
    private ComboBox<String> alertTypeComboBox;
    @FXML
    private TextField alertLocationField;
    @FXML
    private TextArea alertDetailsArea;
    @FXML
    private TableView<Alert> sentAlertsTable;
    @FXML
    private TableColumn<Alert, LocalDateTime> alertTimestampColumn;
    @FXML
    private TableColumn<Alert, String> alertTypeColumn;
    @FXML
    private TableColumn<Alert, String> alertLocationColumn;
    @FXML
    private ComboBox<String> subsystemComboBox;
    @FXML
    private TextField messageSubjectField;
    @FXML
    private TextArea messageContentArea;
    @FXML
    private TableColumn<Message, LocalDateTime> messageTimestampColumn;
    @FXML
    private TableColumn<Message, String> messageFromColumn;
    @FXML
    private TableColumn<Message, String> messageToColumn;
    @FXML
    private TableColumn<Message, String> messageSubjectColumn;
    @FXML
    private TableView<Message> sentMessagesTable;

    
    
    /**
     * Initializes the controller class.
     * This method is called automatically after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * Handles the action of sending a notification.
     *
     * @param event The action event triggered by clicking the send notification button.
     */
    @FXML
    private void sendNotification(ActionEvent event) {
        
    }

    /**
     * Handles the action of sending an alert.
     *
     * @param event The action event triggered by clicking the send alert button.
     */
    @FXML
    private void sendAlert(ActionEvent event) {
    }

    /**
     * Handles the action of sending a message.
     *
     * @param event The action event triggered by clicking the send message button.
     */
    @FXML
    private void sendMessage(ActionEvent event) {
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
