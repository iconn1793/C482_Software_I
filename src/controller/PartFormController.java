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
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.net.URL;
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
    private boolean _isModifyForm = false;
    private Part _part;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setFormState(boolean isModifyingPart, Part part) {
        _isModifyForm = isModifyingPart;
        _part = part;
        if (_isModifyForm) {
            setModifyPartState();
        } else {
            setAddPartState();
        }
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
            if (!_isModifyForm) {
                // FOR SAVING A NEW PART
                saveNewPart();
            } else {
                // FOR SAVING A MODIFIED PART
                saveModifiedPart();
            }
            navigateToMainMenu(actionEvent, "Main Menu");
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    private void setModifyPartState() {
        partFormTitle.setText("Modify Part");
        idTextField.setDisable(true);
        if (_part instanceof InHouse) {
            setInHouseState();
            InHouse inHousePart = (InHouse)_part;
            flexibleTextField.setText(String.valueOf(inHousePart.getMachineID()));
        } else {
            setOutsourcedState();
            Outsourced outsourcedPart = (Outsourced)_part;
            flexibleTextField.setText(outsourcedPart.getCompanyName());
        }
        idTextField.setPromptText(String.valueOf(_part.getId()));
        nameTextField.setText(_part.getName());
        inventoryTextField.setText(String.valueOf(_part.getStock()));
        priceTextField.setText(String.valueOf(_part.getPrice()));
        minTextField.setText(String.valueOf(_part.getMin()));
        maxTextField.setText(String.valueOf(_part.getMax()));
    }

    private void setInHouseState() {
        inHouseRadioButton.setSelected(true);
        outsourcedRadioButton.setSelected(false);
        flexibleLabel.setText("Machine ID");
    }

    private void setOutsourcedState() {
        inHouseRadioButton.setSelected(false);
        outsourcedRadioButton.setSelected(true);
        flexibleLabel.setText("Company Name");
    }

    private void saveNewPart() {
        if (inHouseRadioButton.isSelected()) {
            InHouse newPart = new InHouse(Inventory.getNextPartIndex(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()), Integer.parseInt(flexibleTextField.getText()));
            Inventory.addPart(newPart);
            System.out.println("Added InHouse Part");
        } else {
            Outsourced newPart = new Outsourced(Inventory.getNextPartIndex(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), Integer.parseInt(inventoryTextField.getText()), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()), flexibleTextField.getText());
            Inventory.addPart(newPart);
            System.out.println("Added Outsourced Part");
        }
    }

    private void saveModifiedPart() {
        _part.setName(nameTextField.getText());
        _part.setPrice(Double.parseDouble(priceTextField.getText()));
        _part.setStock(Integer.parseInt(inventoryTextField.getText()));
        _part.setMin(Integer.parseInt(minTextField.getText()));
        _part.setMax(Integer.parseInt(maxTextField.getText()));
        if (_part instanceof InHouse) {
            InHouse inHousePart = (InHouse)_part;
            inHousePart.setMachineID(Integer.parseInt(flexibleTextField.getText()));
        } else {
            Outsourced outsourcedPart = (Outsourced)_part;
            outsourcedPart.setCompanyName(flexibleTextField.getText());
        }
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
