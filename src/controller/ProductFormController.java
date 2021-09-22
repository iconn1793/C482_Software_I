package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {
    public Label titleLabel;
    public TextField searchTextField;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;
    public Button addPartBtn;
    public TableColumn assocPartIdColumn;
    public TableColumn assocPartNameColumn;
    public TableColumn assocInventoryColumn;
    public TableColumn assocPriceColumn;
    public Button removePartButton;
    public Button saveBtn;
    public Button cancelBtn;
    public TableView inventoryPartsTableView;
    public TableView associatedPartsTableView;

    private Boolean _isModifyForm = false;
    private Product _product;
    private ObservableList<Part> _displayedAssociatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inventoryPartsTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(_displayedAssociatedParts);
        assocPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void setFormState(boolean isModifyingPart, Product product) {
        _isModifyForm = isModifyingPart;
        _product = product;
        if (_isModifyForm) {
            setModifyProductState();
        } else {
            setAddProductState();
        }
    }

    public void onAddPartBtn(ActionEvent actionEvent) {
        Part part = (Part)inventoryPartsTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        _displayedAssociatedParts.add(part);
    }

    public void onRemovePartBtn(ActionEvent actionEvent) {
        Part part = (Part)associatedPartsTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            _displayedAssociatedParts.removeIf( (p) -> p.getId() == part.getId() );
        }
    }


    public void onSaveBtn(ActionEvent actionEvent) {
        try {
            validateInputs();
            if (!_isModifyForm) {
                saveNewProduct();
            } else {
                saveModifiedProduct();
            }
            navigateToMainMenu(actionEvent, "Main Menu");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void onCancelBtn(ActionEvent actionEvent) {
        navigateToMainMenu(actionEvent, "Main Menu");
    }

    private void saveNewProduct() {
        Product newProduct = new Product(Inventory.getNextProductIndex(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()));
        try {
            _displayedAssociatedParts.forEach( (part) -> {newProduct.addAssociatedPart(part);});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Inventory.addProduct(newProduct);
    }

    private void saveModifiedProduct() {
        _product.setName(nameTextField.getText());
        _product.setPrice(Double.parseDouble(priceTextField.getText()));
        _product.setStock(Integer.parseInt(inventoryTextField.getText()));
        _product.setMin(Integer.parseInt(minTextField.getText()));
        _product.setMax(Integer.parseInt(maxTextField.getText()));
        try {
            _product.getAllAssociatedParts().removeAll();
            _displayedAssociatedParts.forEach( (part) -> {_product.addAssociatedPart(part);});
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    // Helper functions
    private void setModifyProductState() {
        _displayedAssociatedParts.setAll(_product.getAllAssociatedParts());
        titleLabel.setText("Modify Product");
        idTextField.setDisable(true);
        idTextField.setPromptText(String.valueOf(_product.getId()));
        nameTextField.setText(_product.getName());
        inventoryTextField.setText(String.valueOf(_product.getStock()));
        priceTextField.setText(String.valueOf(_product.getPrice()));
        minTextField.setText(String.valueOf(_product.getMin()));
        maxTextField.setText(String.valueOf(_product.getMax()));
    }
    private void setAddProductState() {
        titleLabel.setText("Add Product");
        idTextField.setDisable(true);
        idTextField.setPromptText("Auto Gen. - Disabled");
    }

    private void validateInputs() throws IllegalArgumentException {
        Float maxValue;
        Float minValue;
        Float stockValue;

        try {
            Float.isNaN(Float.parseFloat(maxTextField.getText()));
            maxValue = Float.parseFloat(maxTextField.getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Input: Max value must be an integer");
        }

        try {
            Float.isNaN(Float.parseFloat(minTextField.getText()));
            minValue = Float.parseFloat(minTextField.getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Input: Min value must be an integer");
        }

        try {
            Float.isNaN(Float.parseFloat(inventoryTextField.getText()));
            stockValue = Float.parseFloat(inventoryTextField.getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Input: Inventory value must be an integer");
        }

        try {
            Float.isNaN(Float.parseFloat(priceTextField.getText()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Input: Price value must be a number");
        }

        if (minValue > maxValue) {
            throw new IllegalArgumentException("Invalid Input: Min value must be less than Max value.");
        }

        if ( stockValue > maxValue || stockValue < minValue) {
            throw new IllegalArgumentException("Invalid Input: Inventory value must be between Min and Max values.");
        }
    }

    private void navigateToMainMenu(ActionEvent event, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 1000, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Todo: Future improvement to search through associated parts table
    public void onSearch(ActionEvent actionEvent) {
        String searchTerm = searchTextField.getText();
        ObservableList<Part> invSearchResults = FXCollections.observableArrayList();
        invSearchResults.addAll(Inventory.lookupPart(searchTerm));
        for (Part p : Inventory.getAllParts()
        ) {
            try {
                if (String.valueOf(p.getId()).contains(searchTerm)) {
                    invSearchResults.add(Inventory.lookupPart(Integer.parseInt(searchTerm)));
                }
            } catch (Exception e) {
                //ignore
            }
        }
        inventoryPartsTableView.setItems(invSearchResults);
    }
}
