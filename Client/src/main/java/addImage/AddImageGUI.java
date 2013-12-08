package addImage;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Picture;
import model.PictureList;

/**
 *
 * @author Johan LG
 */
public class AddImageGUI extends GridPane {

    private AddImageLogic logic = new AddImageLogic();
    private Label statusLabel = new Label("");
    private Label progressLabel = new Label("");
    private ProgressBar progressBar = new ProgressBar(0);
    private final TextField searchField = new TextField();
    public static boolean addingToList = false;
    private Task ProgressTask;
    private PictureList pictureList;
    private ArrayList<Button> tagButtons = new ArrayList();
    private ArrayList<String> tags = new ArrayList();
    private FlowPane grid;

    public AddImageGUI() {

        pictureList = new PictureList();
        ColumnConstraints col1 = new ColumnConstraints(300);
        getColumnConstraints().add(col1);
        setPadding(new Insets(12));
        setAlignment(Pos.TOP_CENTER);
        setHgap(12);
        setVgap(12);

        HBox searchBox = new HBox(12);
        searchBox.setAlignment(Pos.CENTER);

        Label searchLabel = new Label("Legg til tag:");
        searchBox.getChildren().add(searchLabel);
        searchBox.getChildren().add(searchField);

        final Button searchButton = new Button("Legg til");
        searchBox.getChildren().add(searchButton);
        add(searchBox, 0, 0, 2, 1);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                searchButton.setDisable(true);

                ProgressTask = logic.findPicturesTask(searchField.getText());

                progressBar.progressProperty().unbind();
                progressBar.progressProperty().bind(ProgressTask.progressProperty());

                ProgressTask.messageProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (ProgressTask.isDone() || ProgressTask.isCancelled()) {
                            searchButton.setDisable(false);
                        }
                        if (addingToList) {
                            setProgressText(newValue);
                        } else {
                            setStatusText(newValue);
                        }
                    }
                });
                new Thread(ProgressTask).start();
            }
        });



        GridPane statuBox = new GridPane();
        statuBox.add(statusLabel, 0, 0);

        HBox pbHBox = new HBox();
        pbHBox.setAlignment(Pos.CENTER);
        progressBar.setPrefWidth(300);
        pbHBox.getChildren().add(progressBar);
        statuBox.add(pbHBox, 0, 1, 2, 1);

        HBox progressLabelHbox = new HBox();

        progressLabelHbox.setAlignment(Pos.CENTER_RIGHT);
        progressLabelHbox.getChildren().add(progressLabel);

        statuBox.add(progressLabelHbox, 1, 2);

        statuBox.setAlignment(Pos.CENTER);

        add(statuBox, 0, 1, 2, 1);

        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);

        ScrollPane scroll = new ScrollPane();
        setVgrow(scroll, Priority.ALWAYS);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().add(cc);
        scroll.setFitToWidth(true);
        grid = new FlowPane();
        grid.setPadding(new Insets(8));
        grid.setVgap(8);
        grid.setHgap(8);

        final Button removeTag = new Button("Fjern Tag");
        ScrollPane tagScroll = new ScrollPane();
        VBox tagListBox = new VBox();
        tagListBox.setMaxWidth(Double.MAX_VALUE);
        tagScroll.setFitToWidth(true);
        tagScroll.setFitToHeight(true);
        tagScroll.setContent(tagListBox);
        add(tagScroll, 0, 2, 1, 2);
        tagListBox.setStyle("-fx-background-color: #808080;");
        for (final String t : pictureList.getTags()) {
            final Button tagButton = new Button(t);
            tagButton.setMaxWidth(Double.MAX_VALUE);
            tagButtons.add(tagButton);
            tags.add(t);
            tagButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    displayPictures(t);
                    for (Button b : tagButtons) {
                        b.setDisable(false);
                    }
                    removeTag.setDisable(false);
                    tagButton.setDisable(true);
                }
            });
        }
        tagListBox.getChildren().addAll(tagButtons);

        scroll.setContent(grid);
        add(scroll, 1, 2);
        grid.setAlignment(Pos.CENTER);

        removeTag.setDisable(true);
        HBox removeTagBox = new HBox();
        removeTagBox.setAlignment(Pos.CENTER);
        removeTagBox.getChildren().add(removeTag);
        add(removeTagBox, 1, 3);
        
        removeTag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        setHgrow(tagListBox, Priority.NEVER);
        setHgrow(scroll, Priority.ALWAYS);
    }

    private void displayPictures(String tag) {
        grid.getChildren().clear();
        final ArrayList<Thumbnail> thumbnails = new ArrayList();
        for (Picture p : pictureList.getPictures(tag)) {
            Thumbnail tn = new Thumbnail();
            tn.setPicture(p);
            thumbnails.add(tn);
        }
        grid.getChildren().addAll(thumbnails);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Thumbnail t : thumbnails) {
                    t.loadImage();
                }
            }
        }).start();
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    public void setProgressText(String text) {
        progressLabel.setText(text);
    }

    public void setProgress(double progress) {
        progressBar.setProgress(progress);
    }
}
