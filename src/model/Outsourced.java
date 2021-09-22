package model;

/**
 * A subclass of Part for parts created externally.
 */
public class Outsourced extends Part {

    // VARIABLES //
    private String _companyName;

    // INITIALIZERS //
    /**
     * The constructor method. Calls super class constructor internally.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this._companyName = companyName;
    }

    // GETTERS //
    /**
     * Returns the company name of the external company that made the part.
     * @return The company name of the external company that made the part.
     */
    public String getCompanyName() {
        return _companyName;
    }

    // SETTERS //
    /**
     * Sets the company name of the external company that made the part.
     */
    public void setCompanyName(String _companyName) {
        this._companyName = _companyName;
    }
}
