package addImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.PictureList;
import repository.TagCom;

/**
 *
 * @author Johan LG
 */
public class AddImageGUI extends GridPane {

    private AddImageLogic logic = new AddImageLogic();
    private Label statusLabel = new Label("");
    private ProgressBar progressBar = new ProgressBar(0);
    private final TextField searchField = new TextField();
    public static boolean addingToList = false;
    private Task ProgressTask;
    private PictureList pictureList;
    private ArrayList<Button> tagButtons = new ArrayList();
    private FlowPane grid;
    final private TagCom tagCom = new TagCom();
    private VBox tagListBox;
    final private Button removeTag;
    private String selectedTag = "";
    private int selectedTagIndex = 0;
    private Label gridLabel = new Label("");
    private final Button searchButton;
    private long minTagID;

    public AddImageGUI() throws IOException {

        pictureList = new PictureList();
        ColumnConstraints col1 = new ColumnConstraints(300);
        getColumnConstraints().add(col1);
        setPadding(new Insets(12));
        setAlignment(Pos.TOP_CENTER);
        setHgap(12);
        setVgap(12);

        HBox searchBox = new HBox(12);
        searchBox.setPadding(new Insets(12));
        searchBox.setAlignment(Pos.CENTER);

        Label searchLabel = new Label("Legg til tag:");
        searchBox.getChildren().add(searchLabel);
        searchBox.getChildren().add(searchField);

        searchButton = new Button("Legg til");
        searchBox.getChildren().add(searchButton);
        add(searchBox, 0, 0, 2, 1);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addTag();
            }
        });

        searchField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                addTag();
            }
        });

        GridPane statuBox = new GridPane();
        statuBox.add(statusLabel, 0, 0);

        HBox pbHBox = new HBox();
        pbHBox.setAlignment(Pos.CENTER);
        progressBar.setPrefWidth(300);
        pbHBox.getChildren().add(progressBar);
        statuBox.add(pbHBox, 0, 1, 2, 1);

        statuBox.setAlignment(Pos.CENTER);

        add(statuBox, 0, 1, 2, 1);
        add(new Label("Tags:"), 0, 2);
        add(gridLabel, 1, 2);

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

        removeTag = new Button("Fjern Tag");
        ScrollPane tagScroll = new ScrollPane();
        tagListBox = new VBox();
        tagListBox.setMaxWidth(Double.MAX_VALUE);
        tagScroll.setFitToWidth(true);
        tagScroll.setFitToHeight(true);
        tagScroll.setContent(tagListBox);
        add(tagScroll, 0, 3, 1, 2);
        tagListBox.setStyle("-fx-background-color: #808080;");

        updateTagButtons();
        removeTag.setDisable(true);
        if (!tagButtons.isEmpty()) {
            setSelectedButton(0);
        }

        scroll.setContent(grid);
        add(scroll, 1, 3);
        grid.setAlignment(Pos.CENTER);

        HBox removeTagBox = new HBox();
        removeTagBox.setAlignment(Pos.CENTER);
        removeTagBox.getChildren().add(removeTag);
        add(removeTagBox, 1, 4);

        removeTag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
//                updateTagButtons();

                try {
                    ArrayList tags = new ArrayList();
                    tags.add(selectedTag);
                    tagCom.removeTag(tags);
                } catch (IOException ex) {
                    Logger.getLogger(AddImageGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                updateTagButtons();
                if (selectedTagIndex >= tagButtons.size()) {
                    setSelectedButton(tagButtons.size() - 1);
                } else if (tagButtons.size() > 0) {
                    setSelectedButton(selectedTagIndex);
                } else {
                    setSelectedButton(-1);
                }
            }
        });

        setHgrow(tagListBox, Priority.NEVER);
        setHgrow(scroll, Priority.ALWAYS);

        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);
    }

    private void addTag() {
        if (!searchField.getText().equals("")) {
            if (!pictureList.getTags().toString().toLowerCase().contains(searchField.getText().toLowerCase())) {

                searchButton.setDisable(true);
                searchField.setDisable(true);

                ProgressTask = logic.findPicturesTask(searchField.getText());
                ProgressTask.setOnSucceeded(new EventHandler() {
                    @Override
                    public void handle(Event t) {
                        displayPictures(selectedTag);
                        updateTagButtons();
                        setSelectedButton(tagButtons.size() - 1);
                    }
                });
                progressBar.progressProperty().unbind();
                progressBar.progressProperty().bind(ProgressTask.progressProperty());
                ProgressTask.messageProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (ProgressTask.isDone() || ProgressTask.isCancelled()) {
                            searchButton.setDisable(false);
                            searchField.setDisable(false);
                            try {
                                tagCom.storeTag(searchField.getText(), minTagID);
                            } catch (IOException ex) {
                                Logger.getLogger(AddImageGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            updateTagButtons();
                            searchField.setText("");
                        }
                        if (addingToList) {
                            if (Pattern.matches("[0-9]+", newValue)) {
                                minTagID = Long.parseLong(newValue, 10);
                            }
                        } else {
                            setStatusText(newValue);
                        }
                    }
                });
                new Thread(ProgressTask).start();
            } else {
                setStatusText("Du har allerde denne tag-en");
            }
        }
    }

    private void displayPictures(String tag) {
        grid.getChildren().clear();
        final ArrayList<Thumbnail> thumbnails = new ArrayList();
        thumbnails.addAll(pictureList.getThumbnails(tag));
        gridLabel.setText(thumbnails.size() + " bilder med " + selectedTag + " tag:");
        grid.getChildren().addAll(thumbnails);


        for (Thumbnail t : thumbnails) {
            t.loadImage();

        }
    }

    private void setSelectedButton(int i) {
        if (i == -1) {
            selectedTagIndex = i;
            selectedTag = "";
            grid.getChildren().clear();
            removeTag.setDisable(true);
            gridLabel.setText("");
        } else {
            selectedTagIndex = i;
            selectedTag = tagButtons.get(i).getText();
            displayPictures(selectedTag);
            for (Button b : tagButtons) {
                b.setDisable(false);
            }
            removeTag.setDisable(false);
            tagButtons.get(i).setDisable(true);
        }
    }

    private void updateTagButtons() {
//        try {
        tagButtons.clear();
        pictureList.update();
//            for (final String t : tagCom.getTags()) {
        for (final String t : pictureList.getTags()) {
            final Button tagButton = new Button(t);
            tagButton.setMaxWidth(Double.MAX_VALUE);
            tagButtons.add(tagButton);
            tagButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    setSelectedButton(tagButtons.indexOf(tagButton));
                }
            });
        }
//        } catch (IOException ex) {
//            Logger.getLogger(AddImageGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        tagListBox.getChildren().clear();
        tagListBox.getChildren().addAll(tagButtons);
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    public void setProgress(double progress) {
        progressBar.setProgress(progress);
    }
}
