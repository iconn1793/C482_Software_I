package model;

public abstract class Part {

    // VARIABLES //
    private int _id;
    private String _name;
    private double _price;
    private int _stock;
    private int _min;
    private int _max;

    // INITIALIZERS //
    public Part(int id, String name, double price, int stock, int min, int max) {
        _id = id;
        _name = name;
        _price = price;
        _stock = stock;
        _min = min;
        _max = max;
    };

    // GETTERS //
    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public double getPrice() {
        return _price;
    }

    public int getStock() {
        return _stock;
    }

    public int getMin() {
        return _min;
    }

    public int getMax() {
        return _max;
    }

    // SETTERS //
    public void setId(int _id) {
        this._id = _id;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public void setPrice(double _price) {
        this._price = _price;
    }

    public void setStock(int _stock) {
        this._stock = _stock;
    }

    public void setMin(int _min) {
        this._min = _min;
    }

    public void setMax(int _max) {
        this._max = _max;
    }

}
