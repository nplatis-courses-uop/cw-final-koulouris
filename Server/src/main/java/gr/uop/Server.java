package gr.uop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

/**
 * JavaFX App
 */
public class Server extends Application {

    @Override
    public void start(Stage stage) {
        var label = new Label("Hello, JavaFX Server");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    /*public DateTimeFormatter getTimeAndDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return ;
        System.out.println(dtf.format(now)); 
    }*/


    public static void main(String[] args) {
        launch(args);
    }

}
