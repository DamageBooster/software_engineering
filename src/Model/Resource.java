package Model;

/**
 * Represents a resource in the disaster management system.
 * This class encapsulates information about a resource, including its unique identifier,
 * type, quantity, and current status.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class Resource {
    private int resourceID;
    private String type;
    private int quantity;
    private String status;
    
    /**
     * Constructs a new Resource with the specified details.
     *
     * @param resourceID The unique identifier for this resource.
     * @param type       The type of the resource.
     * @param quantity   The quantity of the resource available.
     * @param status     The current status of the resource.
     */
    public Resource(int resourceID, String type, int quantity, String status){
        this.resourceID = resourceID;
        this.type = type;
        this.quantity = quantity;
        this.status = status;
    }
    
    /**
     * Gets the unique identifier of the resource.
     *
     * @return The resource ID.
     */
    public int getResourceID(){
        return resourceID;
    }
    
    /**
     * Sets the unique identifier of the resource.
     *
     * @param resourceID The new resource ID to set.
     */
    public void setResourceID(int resourceID){
        this.resourceID = resourceID;
    }
    
    /**
     * Gets the type of the resource.
     *
     * @return The resource type.
     */
    public String getType(){
        return type;
    }
    
    /**
     * Sets the type of the resource.
     *
     * @param type The new type to set for the resource.
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * Allocates the resource. This method is a placeholder and needs implementation.
     */
    public void allocateResource() {
        // Implementation
    }

    /**
     * Gets the quantity of the resource.
     *
     * @return The quantity of the resource.
     */
    public int getQuantity(){
        return quantity;
    }
    
    /**
     * Sets the quantity of the resource.
     *
     * @param quantity The new quantity to set for the resource.
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    /**
     * Gets the current status of the resource.
     *
     * @return The resource status.
     */
    public String getStatus(){
        return status;
    }
    
    /**
     * Sets the current status of the resource.
     *
     * @param status The new status to set for the resource.
     */
    public void setStatus(String status){
        this.status = status;
    }
    
    /**
     * Updates the status of the resource. This method is a placeholder and needs implementation.
     */
    public void updateStatus() {
        // Implementation
    }

    // Getters and setters
}
