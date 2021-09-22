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

/**
 * The controller class for the Add or Modify Product form.
 */
public class ProductFormController implements Initializable {
    // ** UI ELEMENTS ** //
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

    // ** INTERNAL ELEMENTS ** //
    private Boolean _isModifyForm = false;
    private Product _product;
    private ObservableList<Part> _displayedAssociatedParts = FXCollections.observableArrayList();

    /**
     * This method overrides the superclass' initialization method.
     */
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

    /**
     * This method determines whether to set the form UI as the "Add Product" or "Modify Product" UI according to the flag, and stores a reference to a product to modify if any.
     * @Param isModifyingProduct A boolean to indicate whether to show the Modify Part form (when TRUE) or the Add Part form (when FALSE).
     * @Param product The product to be modified, if applicable.
     */
    public void setFormState(boolean isModifyingProduct, Product product) {
        _isModifyForm = isModifyingProduct;
        _product = product;
        if (_isModifyForm) {
            setModifyProductState();
        } else {
            setAddProductState();
        }
    }

    /**
     * This method ASSOCIATES the selected part with the current product.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onAddPartBtn(ActionEvent actionEvent) {
        // FUTURE ENHANCEMENT: Restrict parts from being associated multiple times. Adding a "part count per product"
        // could allow more advanced production pricing calculation without duplicating memory usage to store the same objects.
        Part part = (Part)inventoryPartsTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        _displayedAssociatedParts.add(part);
    }

    /**
     * This method DISASSOCIATES the selected part with the current product.
     * @Param actionEvent The UI event that triggers the method call.
     */
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

    /**
     * This method validates the user input meets acceptable criteria (or presents an error dialogue if not), and saves a new or modified part as applicable.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onSaveBtn(ActionEvent actionEvent) {
        try {
            validateInputs();
            if (!_isModifyForm) {
                saveNewProduct();
            } else {
                saveModifiedProduct();
            }
            navigateToMainMenu(actionEvent);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    /**
     * This method closes the product form and navigates back to the main menu without saving the product changes.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onCancelBtn(ActionEvent actionEvent) {
        navigateToMainMenu(actionEvent);
    }

    /**
     * This method creates and saves a new product with the data currently entered into the UI fields.
     */
    private void saveNewProduct() {
        Product newProduct = new Product(Inventory.getNextProductIndex(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()));
        try {
            _displayedAssociatedParts.forEach( (part) -> {newProduct.addAssociatedPart(part);});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Inventory.addProduct(newProduct);
    }

    /**
     * This method saves any changes to a modified product with the data currently entered into the UI fields.
     */
    private void saveModifiedProduct() {
        _product.setName(nameTextField.getText());
        _product.setPrice(Double.parseDouble(priceTextField.getText()));
        _product.setStock(Integer.parseInt(inventoryTextField.getText()));
        _product.setMin(Integer.parseInt(minTextField.getText()));
        _product.setMax(Integer.parseInt(maxTextField.getText()));
        try {
            _product.getAllAssociatedParts().removeAll(_product.getAllAssociatedParts());
            _displayedAssociatedParts.forEach( (part) -> { _product.addAssociatedPart(part);});
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    // ** HELPER FUNCTIONS ** //
    /**
     * This method sets the UI to the Modify Product state and populates fields with existing Product Data.
     */
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

    /**
     * This method sets the UI to the Add Product state.
     */
    private void setAddProductState() {
        titleLabel.setText("Add Product");
        idTextField.setDisable(true);
        idTextField.setPromptText("Auto Gen. - Disabled");
    }

    /**
     * This method validates all user inputs meet acceptable criteria or else throws an exception.
     * @throws IllegalArgumentException Throws an exception whenever a datum does not meet acceptable criteria.
     */
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

    private void navigateToMainMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root, 1000, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method filters the visible parts in the inventory table to only the parts that have an ID or name that
     * matches or contains the user input search term, or else presents an error dialogue if not results are found.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onSearch(ActionEvent actionEvent) {
//        FUTURE ENHANCEMENT: Apply results filtering to the associated parts table as well as the inventory table
//        (in case users want to search for specific parts for a product with many associated parts).
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
        if (invSearchResults.size()  !=  0) {
            inventoryPartsTableView.setItems(invSearchResults);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No parts found.");
            alert.showAndWait();
        }
    }
}
