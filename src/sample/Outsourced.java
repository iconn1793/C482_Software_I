package sample;

public class Outsourced extends Part {

    // VARAIBLES //
    private String _companyName;

    // INITIALIZERS //
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this._companyName = companyName;
    }

    // GETTERS //
    public String getCompanyName() {
        return _companyName;
    }

    // SETTERS //
    public void setCompanyName(String _companyName) {
        this._companyName = _companyName;
    }
}
