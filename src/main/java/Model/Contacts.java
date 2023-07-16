package Model;

/**
 * class: Contacts. Encapsulates information about a contact, identified by their contactID and contactName.
 * */
public class Contacts {
    private int contactID;
    private String contactName;

    // ------------ Constructors ----------- //
    // The constructor initializes the contactID and contactName instance variables with the values provided as arguments.
    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    // ------------ Getters ----------- //
    // getter methods for accessing the values of the contactID and contactName variables. The getter methods (getContactID() and getContactName()) allow other parts of the code to retrieve the values of these variables, providing read-only access to the contact information.

    /**
     * Method: getContactID.
     * @return contactID
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * Method: getContactName.
     * @return contactName
     * */
    public String getContactName() {
        return contactName;
    }
}