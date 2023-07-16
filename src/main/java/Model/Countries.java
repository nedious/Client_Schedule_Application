package Model;

/**
 * class: Countries. Encapsulates information about a Countries, identified by their countryID and countryName.
 * */
public class Countries {
    private int countryID;
    private String countryName;

// ------------ Constructors ----------- //

    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    // ------------ Getters ----------- //
    /**
     * Method: getCountryID.
     * @return countryID
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Method: getCountryName.
     * @return countryName
     * */
    public String getCountryName() {
        return countryName;
    }
}
