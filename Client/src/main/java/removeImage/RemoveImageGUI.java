package removeImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Picture;
import repository.DeletePicturesCom;
import repository.RetrievePicturesCom;

/**
 *
 * @author Johan LG
 */
public class RemoveImageGUI extends GridPane {

    private final ArrayList<SelectableThumbnail> thumbnails;
    private ArrayList<Picture> images;
    private RetrievePicturesCom com;
    private FlowPane grid;
    private SelectedThumbnailLister lister;

    public RemoveImageGUI() {
        thumbnails = new ArrayList<>();
        com = new RetrievePicturesCom();
        try {
            images = com.getImageList();
        } catch (IOException ex) {
            Logger.getLogger(RemoveImageGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        lister = new SelectedThumbnailLister(thumbnails);
        int gap = 8;
        setHgap(gap);
        setVgap(gap);
        setPadding(new Insets(gap));
        ScrollPane scroll = new ScrollPane();
        setVgrow(scroll, Priority.ALWAYS);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().add(cc);
        scroll.setFitToWidth(true);
        grid = new FlowPane();
        grid.setPadding(new Insets(8));
        grid.setVgap(gap);
        grid.setHgap(gap);

        grid.setAlignment(Pos.CENTER);

        scroll.setContent(grid);
        add(scroll, 0, 0, 4, 1);

        Button mark = new Button("Merk alle");
        Button unmark = new Button("Avmerk alle");
        Button delete = new Button("Slett merkede");

        mark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                for (SelectableThumbnail thumbnail : thumbnails) {
                    thumbnail.setSelected(true);
                }
            }
        });
        unmark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                for (SelectableThumbnail thumbnail : thumbnails) {
                    thumbnail.setSelected(false);
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                DeletePicturesCom delCom = new DeletePicturesCom();
                try {
                    delCom.deletePictures(lister.ListSelectedThumbnails());
                } catch (IOException ex) {
                    Logger.getLogger(RemoveImageGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                ArrayList<SelectableThumbnail> selected = new ArrayList();
                for (SelectableThumbnail thumbnail : thumbnails) {
                    if (thumbnail.isSelected()) {
                        selected.add(thumbnail);
                    }
                }
                for (SelectableThumbnail thumb : selected) {
                    images.remove(thumb.getPicture());
                }
                thumbnails.removeAll(selected);
                updateGrid();
            }
        });

        HBox markBox = new HBox(gap);
        markBox.getChildren().addAll(mark, unmark);
        markBox.setAlignment(Pos.CENTER);
        add(markBox, 0, 2, 3, 1);

        setHalignment(delete, HPos.RIGHT);
        add(delete, 2, 2);

        for (int i = 0; i < images.size(); i++) {
            SelectableThumbnail tn = new SelectableThumbnail();
            tn.setPicture(images.get(i));
            thumbnails.add(tn);
        }
        updateGrid();
        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);

    }

    private void updateGrid() {
        grid.getChildren().clear();
        grid.getChildren().addAll(thumbnails);
        for (SelectableThumbnail thumb : thumbnails) {
            thumb.loadImage();
        }
    }

    /**
     * Checks if there list of pictures has changed and updates the thumbnails
     * accordingly
     */
    public void update() {
        ArrayList<Picture> newPics = new ArrayList();
        try {
            newPics = com.getImageList();
        } catch (IOException ex) {
            Logger.getLogger(RemoveImageGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        images = newPics;
        thumbnails.clear();
        for (int i = 0; i < images.size(); i++) {
            SelectableThumbnail tn = new SelectableThumbnail();
            tn.setPicture(images.get(i));
            thumbnails.add(tn);
        }
        updateGrid();


    }
}
