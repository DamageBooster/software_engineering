package Model;

import java.time.LocalDateTime;

/**
 * Represents a disaster report in a disaster management system.
 * This class encapsulates information about a specific disaster event, including
 * its type, location, severity, description, status, and timestamp.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class DisasterReport {
    private int reportID;
    private String disasterType;
    private String location;
    private int severity;
    private String description;
    private String status;
    private LocalDateTime timeStamp;

    /**
     * Constructs a new DisasterReport with the specified details.
     *
     * @param reportID      The unique identifier for this disaster report.
     * @param disasterType  The type of disaster (e.g., earthquake, flood).
     * @param location      The location where the disaster occurred.
     * @param severity      The severity level of the disaster (typically on a scale).
     * @param description   A detailed description of the disaster.
     * @param status        The current status of the disaster (e.g., active, resolved).
     * @param timeStamp     The date and time when the report was created or last updated.
     */
    public DisasterReport(int reportID, String disasterType, String location, int severity, String description, String status, LocalDateTime timeStamp){
        this.reportID = reportID;
        this.disasterType = disasterType;
        this.location = location;
        this.severity = severity;
        this.description = description;
        this.timeStamp = timeStamp;
        this.status = status;
    }
    
    /**
     * Gets the unique identifier of this disaster report.
     *
     * @return The report ID.
     */
    public int getReportID(){
        return reportID;
    }
    
    /**
     * Sets the unique identifier of this disaster report.
     *
     * @param reportID The new report ID to set.
     */
    public void setReportID(int reportID){
        this.reportID = reportID;
    }
    
    /**
     * Gets the type of disaster.
     *
     * @return The disaster type.
     */
    public String getDisasterType(){
        return disasterType;
    }
    
    /**
     * Sets the type of disaster.
     *
     * @param disasterType The new disaster type to set.
     */
    public void setDisasterType(String disasterType){
        this.disasterType = disasterType;
    }
    
    /**
     * Gets the location of the disaster.
     *
     * @return The disaster location.
     */
    public String getLocation(){
        return location;
    }
    
    /**
     * Sets the location of the disaster.
     *
     * @param location The new location to set.
     */
    public void setLocation(String location){
        this.location = location;
    }
    
    /**
     * Gets the severity level of the disaster.
     *
     * @return The severity level.
     */
    public int getSeverity(){
        return severity;
    }
    
    /**
     * Sets the severity level of the disaster.
     *
     * @param severity The new severity level to set.
     */
    public void setSeverity(int severity){
        this.severity = severity;
    }
    
    /**
     * Gets the detailed description of the disaster.
     *
     * @return The disaster description.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Sets the detailed description of the disaster.
     *
     * @param description The new description to set.
     */
    public void setDescription(String description){
        this.description = description;
    }
    
    /**
     * Gets the timestamp of when the report was created or last updated.
     *
     * @return The timestamp of the report.
     */
    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }
    
    /**
     * Sets the timestamp of when the report was created or last updated.
     *
     * @param timeStamp The new timestamp to set.
     */
    public void setTimeStamp(LocalDateTime timeStamp){
        this.timeStamp = timeStamp;
    }
    
    /**
     * Creates a new disaster report. This method is a placeholder and needs implementation.
     */
    public void createReport() {
        // Implementation
    }

    /**
     * Updates an existing disaster report. This method is a placeholder and needs implementation.
     */
    public void updateReport() {
        // Implementation
    }
    
    /**
     * Gets the current status of the disaster.
     *
     * @return The current status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the disaster.
     *
     * @param status The new status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Returns a string representation of the DisasterReport object.
     *
     * @return A string containing all the details of the disaster report.
     */
    @Override
    public String toString() {
        return "DisasterReport{" +
               "reportID=" + reportID +
               ", disasterType='" + disasterType + '\'' +
               ", location='" + location + '\'' +
               ", severity=" + severity +
               ", description='" + description + '\'' +
               ", timeStamp=" + timeStamp +
               ", status='" + status + '\'' +
               '}';
    }

    
}
