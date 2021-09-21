package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();

        Inventory.addPart(new Outsourced(0, "testName", 100, 2, 1, 45, "Dovahkin"));
        Inventory.addProduct(new Product(0, "testProd", 1000, 20, 1, 30));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
