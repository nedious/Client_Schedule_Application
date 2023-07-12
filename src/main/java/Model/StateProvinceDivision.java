package Model;

public class StateProvinceDivision {
    private int stateProvinceDivisionID;
    private String stateProvinceDivisionName;
    private int countryID;

    // --------------- Constructors ------------ //
    public StateProvinceDivision(int stateProvinceDivisionID, String stateProvinceDivisionName, int countryID) {
        this.stateProvinceDivisionID = stateProvinceDivisionID;
        this.stateProvinceDivisionName = stateProvinceDivisionName;
        this.countryID = countryID;
    }

// --------------- Getters ------------ //

    public int getStateProvinceDivisionID() {
        return stateProvinceDivisionID;
    }

    public String getStateProvinceDivisionName() {
        return stateProvinceDivisionName;
    }

    public int getCountryID() {
        return countryID;
    }


    // --------------- Setters ------------ //


    public void setStateProvinceDivisionID(int stateProvinceDivisionID) {
        this.stateProvinceDivisionID = stateProvinceDivisionID;
    }

    public void setStateProvinceDivisionName(String stateProvinceDivisionName) {
        this.stateProvinceDivisionName = stateProvinceDivisionName;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
