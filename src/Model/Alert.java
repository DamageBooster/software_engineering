package Model;

import java.sql.Timestamp;

/**
 * Represents an alert in a disaster management system.
 * This class encapsulates information about an alert, including its unique identifier,
 * type, location, details, and timestamp.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class Alert {
    private int id;
    private String type;
    private String location;
    private String details;
    private Timestamp timestamp;

    /**
     * Constructs a new Alert with all fields specified.
     *
     * @param id        The unique identifier for the alert.
     * @param type      The type of the alert.
     * @param location  The location associated with the alert.
     * @param details   Additional details about the alert.
     * @param timestamp The timestamp when the alert was created or last updated.
     */
    public Alert(int id, String type, String location, String details, Timestamp timestamp) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.details = details;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a new Alert without an ID and timestamp.
     * This constructor is typically used for creating new alerts before they are assigned an ID and timestamp.
     *
     * @param type     The type of the alert.
     * @param location The location associated with the alert.
     * @param details  Additional details about the alert.
     */
    public Alert(String type, String location, String details) {
        this.type = type;
        this.location = location;
        this.details = details;
    }

    /**
     * Gets the unique identifier of the alert.
     *
     * @return The alert's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the alert.
     *
     * @param id The new ID to set for the alert.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of the alert.
     *
     * @return The alert's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the alert.
     *
     * @param type The new type to set for the alert.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the location associated with the alert.
     *
     * @return The alert's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location associated with the alert.
     *
     * @param location The new location to set for the alert.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the additional details of the alert.
     *
     * @return The alert's details.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the additional details of the alert.
     *
     * @param details The new details to set for the alert.
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Gets the timestamp of the alert.
     *
     * @return The alert's timestamp.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the alert.
     *
     * @param timestamp The new timestamp to set for the alert.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
