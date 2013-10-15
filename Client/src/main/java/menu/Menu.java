package menu;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu {

    private VBox sidePane;
    private GridPane root;
    private Stage stage;
    private Scene scene;
    private ArrayList<Button> buttons;
    //Skal holde panes fra andre aktiviteter
    private Pane searchPane, deletePane;
    
    public Menu() {
        buttons = new ArrayList<>();
        this.makeButtons();
        
        root = new GridPane();
        this.buildRootPane();
        
        sidePane = new VBox();
        this.buildSidePane();
        
        root.add(sidePane, 0, 0);
        Pane testPane = new Pane();
        testPane.setPrefSize(600, 600);
        setActivityPane(testPane);
        scene = new Scene(root, 800, 600, Color.WHITE);

    }

    private void buildRootPane() {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(5, 5, 5, 5));
        root.setGridLinesVisible(true);


    }

    private void buildSidePane() {
        sidePane.setAlignment(Pos.TOP_RIGHT);
        sidePane.setMaxHeight(Double.MAX_VALUE);
        sidePane.setSpacing(10);
        sidePane.setMinWidth(200);
        sidePane.getChildren().addAll(buttons);
    }
    
    private void makeButtons(){
        Button btnSearch = new Button("Hente Bilder");
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //setActivityPane(searchPane);
                stage.setTitle("Emil er teit!");
            }
        });
        Button btnDelete = new Button("Slette Bilder");
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //setActivityPane(deletePane);
                stage.setTitle("PhotoApp");
            }
        });
        buttons.add(btnSearch);
        buttons.add(btnDelete);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setPrefSize(190, 40);
            buttons.get(i).setTranslateY(10);
        }
    }

    private void setActivityPane(Pane activityPane) {
        if (root.getChildren().size() > 2) {
            root.getChildren().remove(1);
        }
        root.add(activityPane, 1, 0);      
    }

    public void generateStage() {
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PhotoApp");
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.show();
    }

}
