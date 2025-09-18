package Model;

import java.time.LocalDate;

/**
 * Represents a user in the disaster management system.
 * This class encapsulates user information including authentication details,
 * personal information, and system role.
 *
 * @author Sagar Bhujel
 * student id 12256739
 */
public class User {
    private int userID;
    private String username;
    private String password;
    private String role;
    
    // Additional registration fields
    private String fullName;
    private int gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private String rePassword;
    private String email;
    
    /**
     * Constructs a new User with basic authentication information.
     *
     * @param userID   The unique identifier for the user.
     * @param username The user's username.
     * @param password The user's password.
     * @param role     The user's role in the system.
     */
    public User(int userID, String username, String password, String role){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Constructs a new User with full registration information.
     *
     * @param userID      The unique identifier for the user.
     * @param fullname    The user's full name.
     * @param username    The user's username.
     * @param gender      The user's gender (represented as an integer).
     * @param dateOfBirth The user's date of birth.
     * @param phoneNumber The user's phone number.
     * @param address     The user's address.
     * @param role        The user's role in the system.
     * @param email       The user's email address.
     * @param password    The user's password.
     */
    public User(int userID, String fullname, String username, int gender, LocalDate dateOfBirth, 
            String phoneNumber, String address, String role, String email, String password){
        this.userID = userID;
        this.fullName = fullname;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Gets the user's unique identifier.
     *
     * @return The user ID.
     */
    public int getUserID(){
        return userID;
    }
    
    /**
     * Sets the user's unique identifier.
     *
     * @param userID The new user ID to set.
     */
    public void setUserID(int userID){
        this.userID = userID;
    }
    
    /**
     * Gets the user's username.
     *
     * @return The username.
     */
    public String getUserName(){
        return username;
    }
    
    /**
     * Sets the user's username.
     *
     * @param username The new username to set.
     */
    public void setUserName(String username){
        this.username = username;
    }
    
    /**
     * Gets the user's password.
     *
     * @return The password.
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Sets the user's password.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * Gets the user's re-entered password (for confirmation during registration).
     *
     * @return The re-entered password.
     */
    public String getRePassword(){
        return rePassword;
    }
    
    /**
     * Sets the user's re-entered password.
     *
     * @param rePassword The re-entered password to set.
     */
    public void setRePassword(String rePassword){
        this.rePassword = rePassword;
    }
    
    /**
     * Gets the user's role in the system.
     *
     * @return The user's role.
     */
    public String getRole(){
        return role;
    }
    
    /**
     * Sets the user's role in the system.
     *
     * @param role The new role to set.
     */
    public void setRole(String role){
        this.role = role;
    }
    
    /**
     * Gets the user's email address.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the user's email address.
     *
     * @param email The new email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Attempts to log in the user.
     *
     * @return true if login is successful, false otherwise.
     */
    public boolean login() {
        // Implementation
        return false;
    }

    /**
     * Gets the user's full name.
     *
     * @return The full name.
     */
    public String getFullName() {
        return fullName;
    }
    
    /**
     * Sets the user's full name.
     *
     * @param fullName The new full name to set.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    /**
     * Gets the user's date of birth.
     *
     * @return The date of birth.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    /**
     * Sets the user's date of birth.
     *
     * @param dateOfBirth The new date of birth to set.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    /**
     * Gets the user's phone number.
     *
     * @return The phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * Sets the user's phone number.
     *
     * @param phoneNumber The new phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Gets the user's gender.
     *
     * @return The gender (represented as an integer).
     */
    public int getGender(){
        return gender;
    }
    
    /**
     * Sets the user's gender.
     *
     * @param gender The new gender to set (represented as an integer).
     */
    public void setGender(int gender){
        this.gender = gender;
    }
    
    /**
     * Gets the user's address.
     *
     * @return The address.
     */
    public String getAddress(){
        return address;
    }
    
    /**
     * Sets the user's address.
     *
     * @param address The new address to set.
     */
    public void setAddress(String address){
        this.address = address;
    }
    
    /**
     * Logs out the current user.
     */
    public void logout() {
        // Implementation
    }
    
    /**
     * Attempts to register the user in the system.
     *
     * @return true if registration is successful, false otherwise.
     */
    public boolean register() {
        // Implementation for user registration
        return false;
    }
    
}
