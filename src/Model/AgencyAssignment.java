package Model;

import java.time.LocalDateTime;

/**
 * Represents an assignment of an agency to a disaster in a disaster management system.
 * This class encapsulates information about the assignment, including identifiers for the
 * assignment, agency, and disaster, as well as assignment details such as date and status.
 *
 * @author Sagar Bhujel
 * Student id 12256739
 */
public class AgencyAssignment {
    private int assignmentID;
    private int agencyID;
    private String agencyName;
    private int disasterID;
    private LocalDateTime assignmentDate;
    private String status;
    private String disasterType;
    
    /**
     * Constructs a new AgencyAssignment with the specified details.
     *
     * @param assignmentID   The unique identifier for this assignment.
     * @param agencyID       The ID of the assigned agency.
     * @param agencyName     The name of the assigned agency.
     * @param disasterID     The ID of the disaster to which the agency is assigned.
     * @param assignmentDate The date and time when the assignment was made.
     * @param status         The current status of the assignment.
     * @param disasterType   The type of disaster involved in this assignment.
     */
    public AgencyAssignment(int assignmentID, int agencyID, String agencyName, int disasterID, LocalDateTime assignmentDate, String status, String disasterType) {
        this.assignmentID = assignmentID;
        this.agencyID = agencyID;
        this.agencyName = agencyName;
        this.disasterID = disasterID;
        this.assignmentDate = assignmentDate;
        this.status = status;
        this.disasterType = disasterType;
    }

    /**
     * Gets the unique identifier of this assignment.
     *
     * @return The assignment ID.
     */
    public int getAssignmentID() {
        return assignmentID;
    }

    /**
     * Gets the ID of the assigned agency.
     *
     * @return The agency ID.
     */
    public int getAgencyID() {
        return agencyID;
    }

    /**
     * Gets the name of the assigned agency.
     *
     * @return The agency name.
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * Gets the ID of the disaster to which the agency is assigned.
     *
     * @return The disaster ID.
     */
    public int getDisasterID() {
        return disasterID;
    }

    /**
     * Gets the date and time when the assignment was made.
     *
     * @return The assignment date and time.
     */
    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }
    
    /**
     * Gets the current status of the assignment.
     *
     * @return The assignment status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the unique identifier of this assignment.
     *
     * @param assignmentID The new assignment ID to set.
     */
    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    /**
     * Sets the ID of the assigned agency.
     *
     * @param agencyID The new agency ID to set.
     */
    public void setAgencyID(int agencyID) {
        this.agencyID = agencyID;
    }

    /**
     * Sets the name of the assigned agency.
     *
     * @param agencyName The new agency name to set.
     */
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    /**
     * Sets the ID of the disaster to which the agency is assigned.
     *
     * @param disasterID The new disaster ID to set.
     */
    public void setDisasterID(int disasterID) {
        this.disasterID = disasterID;
    }

    /**
     * Sets the date and time when the assignment was made.
     *
     * @param assignmentDate The new assignment date and time to set.
     */
    public void setAssignmentDate(LocalDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    /**
     * Sets the current status of the assignment.
     *
     * @param status The new status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Gets the type of disaster involved in this assignment.
     *
     * @return The disaster type.
     */
    public String getDisasterType() {
        return disasterType;
    }  
    
    /**
     * Sets the type of disaster involved in this assignment.
     *
     * @param disasterType The new disaster type to set.
     */
    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }
}
