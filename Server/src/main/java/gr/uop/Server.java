package gr.uop;

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

         try (ServerSocket serverSocket = new ServerSocket(7777);
             Socket connectionSocket = serverSocket.accept();
             ObjectInputStream fromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ) 
            {

                /*
                 * Πρίν κάνει οτιδήποτε, να κοιτά το αρχείο με τα έσοδα και να γεμίζει ένα πίνακα από MoneyBookEntries
                 * Αντί για κλάση MoneyBook, κλάση MoneyBookEntry αφού κάθε φορά θα χειρίζεται μία εγγραφή, το "βιβλίο" είναι το ίδιο το αρχείο.
                 * RandomAccessFile, fixed length.
                 * Η VehicleOrder δέν χρειάζεται.
                 */
                GridPane details = new GridPane();
                details.setStyle("-fx-font-size: 18px;");
                //Πρώτη στήλη: κουμπιά "Πληρωμή", δεύτερη στήλη: τύπος οχήματος, τρίτη στήλη: αριθμός κυκλοφορίας, 4η στήλη: επιλεγμένες υπηρεσίες, 5η στήλη: συνολικό κόστος, 6η στήλη: ώρα άφιξης
                //τα ίδια ακριβώς θα εγγράφονται στο βιβλίο εσόδων
                details.add(new Label("Τύπος οχήματος"), 1, 0);
                details.add(new Label("Αριθμός κυκλοφορίας"), 2, 0);
                details.add(new Label("Επιλεγμένες υπηρεσίες"), 3, 0);
                details.add(new Label("Συνολικό ποσό"), 4, 0);
                details.add(new Label("Ώρα άφιξης"), 5, 0);
                int row = 1;
                do{//programm runnning
                    ClientInfo clientInformation = (ClientInfo)fromClient.readObject();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                    LocalDateTime now = LocalDateTime.now();  

                    details.add(new Button("Πληρωμή"), 0, row);
                    //add client info to GridPane
                    details.add(new Label(clientInformation.getVehicleType()), 1, row);
                    details.add(new Label(clientInformation.getregNumber()), 2, row);
                    String selectedServices  = "";
                    Iterator<Service> it = clientInformation.getSelectedServices().iterator();
                    while(it.hasNext()){
                        Service s = it.next();
                        selectedServices += s.getName();
                        if(it.hasNext()){selectedServices+=", ";}
                    }
                    details.add(new Label(selectedServices), 3, row);
                    details.add(new Label(Integer.toString(clientInformation.getTotalCost())), 4, row);
                    details.add(new Label(Integer.toString(now.getHour())+now.getMinute()), 5, row);
                    
                    
                    MoneyBook bookOfServices = new MoneyBook();
                    bookOfServices.inputServiceInfo(clientInformation, now); 
                    LinkedList<ClientInfo> q = new LinkedList<>();//πληροφοριες για το καθε οχημα
                    TreeMap<ClientInfo,String> ordering = new TreeMap<>();//ClientInfo για το κλειδι και το String για το alert απο την κλαση VecicleOrder                  
                    q.add(clientInformation);
                    VehicleOrder client = new VehicleOrder();
                    ordering.put(clientInformation,client.getProc());               //εδω 
                    //q.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//δεν βλεπει το χρόνο?
                    details.getChildren().add(client);                             //εδω
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
                    row += 1;
                }while(true);//το πρόγραμμα θα τερματίζει μόνο χειροκίνητα.

               
            var label = new Label("No Services Right Now");
            var scene = new Scene(new StackPane(label), 1024, 640);
            stage.setMinWidth(1024);
            stage.setMinHeight(640);
            stage.setMaxWidth(1920);
            stage.setMaxHeight(1080);
            stage.setTitle("Πρόγραμμα ταμείου");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    
    }

    public static void main(String[] args) {
        launch(args);
    }


}
