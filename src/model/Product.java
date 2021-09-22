package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class stores relevant information about a product and its associated parts.
 */
public class Product {

    // VARIABLES //
    private ObservableList<Part> _associatedParts = FXCollections.observableArrayList();
    private int _id;
    private String _name;
    private double _price;
    private int _stock;
    private int _min;
    private int _max;

    /**
     * The classes constructor method.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        _id = id;
        _name = name;
        _price = price;
        _stock = stock;
        _min = min;
        _max = max;
    }

    // MODIFYING FUNCTIONS //
    /**
     * This method associates a part with a product.
     * @Param part The part to be associated.
     */
    public void addAssociatedPart(Part part) {
        _associatedParts.add(part);
    }

    /**
     * This method disassociates any parts with a given Id from a product.
     * @Param part The part type to be disassociated.
     * @return Returns TRUE if one or more parts were found and disassociated, else FALSE.
     */
    public Boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        try {
            return _associatedParts.removeIf( (p) -> p.getId() == selectedAssociatedPart.getId() );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // GETTERS //
    /**
     * This method returns an array of all parts associated with a product.
     * @return Returns an array of all parts associated with a product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return _associatedParts;
    }

    /**
     * Fetches product Id.
     * @return Returns the product Id.
     */
    public int getId() {
        return _id;
    }

    /**
     * Fetches product name.
     * @return Returns the product name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Fetches product price/cost per unit.
     * @return Returns the product price/cost per unit.
     */
    public double getPrice() {
        return _price;
    }

    /**
     * Fetches the number of product currently in inventory.
     * @return Returns the number of product currently in inventory.
     */
    public int getStock() {
        return _stock;
    }

    /**
     * Fetches the minimum number of product required to be in inventory.
     * @return Returns the minimum number of product required to be in inventory.
     */
    public int getMin() {
        return _min;
    }

    /**
     * Fetches the maximum number of product able to be in inventory.
     * @return Returns the maximum number of product able to be in inventory.
     */
    public int getMax() {
        return _max;
    }

    // SETTERS //
    /**
     * Sets product Id.
     * @param _id  The product id to be set.
     */
    public void setId(int _id) {
        this._id = _id;
    }

    /**
     * Sets product name.
     * @param _name  The product name to be set.
     */
    public void setName(String _name) {
        this._name = _name;
    }

    /**
     * Sets product price/cost per unit.
     * @param _price The product price/cost per unit to be set.
     */
    public void setPrice(double _price) {
        this._price = _price;
    }

    /**
     * Sets the number of product currently in inventory.
     * @param _stock The number of product currently in inventory to be set.
     */
    public void setStock(int _stock) {
        this._stock = _stock;
    }

    /**
     * Sets the minimum number of product required to be in inventory.
     * @param _min The minimum number of product required to be in inventory.
     */
    public void setMin(int _min) {
        this._min = _min;
    }

    /**
     * Sets the maximum number of product required to be in inventory.
     * @param _max The minimum number of product required to be in inventory.
     */
    public void setMax(int _max) {
        this._max = _max;
    }
}
