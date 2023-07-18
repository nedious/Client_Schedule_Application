package Model;

import java.time.*;

/**
 * class: Appointments. Encapsulates information about a Appointment, identified by apptID, apptTitle, description, location, type etc.
 * */
public class Appointments {
    private int apptID;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private LocalDate apptDayPicker;
    private LocalDateTime apptStartDateTime;
    private LocalDateTime apptEndDateTime;
    public int apptContactID;
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
    /**
     * Method: getApptID.
     * @return apptID
     * */
    public int getApptID() {
        return apptID;
    }

    /**
     * Method: getApptTitle.
     * @return apptTitle
     * */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * Method: getApptDescription.
     * @return apptDescription
     * */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     * Method: getApptLocation.
     * @return apptLocation
     * */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     * Method: getApptContactID.
     * @return apptContactID
     * */
    public int getApptContactID() {
        return apptContactID;
    }

    /**
     * Method: getApptType.
     * @return apptType
     * */
    public String getApptType() {
        return apptType;
    }

    /**
     * Method: getApptDayPicker.
     * @return apptDayPicker
     * */
    public LocalDate getApptDayPicker() {
        return apptDayPicker;
    }

    /**
     * Method: getApptStartDateTime.
     * @return apptStartDateTime
     * */
    public LocalDateTime getApptStartDateTime() {
        return apptStartDateTime;
    }

    /**
     * Method: getApptEndDateTime.
     * @return apptEndDateTime
     * */
    public LocalDateTime getApptEndDateTime() {
        return apptEndDateTime;
    }

    /**
     * Method: getApptCustomerID.
     * @return apptCustomerID
     * */
    public int getApptCustomerID() {
        return apptCustomerID;
    }

    /**
     * Method: getApptUserID.
     * @return apptUserID
     * */
    public int getApptUserID() {
        return apptUserID;
    }
}


