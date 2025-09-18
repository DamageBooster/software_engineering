package Model;

/**
 * Represents an agency involved in disaster management.
 * This class encapsulates information about an agency, including its unique identifier,
 * name, and type.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class Agency {
    private int id;
    private String name;
    private String type;

    /**
     * Constructs a new Agency with a specified ID, name, and type.
     *
     * @param id   The unique identifier for the agency.
     * @param name The name of the agency.
     * @param type The type or category of the agency.
     */
    public Agency(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * Constructs a new Agency with a specified name and type.
     * This constructor is typically used for creating new agencies before they are assigned an ID.
     *
     * @param name The name of the agency.
     * @param type The type or category of the agency.
     */
    public Agency(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the unique identifier of the agency.
     *
     * @return The agency's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the agency.
     *
     * @param id The new ID to set for the agency.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the agency.
     *
     * @return The agency's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the agency.
     *
     * @param name The new name to set for the agency.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type or category of the agency.
     *
     * @return The agency's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type or category of the agency.
     *
     * @param type The new type to set for the agency.
     */
    public void setType(String type) {
        this.type = type;
    }
}
