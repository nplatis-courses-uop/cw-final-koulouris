package gr.uop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;
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
            ) 
            {
            ClientInfo clientInformation = (ClientInfo)fromClient.readObject();
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
             LocalDateTime now = LocalDateTime.now();  
                System.out.println(clientInformation.getVehicleType());
                System.out.println(clientInformation.getregNumber());
                System.out.println(clientInformation.getTotalCost());
                MoneyBook bookOfServices = new MoneyBook();
                bookOfServices.inputServiceInfo(clientInformation, now);

                
                
                //var label = new Label("Receit for Car");
                StackPane details = new StackPane();
                LinkedList<ClientInfo> q = new LinkedList();
                
                var scene = new Scene(details, 1024, 640);
                stage.setMinWidth(1024);
                stage.setMinHeight(640);
                stage.setMaxWidth(1920);
                stage.setMaxHeight(1080);
                stage.setTitle("Πρόγραμμα ταμείου");
                stage.setScene(scene);
                stage.show();
                do{//programm runnning
                    q.add(clientInformation);
                    VehicleOrder client = new VehicleOrder();
                    details.getChildren().add(client);
                    if(client.getCorrectButtonValue() == 1){//if radio button true for payment
                        client.addClientToBook(q, clientInformation, now);
                    }
                    else if(client.rbCancel == 2){
                        client.remClient(q, clientInformation);
                    }
                    if(q.isEmpty()){//end window
                        //have to set the window to not close unless the linked list is empty
                        break;
                    }
                }while(true);
                details.getChildren().add(clientInformation);
               
                
        }
        catch (IOException e) {
            System.out.println(e);
        }

       
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
