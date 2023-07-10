package gr.uop;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
public class VehicleOrder {
    private Optional<String> proc;
public void setProc(Optional<String> result){
    proc=result;
}
public String/*Optional<String>*/ getProc(){
    return proc.toString();
}

    public StackPane order(ClientInfo x){//javafx create and input it in main
        StackPane p = new StackPane();  
        VBox serv = new VBox();
        TextArea services = new TextArea(toString(x));
        services.setEditable(false);
        
        Button butt = new Button("Status");
        butt.setOnAction((e) -> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Prossesing", "Pay", "Prossesing", "Cancel");
            dialog.setContentText("Select one:");
            dialog.setTitle("Staus Of Order");
            dialog.setHeaderText(null);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                setProc(result);
            }
            else {
                System.out.println("Alert closed in some other way");
            }
        });
        serv.getChildren().add(services);
        p.getChildren().add(butt);
        p.getChildren().add(serv);
        return p;
    }
    public String toString(ClientInfo ci){
        return /*""+ci.getServiceCost(null).getCarCost()+"$ "+*/ci.getSelectedServices()+" "+ci.getregNumber()+" "+ci.getVehicleType();
    }
    public LinkedList<ClientInfo> remClient(LinkedList<ClientInfo> x,ClientInfo y){
            if(x.contains(y)){
                x.remove(y);
            }
        return x;
    }
    public LinkedList<ClientInfo> addClientToBook(LinkedList<ClientInfo> x,ClientInfo y,LocalDateTime now){
        MoneyBook mb = new MoneyBook();
        Boolean found=false;
            if(x.contains(y)){
                mb.inputServiceInfo(y, now);
                x.remove(y);
                found=true;
            }
        if(found == false){
           System.out.println("Client not found with these elements.Please check again the ClientElle.");
        }
        return x;
    }
    public void printAllClients(LinkedList<ClientInfo> x){// see later
        System.out.println(x);
    }
}
