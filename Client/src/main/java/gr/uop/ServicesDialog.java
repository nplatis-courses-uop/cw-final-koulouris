package gr.uop;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Iterator;
import javafx.scene.control.Separator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServicesDialog extends Dialog<Service[]>  implements EventHandler<ActionEvent>{
    private Map<Service, CheckBox[]> list;
    private String[] names = {"Πλύσιμο εξωτερικό", "Πλύσιμο εσωτερικό", "Πλύσιμο εξωτ.+εσωτ.", "Πλύσιμο εξωτ. σπέσιαλ", "Πλύσιμο εσωτ. σπέσιαλ",
    "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ", "Βιολογικός καθαρισμός εσωτ.", "Κέρωμα-Γυάλισμα", "Καθαρισμός κινητήρα", "Πλύσιμο σασί"
    };
    private int totalCost = 0;
    private Label totCost = new Label("Συνολικό ποσό: "+totalCost);

    public ServicesDialog(Stage stage){
        list = new TreeMap<>();
        
        for(int i = 0; i < names.length; i++){
            CheckBox b[] = new CheckBox[3];
            Service s = new Service(i, names[i]);
            list.put(s, b);
        }
        setServicesCosts();
        
        GridPane servicesPane = new GridPane();
        servicesPane.setHgap(10);
        servicesPane.setVgap(5);
        servicesPane.setStyle("-fx-font-size: 20px;");

        Set<Map.Entry<Service, CheckBox[]>> entries = list.entrySet();
        Iterator<Entry<Service, CheckBox[]>> it = entries.iterator();
        Entry<Service, CheckBox[]> e;

        servicesPane.add(new Label("Υπηρεσία"), 0, 0);
        servicesPane.add(new Label("Αυτοκίνητο"), 1, 0);
        servicesPane.add(new Label("Τζίπ"), 2, 0);
        servicesPane.add(new Label("Μοτοσυκλέτα"), 3, 0);
        for(int i=1; i < names.length; i++){
            if(it.hasNext()){
                e = it.next();
            }else{break;}
            servicesPane.add(new Label(e.getKey().getName()), 0, i);
            int k = 1;
            CheckBox[] table = e.getValue();
            for(int j = 0; j < table.length; j++){
                table[j] = new CheckBox();
                servicesPane.add(table[j], k, i);
                k+=1;
                GridPane.setHalignment(table[j], HPos.CENTER);
            }
            if(table.length == 2){
                Label l = new Label("-");
                servicesPane.add(l, 3, i);
                GridPane.setHalignment(l, HPos.CENTER);
            }
        }
        
        VBox status = new VBox();
        status.getChildren().add(new Separator());
        status.getChildren().add(totCost);
        status.setPadding(new Insets(5, 0, 0, 0));
        status.setStyle("-fx-font-size: 20px;");

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(servicesPane);
        mainPane.setBottom(status);

        getDialogPane().setContent(mainPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        initOwner(stage);
        setTitle("Επιλογή υπηρεσιών");
    }

    private void setServicesCosts() {
        Set<Map.Entry<Service,CheckBox[]>> entries = list.entrySet();
        for(Map.Entry<Service,CheckBox[]> e: entries){
            switch(e.getKey().getName()){
                case "Πλύσιμο εξωτερικό":
                    e.getKey().setCost(7, 8, 6);
                    break;
                case "Πλύσιμο εσωτερικό":
                    e.getKey().setCost(6, 7);
                    e.setValue(new CheckBox[2]);
                    break;
                case "Πλύσιμο εξωτ.+εσωτ.":
                    e.getKey().setCost(12, 14);
                    e.setValue(new CheckBox[2]);
                    break;
                case "Πλύσιμο εξωτ. σπέσιαλ":
                    e.getKey().setCost(9, 10, 8);
                    break;
                case "Πλύσιμο εσωτ. σπέσιαλ":
                    e.getKey().setCost(8, 9);
                    e.setValue(new CheckBox[2]);
                    break;
                 case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":
                    e.getKey().setCost(15, 17);
                    e.setValue(new CheckBox[2]);
                    break;
                case "Βιολογικός καθαρισμός εσωτ.":
                    e.getKey().setCost(80, 80);
                    e.setValue(new CheckBox[2]);
                    break;
                case "Κέρωμα-Γυάλισμα":
                    e.getKey().setCost(80, 90, 40);
                    break;
                case "Καθαρισμός κινητήρα":
                    e.getKey().setCost(20, 20, 10);
                    break;
                case "Πλύσιμο σασί":
                    e.getKey().setCost(3, 3);
                    e.setValue(new CheckBox[2]);
                    break;
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {
        Optional<Service[]> res = showAndWait();

    }
    
}
