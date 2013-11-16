package addImage;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import model.Picture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Johan LG & T
 * @web http://java-buddy.blogspot.com/
 */
public class AddImageGUI extends GridPane {

    private AddImageLogic logic = new AddImageLogic();
    private List<Picture> pictureList = new ArrayList<Picture>();
    //private UpdateThread thread = logic.getThread();
    private Label statusLabel = new Label("");
    private Label progressLabel = new Label("");
    private ProgressBar progressBar = new ProgressBar(0);
    final TextField searchField = new TextField();
    public static boolean addingToList = false;
    Task ProgressTask;

    public AddImageGUI() {
        setAlignment(Pos.CENTER);
        setHgap(12);
        setVgap(12);

        Label searchLabel = new Label("Søk bilder etter tag:");
        add(searchLabel, 0, 0);
        add(searchField, 1, 0);

        final Button searchButton = new Button("Legg til");
        add(searchButton, 2, 0);
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

        add(statusLabel, 0, 4);

        HBox progressLabelHbox = new HBox();
        progressLabelHbox.setAlignment(Pos.CENTER_RIGHT);
        progressLabelHbox.getChildren().add(progressLabel);
        add(progressLabelHbox, 2, 6);

        HBox pbHBox = new HBox();
        pbHBox.setAlignment(Pos.CENTER);
        progressBar.setPrefWidth(300);
        pbHBox.getChildren().add(progressBar);
        add(pbHBox, 0, 5, 3, 1);
        
        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);
        
        
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
