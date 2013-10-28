package menu;

import java.io.IOException;
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
        
    private Text header, unit, error, confirm;
    private TextField delayField;
    private Button setButton;
    DelayCom action;
    
    public DelayNode(){
        super();
                
        action = new DelayCom();
        
        header  = new Text("Slideshow Intervall");
        header.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        unit = new Text("Sekunder");
        unit.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
    
        error = new Text("Feil i input!");
        error.setVisible(false);
        error.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        confirm = new Text("Nytt Intervall Satt!");
        confirm.setVisible(false);
        confirm.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        delayField = new TextField(getDelay());
        delayField.setPrefSize(80, 30);
        delayField.setAlignment(Pos.CENTER_RIGHT);
        
        setButton = new Button("Sett Intervall");
        setButton.setPrefSize(180, 30);

        
        setButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                error.setVisible(false);
                confirm.setVisible(false);
                String input = delayField.getText();
                if(testInput(input)){
                    int ret = setDelay(Integer.parseInt(input));
                    if(ret != 0){
                        confirm.setVisible(true);
                    }
                } else{
                    error.setVisible(true);
                }
            }
        });
        
        this.add(header, 0, 0, 2, 1);
        this.add(delayField, 0, 1);
        this.add(unit, 1, 1);
        this.add(error, 0, 2, 2, 1);
        this.add(confirm, 0, 2, 2, 1);
        this.add(setButton, 0, 3, 2, 1);
    }
    
    //Hvilke mulige delay skal vi ha?
    private boolean testInput(String input){
        try {
            int i = Integer.parseInt(input);
        } catch(NumberFormatException e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private String getDelay(){
        int i;
        try {
            i = action.getDelay();
        } catch(IOException ex) {
            //ex.printStackTrace();
            return "error";
        }   
        return i + "";
    }
    
    private int setDelay(int delay){
        int newDelay = 0;
        try {
            newDelay = action.setDelay(delay);
        } catch (IOException ex) {
            //ex.printStackTrace();            
        }
        return newDelay;
    }
    
}
