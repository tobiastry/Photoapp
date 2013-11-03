package menu;

import getImage.AddPictureGUI;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import removeImage.RemovePictureGUI;

public class Menu {

    private VBox vPane;
    private AnchorPane sidePane;
    private GridPane root;
    private Stage stage;
    private Scene scene;
    private ArrayList<Button> buttons;
    private DelayNode delay;
    double xPos, yPos;
    //Skal holde panes fra andre aktiviteter
    private Pane getImagePane, removeImagePane;

    public Menu() {
        buttons = new ArrayList<>();
        this.makeButtons();

        delay = new DelayNode();

        root = new GridPane();
        this.buildRootPane();

        sidePane = new AnchorPane();
        vPane = new VBox();
        this.buildSidePane();

        root.add(sidePane, 0, 0);

        removeImagePane = new RemovePictureGUI();
        //Pane testPane = new Pane();
        removeImagePane.setMinSize(800, 600);
        removeImagePane.setPrefSize(1080, 720);
        GridPane.setHgrow(removeImagePane, Priority.ALWAYS);
        GridPane.setVgrow(removeImagePane, Priority.ALWAYS);

        getImagePane = new AddPictureGUI();
        getImagePane.setMinSize(800, 600);
        getImagePane.setPrefSize(1080, 720);
        GridPane.setHgrow(getImagePane, Priority.ALWAYS);
        GridPane.setVgrow(getImagePane, Priority.ALWAYS);

        setActivityPane(getImagePane);
        scene = new Scene(root, 1280, 720, Color.WHITE);
        scene.getStylesheets().add(Menu.class.getResource("../stylesheets/Menu.css").toExternalForm());


        sidePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && !event.isConsumed()) {
                    event.consume();
                    stage.setFullScreen(!stage.isFullScreen());
                }
            }
        });

        sidePane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xPos = event.getX();
                yPos = event.getY();
            }
        });

        sidePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!stage.isFullScreen()) {
                    scene.getWindow().setX(event.getScreenX() - xPos);
                    scene.getWindow().setY(event.getScreenY() - yPos);
                }
            }
        });

    }

    private void buildRootPane() {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(5, 5, 5, 5));
        //root.setGridLinesVisible(true);


    }

    private void buildSidePane() {
        vPane.setAlignment(Pos.TOP_RIGHT);
        VBox.setVgrow(vPane, Priority.ALWAYS);
        vPane.setSpacing(10);
        vPane.getChildren().addAll(buttons);
        vPane.setMinWidth(200);

        sidePane.setMinWidth(200);
        sidePane.getChildren().add(vPane);
        AnchorPane.setTopAnchor(vPane, 5.0);
        sidePane.getChildren().add(delay);
        AnchorPane.setBottomAnchor(delay, 10.0);
    }

    private void makeButtons() {
        Button btnSearch = new Button("Hente Bilder");
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setActivityPane(getImagePane);
            }
        });
        Button btnDelete = new Button("Slette Bilder");
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setActivityPane(removeImagePane);
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
        if (root.getChildren().size() > 1) {
            root.getChildren().remove(1);
        }
        root.add(activityPane, 1, 0);
    }

    public void generateStage() {
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("PhotoApp");
        stage.setMinHeight(620);
        stage.setMinWidth(1020);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}
