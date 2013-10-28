/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import javafx.application.Application;
import javafx.stage.Stage;
import login.LoginWindow;

/**
 *
 * @author Stian
 */
public class TestMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        //Menu menu = new Menu();
        //menu.generateStage();
        LoginWindow login = new LoginWindow();
        login.generateStage();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
