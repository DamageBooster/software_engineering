package Model;

import java.sql.Timestamp;
/**
 * Represents a strategic decision made in response to a disaster in the disaster management system.
 * This class encapsulates information about a strategic decision, including its unique identifier,
 * the associated disaster ID, the decision content, and the timestamp of when the decision was made.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class StrategicDecision {
    private int id;
    private int disasterId;
    private String decision;
    private Timestamp timestamp;

    /**
     * Constructs a new StrategicDecision with all fields specified.
     *
     * @param id         The unique identifier for this strategic decision.
     * @param disasterId The ID of the disaster associated with this decision.
     * @param decision   The content of the strategic decision.
     * @param timestamp  The date and time when the decision was made.
     */
    public StrategicDecision(int id, int disasterId, String decision, Timestamp timestamp) {
        this.id = id;
        this.disasterId = disasterId;
        this.decision = decision;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a new StrategicDecision without an ID and timestamp.
     * This constructor is typically used for creating new strategic decisions before they are assigned an ID and timestamp.
     *
     * @param disasterId The ID of the disaster associated with this decision.
     * @param decision   The content of the strategic decision.
     */
    public StrategicDecision(int disasterId, String decision) {
        this.disasterId = disasterId;
        this.decision = decision;
    }

    /**
     * Gets the unique identifier of the strategic decision.
     *
     * @return The strategic decision's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the strategic decision.
     *
     * @param id The new ID to set for the strategic decision.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the disaster associated with this strategic decision.
     *
     * @return The associated disaster's ID.
     */
    public int getDisasterId() {
        return disasterId;
    }

    /**
     * Sets the ID of the disaster associated with this strategic decision.
     *
     * @param disasterId The new disaster ID to set.
     */
    public void setDisasterId(int disasterId) {
        this.disasterId = disasterId;
    }

    /**
     * Gets the content of the strategic decision.
     *
     * @return The decision content.
     */
    public String getDecision() {
        return decision;
    }

    /**
     * Sets the content of the strategic decision.
     *
     * @param decision The new decision content to set.
     */
    public void setDecision(String decision) {
        this.decision = decision;
    }

    /**
     * Gets the timestamp of when the strategic decision was made.
     *
     * @return The decision's timestamp.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of when the strategic decision was made.
     *
     * @param timestamp The new timestamp to set for the decision.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
