package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class for the Add and Modify Part form.
 */
public class PartFormController implements Initializable {
    // ** UI ELEMENTS ** //
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

    // ** INTERNAL ELEMENTS ** //
    private boolean _isModifyForm = false;
    private Part _part;

    /**
     * This method overrides the superclass' initialization method.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * This method determines whether to set the form UI as the "Add Part" or "Modify Part" UI according to the flag, and stores a reference to a part to modify if any.
     * @Param isModifyingPart A boolean to indicate whether to show the Modify Part form (when TRUE) or the Add Part form (when FALSE).
     * @Param part The part to be modified, if applicable.
     */
    public void setFormState(boolean isModifyingPart, Part part) {
        _isModifyForm = isModifyingPart;
        _part = part;
        if (_isModifyForm) {
            setModifyPartState();
        } else {
            setAddPartState();
        }
    }

    /**
     * This method sets the form state to InHouse when the InHouse radio button is selected.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onInHouseBtn(ActionEvent actionEvent) {
        setInHouseState();
    }

    /**
     * This method sets the form state to Outsourced when the Outsourced radio button is selected.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onOutsourcedBtn(ActionEvent actionEvent) {
        setOutsourcedState();
    }

    /**
     * This method triggers when the save button is pressed. It validates the user input and saves the new or modified part before navigating the user back to the main menu.
     * @Param actionEvent The UI event that triggers the method call.
     */
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
            navigateToMainMenu(actionEvent);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * This method is called when the cancel button is pressed. It closes the form and navigates the user back to the main menu without saving or updating the part.
     * @Param actionEvent The UI event that triggers the method call.
     */
    public void onCancelBtn(ActionEvent actionEvent) {
        navigateToMainMenu(actionEvent);
    }

    /**
     * This method navigates the user back to the main menu.
     * @Param actionEvent The UI event that triggers the method call.
     */
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

    // ** HELPER FUNCTIONS ** //
    /**
     * This method sets the UI for the Add Part state, defaults in to In House state.
     */
    private void setAddPartState() {
        partFormTitle.setText("Add Part");
        idTextField.setDisable(true);
        idTextField.setPromptText("Auto Gen. - Disabled");
        setInHouseState();
    }

    /**
     * This method sets the UI for the Modify Part state.
     */
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

    /**
     * This method sets the UI for the InHouse part state.
     */
    private void setInHouseState() {
        inHouseRadioButton.setSelected(true);
        outsourcedRadioButton.setSelected(false);
        flexibleLabel.setText("Machine ID");
    }

    /**
     * This method sets the UI for the Outsourced part state.
     */
    private void setOutsourcedState() {
        inHouseRadioButton.setSelected(false);
        outsourcedRadioButton.setSelected(true);
        flexibleLabel.setText("Company Name");
    }

    /**
     * This method creates and stores a new part with the data entered, as either InHouse or Outsourced part accordingly.
     */
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

    /**
     * This method updates an existing part with the data entered.
     */
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

    /**
     * This method validates all user inputs meet acceptable criteria or else throws an exception.
     * @throws IllegalArgumentException Throws an exception whenever a datum does not meet acceptable criteria.
     */
    private void validateInputs() throws IllegalArgumentException {
        // FU : Add validation on the flex field to enforce String or Number data type according to
        // InHouse or Outsourced form state. Additionally, requiring name as non-null may prevent later user error.
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
            throw new IllegalArgumentException("Invalid Input: Stock value must be between Min and Max values.");
        }
    }
}
