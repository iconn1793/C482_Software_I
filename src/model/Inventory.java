package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A public static data structure for maintaining accurate inventory data.
 */
public class Inventory {

    // VARIABLES //
    private static ObservableList<Part> _allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> _allProducts = FXCollections.observableArrayList();
    private static int partIndex = 0;
    private static int productIndex = 0;

    // MODIFYING FUNCTIONS //
    /**
     * Adds a new part the inventory list.
     * @param newPart The part to be added.
     */
    public static void addPart(Part newPart) {
        _allParts.add(newPart);
    }

    /**
     * Adds a new product the inventory list.
     * @param newProduct The product to be added.
     */
    public static void addProduct(Product newProduct) { _allProducts.add(newProduct); }

    /**
     * Returns the first part in inventory with a matching ID.
     * @param partId A number used to find an existing part with a matching ID value.
     * @return The first part in inventory found with an ID matching the search parameter.
     */
    public static Part lookupPart(int partId) {
        for (Part p : _allParts) {
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }
    /**
     * Returns the first product in inventory with a matching ID.
     * @param productId A number used to find an existing product with a matching ID value.
     * @return The first product in inventory found with an ID matching the search parameter.
     */
    public static Product lookupProduct(int productId) {
        for (Product p : _allProducts) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns an array of parts from the inventory with a name containing or matching the search parameter.
     * @param partName A string used to find existing parts with names containing or matching it.
     * @return An array of parts in inventory found with names containing or matching the search parameter.
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        for (Part p : _allParts) {
            if (p.getName().contains(partName)) {
                searchResults.add(p);
            }
        }
        return searchResults;
    }

    /**
     * Returns an array of products from the inventory with a name containing or matching the search parameter.
     * @param productName A string used to find existing products with names containing or matching it.
     * @return An array of products in inventory found with names containing or matching the search parameter.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        for (Product p : _allProducts) {
            if (p.getName().contains(productName)) {
                searchResults.add(p);
            }
        }
        return searchResults;
    }

    /**
     * Replaces a part at a given index with another part.
     * @param index The inventory index to remove an existing part, if any.
     * @param selectedPart The new part to replace at the index point.
     */
    public static void updatePart(int index, Part selectedPart) {
        try {
            _allParts.remove(index);
            _allParts.add(selectedPart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Replaces a product at a given index with another product.
     * @param index The inventory index to remove an existing product, if any.
     * @param newProduct The new product to replace at the index point.
     */
    public static void updateProduct(int index, Product newProduct) {
        try {
            _allProducts.remove(index);
            _allProducts.add(newProduct);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes all parts in inventory with ids matching that of the part given in the parameter.
     * @param selectedPart The part to find and remove from inventory.
     * @return Returns TRUE if successfully deleted one or more parts, else FALSE.
     */
    public static boolean deletePart(Part selectedPart) {
        try {
            return _allParts.removeIf( (p) -> p.getId() == selectedPart.getId() );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * Deletes all products in inventory with ids matching that of the product given in the parameter.
     * @param selectedProduct The product to find and remove from inventory.
     * @return Returns TRUE if successfully deleted one or more products, else FALSE.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        try {
            return _allProducts.removeIf( (p) -> p.getId() == selectedProduct.getId() );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Returns the full list of parts in inventory.
     * @return The full list of parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return _allParts;
    }

    /**
     * Returns the full list of products in inventory.
     * @return The full list of products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return _allProducts;
    }

    /**
     * Returns the next usable part index to maintain uniqueness.
     * @return The next usable part index.
     */
    public static int getNextPartIndex() { return ++partIndex; }

    /**
     * Returns the next usable product index to maintain uniqueness.
     * @return The next usable product index.
     */
    public static int getNextProductIndex() { return ++productIndex; }
}
