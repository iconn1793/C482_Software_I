package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    // VARIABLES //
    private static ObservableList<Part> _allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> _allProducts = FXCollections.observableArrayList();
    private static int partIndex = 0;
    private static int productIndex = 0;

    // MODIFYING FUNCTIONS //
    public static void addPart(Part newPart) {
        _allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) { _allProducts.add(newProduct); }

    public static Part lookupPart(int partId) {
        for (Part p : _allParts) {
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }
    public static Product lookupProduct(int productId) {
        for (Product p : _allProducts) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        for (Part p : _allParts) {
            if (p.getName().contains(partName)) {
                searchResults.add(p);
            }
        }
        return searchResults;
    }
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        for (Product p : _allProducts) {
            if (p.getName().contains(productName)) {
                searchResults.add(p);
            }
        }
        return searchResults;
    }
    public static void updatePart(int index, Part selectedPart) {
        try {
            _allParts.remove(index);
            _allParts.add(selectedPart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void updateProduct(int index, Product newProduct) {
        try {
            _allProducts.remove(index);
            _allProducts.add(newProduct);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static boolean deletePart(Part selectedPart) {
        try {
            return _allParts.removeIf( (p) -> p.getId() == selectedPart.getId() );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean deleteProduct(Product selectedProduct) {
        try {
            return _allProducts.removeIf( (p) -> p.getId() == selectedProduct.getId() );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static ObservableList<Part> getAllParts() {
        return _allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return _allProducts;
    }
    public static int getNextPartIndex() { return ++partIndex; }
    public static int getNextProductIndex() { return ++productIndex; }
}
