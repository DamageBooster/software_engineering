package Model;

import java.time.LocalDateTime;
/**
 * Represents a notification in the disaster management system.
 * This class encapsulates information about a notification, including its unique identifier,
 * recipient type, title, content, and timestamp.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class Notification {
    private int id;
    private String recipientType;
    private String title;
    private String content;
    private LocalDateTime timestamp;

    /**
     * Constructs a new Notification with all fields specified.
     *
     * @param id            The unique identifier for the notification.
     * @param recipientType The type of recipient for this notification.
     * @param title         The title of the notification.
     * @param content       The content or body of the notification.
     * @param timestamp     The date and time when the notification was created.
     */
    public Notification(int id, String recipientType, String title, String content, LocalDateTime timestamp) {
        this.id = id;
        this.recipientType = recipientType;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a new Notification without an ID and timestamp.
     * This constructor is typically used for creating new notifications before they are assigned an ID.
     * The timestamp is automatically set to the current date and time.
     *
     * @param recipientType The type of recipient for this notification.
     * @param title         The title of the notification.
     * @param content       The content or body of the notification.
     */
    public Notification(String recipientType, String title, String content) {
        this.recipientType = recipientType;
        this.title = title;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the unique identifier of the notification.
     *
     * @return The notification's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the notification.
     *
     * @param id The new ID to set for the notification.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the recipient type of the notification.
     *
     * @return The recipient type.
     */
    public String getRecipientType() {
        return recipientType;
    }

    /**
     * Sets the recipient type of the notification.
     *
     * @param recipientType The new recipient type to set.
     */
    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    /**
     * Gets the title of the notification.
     *
     * @return The notification's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the notification.
     *
     * @param title The new title to set for the notification.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the content of the notification.
     *
     * @return The notification's content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the notification.
     *
     * @param content The new content to set for the notification.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the timestamp of when the notification was created.
     *
     * @return The notification's timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of when the notification was created.
     *
     * @param timestamp The new timestamp to set for the notification.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
