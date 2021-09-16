package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Part;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class PartFormController implements Initializable {
    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField flexibleTextField;
    public TextField minTextField;
    public Button saveButton;
    public Button cancelButton;
    public Label flexibleLabel;
    public Label partFormTitle;

    public static LinkedList<Part> partsList = new LinkedList<Part>();
    private static int partIndex = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAddPartState();
    }

    public void onInHouseBtn(ActionEvent actionEvent) {
        setInHouseState();
    }

    public void onOutsourcedBtn(ActionEvent actionEvent) {
        setOutsourcedState();
    }

    public void onSaveBtn(ActionEvent actionEvent) {
        try {
            validateInputs();
            //create/save/modify part
            navigateToMainMenu(actionEvent, "Main Menu");
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

    }

    public void onCancelBtn(ActionEvent actionEvent) {
        navigateToMainMenu(actionEvent, "Main Menu");
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

    // HELPER FUNCTIONS
    private void setAddPartState() {
        partFormTitle.setText("Add Part");
        idTextField.setDisable(true);
        idTextField.setPromptText("Auto Gen. - Disabled");
        setInHouseState();
    }

    private void setInHouseState() {
        outsourcedRadioButton.setSelected(false);
        inHouseRadioButton.setSelected(true);
        flexibleLabel.setText("Machine ID");
    }

    private void setOutsourcedState() {
        outsourcedRadioButton.setSelected(true);
        inHouseRadioButton.setSelected(false);
        flexibleLabel.setText("Company Name");
    }

    private void validateInputs() throws IllegalArgumentException {
        Float maxValue;
        Float minValue;

        try {
            Float.isNaN(Float.parseFloat(maxTextField.getText()));
            maxValue = Float.parseFloat(maxTextField.getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Input: Max value must be an integer");
        }

        try {
            Float.isNaN(Float.parseFloat(maxTextField.getText()));
            minValue = Float.parseFloat(minTextField.getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Input: Min value must be an integer");
        }

        if (minValue > maxValue) {
            throw new IllegalArgumentException("Invalid Input: Min value must be less than Max value.");
        }

    }
}
