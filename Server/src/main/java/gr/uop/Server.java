package gr.uop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;   

/**
 * JavaFX App
 */
public class Server extends Application {
    private ServerWindow details = new ServerWindow();

    @Override
    public void start(Stage stage) throws ClassNotFoundException {

        /*
         * Πρίν κάνει οτιδήποτε, να κοιτά το αρχείο με τα έσοδα και να γεμίζει ένα πίνακα από MoneyBookEntries
         * Αντί για κλάση MoneyBook, κλάση MoneyBookEntry αφού κάθε φορά θα χειρίζεται μία εγγραφή, το "βιβλίο" είναι το ίδιο το αρχείο.
         * RandomAccessFile, fixed length.
         * Η VehicleOrder δέν χρειάζεται.
         */
        var scene = new Scene(details, 1024, 640);
        stage.setMinWidth(1024);
        stage.setMinHeight(640);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
        stage.setTitle("Πρόγραμμα ταμείου");
        stage.setScene(scene);
        stage.show();

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
