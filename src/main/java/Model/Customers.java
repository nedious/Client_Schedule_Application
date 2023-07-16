package Model;

/**
 * class: Customers. Encapsulates information about a Customers, identified by their customerID, customerName, address, postal code, country, state, and phone number.
 * */
public class Customers {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerCountry;
    private String stateProvinceDivisionID;

    private String customerPhoneNumber;


// ---------------- Constructors --------------//
    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerCountry, String stateProvinceDivisionID, String customerPhoneNumber) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;

        this.customerCountry = customerCountry;
        this.stateProvinceDivisionID = stateProvinceDivisionID;

        this.customerPhoneNumber = customerPhoneNumber;
    }

// ---------------- Getters --------------//

    /**
     * Method: getCustomerID.
     * @return customerID
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Method: getCustomerName.
     * @return customerName
     * */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Method: getCustomerAddress.
     * @return customerAddress
     * */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Method: getCustomerPostalCode.
     * @return customerPostalCode
     * */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Method: getStateProvinceDivisionID.
     * @return stateProvinceDivisionID
     * */
    public String getStateProvinceDivisionID() {
        return stateProvinceDivisionID;
    }

    /**
     * Method: getCustomerCountry.
     * @return customerCountry
     * */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     * Method: getCustomerPhoneNumber.
     * @return customerPhoneNumber
     * */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
}
