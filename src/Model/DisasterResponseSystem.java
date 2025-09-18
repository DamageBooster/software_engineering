package Model;
import Controller.MainMenuController;
import Persistance.DisasterDatabaseConn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for the Disaster Response System application.
 * This class extends JavaFX's Application class and handles the initialization
 * and launch of the application, as well as database connection management.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class DisasterResponseSystem extends Application{
    private static DisasterDatabaseConn dbConn;

    /**
     * Starts the JavaFX application by loading the main menu FXML and setting up the primary stage.
     *
     * @param primaryStage The primary stage for this application, onto which
     *                     the application scene can be set.
     * @throws Exception If there is an error during application start-up.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenu.fxml"));
            Parent root = loader.load();

            // Set up the primary stage
            primaryStage.setTitle("Disaster Response System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * The main entry point for the application. Initializes the database connection
     * and launches the JavaFX application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Initialize the database connection
        dbConn = new DisasterDatabaseConn();

        // Launch the JavaFX application
        launch(args);
    }

    /**
     * Closes the database connection when the application exits.
     * This method is called automatically by the JavaFX runtime.
     *
     * @throws Exception If there is an error during application shutdown.
     */
    @Override
    public void stop() throws Exception {
        // Close the database connection when the application exits
        if (dbConn != null) {
            dbConn.closeConnection();
        }
        super.stop();
    }
    
    /**
     * Gets the database connection object.
     *
     * @return The DisasterDatabaseConn object used for database operations.
     */
    public static DisasterDatabaseConn getDbConn() {
        return dbConn;
    }
   
}
