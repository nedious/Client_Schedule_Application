package Model;

public class Contacts {

    private int contactID;
    private String contactName;

    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

}