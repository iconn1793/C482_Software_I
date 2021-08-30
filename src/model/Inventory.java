package model;

import javafx.collections.ObservableList;

public class Inventory {

    // VARIABLES //
    private ObservableList<Part> _allParts;
    private ObservableList<Product> _allProducts;

    // MODIFYING FUNCTIONS //
    public void addPart(Part newPart) {
        _allParts.add(newPart);
    }
    public void addProduct(Product newProduct) {
        _allProducts.add(newProduct);
    }
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
    public void updatePart(int index, Part selectedPart) {
        // remove a part
        // add updated part
    }
    public void updateProduct(int index, Product newProduct) {

    }
    public boolean deletePart(Part selectedPart) {
        try {
            return _allParts.removeIf( (p) -> p.getId() == selectedPart.getId() );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
//    public boolean deleteProduct(Product selectedProduct) {
//
//    }
    public ObservableList<Part> getAllParts() {
        return _allParts;
    }
    public ObservableList<Product> getAllProducts() {
        return _allProducts;
    }
}
