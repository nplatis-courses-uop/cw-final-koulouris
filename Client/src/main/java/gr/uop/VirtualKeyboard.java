package gr.uop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class VirtualKeyboard extends GridPane{
    private Button[] letters = {
        new Button("Q"), new Button("W"), new Button("E"), new Button("R"), new Button("T"), new Button("Y"), new Button("U"),
        new Button("I"), new Button("O"), new Button("P"), new Button("A"), new Button("S"), new Button("D"), new Button("F"),
        new Button("G"), new Button("H"), new Button("J"), new Button("K"), new Button("L"), new Button("Z"), new Button("X"),
        new Button("C"), new Button("V"), new Button("B"), new Button("N"), new Button("M")
    };
    private Button[] digits = new Button[10];
    private Button backspace = new Button("Backspace");
    private Button enter = new Button("Enter");
    private Button space = new Button(" ");
    private TextField userInput;

    public VirtualKeyboard(TextField dest){
        super();
        userInput = dest;
        for(int i = 0; i < 10; i++){
            digits[i] = new Button(Integer.toString(i));
        }
        setPadding(new Insets(5));
        super.setHgap(5);
        super.setVgap(5);
        addKeysToPane();
        addActions();
        enter.setDisable(true);
    }

    public void addEnterAction(EventHandler<ActionEvent> e){
        enter.setOnAction(e);
    }

    private void addActions() {
        for(Button b: letters){
            b.setOnAction((e)->{
                String prevText = userInput.getText();
                prevText += b.getText();
                userInput.setText(prevText);
            });
        }
        for(Button d: digits){
            d.setOnAction((e)->{
                userInput.appendText(d.getText());
            });
        }
        backspace.setOnAction((e)->{
            if(userInput.getText().isEmpty() == false){
                String prevText = userInput.getText();
                prevText = prevText.substring(0, prevText.length()-1);
                userInput.setText(prevText);
            }
        });
        space.setOnAction((e)->{
            userInput.appendText(space.getText());
        });

        userInput.textProperty().addListener((obs, oldV, newV)->{
            if(newV.length() >= 2 && newV.isEmpty() == false){
                enter.setDisable(false);
            }else{
                enter.setDisable(true);
            }
        });
    }

    private void addKeysToPane() {
        int i = 0;
        while(i<10){
            add(letters[i], i, 0, 1, 1);
            letters[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            letters[i].setStyle("-fx-font-size: 20px;");
            i+=1;
        }
        while(i < 19){
            add(letters[i], i-10, 1, 1, 1);
            letters[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            letters[i].setStyle("-fx-font-size: 20px;");
            i+=1;
        }
        while(i < 26){
            add(letters[i], i-19, 2, 1, 1);
            letters[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            letters[i].setStyle("-fx-font-size: 20px;");
            i+=1;
        }

        int dist = 3;
        for(int j=0; j < 3; j++){
            for(i=10; i < 13; i++){
                add(digits[i-dist], i, j, 1, 1);
                digits[i-dist].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                digits[i-dist].setStyle("-fx-font-size: 20px;");
            }
            dist+=3;
        }
        add(digits[0], 10, 3, 2, 1);
        digits[0].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        digits[0].setStyle("-fx-font-size: 20px;");


        space.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        backspace.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        backspace.setStyle("-fx-font-size: 20px;");
        enter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        enter.setStyle("-fx-font-size: 20px;");

        add(space, 2, 3, 6, 1);
        add(enter, 7, 2, 2, 1);
        add(backspace, 8, 3, 2, 1);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100 / getColumnCount());
        for (i = 0; i < getColumnCount(); i++) {
            getColumnConstraints().add(column);
        }
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(100/ getRowCount());
        for (i = 0; i < getRowCount(); i++) {
            getRowConstraints().add(row);
        }
    }
}
