package gr.uop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.WindowConstants;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;   

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
                StackPane details = new StackPane();
                LinkedList<ClientInfo> q = new LinkedList();//πληροφοριες για το καθε οχημα
                TreeMap<ClientInfo,String> ordering = new TreeMap();//ClientInfo για το κλειδι και το String για το alert απο την κλαση VecicleOrder
                //test here
                        var label = new Label("No Services Right Now");
                        var scene = new Scene(new StackPane(label), 1024, 640);
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
                    ordering.add(clientInformation,client.getProc());               //εδω 
                    q.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//δεν βλεπει το χρόνο?
                    details.getChildren().add(client);                              //εδω
                    String ch=(String)client.getProc();
                    if(ch.contains("Pay")){//if radio button true for payment
                        client.addClientToBook(q, clientInformation, now);
                    }
                    else if(ch.contains("Cancel")){
                        client.remClient(q, clientInformation);
                    }
                    if(q.isEmpty()){//end window
                        //have to set the window to not close unless the linked list is empty
                        break;
                    }
                }while(true);
                details.getChildren().add(clientInformation);
               
              
                ClientInfo clientInfo = (ClientInfo)fromClient.readObject();

                System.out.println(clientInfo.getVehicleType());
                System.out.println(clientInfo.getregNumber());
                System.out.println(clientInfo.getTotalCost());
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
