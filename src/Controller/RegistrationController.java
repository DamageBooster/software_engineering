package Controller;

import Model.Agency;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.time.LocalDate;
import Model.User;
import Persistance.DisasterDatabaseConn;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class for the Registration view.
 * This class handles user registration functionality.
 *
 * @author Sagar Bhujel
 * student ID 12256739
 */
public class RegistrationController implements Initializable {

    @FXML
    private PasswordField passText;
    @FXML
    private PasswordField rePassText;
    @FXML
    private TextField userNameText;
    @FXML
    private TextField dobText;
    @FXML
    private TextField numberText;
    @FXML
    private TextField addrText;
    @FXML
    private RadioButton male;
    @FXML
    private ToggleGroup genderID;
    @FXML
    private RadioButton female;
    @FXML
    private RadioButton others;
    @FXML
    private TextField fullName;
    @FXML
    private TextField emailField;
    private TextField roleField;
    /**
     * Initializes the controller class.
     */
    private final DisasterDatabaseConn dbConn;
    @FXML
    private ComboBox<Agency> roleComboBox;
    
    /**
     * Constructor for RegistrationController.
     * Initializes the database connection.
     */
    public RegistrationController() {
        dbConn = new DisasterDatabaseConn();
    }
    
    /**
     * Initializes the controller class.
     * Sets up the gender toggle group and populates the role combo box.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up gender toggle group if not done in FXML
        if (genderID == null) {
            genderID = new ToggleGroup();
            male.setToggleGroup(genderID);
            female.setToggleGroup(genderID);
            others.setToggleGroup(genderID);
        }
        populateRoleComboBox();
    }    
    
    /**
     * Populates the role combo box with agencies from the database.
     * Sets up custom cell factory and string converter for the combo box.
     */
    private void populateRoleComboBox() {
        List<Agency> agencies = dbConn.getAllAgencies();
        roleComboBox.getItems().addAll(agencies);
        
        // Set a custom cell factory to display agency names
        roleComboBox.setCellFactory(param -> new ListCell<Agency>() {
            @Override
            protected void updateItem(Agency item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // Set a custom string converter to display selected agency name
        roleComboBox.setConverter(new StringConverter<Agency>() {
            @Override
            public String toString(Agency agency) {
                return agency == null ? null : agency.getName();
            }

            @Override
            public Agency fromString(String string) {
                return null; // Not needed for this use case
            }
        });
    }
    
    /**
     * Handles the registration process when the register button is clicked.
     * Validates input, creates a new user, and adds it to the database.
     *
     * @param event The action event triggered by clicking the register button.
     */
    @FXML
    private void register(ActionEvent event) {
        if (validateInput()) {
            User newUser = createUser();
            if (dbConn.addUser(newUser)) {
                showAlert("Registration Successful", "User has been registered successfully.", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showAlert("Registration Failed", "Failed to register user. Please try again.", Alert.AlertType.ERROR);
            }
        }
    }
    
    /**
     * Handles navigation back to the main menu.
     *
     * @param event The action event triggered by clicking the back button.
     */
    @FXML
    private void goBack(ActionEvent event) {
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
     * Validates user input for registration.
     * Checks for empty fields, password match, valid date format, gender selection, and role selection.
     *
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateInput() {
        if (fullName.getText().isEmpty() || userNameText.getText().isEmpty() || 
            dobText.getText().isEmpty() || numberText.getText().isEmpty() || 
            addrText.getText().isEmpty() || passText.getText().isEmpty() || 
            rePassText.getText().isEmpty() || emailField.getText().isEmpty()) {
            showAlert("Validation Error", "All fields must be filled.", Alert.AlertType.ERROR);
            return false;
        }

        if (!passText.getText().equals(rePassText.getText())) {
            showAlert("Validation Error", "Passwords do not match.", Alert.AlertType.ERROR);
            return false;
        }

        try {
            LocalDate.parse(dobText.getText());
        } catch (DateTimeParseException e) {
            showAlert("Validation Error", "Invalid date format. Use YYYY-MM-DD.", Alert.AlertType.ERROR);
            return false;
        }

        if (genderID.getSelectedToggle() == null) {
            showAlert("Validation Error", "Please select a gender.", Alert.AlertType.ERROR);
            return false;
        }
        
        if (roleComboBox.getValue() == null) {
            showAlert("Validation Error", "Please select a role.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }
    
    /**
     * Creates a User object from the input fields.
     *
     * @return A new User object with the input data.
     */
    private User createUser() {
        String username = userNameText.getText();
        String password = passText.getText();
        Agency selectedAgency = roleComboBox.getValue();
        String role = selectedAgency != null ? selectedAgency.getName() : "";
        String email = emailField.getText();
        String fullname = fullName.getText();
        int gender = getSelectedGender();
        LocalDate dateOfBirth = LocalDate.parse(dobText.getText());
        String phoneNumber = numberText.getText();
        String address = addrText.getText();

        return new User(0, fullname, username, gender, dateOfBirth, phoneNumber, address, role, email, password);
    }
    
    /**
     * Determines the selected gender from the radio buttons.
     *
     * @return An integer representing the selected gender (1 for male, 2 for female, 3 for others, 0 for unspecified).
     */
    private int getSelectedGender() {
        RadioButton selectedRadioButton = (RadioButton) genderID.getSelectedToggle();
        if (selectedRadioButton == male) return 1;
        if (selectedRadioButton == female) return 2;
        if (selectedRadioButton == others) return 3;
        return 0; // Default or unspecified
    }

    /**
     * Clears all input fields after successful registration.
     */
    private void clearFields() {
        fullName.clear();
        userNameText.clear();
        dobText.clear();
        numberText.clear();
        roleComboBox.getSelectionModel().clearSelection();
        addrText.clear();
        passText.clear();
        rePassText.clear();
        emailField.clear();
        genderID.selectToggle(null);
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
