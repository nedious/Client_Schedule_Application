package Model;

import java.time.LocalDateTime;

public class Appointments {

    private int apptID;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    public int apptContactID;
    private String apptType;
    private LocalDateTime apptStartDateTime;
    private LocalDateTime apptEndDateTime;
    public int apptCustomerID;
    public int apptUserID;

// ----------------- Constructors --------------- //

    public Appointments(int apptID, String apptTitle, String apptDescription, String apptLocation, int apptContactID, String apptType, LocalDateTime apptStartDateTime, LocalDateTime apptEndDateTime, int apptCustomerID, int apptUserID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptContactID = apptContactID;
        this.apptType = apptType;
        this.apptStartDateTime = apptStartDateTime;
        this.apptEndDateTime = apptEndDateTime;
        this.apptCustomerID = apptCustomerID;
        this.apptUserID = apptUserID;
    }

// ----------------- Getters --------------- //
    public int getApptID() {
        return apptID;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public String getApptDescription() {
        return apptDescription;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public int getApptContactID() {
        return apptContactID;
    }

    public String getApptType() {
        return apptType;
    }

    public LocalDateTime getApptStartDateTime() {
        return apptStartDateTime;
    }

    public LocalDateTime getApptEndDateTime() {
        return apptEndDateTime;
    }

    public int getApptCustomerID() {
        return apptCustomerID;
    }

    public int getApptUserID() {
        return apptUserID;
    }

// ----------------- Setters --------------- //


    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public void setApptContactID(int apptContactID) {
        this.apptContactID = apptContactID;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public void setApptStartDateTime(LocalDateTime apptStartDateTime) {
        this.apptStartDateTime = apptStartDateTime;
    }

    public void setApptEndDateTime(LocalDateTime apptEndDateTime) {
        this.apptEndDateTime = apptEndDateTime;
    }

    public void setApptCustomerID(int apptCustomerID) {
        this.apptCustomerID = apptCustomerID;
    }

    public void setApptUserID(int apptUserID) {
        this.apptUserID = apptUserID;
    }
}


