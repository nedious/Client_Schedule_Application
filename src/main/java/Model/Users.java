package Model;

/**
 * class: Users. Encapsulates information about a Users, identified by their userID, username, and userPassword.
 * */
public class Users {
    public int userID;
    public String username;
    public String userPassword;

// --------------- Getters ------------ //
    /**
     * Method: getUserID.
     * @return the userID
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Method: getUsername.
     * @return the userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method: getUserPassword.
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }
}
