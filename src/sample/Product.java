package sample;

import javafx.collections.ObservableList;

public class Product {

    // VARIABLES //
    private ObservableList<Part> _associatedParts;
    private int _id;
    private String _name;
    private double _price;
    private int _stock;
    private int _min;
    private int _max;

    // MODIFYING FUNCTIONS //
    public void addAssociatedPart(Part part) {
        // TODO Implement
    }

    public Boolean deleteAssocaitedPart(Part selectedAssociatedPart) {
        // TODO Implement
        return true;
    }

    // GETTERS //
    public ObservableList<Part> getAllAssociatedParts() {
        return _associatedParts;
    }

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
