package gr.uop;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Client extends Application {
    private Socket clientSocket;
    private ObjectOutputStream toServer;

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
            Optional<String> res = options.showAndWait();
            if(res.get().equalsIgnoreCase("SEND")){
                    ClientInfo toSend = new ClientInfo(options.getVehicleType(), regNumber.getText(), options.getSelectedServices());
                    try{
                        clientSocket = new Socket("localhost", 7777);
                        toServer = new ObjectOutputStream(clientSocket.getOutputStream());

                    }catch(IOException io){
                        System.out.println("Error connecting to server.");
                        System.exit(0);
                    }
                    if(clientSocket.isClosed()){
                        System.out.println("socket is closed");
                    }else{
                        try{
                            toServer.writeObject(toSend);
                            options.clear();
                            regNumber.clear();
                        }catch(IOException io){
                            System.out.println("87 "+io.getMessage());
                        }
                    }
            }else{
                Alert cancel = new Alert(AlertType.ERROR);
                cancel.setHeaderText("Ακυρώθηκε απο τον χρήστη.");
                cancel.initOwner(stage);
                cancel.show();
            }
        });

       

        /***************/
    }

    public static void main(String[] args) {
        launch(args);
    }

}
