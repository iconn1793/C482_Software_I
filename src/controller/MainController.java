package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Button mainExitButton;

    // PART ELEMENTS
    public TextField partSearchbar;
    public TableView partTable;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;
    public Button partAddButton;
    public Button partModifyButton;
    public Button partDeleteButton;

    // PRODUCT ELEMENTS
    public TextField productSearchbar;
    public TableView productTable;
    public TableColumn productIdColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryColumn;
    public TableColumn productPriceColumn;
    public Button productAddButton;
    public Button productModifyButton;
    public Button productDeleteButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // PART METHODS
    public void onPartSearch(ActionEvent actionEvent) {
        System.out.println("Searched!");
    }

    public void onAddPartBtn(ActionEvent actionEvent) {
        navigateToPartsForm(actionEvent, "Add Part");
    }

    public void onModifyPartBtn(ActionEvent actionEvent) {
    }

    public void onDeletePartBtn(ActionEvent actionEvent) {
    }

    // PRODUCT METHODS
    public void onProductSearch(ActionEvent actionEvent) {
    }

    public void onAddProductBtn(ActionEvent actionEvent) {
    }

    public void onModifyProductBtn(ActionEvent actionEvent) {
    }

    public void onDeleteProductBtn(ActionEvent actionEvent) {
    }

    // NAVIGATION METHODS
    public void onExitBtn(ActionEvent actionEvent) {
        System.out.println("Exit Button Pressed -- Closing Application");
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    // NAV HELPERS
    public void navigateToPartsForm(ActionEvent event, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/PartForm.fxml"));
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
