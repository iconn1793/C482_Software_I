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
//    public Part lookupPart(int partId) {
//
//    }
//    public Product lookupProduct(int productId) {
//
//    }
//    public ObservableList<Part> lookupPart(String partName){
//
//    }
//    public ObservableList<Product> lookupProduct(String productName) {
//
//    }
    public static void updatePart(int index, Part selectedPart) {
        // remove a part
        // add updated part
    }
    public static void updateProduct(int index, Product newProduct) {

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
