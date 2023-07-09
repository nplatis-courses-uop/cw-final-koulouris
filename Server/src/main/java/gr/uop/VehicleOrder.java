package gr.uop;

import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VehicleOrder {
    
    private boolean rbCancel=false;
    private boolean rbYes=false;

    public void order(ClientInfo x){
        StackPane p = new StackPane();
        RadioButton rbYes = new RadioButton("Pay");
        RadioButton rbWaiting = new RadioButton("Prossesing");
        RadioButton rbCancel = new RadioButton("Cancel");
        ToggleGroup tg = new ToggleGroup();
        //RadioButton selectedRadio = (RadioButton)(tg.getSelectedToggle());
        VBox serv = new VBox();
        
        TextArea services = new TextArea(toString(x));
        //JScrollPane scrollPane = new JScrollPane(textArea);
        services.setEditable(false);
        
        rbWaiting.setSelected(true);
        rbYes.setToggleGroup(tg);
        rbWaiting.setToggleGroup(tg);
        Button btn = new Button("Show selected");
        btn.setOnAction((e) -> {
            RadioButton selectedRadio = (RadioButton)(tg.getSelectedToggle());
            System.out.println(selectedRadio.getText());
        });
        serv.getChildren().add(services);
        p.getChildren().add(tg);
        p.getChildren().add(serv);
    }
    public String toString(ClientInfo ci){
        return /*""+ci.getServiceCost(null).getCarCost()+"$ "+*/ci.getSelectedServices()+" "+ci.getregNumber()+" "+ci.getVehicleType();
    }
    public LinkedList<ClientInfo> remClient(LinkedList<ClientInfo> x,ClientInfo y){
        //for (ClientInfo element : x)  {
            if(x.contains(y)){
                x.remove(y);
            }
        //}
        return x;
    }
    public LinkedList<ClientInfo> addClientToBook(LinkedList<ClientInfo> x,ClientInfo y,LocalDateTime now){
        MoneyBook mb = new MoneyBook();
        Boolean found=false;
        //for (ClientInfo element : x)  {
            if(x.contains(y)){
                mb.inputServiceInfo(y, now);
                x.remove(y);
                found=true;
            }
        //}
        if(found == false){
           System.out.println("Client not found with these elements.Please check again the Clientelle.");
        }
        return x;
    }
    public int getCorrectButtonValue(){
        if(rbYes == true){
            return 1;
        }
        else if(rbCancel == true){
            return 2;
        }
        return 3;//still prossesing the order
    }
}
