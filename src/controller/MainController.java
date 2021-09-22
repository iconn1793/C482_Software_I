package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller class for the main menu which appears at the start of the application.
 */
public class MainController implements Initializable {

    // ** UI ELEMENTS ** //
    // GENERAL ELEMENTS //
    public Button mainExitButton;

    // PART ELEMENTS //
    public TextField partSearchbar;
    public TableView partTable;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;
    public Button partAddButton;
    public Button partModifyButton;
    public Button partDeleteButton;

    // PRODUCT ELEMENTS //
    public TextField productSearchbar;
    public TableView productTable;
    public TableColumn productIdColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryColumn;
    public TableColumn productPriceColumn;
    public Button productAddButton;
    public Button productModifyButton;
    public Button productDeleteButton;

    /**
     * This method overrides the superclass' initialization method and sets the Parts and Products database info into UI tables for user interaction.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // ** PART METHODS ** //
    /**
     * This method filters the parts table for parts that have a name or ID that contain or match a user input search query.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onPartSearch(ActionEvent actionEvent) {
        String searchTerm = partSearchbar.getText();
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        searchResults.addAll(Inventory.lookupPart(searchTerm));
        for (Part p : Inventory.getAllParts()
        ) {
            try {
                if (String.valueOf(p.getId()).contains(searchTerm)) {
                    searchResults.add(Inventory.lookupPart(Integer.parseInt(searchTerm)));
                }
            } catch (Exception e) {
                //ignore
            }
        }
        if (searchResults.size() != 0) {
            partTable.setItems(searchResults);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No parts found.");
            alert.showAndWait();
        }

    }

    /**
     * This method triggers when the Add Part button is pressed.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onAddPartBtn(ActionEvent actionEvent) {
        navigateToPartsForm(actionEvent);
    }

    /**
     * This method triggers when the Modify Part button is pressed.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onModifyPartBtn(ActionEvent actionEvent) {
        Part part = (Part)partTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }
        navigateToPartsForm(actionEvent, true, part);
    }

    /**
     * This method prompts the user with a confirmation dialogue to delete the selected part. This method triggers when the Delete Part button is pressed.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onDeletePartBtn(ActionEvent actionEvent) {
        Part part = (Part)partTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Part not deleted: No Part selected.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to permanently delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(part);
        }
    }

    // ** PRODUCT METHODS ** //
    /**
     * This method filters the products table for products that have a name or ID that contain or match a user input search query.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onProductSearch(ActionEvent actionEvent) {
        // RUNTIME ERROR: Initial implementation threw a runtime error when searching for a null query (""). Added
        // a try/catch block to handle and ignore.
        String searchTerm = productSearchbar.getText();
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        searchResults.addAll(Inventory.lookupProduct(searchTerm));
        for (Product p : Inventory.getAllProducts()
             ) {
            try {
                if (String.valueOf(p.getId()).contains(searchTerm)) {
                    searchResults.add(Inventory.lookupProduct(Integer.parseInt(searchTerm)));
                }
            } catch (Exception e) {
                //ignore
            }
        }
        if (searchResults.size() != 0) {
            productTable.setItems(searchResults);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No products found.");
            alert.showAndWait();
        }
    }

    /**
     * This method triggers when the Add Product button is pressed, and opens the add product form.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onAddProductBtn(ActionEvent actionEvent) {
        navigateToProductForm(actionEvent);
    }

    /**
     * This method triggers when the Modify Product button is pressed, and opens the modify product form.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onModifyProductBtn(ActionEvent actionEvent) {
        Product product = (Product)productTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }
        navigateToProductForm(actionEvent, true, product);
    }

    /**
     * This method triggers when the delete product button is pressed, and prompts the user with a confirmation dialogue to delete the selected Product.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onDeleteProductBtn(ActionEvent actionEvent) {
        Product product = (Product)productTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Product not deleted: No Product selected.");
            alert.showAndWait();
            return;
        }

        if (product.getAllAssociatedParts().size() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Product not deleted: Cannot delete a product with associated parts. Please disassociate the parts and try again.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to permanently delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(product);
        }
    }

    // ** NAVIGATION METHODS ** //
    /**
     * This method closes the application when the exit button is pressed.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onExitBtn(ActionEvent actionEvent) {
        System.out.println("Exit Button Pressed -- Closing Application");
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * This method navigates the user to either the Add or Modify Part form as specified.
     * @Param actionEvent The UI event that triggers the method call.
     * @Param isModifying A flag that opens the Modify Part form when TRUE, otherwise opens Add Part form.
     * @Param part The part to be modified, as is relevant. Nullable.
     */
    public void navigateToPartsForm(ActionEvent event, Boolean isModifying, Part part) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PartForm.fxml"));
            Parent root = loader.load();
            PartFormController partController = loader.getController();
            partController.setFormState(isModifying, part);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            if (isModifying) {
                stage.setTitle("Modify Part");
            } else {
                stage.setTitle("Add Part");
            }
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Overloaded method to make calling navigateToPartsForm() more convenient. This method opens the "Add Part" form only.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void navigateToPartsForm(ActionEvent event) {
        navigateToPartsForm(event, false, null);
    }

    /**
     * This method navigates the user to either the Add or Modify Product form as specified.
     * @Param actionEvent The UI event that triggers the method call.
     * @Param isModifying A flag that opens the Modify Product form when TRUE, otherwise opens Add Product form.
     * @Param part The product to be modified, as is relevant. Nullable.
     */
    public void navigateToProductForm(ActionEvent event, Boolean isModifying, Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProductForm.fxml"));
            Parent root = loader.load();
            ProductFormController productFormController = loader.getController();
            productFormController.setFormState(isModifying, product);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            if (isModifying) {
                stage.setTitle("Modify Product");
            } else {
                stage.setTitle("Add Product");
            }
            stage.setScene(new Scene(root, 1000, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Overloaded method to make calling navigateToProductForm() more convenient. This method opens the "Add Product" form only.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void navigateToProductForm (ActionEvent event) {
        navigateToProductForm(event, false, null);
    }
}
