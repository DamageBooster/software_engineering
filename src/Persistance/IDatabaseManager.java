package Persistance;

import java.util.List;
import Model.*;
/**
 * Interface defining the contract for database operations in the Disaster Response System.
 * This interface provides methods for managing users, resources, disaster reports,
 * resource allocations, and agency assignments.
 *
 * @author Sagar Bhujel
 * Student id 12256739
 */
public interface IDatabaseManager {
    //creation of database
    // User-related methods
    boolean addUser(User user);
    User getUserById(int userId);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    List<String> getAllRoles();
    boolean updateUser(User user);
    boolean deleteUser(int userId);
    
    // Resource-related methods
    boolean addResource(Resource resource);
    Resource getResourceById(int resourceId);
    List<Resource> getAllResources();
    List<Resource> getResourcesByType(String resourceType);
    boolean updateResource(Resource resource);
    boolean deleteResource(int resourceId);
    
    // Disaster report-related methods
    boolean addDisasterReport(DisasterReport report);
    DisasterReport getDisasterReportById(int reportId);
    int getDisasterIdByType(String disasterType);
    List<DisasterReport> getAllDisasterReports();
    List<DisasterReport> getActiveDisasterReports();
    boolean updateDisasterReport(DisasterReport report);
    boolean deleteDisasterReport(int reportId);
    
    // Resource allocation methods
    boolean allocateResourceToDisaster(int resourceId, int disasterId, int quantity);
    List<Resource> getResourcesAllocatedToDisaster(int disasterId);
    
    //Adding agency
    List<Agency> getAllAgencies();
    
    //agency allocation
    boolean assignAgencyToDisaster(int agencyId, int disasterId);
    List<AgencyAssignment> getAllAgencyAssignments();
    List<AgencyAssignment> getAgenciesAssignedToDisaster(int disasterId);
    
    
    // Connection management
    void closeConnection();
}
