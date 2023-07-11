package gr.uop;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;   

/**
 * JavaFX App
 */
public class Server extends Application {

    @Override
    public void start(Stage stage) throws ClassNotFoundException {

    //do{//programm runnning, accept multiple clients one by one
         try (ServerSocket serverSocket = new ServerSocket(7777)) 
            {

                /*
                 * Πρίν κάνει οτιδήποτε, να κοιτά το αρχείο με τα έσοδα και να γεμίζει ένα πίνακα από MoneyBookEntries
                 * Αντί για κλάση MoneyBook, κλάση MoneyBookEntry αφού κάθε φορά θα χειρίζεται μία εγγραφή, το "βιβλίο" είναι το ίδιο το αρχείο.
                 * RandomAccessFile, fixed length.
                 * Η VehicleOrder δέν χρειάζεται.
                 */
                

                ServerWindow details = new ServerWindow();
                var scene = new Scene(details, 1024, 640);
                stage.setMinWidth(1024);
                stage.setMinHeight(640);
                stage.setMaxWidth(1920);
                stage.setMaxHeight(1080);
                stage.setTitle("Πρόγραμμα ταμείου");
                stage.setScene(scene);
                stage.show();

                    do{
                        Socket connectionSocket = serverSocket.accept();
                        ObjectInputStream fromClient = new ObjectInputStream(connectionSocket.getInputStream());

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    ClientInfo clientInformation = (ClientInfo)fromClient.readObject();
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                                    LocalDateTime now = LocalDateTime.now();  
                                    details.add(clientInformation, now);
                                }
                                catch (IOException|ClassNotFoundException e) {
                                    System.out.println(e);
                                }
                            }
                        });
                        thread.start();
                    }while(true);

        }
        catch (IOException e) {
            System.out.println(e);
        }
        //}while(true);//το πρόγραμμα θα τερματίζει μόνο χειροκίνητα.
    }

    public static void main(String[] args) {
        launch(args);
    }


}
