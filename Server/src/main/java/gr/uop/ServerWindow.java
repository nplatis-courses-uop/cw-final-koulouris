package gr.uop;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

public class ServerWindow extends GridPane{
    private int row = 0;

    public ServerWindow(){
        super();
        setStyle("-fx-font-size: 18px;");
        //Πρώτη στήλη: κουμπιά "Πληρωμή", δεύτερη στήλη: τύπος οχήματος, τρίτη στήλη: αριθμός κυκλοφορίας, 4η στήλη: επιλεγμένες υπηρεσίες, 5η στήλη: συνολικό κόστος, 6η στήλη: ώρα άφιξης
        //τα ίδια ακριβώς θα εγγράφονται στο βιβλίο εσόδων
        add(new Label("Τύπος οχήματος   "), 1, 0);
        add(new Label("Αριθμός κυκλοφορίας   "), 2, 0);
        add(new Label("Επιλεγμένες υπηρεσίες   "), 3, 0);
        add(new Label("Συνολικό ποσό   "), 4, 0);
        add(new Label("Ώρα άφιξης   "), 5, 0);
    }

    public void add(ClientInfo input, LocalDateTime now){
        row += 1;
        Button but = new Button("Πληρωμή ");
        add(but,0,row);
        but.setOnAction((e) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Payment Confirmation");
            alert.setContentText("Do you want to proceed with Payments?");
            //alert.setHeaderText(null);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.getButtonTypes().add(ButtonType.CLOSE);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {//enter Money book
                MoneyBook mb = new MoneyBook();
                mb.inputServiceInfo(input, now);
                System.out.println("OK");
            }
            else if (result.get() == ButtonType.CANCEL) {//cancel order
                input.deletClientInfo(input);
                //row-=1;
                System.out.println("Cancel Order");
            }
            else if (result.get() == ButtonType.CLOSE) {
                System.out.println("Close Menu");
            }
            System.out.println("Window Closed");

        });
        
        add(new Label(input.getVehicleType()), 1, row);
        add(new Label(input.getregNumber()), 2, row);
        String selectedServices  = "";
        Iterator<Service> it = input.getSelectedServices().iterator();
        while(it.hasNext()){
            Service s = it.next();
            selectedServices += s.getName();
            if(it.hasNext()){selectedServices+=", ";}
        }
        add(new Label(selectedServices), 3, row);
        add(new Label(Integer.toString(input.getTotalCost())), 4, row);
        String n;
        if(now.getMinute()<10){
            n="0"+now.getMinute();
        }
        else{
            n=""+now.getMinute();
        }
        
        add(new Label(now.getHour()+":"+n), 5, row); 
    }
    
}
