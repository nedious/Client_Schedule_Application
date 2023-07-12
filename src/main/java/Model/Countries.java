package Model;

public class Countries {
    private int countryID;
    private String countryName;

// ------------ Constructors ----------- //

    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    // ------------ Getters ----------- //
    public int getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    // ------------ Setters ----------- //


    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
