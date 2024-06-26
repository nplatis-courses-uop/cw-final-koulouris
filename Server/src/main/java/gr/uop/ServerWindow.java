package gr.uop;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ServerWindow extends TableView<ClientInfo>{
    private TableColumn<ClientInfo, String> vehicleType, regNumber, totalCost, arrivalTime;
    private TableColumn<ClientInfo, HBox> pay;
    private TableColumn<ClientInfo, TextArea> selectedServices;
    private ObservableList<ClientInfo> data = FXCollections.observableArrayList();

    /**
     * Creates a new ServerWindow, also setting to get the data from ObservableList<ClientInfo> data
     */
    public ServerWindow(){
        super();
        setStyle("-fx-font-size: 18px;");
        //Πρώτη στήλη: κουμπιά "Πληρωμή", δεύτερη στήλη: τύπος οχήματος, τρίτη στήλη: αριθμός κυκλοφορίας, 4η στήλη: επιλεγμένες υπηρεσίες, 5η στήλη: συνολικό κόστος, 6η στήλη: ώρα άφιξης
        pay = new TableColumn<>("");
        pay.setMinWidth(220);
        vehicleType = new TableColumn<>("Τύπος οχήματος");

        regNumber = new TableColumn<>("Αριθμός κυκλοφορίας");
        selectedServices = new TableColumn<>("Επιλεγμένες υπηρεσίες");      
        totalCost = new TableColumn<>("Συνολικό ποσό");       
        arrivalTime = new TableColumn<>("Ώρα άφιξης");

        int mult = 12;
        vehicleType.setMinWidth("Τύπος οχήματος".length()*mult);
        regNumber.setMinWidth("Αριθμός κυκλοφορίας".length()*mult);
        selectedServices.setMinWidth("Επιλεγμένες υπηρεσίες".length()*mult); 
        totalCost.setMinWidth("Συνολικό ποσό".length()*mult); 
        arrivalTime.setMinWidth("Ώρα άφιξης".length()*mult+50);

        selectedServices.setStyle("-fx-alignment: CENTER");
        vehicleType.setStyle("-fx-alignment: CENTER");
        regNumber.setStyle("-fx-alignment: CENTER");
        totalCost.setStyle("-fx-alignment: CENTER");
        arrivalTime.setStyle("-fx-alignment: CENTER");
        setFixedCellSize(70);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);//columns will share all of TableView's width
        
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getSelectionModel().getTableView().setOnKeyPressed((e)->{
            if(e.getCode() == KeyCode.ENTER){
                TableRow<ClientInfo> row = new TableRow<>();
                row.setItem(getSelectionModel().getSelectedItem());
                payAction(row);
            }
        });
        Callback<TableColumn<ClientInfo, HBox>, TableCell<ClientInfo, HBox>> payCellFactory = new Callback<TableColumn<ClientInfo,HBox>,TableCell<ClientInfo,HBox>>() {

            @Override
            public TableCell<ClientInfo, HBox> call(TableColumn<ClientInfo, HBox> param) {
                // TODO Auto-generated method stub
                final TableCell<ClientInfo, HBox> cell = new TableCell<ClientInfo, HBox>(){
                    private final Button payment = new Button("Πληρωμή");
                    private final Button cancel = new Button("Ακύρωση");
                    private final HBox buttonsPane = new HBox(5, payment, cancel);

                    {
                        payment.setOnAction((e)->{//open dialog with the selected row's content, user must confirm payment, then add departure time, remove from main window.    
                            payAction(getTableRow());
                        });
                        cancel.setOnAction((e)->{//remove entry from book and from window, after confirmation
                            Alert confirmRemove = confirmAction("Αφαίρεση καταχώρισης;", getTableRow());
                            Optional<ButtonType> response = confirmRemove.showAndWait();
                            if(response.get() == ButtonType.OK){
                                ClientInfo ci = getTableRow().getItem();
                                MoneyBook.removeEntry(ci);
                                data.remove((ClientInfo)ci);
                            }

                        });
                        buttonsPane.setAlignment(Pos.CENTER);
                    }

                    @Override
                    public void updateItem(HBox item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttonsPane);
                        }
                    }
                };
                return cell;
            }
            
        };
        pay.setCellFactory(payCellFactory);
        vehicleType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientInfo,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClientInfo, String> param) {
                // TODO Auto-generated method stub
                return new ReadOnlyObjectWrapper<String>(param.getValue().getVehicleType());
            }
            
        });
        regNumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientInfo,String>,ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<ClientInfo, String> param) {
                // TODO Auto-generated method stub
                return new ReadOnlyObjectWrapper<String>(param.getValue().getregNumber());
            }
            
        });
        selectedServices.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientInfo,TextArea>, ObservableValue<TextArea>>() {
            @Override
            public ObservableValue<TextArea> call(CellDataFeatures<ClientInfo, TextArea> param) {
                Set<Service> serviceSet = param.getValue().getSelectedServices();
                String ret = "";
                Iterator<Service> it = serviceSet.iterator();
                while(it.hasNext()){
                    Service s = it.next();
                    ret+=s.getName();
                    if(it.hasNext()){
                        ret+=", ";
                    }
                }
                TextArea wrapAndNotEditable = new TextArea(ret);
                wrapAndNotEditable.setWrapText(true);
                wrapAndNotEditable.setEditable(false);
                wrapAndNotEditable.setMaxHeight(50);
                return new ReadOnlyObjectWrapper<TextArea>(wrapAndNotEditable);
            }
        });
        totalCost.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientInfo,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClientInfo, String> param) {
                return new ReadOnlyObjectWrapper<String>(Integer.toString(param.getValue().getTotalCost()));
            }
        });
        arrivalTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ClientInfo,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ClientInfo, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getArrivalTime());
            }
        });
        getColumns().add(pay);
        getColumns().add(vehicleType);
        getColumns().add(regNumber);
        getColumns().add(selectedServices);
        getColumns().add(totalCost);
        getColumns().add(arrivalTime);
        
        setItems(data);
    }

    /**
    * generates a confirm dialog with given parameters
    * @param headerText the header text of the dialog
    * @param row the row in the TableView
    * @return the Alert generated, to handle showAndWait outside.
    */
    private Alert confirmAction(String headerText, TableRow<ClientInfo> row){
        Alert confirmAction = new Alert(AlertType.CONFIRMATION);
        confirmAction.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        confirmAction.setHeaderText(headerText);
        ClientInfo ci = row.getItem();
        String InfoText = ci.getVehicleType()+", Αριθμός κυκλοφορίας: "+ci.getregNumber()+"\nΕπιλεγμένες υπηρεσίες:\n";
        Set<Service> services = ci.getSelectedServices();
        for(Service s: services){
            InfoText+=s.getName()+"\n";
        }
        InfoText+="Συνολικό ποσό: "+ci.getTotalCost()+"$, "+"Ημερομηνία άφιξης: "+ci.getArrivalTime();
        confirmAction.setContentText(InfoText);
        return confirmAction;
    }

    /**
     * sets the action for when the user pays (button or Enter)
     * @param row the selected TableRow
     */
    private void payAction(TableRow<ClientInfo> row){
        Alert confirmPayment = confirmAction("Ολοκλήρωση πληρωμής;", row);
        Optional<ButtonType> response = confirmPayment.showAndWait();
        if(response.get() == ButtonType.OK){
            LocalDateTime now = LocalDateTime.now();
            ClientInfo ci = row.getItem();
            MoneyBook.update(ci, now);
            ci.setDepartureTime(now);
            data.remove(ci);
        }
    }

    /**
     * Adds ClientInfo to main window, sets its arrival time
     * @param input  the ClientInfo to add
     */
    public void add(ClientInfo input){
        data.add(input);
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
