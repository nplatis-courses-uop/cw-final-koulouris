package gr.uop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;    

/**
 * JavaFX App
 */
public class Server extends Application {

    @Override
    public void start(Stage stage) throws ClassNotFoundException {

         try (ServerSocket serverSocket = new ServerSocket(7777);
             Socket connectionSocket = serverSocket.accept();
             ObjectInputStream fromClient = new ObjectInputStream(connectionSocket.getInputStream());
             ) {
                ClientInfo clientInfo = (ClientInfo)fromClient.readObject();

                System.out.println(clientInfo.getVehicleType());
                System.out.println(clientInfo.getregNumber());
                System.out.println(clientInfo.getTotalCost());
                
        }
        catch (IOException e) {
            System.out.println(e);
        }

        var label = new Label("Hello, JavaFX Server");
        var scene = new Scene(new StackPane(label), 1024, 640);
        stage.setMinWidth(1024);
        stage.setMinHeight(640);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
        stage.setTitle("Πρόγραμμα ταμείου");
        stage.setScene(scene);
        stage.show();
    }
    /*public DateTimeFormatter getTimeAndDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return ;
        System.out.println(dtf.format(now)); 
    }*/


    public static void main(String[] args) {
        launch(args);
    }

}
