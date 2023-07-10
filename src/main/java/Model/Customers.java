package Model;

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

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public String getStateProvinceDivisionID() {
        return stateProvinceDivisionID;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }


// ---------------- Setters --------------//


    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public void setStateProvinceDivisionID(String stateProvinceDivisionID) {
        this.stateProvinceDivisionID = stateProvinceDivisionID;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
}
