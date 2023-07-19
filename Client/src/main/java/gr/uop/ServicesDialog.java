package gr.uop;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServicesDialog extends Dialog<String>{
    private Map<Service, CheckBox[]> list;
    private String[] names = {"Πλύσιμο εξωτερικό", "Πλύσιμο εσωτερικό", "Πλύσιμο εξωτ.+εσωτ.", "Πλύσιμο εξωτ. σπέσιαλ", "Πλύσιμο εσωτ. σπέσιαλ",
    "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ", "Βιολογικός καθαρισμός εσωτ.", "Κέρωμα-Γυάλισμα", "Καθαρισμός κινητήρα", "Πλύσιμο σασί"
    };
    private int totalCost = 0;
    private Label totCost = new Label("Συνολικό ποσό: "+totalCost);
    private String vehicleType;
    private ArrayList<Service> selectedServices;
    private ArrayList<CheckBox> visited = new ArrayList<>();

    public ServicesDialog(Stage stage){
        selectedServices = new ArrayList<>();
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
        for(int i=0; i < names.length; i++){
            if(it.hasNext()){
                e = it.next();
            }else{break;}
            servicesPane.add(new Label(e.getKey().getName()), 0, i+1);
            int k = 1;
            CheckBox[] table = e.getValue();
            for(int j = 0; j < table.length; j++){
                table[j] = new CheckBox();
                servicesPane.add(table[j], k, i+1);
                k+=1;
                GridPane.setHalignment(table[j], HPos.CENTER);
            }e.setValue(table);
            if(table.length == 2){
                Label l = new Label("-");
                servicesPane.add(l, 3, i+1);
                GridPane.setHalignment(l, HPos.CENTER);
            }
        }
        setCheckBoxesActions();
        
        VBox status = new VBox();
        status.getChildren().add(new Separator());
        status.getChildren().add(totCost);
        status.setPadding(new Insets(5, 0, 0, 0));
        status.setStyle("-fx-font-size: 15px;");

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(servicesPane);
        mainPane.setBottom(status);

        getDialogPane().setContent(mainPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        initOwner(stage);
        setTitle("Επιλογή υπηρεσιών");

        setResultConverter((button)->{
            if(button == ButtonType.OK){
                Alert confirm = new Alert(AlertType.CONFIRMATION);
                confirm.setHeaderText("Επιβεβαίωση επιλογών");
                confirm.setContentText("Έχεις επιλέξει συνολικά "+selectedServices.size()+" υπηρεσίες με συνολικό κόστος "+totalCost);
                confirm.initOwner(stage);
                confirm.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                confirm.initStyle(StageStyle.DECORATED);
                Optional<ButtonType> res = confirm.showAndWait();
                if(res.get() == ButtonType.OK){
                    return "SEND";
                }//else do nothing
            }
            return "CANCELED";
        });
    }

    private void setCheckBoxesActions() {
        Set<Map.Entry<Service,CheckBox[]>> entries = list.entrySet();
        final Set<Map.Entry<Service,CheckBox[]>> actionEntries = list.entrySet();
        for(Map.Entry<Service,CheckBox[]> e: entries){
            CheckBox[] table = e.getValue();
            for(int i = 0; i < table.length; i++){
                final int index = i;                
                switch(e.getKey().getName()){//disable checkboxes in the same column
                    case "Πλύσιμο εξωτερικό"://disable: Πλύσιμο εξωτ.+εσωτ., Πλύσιμο εξωτ. σπέσιαλ, Πλύσιμο εξωτ.+εσωτ. σπέσιαλ
                        table[i].selectedProperty().addListener((obs, oldV, newV)->{
                            actionEntries.forEach((l)->{
                                switch(l.getKey().getName()){
                                    case "Πλύσιμο εξωτ.+εσωτ.":
                                    case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":
                                        boolean isAnotherSelected = false;
                                        for(Map.Entry<Service,CheckBox[]> a: actionEntries){
                                            CheckBox[] tb = a.getValue();
                                                for(int j = 0; j < tb.length; j++){
                                                    switch(a.getKey().getName()){
                                                        case "Πλύσιμο εξωτ. σπέσιαλ":
                                                        case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":
                                                        case "Πλύσιμο εσωτερικό":
                                                        case "Πλύσιμο εσωτ. σπέσιαλ":
                                                            if(tb[index].isSelected()){
                                                                isAnotherSelected = true;
                                                            }
                                                            break;
                                                    }
                                                }
                                                if(isAnotherSelected){break;}
                                        }
                                        disableCheckBoxes(l.getValue(), index, table[index].isSelected() || isAnotherSelected);
                                        break;
                                    case "Πλύσιμο εξωτ. σπέσιαλ":
                                        disableCheckBoxes(l.getValue(), index, table[index].isSelected());
                                        break;
                                }
                            });
                        });
                        break;
                    case "Πλύσιμο εσωτερικό"://disable: Πλύσιμο εξωτ.+εσωτ, Πλύσιμο εσωτ. σπέσιαλ, Πλύσιμο εξωτ.+εσωτ. σπέσιαλ
                        table[i].selectedProperty().addListener((obs, oldV, newV)->{
                            actionEntries.forEach((l)->{
                                switch(l.getKey().getName()){
                                    case "Πλύσιμο εξωτ.+εσωτ.":
                                    case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":

                                        break;
                                    case "Πλύσιμο εσωτ. σπέσιαλ":
                                        disableCheckBoxes(l.getValue(), index, table[index].isSelected());
                                        break;
                                }   
                            });
                        });
                        break;
                    case "Πλύσιμο εξωτ. σπέσιαλ"://disable: Πλύσιμο εξωτερικό, Πλύσιμο εξωτ.+εσωτ., Πλύσιμο εξωτ.+εσωτ. σπέσιαλ
                        table[i].selectedProperty().addListener((obs, oldV, newV)->{
                            actionEntries.forEach((l)->{
                                switch(l.getKey().getName()){
                                    case "Πλύσιμο εξωτ.+εσωτ.":
                                    case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":

                                        break;
                                    case "Πλύσιμο εξωτερικό":
                                        disableCheckBoxes(l.getValue(), index, table[index].isSelected());
                                        break;
                                }
                            });
                        });
                        break;
                    case "Πλύσιμο εσωτ. σπέσιαλ"://disable: Πλύσιμο εξωτ.+εσωτ., Πλύσιμο εσωτερικό, Πλύσιμο εξωτ.+εσωτ. σπέσιαλ
                        table[i].selectedProperty().addListener((obs, oldV, newV)->{
                            actionEntries.forEach((l)->{
                                switch(l.getKey().getName()){
                                    case "Πλύσιμο εξωτ.+εσωτ.":
                                    case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":

                                        break;
                                    case "Πλύσιμο εσωτερικό":
                                        disableCheckBoxes(l.getValue(), index, table[index].isSelected());
                                        break;
                                }
                            });
                        });
                        break;
                    case "Πλύσιμο εξωτ.+εσωτ."://disable: Πλύσιμο εξωτερικό, Πλύσιμο εσωτερικό, Πλύσιμο εξωτ. σπέσιαλ, Πλύσιμο εσωτ. σπέσιαλ, Πλύσιμο εξωτ.+εσωτ. σπέσιαλ
                        table[i].selectedProperty().addListener((obs, oldV, newV)->{
                            actionEntries.forEach((l)->{
                                switch(l.getKey().getName()){
                                    case "Πλύσιμο εξωτερικό":
                                    case "Πλύσιμο εσωτερικό":
                                    case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ":
                                    case "Πλύσιμο εξωτ. σπέσιαλ":
                                    case "Πλύσιμο εσωτ. σπέσιαλ":
                                        disableCheckBoxes(l.getValue(), index, table[index].isSelected());
                                        break;
                                }
                            });
                        });
                        break;
                    case "Πλύσιμο εξωτ.+εσωτ. σπέσιαλ"://disable: Πλύσιμο εξωτερικό, Πλύσιμο εσωτερικό, Πλύσιμο εξωτ. σπέσιαλ, Πλύσιμο εσωτ. σπέσιαλ, Πλύσιμο εξωτ.+εσωτ.
                        table[i].selectedProperty().addListener((obs, oldV, newV)->{
                            actionEntries.forEach((l)->{
                                switch(l.getKey().getName()){
                                    case "Πλύσιμο εξωτερικό":
                                    case "Πλύσιμο εσωτερικό":
                                    case "Πλύσιμο εξωτ.+εσωτ.":
                                    case "Πλύσιμο εσωτ. σπέσιαλ":
                                    case "Πλύσιμο εξωτ. σπέσιαλ":
                                        disableCheckBoxes(l.getValue(), index, table[index].isSelected());
                                        break;
                                }
                            });
                        });
                        break;                             
                }
                    
                table[i].selectedProperty().addListener((obs, oldV, newV)->{
                    if(newV == true){//disable checkboxes from columns != index
                        actionEntries.forEach((l)->{
                            CheckBox tb[] = l.getValue();
                            int k = 0;
                            for(CheckBox ch: tb){
                                if(k!=index){ ch.setDisable(true);}
                                k+=1;
                            }
                        });
                    }else{//check if any other checkbox in the same column is selected, if not, enable checkboxes from rest columns
                        boolean isSelected = false;
                        for(Map.Entry<Service,CheckBox[]> l: actionEntries){
                            CheckBox tb[] = l.getValue();
                            int k = 0;
                            for(CheckBox ch: tb){
                                if(k == index){
                                    if(ch.isSelected() == true){
                                        isSelected = true;
                                        break;
                                    }
                                }
                                k+=1;
                            }
                        }
                        if(!isSelected){//enable checkboxes from all columns
                            actionEntries.forEach((l)->{
                                CheckBox tb[] = l.getValue();
                                for(CheckBox ch: tb){
                                    ch.setDisable(false);
                                }
                            });
                        }
                    }
                });

                table[i].setOnAction((c)->{
                    int cost = 0;
                    switch(index){
                        case 0:
                            vehicleType = "Αυτοκίνητο";
                            cost = e.getKey().getCarCost();
                            break;
                        case 1:
                            vehicleType = "Τζίπ";
                            cost = e.getKey().getJeepCost();
                            break;
                        case 2:
                            vehicleType = "Μοτοσυκλέτα";
                            cost = e.getKey().getMotorcycleCost();
                            break;
                    }
                    //add to total cost or subtract, if the checkbox is selected then clicked again
                    //add service to list of selected services
                    if(table[index].isSelected()){
                        totalCost += cost;
                        totCost.setText("Συνολικό ποσό: "+totalCost+" $");
                        selectedServices.add(e.getKey());
                    }else{
                        totalCost -= cost;
                        totCost.setText("Συνολικό ποσό: "+totalCost+" $");
                        selectedServices.remove(e.getKey());
                    }
                });
            }
        }
    }

    private void disableCheckBoxes(CheckBox[] value, int index, boolean b) {
        if(index < value.length){
            value[index].setDisable(b);
        }
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

    public String getVehicleType() {
        return vehicleType;
    }
    public ArrayList<Service> getSelectedServices(){
        return selectedServices;
    }

    /**
     * Clear all previously selected options
     */
    public void clear() {
        Set<Map.Entry<Service, CheckBox[]>> entries = list.entrySet();
        for(Map.Entry<Service, CheckBox[]> entry: entries){
            CheckBox[] ch = entry.getValue();
            for(CheckBox c: ch){
                c.selectedProperty().set(false);
                c.setDisable(false);
            }
        }
        totalCost = 0;
        totCost.setText("Συνολικό ποσό: "+totalCost+"$");
    }
}
