package gr.uop;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Client extends Application {

    public  ArrayList<Service> selectedServices;
    public  int totalCost;
    public  String vehicleType;

    @Override
    public void start(Stage stage) {
        Label l = new Label("Αριθμός κυκλοφορίας: ");
        TextField regNumber = new TextField();
        HBox info = new HBox(5);
        info.setPadding(new Insets(5));
        info.getChildren().addAll(l, regNumber);
        info.setAlignment(Pos.CENTER);
        l.setStyle("-fx-font-size: 20px;");
        regNumber.setStyle("-fx-font-size: 20px;");
        l.setAlignment(Pos.BASELINE_CENTER);
        regNumber.setAlignment(Pos.BOTTOM_CENTER);
        regNumber.setEditable(false);

        VirtualKeyboard VKeys = new VirtualKeyboard(regNumber);
        VKeys.setAlignment(Pos.CENTER);

        VBox mainPane = new VBox(5);
        mainPane.getChildren().addAll(info, VKeys);
        VBox.setVgrow(VKeys, Priority.ALWAYS);
        VBox.setVgrow(info, Priority.ALWAYS);

        var scene = new Scene(mainPane, 1024, 640);
        stage.setScene(scene);
        stage.setMinWidth(1024);
        stage.setMinHeight(640);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
        stage.setTitle("Πρόγραμμα υποδοχής οχημάτων");
        stage.show();

        /*functionality*/
        ServicesDialog options = new ServicesDialog(stage);
        VKeys.addEnterAction((e)->{
            var res = options.showAndWait();
            
            System.out.println(options.getVehicleType());
        });

        /***************/
    }

    public static void main(String[] args) {
        launch(args);
    }

}
