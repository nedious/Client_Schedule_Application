package Model;

/**
 * class: StateProvinceDivision. Encapsulates information about a StateProvinceDivision, identified by their stateProvinceDivisionID, stateProvinceDivisionName, and countryID.
 * */
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
    /**
     * Method: getStateProvinceDivisionID.
     * @return stateProvinceDivisionID
     * */
    public int getStateProvinceDivisionID() {
        return stateProvinceDivisionID;
    }

    /**
     * Method: getStateProvinceDivisionName.
     * @return stateProvinceDivisionName
     * */
    public String getStateProvinceDivisionName() {
        return stateProvinceDivisionName;
    }

    /**
     * Method: getCountryID.
     * @return countryID
     * */
    public int getCountryID() {
        return countryID;
    }
}
