
package Model;

import java.time.LocalDateTime;

/**
 * Represents a message in the disaster response communication system.
 * This class encapsulates information about a message, including its unique identifier,
 * sender, recipient, subject, content, and timestamp.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class Message {
    private int messageID;
    private String from;
    private String to;
    private String subject;
    private String content;
    private LocalDateTime timestamp;

    /**
     * Constructs a new Message with the specified details.
     *
     * @param messageID The unique identifier for this message.
     * @param from      The sender of the message.
     * @param to        The recipient of the message.
     * @param subject   The subject line of the message.
     * @param content   The main content of the message.
     * @param timestamp The date and time when the message was sent.
     */
    public Message(int messageID, String from, String to, String subject, String content, LocalDateTime timestamp) {
        this.messageID = messageID;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.timestamp = timestamp;
    }

    /**
     * Gets the unique identifier of the message.
     *
     * @return The message ID.
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * Gets the sender of the message.
     *
     * @return The sender's identifier.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets the recipient of the message.
     *
     * @return The recipient's identifier.
     */
    public String getTo() {
        return to;
    }

    /**
     * Gets the subject of the message.
     *
     * @return The message subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the timestamp of when the message was sent.
     *
     * @return The message timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the unique identifier of the message.
     *
     * @param messageID The new message ID to set.
     */
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    /**
     * Sets the sender of the message.
     *
     * @param from The new sender to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Sets the recipient of the message.
     *
     * @param to The new recipient to set.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Sets the subject of the message.
     *
     * @param subject The new subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Sets the content of the message.
     *
     * @param content The new content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets the timestamp of when the message was sent.
     *
     * @param timestamp The new timestamp to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
