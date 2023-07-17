package gr.uop;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;   

/**
 * JavaFX App
 */
public class Server extends Application {
    private ServerWindow details = new ServerWindow();

    @Override
    public void start(Stage stage) throws ClassNotFoundException{ 

        ArrayList<ClientInfo> saved = MoneyBook.loadAllEntries();
        for(ClientInfo c: saved){
            if(c.getDepartureTime().isEmpty()){
                details.add(c);
            }
        }
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(details);
        var scene = new Scene(mainPane, 1220, 640);
        stage.setMinWidth(1220);
        stage.setMinHeight(640);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
        stage.setTitle("Πρόγραμμα ταμείου");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((e)->{//don't close if there's still entries in details
            if(details.isEmpty() == false){
                e.consume();
                Alert cantClose = new Alert(AlertType.ERROR);
                cantClose.setHeaderText("Το παράθυρο δεν γίνεται να κλείσει.");
                cantClose.setContentText("Υπάρχουν ακόμα εγγραφές στο πίνακα.");
                cantClose.initOwner(stage);
                cantClose.show();
            }
        });

        startServer();
    }

    private void startServer() {
        new Thread(()->{
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(7777);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    handleClientConnection(clientSocket);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }).start();
    }

    private void handleClientConnection(Socket clientSocket) {
        new Thread(() -> {
             try {
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                ClientInfo clientInfo = (ClientInfo)inputStream.readObject();
                LocalDateTime now = LocalDateTime.now();
                clientInfo.setArrivvalTime(now);

                // Process the received data and update the TableView
                Platform.runLater(() -> {
                    details.add(clientInfo);
                    MoneyBook.inputServiceInfo(clientInfo);
                });

                // Close the client socket
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
