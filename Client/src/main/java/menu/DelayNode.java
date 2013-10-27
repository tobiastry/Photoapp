/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Stian
 */
public class DelayNode extends GridPane{
        
    private Text header, unit, error;
    private TextField delayField;
    private Button setButton;
    private int delay = 0;
    
    public DelayNode(){
        super();
        
        header  = new Text("Slideshow Intervall");
        header.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        unit = new Text("Sekunder");
        unit.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
    
        error = new Text("Feil i input!");
        error.setVisible(false);
        error.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        delayField = new TextField(Integer.toString(getDelay()));
        delayField.setPrefSize(80, 30);
        delayField.setAlignment(Pos.CENTER_RIGHT);
        
        setButton = new Button("Sett Intervall");
        setButton.setPrefSize(180, 30);
        
        setButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String input = delayField.getText();
                if(testInput(input)){
                    error.setVisible(false);
                    setDelay(Integer.parseInt(input));
                } else{
                    error.setVisible(true);
                }
            }
        });
        
        //TODO vurder annet enn gridpane, eller få til colspan
        this.add(header, 0, 0);
        this.add(delayField, 0, 1);
        this.add(unit, 1, 1);
        this.add(error, 0, 2);
        this.add(setButton, 0, 3);
    }
    
    private boolean testInput(String input){
        try {
            int i = Integer.parseInt(input);
        } catch(NumberFormatException e) {
            return false;
        }        
        return true;
    }

    private int getDelay() {
        return delay;
    }
    
    private void setDelay(int delay){
        this.delay = delay + 2;
        delayField.setText("" + this.delay);
    }
    
}
