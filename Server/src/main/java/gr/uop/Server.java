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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;   

/**
 * JavaFX App
 */
public class Server extends Application {
    private ServerWindow details = new ServerWindow();

    @Override
    public void start(Stage stage) throws ClassNotFoundException {

        ArrayList<ClientInfo> saved = MoneyBook.loadAllEntries();
        for(ClientInfo c: saved){
            if(c.getDepartureTime().equalsIgnoreCase("")){
                details.add(c, LocalDateTime.parse(c.getArrivalTime(), DateTimeFormatter.ofPattern(ClientInfo.DATE_FORMAT_PATTERN, Locale.ROOT)));
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
        stage.setOnCloseRequest((e)->System.exit(0));

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

                // Process the received data and update the GridPane
                Platform.runLater(() -> {
                    details.add(clientInfo, LocalDateTime.now());
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
