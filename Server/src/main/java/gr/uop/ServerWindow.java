package gr.uop;

import java.time.LocalDateTime;
import java.util.Iterator;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ServerWindow extends GridPane{
    private int row = 0;

    public ServerWindow(){
        super();
        setStyle("-fx-font-size: 18px;");
        //Πρώτη στήλη: κουμπιά "Πληρωμή", δεύτερη στήλη: τύπος οχήματος, τρίτη στήλη: αριθμός κυκλοφορίας, 4η στήλη: επιλεγμένες υπηρεσίες, 5η στήλη: συνολικό κόστος, 6η στήλη: ώρα άφιξης
        //τα ίδια ακριβώς θα εγγράφονται στο βιβλίο εσόδων
        add(new Label("Τύπος οχήματος"), 1, 0);
        add(new Label("Αριθμός κυκλοφορίας"), 2, 0);
        add(new Label("Επιλεγμένες υπηρεσίες"), 3, 0);
        add(new Label("Συνολικό ποσό"), 4, 0);
        add(new Label("Ώρα άφιξης"), 5, 0);
    }

    public void add(ClientInfo input, LocalDateTime now){
        row += 1;
        add(new Button("Πληρωμή"), 0, row);
        //add client info to GridPane
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
        add(new Label(Integer.toString(now.getHour())+":"+now.getMinute()), 5, row);
    }
    
}
