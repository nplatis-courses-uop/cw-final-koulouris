package gr.uop;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ServicesDialog extends Dialog<Service[]>  implements EventHandler<ActionEvent>{
    private Map<Service, CheckBox> list;
    private String[] names = {"Πλύσιμο εξωτερικό", "Πλύσιμο εσωτερικό", "Πλύσιμο εξωτ.+εσωτ.", "Πλύσιμο εξωτ. σπέσιαλ", "Πλύσιμο εσωτ. σπέσιαλ",
    "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ", "Βιολογικός καθαρισμός εσωτ.", "Κέρωμα-Γυάλισμα", "Καθαρισμός κινητήρα", "Πλύσιμο σασί"
}; 

    public ServicesDialog(Stage stage){
        list = new TreeMap<>();
        
        for(int i = 0; i < names.length; i++){
            CheckBox b = new CheckBox();
            Service s = new Service(i, names[i]);
            list.put(s, b);
            b.selectedProperty().addListener((e)->{
                Service sb = new Service(s.getName());
                Set<Map.Entry<Service, CheckBox>> entries = list.entrySet();
                for(Map.Entry<Service, CheckBox> l: entries){
                    if(l.getKey().equals(sb)){
                        System.out.println(l.getKey().getName());
                    }
                }
            });
        }
        setServicesCosts();
        
        GridPane mainPane = new GridPane();
        mainPane.setHgap(10);
        mainPane.setVgap(5);
        mainPane.setStyle("-fx-font-size: 20px;");

        Set<Map.Entry<Service, CheckBox>> entries = list.entrySet();
        Iterator<Entry<Service, CheckBox>> it = entries.iterator();
        Entry<Service, CheckBox> e;
        int c;

        mainPane.add(new Label("Υπηρεσία"), 1, 0);
        mainPane.add(new Label("Αυτοκίνητο"), 2, 0);
        mainPane.add(new Label("Τζίπ"), 3, 0);
        mainPane.add(new Label("Μοτοσυκλέτα"), 4, 0);
        for(int i=1; i < names.length; i++){
            if(it.hasNext()){
                e = it.next();
            }else{break;}
            mainPane.add(e.getValue(), 0, i);
            mainPane.add(new Label(e.getKey().getName()), 1, i);
            Label a, j, m;
            c = e.getKey().getCarCost();
            a = new Label(Integer.toString(c));
            mainPane.add(a, 2, i);
            c = e.getKey().getJeepCost();
            j = new Label(Integer.toString(c));
            mainPane.add(j, 3, i);
            c = e.getKey().getMotorcycleCost();
            if(c == -1){
                m = new Label("-");
            }else{m  = new Label(Integer.toString(c));}
            mainPane.add(m, 4, i);
            GridPane.setHalignment(a, HPos.CENTER);
            GridPane.setHalignment(j, HPos.CENTER);    
            GridPane.setHalignment(m, HPos.CENTER);
        }

        getDialogPane().setContent(mainPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        initOwner(stage);
        setTitle("Επιλογή υπηρεσιών");
    }

    private void setServicesCosts() {
        Set<Map.Entry<Service,CheckBox>> entries = list.entrySet();
        for(Map.Entry<Service,CheckBox> e: entries){
            switch(e.getKey().getName()){
                case "Πλύσιμο εξωτερικό":
                    e.getKey().setCost(7, 8, 6);
                    break;
                case "Πλύσιμο εσωτερικό":
                    e.getKey().setCost(6, 7);
                    break;
                case "Πλύσιμο εξωτ.+εσωτ.":
                    e.getKey().setCost(12, 14);
                    break;
                case "Πλύσιμο εξωτ. σπέσιαλ":
                    e.getKey().setCost(9, 10, 8);
                    break;
                case "Πλύσιμο εσωτ. σπέσιαλ":
                    e.getKey().setCost(8, 9);
                    break;
                 case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":
                    e.getKey().setCost(15, 17);
                    break;
                case "Βιολογικός καθαρισμός εσωτ.":
                    e.getKey().setCost(80, 80);
                    break;
                case "Κέρωμα-Γυάλισμα":
                    e.getKey().setCost(80, 90, 40);
                    break;
                case "Καθαρισμός κινητήρα":
                    e.getKey().setCost(20, 20, 10);
                    break;
                case "Πλύσιμο σασί":
                    e.getKey().setCost(3, 3);
                    break;
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {
        Optional<Service[]> res = showAndWait();

    }
    
}
