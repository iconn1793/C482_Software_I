package model;

/**
 * The abstract superclass for InHouse and Outsourced concrete classes.
 */
public abstract class Part {

    // VARIABLES //
    private int _id;
    private String _name;
    private double _price;
    private int _stock;
    private int _min;
    private int _max;

    // INITIALIZERS //
    /**
     * The superclass constructor method. Cannot be called directly as a member of an abstract class.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        _id = id;
        _name = name;
        _price = price;
        _stock = stock;
        _min = min;
        _max = max;
    };

    // GETTERS //
    /**
     * Returns the part Id.
     * @return The parts Id.
     */
    public int getId() {
        return _id;
    }

    /**
     * Returns the part name.
     * @return The parts name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the part price/cost per unit.
     * @return The parts price/cost per unit.
     */
    public double getPrice() {
        return _price;
    }

    /**
     * Returns the amount of parts currently in stock.
     * @return The amount of parts currently in stock.
     */
    public int getStock() {
        return _stock;
    }

    /**
     * Returns the minimum amount of parts required to be in stock.
     * @return The minimum amount of parts required to be in stock.
     */
    public int getMin() {
        return _min;
    }

    /**
     * Returns the maximum amount of parts able to be in stock.
     * @return The maximum amount of parts able to be in stock.
     */
    public int getMax() {
        return _max;
    }

    // SETTERS //
    /**
     * Sets the part Id.
     */
    public void setId(int _id) {
        this._id = _id;
    }

    /**
     * Sets the part name.
     */
    public void setName(String _name) {
        this._name = _name;
    }

    /**
     * Sets the price/cost per unit of the part.
     */
    public void setPrice(double _price) {
        this._price = _price;
    }

    /**
     * Sets the amount of parts currently in stock.
     */
    public void setStock(int _stock) {
        this._stock = _stock;
    }

    /**
     * Sets the minimum amount of parts required to be in stock.
     */
    public void setMin(int _min) {
        this._min = _min;
    }

    /**
     * Sets the maximum amount of parts able to be in stock.
     */
    public void setMax(int _max) {
        this._max = _max;
    }

}
