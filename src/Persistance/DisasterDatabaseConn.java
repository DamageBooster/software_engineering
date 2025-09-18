package Persistance;

import Model.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class manages the database connection and operations for the Disaster Response System.
 * It implements the IDatabaseManager interface and provides methods for interacting with
 * various tables in the database, including users, resources, disaster reports, and agency assignments.
 * @author Sagar Bhujel
 * student id 12256739
 */

public class DisasterDatabaseConn implements IDatabaseManager{
    private final String MYSQL_URL = "jdbc:mysql://localhost:3306";
    private final String DB_URL = MYSQL_URL + "/disasterresponse";
    private final String USERNAME = "root";
    private final String PASSWORD = "pass";
    
    private Connection connection;
    
    /**
     * Constructs a new DisasterDatabaseConn object.
     * Initializes the database connection and creates necessary tables if they don't exist.
     */
    public DisasterDatabaseConn() {
        try {
            // First, connect to MySQL server without specifying a database
            connection = DriverManager.getConnection(MYSQL_URL, USERNAME, PASSWORD);
            // Create the database if it doesn't exist
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS disasterresponse");
            }
            
            // Close the initial connection
            connection.close();
            
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            createTables();
        } catch (SQLException e) {
            System.out.println("Database connection failed. Error: " + e.getMessage());
        }
    }
    
    /**
     * Creates the necessary tables in the database if they don't already exist.
     *
     * @throws SQLException if there's an error executing SQL statements
     */
    private void createTables() throws SQLException {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
            "userID INT AUTO_INCREMENT PRIMARY KEY," +
            "username VARCHAR(50) UNIQUE NOT NULL," +
            "password VARCHAR(50) NOT NULL," +
            "role VARCHAR(20) NOT NULL," +
            "fullName VARCHAR(100) NOT NULL," +
            "gender INT," +
            "dateOfBirth DATE," +
            "phoneNumber VARCHAR(20)," +
            "address VARCHAR(200)," +
            "email VARCHAR(100)" +
        ")";

        String createResourcesTable = "CREATE TABLE IF NOT EXISTS resources (" +
            "resourceID INT AUTO_INCREMENT PRIMARY KEY," +
            "type VARCHAR(50) NOT NULL," +
            "quantity INT NOT NULL," +
            "status VARCHAR(20) NOT NULL" +
        ")";

        String createDisasterReportsTable = "CREATE TABLE IF NOT EXISTS disaster_reports (" +
            "reportID INT AUTO_INCREMENT PRIMARY KEY," +
            "disasterType VARCHAR(50) NOT NULL," +
            "location VARCHAR(100) NOT NULL," +
            "severity INT NOT NULL," +
            "description TEXT," +
            "timeStamp DATETIME NOT NULL," +
            "status VARCHAR(20) DEFAULT 'Active'" +
        ")";

        String createResourceAllocationsTable = "CREATE TABLE IF NOT EXISTS resource_allocations (" +
            "allocationID INT AUTO_INCREMENT PRIMARY KEY," +
            "resourceID INT," +
            "disasterID INT," +
            "quantity INT NOT NULL," +
            "FOREIGN KEY (resourceID) REFERENCES resources(resourceID)," +
            "FOREIGN KEY (disasterID) REFERENCES disaster_reports(reportID)" +
        ")";
        
        String createAgenciesTable = "CREATE TABLE IF NOT EXISTS agencies (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL," +
            "type VARCHAR(50) NOT NULL" +
        ")";
        
        String createAgencyAssignmentsTable = "CREATE TABLE IF NOT EXISTS agency_assignments (" +
            "assignmentID INT AUTO_INCREMENT PRIMARY KEY," +
            "agencyID INT," +
            "disasterID INT," +
            "assignmentDate DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "status VARCHAR(20) DEFAULT 'Assigned'," +
            "FOREIGN KEY (agencyID) REFERENCES agencies(id)," +
            "FOREIGN KEY (disasterID) REFERENCES disaster_reports(reportID)" +
        ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createResourcesTable);
            stmt.execute(createDisasterReportsTable);
            stmt.execute(createResourceAllocationsTable);
            stmt.execute(createAgenciesTable);
            stmt.execute(createAgencyAssignmentsTable);
            
            // Insert default agencies if the table is empty
            String checkAgencies = "SELECT COUNT(*) FROM agencies";
            ResultSet rs = stmt.executeQuery(checkAgencies);
            rs.next();
            if (rs.getInt(1) == 0) {
                String insertAgencies = "INSERT INTO agencies (name, type) VALUES " +
                    "('Fire Department', 'Emergency')," +
                    "('Police', 'Law Enforcement')," +
                    "('Medical Services', 'Healthcare')," +
                    "('Red Cross', 'Humanitarian')";
                stmt.execute(insertAgencies);
            }
        }
    }
    
    /**
     * Adds a new user to the database.
     *
     * @param user the User object to be added
     * @return true if the user was successfully added, false otherwise
     */
    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, role, fullName, gender, dateOfBirth, phoneNumber, address, email) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getFullName());
            pstmt.setInt(5, user.getGender());
            pstmt.setDate(6, Date.valueOf(user.getDateOfBirth()));
            pstmt.setString(7, user.getPhoneNumber());
            pstmt.setString(8, user.getAddress());
            pstmt.setString(9, user.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User object if found, null otherwise
     */
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getting user by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a user from the database by their username.
     *
     * @param username the username of the user to retrieve
     * @return the User object if found, null otherwise
     */
    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getting user by username: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a List of all User objects in the database
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Adds a new resource to the database.
     *
     * @param resource the Resource object to be added
     * @return true if the resource was successfully added, false otherwise
     */
    @Override
    public boolean addResource(Resource resource) {
        String sql = "INSERT INTO resources (type, quantity, status) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, resource.getType());
            pstmt.setInt(2, resource.getQuantity());
            pstmt.setString(3, resource.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding resource: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves a resource from the database by its ID.
     *
     * @param resourceId the ID of the resource to retrieve
     * @return the Resource object if found, null otherwise
     */
    @Override
    public Resource getResourceById(int resourceId) {
        String sql = "SELECT * FROM resources WHERE resourceID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, resourceId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createResourceFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getting resource by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all resources from the database.
     *
     * @return a List of all Resource objects in the database
     */
    @Override
    public List<Resource> getAllResources() {
        List<Resource> resources = new ArrayList<>();
        String sql = "SELECT * FROM resources";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                resources.add(createResourceFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all resources: " + e.getMessage());
        }
        return resources;
    }

    /**
     * Retrieves resources from the database by their type.
     *
     * @param resourceType the type of resources to retrieve
     * @return a List of Resource objects matching the specified type
     */
    @Override
    public List<Resource> getResourcesByType(String resourceType) {
        List<Resource> resources = new ArrayList<>();
        String sql = "SELECT * FROM resources WHERE type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, resourceType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resources.add(createResourceFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting resources by type: " + e.getMessage());
        }
        return resources;
    }

    /**
     * Adds a new disaster report to the database.
     *
     * @param report the DisasterReport object to be added
     * @return true if the report was successfully added, false otherwise
     */
    @Override
    public boolean addDisasterReport(DisasterReport report) {
        String sql = "INSERT INTO disaster_reports (disasterType, location, severity, description, timeStamp) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, report.getDisasterType());
            pstmt.setString(2, report.getLocation());
            pstmt.setInt(3, report.getSeverity());
            pstmt.setString(4, report.getDescription());
            pstmt.setTimestamp(5, Timestamp.valueOf(report.getTimeStamp()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding disaster report: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves a disaster report from the database by its ID.
     *
     * @param reportId the ID of the disaster report to retrieve
     * @return the DisasterReport object if found, null otherwise
     */
    @Override
    public DisasterReport getDisasterReportById(int reportId) {
        String sql = "SELECT * FROM disaster_reports WHERE reportID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reportId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createDisasterReportFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getting disaster report by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all disaster reports from the database.
     *
     * @return a List of all DisasterReport objects in the database
     */
    @Override
    public List<DisasterReport> getAllDisasterReports() {
        List<DisasterReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM disaster_reports";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reports.add(createDisasterReportFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all disaster reports: " + e.getMessage());
        }
        return reports;
    }

    /**
     * Retrieves all active disaster reports from the database.
     *
     * @return a List of all active DisasterReport objects in the database
     */
    @Override
    public List<DisasterReport> getActiveDisasterReports() {
        List<DisasterReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM disaster_reports WHERE status = 'Active'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reports.add(createDisasterReportFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting active disaster reports: " + e.getMessage());
        }
        return reports;
    }

    /**
     * Allocates a resource to a specific disaster.
     *
     * @param resourceId the ID of the resource to allocate
     * @param disasterId the ID of the disaster to allocate the resource to
     * @param quantity the quantity of the resource to allocate
     * @return true if the allocation was successful, false otherwise
     */
    @Override
    public boolean allocateResourceToDisaster(int resourceId, int disasterId, int quantity) {
        String sql = "INSERT INTO resource_allocations (resourceID, disasterID, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, resourceId);
            pstmt.setInt(2, disasterId);
            pstmt.setInt(3, quantity);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error allocating resource to disaster: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all resources allocated to a specific disaster.
     *
     * @param disasterId the ID of the disaster
     * @return a List of Resource objects allocated to the specified disaster
     */
    @Override
    public List<Resource> getResourcesAllocatedToDisaster(int disasterId) {
        List<Resource> resources = new ArrayList<>();
        String sql = "SELECT r.*, ra.quantity FROM resources r JOIN resource_allocations ra ON r.resourceID = ra.resourceID WHERE ra.disasterID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, disasterId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resources.add(createResourceFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting resources allocated to disaster: " + e.getMessage());
        }
        return resources;
    }

    /**
     * Closes the database connection.
     */
    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
    
    /**
     * Creates a User object from a ResultSet.
     *
     * @param rs the ResultSet containing user data
     * @return a User object populated with data from the ResultSet
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("userID"),
            rs.getString("fullName"),
            rs.getString("username"),
            rs.getInt("gender"),
            rs.getDate("dateOfBirth").toLocalDate(),
            rs.getString("phoneNumber"),
            rs.getString("address"),
            rs.getString("role"),
            rs.getString("email"),
            rs.getString("password")
        );
    }

    /**
     * Creates a Resource object from a ResultSet.
     *
     * @param rs the ResultSet containing resource data
     * @return a Resource object populated with data from the ResultSet
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private Resource createResourceFromResultSet(ResultSet rs) throws SQLException {
        return new Resource(
            rs.getInt("resourceID"),
            rs.getString("type"),
            rs.getInt("quantity"),
            rs.getString("status")
        );
    }

    /**
     * Creates a DisasterReport object from a ResultSet.
     *
     * @param rs the ResultSet containing disaster report data
     * @return a DisasterReport object populated with data from the ResultSet
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private DisasterReport createDisasterReportFromResultSet(ResultSet rs) throws SQLException {
        return new DisasterReport(
            rs.getInt("reportID"),
            rs.getString("disasterType"),
            rs.getString("location"),
            rs.getInt("severity"),
            rs.getString("description"),
            rs.getString("status"),
            rs.getTimestamp("timestamp").toLocalDateTime()
            
        );
    }

    /**
     * Updates an existing resource in the database.
     *
     * @param resource the Resource object with updated information
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateResource(Resource resource) {
        String sql = "UPDATE resources SET type = ?, quantity = ?, status = ? WHERE resourceID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, resource.getType());
            pstmt.setInt(2, resource.getQuantity());
            pstmt.setString(3, resource.getStatus());
            pstmt.setInt(4, resource.getResourceID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating resource: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the User object with updated information
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ?, fullName = ?, gender = ?, "
                + "dateOfBirth = ?, phoneNumber = ?, address = ?, email = ? WHERE userID = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getFullName());
            pstmt.setInt(5, user.getGender());
            pstmt.setDate(6, Date.valueOf(user.getDateOfBirth()));
            pstmt.setString(7, user.getPhoneNumber());
            pstmt.setString(8, user.getAddress());
            pstmt.setString(9, user.getEmail());
            pstmt.setInt(10, user.getUserID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
        
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId the ID of the user to delete
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    public boolean deleteUser(int userId) {
//        String sql = "DELETE FROM users WHERE userID = ?";
//        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
//            pstmt.setInt(1, userId);
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.out.println("Error deleting user: " + e.getMessage());
//            return false;
//        }  
        return true;
    }

    /**
     * Deletes a resource from the database.
     *
     * @param resourceId the ID of the resource to delete
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    public boolean deleteResource(int resourceId) {
//        String sql = "DELETE FROM resources WHERE resourceID = ?";
//        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
//            pstmt.setInt(1, resourceId);
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.out.println("Error deleting resource: " + e.getMessage());
//            return false;
//        }
        return true;
    }

    /**
     * Updates an existing disaster report in the database.
     *
     * @param report the DisasterReport object with updated information
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateDisasterReport(DisasterReport report) {
        String sql = "UPDATE disaster_reports SET disasterType = ?, "
                + "location = ?, severity = ?, description = ?, timeStamp = ? WHERE reportID = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, report.getDisasterType());
            pstmt.setString(2, report.getLocation());
            pstmt.setInt(3, report.getSeverity());
            pstmt.setString(4, report.getDescription());
            pstmt.setTimestamp(5, Timestamp.valueOf(report.getTimeStamp()));
            pstmt.setInt(6, report.getReportID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating disaster report: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a disaster report from the database.
     *
     * @param reportId the ID of the disaster report to delete
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    public boolean deleteDisasterReport(int reportId) {
        String sql = "DELETE FROM disaster_reports WHERE reportID = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, reportId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting disaster report: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the ID of a disaster by its type.
     *
     * @param disasterType the type of disaster to search for
     * @return the ID of the disaster if found, -1 otherwise
     */
    @Override
    public int getDisasterIdByType(String disasterType) {
        String sql = "SELECT reportID FROM disaster_reports WHERE disasterType = ? LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, disasterType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("reportID");
            }
        } catch (SQLException e) {
            System.out.println("Error getting disaster ID by type: " + e.getMessage());
        }
        return -1; // Return -1 if no disaster found with the given type
    }

    /**
     * Retrieves all unique roles from the users table.
     *
     * @return a List of all unique roles in the database
     */
    @Override
    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT DISTINCT role FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting roles: " + e.getMessage());
        }
        return roles;
    }

    /**
     * Retrieves all agencies from the database.
     *
     * @return a List of all Agency objects in the database
     */
    @Override
    public List<Agency> getAllAgencies() {
        List<Agency> agencies = new ArrayList<>();
        String sql = "SELECT * FROM agencies";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                agencies.add(new Agency(Integer.parseInt(rs.getString("id")), rs.getString("name"), rs.getString("type")));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all agencies: " + e.getMessage());
        }
        return agencies;
    }

    /**
     * Assigns an agency to a specific disaster.
     *
     * @param agencyId the ID of the agency to assign
     * @param disasterId the ID of the disaster to assign the agency to
     * @return true if the assignment was successful, false otherwise
     */
    @Override
    public boolean assignAgencyToDisaster(int agencyId, int disasterId) {
       String sql = "INSERT INTO agency_assignments (agencyID, disasterID) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, agencyId);
            pstmt.setInt(2, disasterId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error assigning agency to disaster: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all agencies assigned to a specific disaster.
     *
     * @param disasterId the ID of the disaster
     * @return a List of AgencyAssignment objects for the specified disaster
     */
    @Override
    public List<AgencyAssignment> getAgenciesAssignedToDisaster(int disasterId) {
        List<AgencyAssignment> assignments = new ArrayList<>();
        String sql = "SELECT aa.*, a.name as agencyName FROM agency_assignments aa " +
                     "JOIN agencies a ON aa.agencyID = a.id " +
                     "WHERE aa.disasterID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, disasterId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                assignments.add(new AgencyAssignment(
                    rs.getInt("assignmentID"),
                    rs.getInt("agencyID"),
                    rs.getString("agencyName"),
                    rs.getInt("disasterID"),
                    rs.getTimestamp("assignmentDate").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getString("DisasterType")
                    
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting agencies assigned to disaster: " + e.getMessage());
        }
        return assignments;
    }
    
    /**
     * Retrieves all agency assignments from the database.
     *
     * @return a List of all AgencyAssignment objects in the database
     */
    @Override
    public List<AgencyAssignment> getAllAgencyAssignments() {
        List<AgencyAssignment> assignments = new ArrayList<>();
        String sql = "SELECT aa.*, a.name as agencyName, dr.disasterType FROM agency_assignments aa " +
                     "JOIN agencies a ON aa.agencyID = a.id " +
                     "JOIN disaster_reports dr ON aa.disasterID = dr.reportID";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                assignments.add(new AgencyAssignment(
                    rs.getInt("assignmentID"),
                    rs.getInt("agencyID"),
                    rs.getString("agencyName"),
                    rs.getInt("disasterID"),
                    rs.getTimestamp("assignmentDate").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getString("disasterType")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all agency assignments: " + e.getMessage());
        }
        return assignments;
    }
}