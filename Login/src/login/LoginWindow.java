package login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginWindow {

    /*
     * Scene setup
     */
    private Scene scene;
    
    public LoginWindow() {
        Text header = new Text("Login");
        header.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
        header.setFill(Color.web("#0E485E"));

        Button btn = new Button();
        btn.setText("Cancel");

        final PasswordField txtBox = new PasswordField();

        final Text denied = new Text("Wrong password");
        denied.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        denied.setFill(Color.RED);
        denied.setVisible(false);

        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(10);

        grid.add(header, 0, 0);
        grid.add(txtBox, 0, 1);
        grid.add(btn, 1, 1);
        grid.add(denied, 0, 2);

        scene = new Scene(grid, 300, 150);
        scene.getStylesheets().add(LoginWindow.class.getResource("Login.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);

        /*
         * Clicking the cancel button
         */
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Login canceled");
                System.exit(0);
            }
        });
        /*
         * Pressing "enter" in the textfield
         */
        txtBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (LoginLogic.checkLogin(txtBox.getText())) {
                    System.exit(0);
                } else {
                    denied.setVisible(true);
                    txtBox.setText("");
                }
            }
        });
    }

    /*
     * Generating the scene in a new window
     */
    public void generateScene() {
                LoginWindow d = new LoginWindow();
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
    }
}