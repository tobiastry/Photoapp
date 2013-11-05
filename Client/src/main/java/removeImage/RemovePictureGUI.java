package removeImage;

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

/**
 *
 * @author Johan LG
 */
public class RemovePictureGUI extends GridPane {

    private final ArrayList<Thumbnail> thumbnails;
    private final ThumbnailLoader pl;
    private final static int imagePerPane = 24;
    private int nextThumbIndex = 0, maxImages = 0, rem = 0;
    private FlowPane grid;
    private Button next, previous;
    private SelectedThumbnailLister lister;

    public RemovePictureGUI() {
        thumbnails = new ArrayList<>();
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
        previous = new Button("< Forrige");
        next = new Button("Neste >");
        Button markPage = new Button("Merk alle på siden");
        Button unmarkPage = new Button("Avmerk alle på siden");

        mark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        unmark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                lister.ListSelectedThumbnails();
            }
        });
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                showNext();
            }
        });

        previous.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                showPrevious();
            }
        });
        previous.setDisable(true);
        markPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        unmarkPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        HBox pageMarkBox = new HBox(gap);
        pageMarkBox.getChildren().addAll(previous, markPage, unmarkPage, next);
        pageMarkBox.setAlignment(Pos.CENTER);
        add(pageMarkBox, 0, 1, 3, 1);

        HBox markBox = new HBox(gap);
        markBox.getChildren().addAll(mark, unmark);
        markBox.setAlignment(Pos.CENTER);
        add(markBox, 0, 2, 3, 1);

        setHalignment(delete, HPos.RIGHT);
        add(delete, 2, 2);

        pl = new ThumbnailLoader(thumbnails);

        maxImages = pl.imageListSize();

        if (maxImages < imagePerPane) {
            next.setDisable(true);
            //markPage.setDisable(true);
            //unmarkPage.setDisable(true);
        }
        //temp
        for (int i = 0; i < maxImages; i++) {
            Thumbnail tn = new Thumbnail();
            thumbnails.add(tn);
        }

        rem = maxImages % imagePerPane;
        if (maxImages > imagePerPane) {
            maxImages -= rem;
        }
        grid.getChildren().addAll(thumbnails);
        pl.loadPictures(0);
        
        nextThumbIndex = imagePerPane;
    }

    private void showNext() {
        if (maxImages >= imagePerPane) {
            grid.getChildren().clear();
            if (nextThumbIndex == maxImages) {
                for (int i = nextThumbIndex; i < (maxImages + rem); i++) {
                    previous.setDisable(false);
                    grid.getChildren().add(thumbnails.get(i));
                }
                next.setDisable(true);
                return;
            } else {
                for (int i = nextThumbIndex; i < (nextThumbIndex + imagePerPane); i++) {
                    previous.setDisable(false);
                    grid.getChildren().add(thumbnails.get(i));
                }
            }

            nextThumbIndex += imagePerPane;

            if (nextThumbIndex > thumbnails.size() - 1) {
                next.setDisable(true);
            }
        }
    }

    private void showPrevious() {
        nextThumbIndex -= imagePerPane;

        grid.getChildren().clear();
        for (int i = nextThumbIndex; i < ((nextThumbIndex) + imagePerPane); i++) {
            next.setDisable(false);
            grid.getChildren().add(thumbnails.get(i));
        }
        if (nextThumbIndex == 0) {
            previous.setDisable(true);
            nextThumbIndex = imagePerPane;
        }
    }

    private void showNext() {
        if (maxImages >= imagePerPane) {
            grid.getChildren().clear();
            if (nextThumbIndex == maxImages) {
                for (int i = nextThumbIndex; i < (maxImages + rem); i++) {
                    previous.setDisable(false);
                    grid.getChildren().add(thumbnails.get(i));
                }
                next.setDisable(true);
                return;
            } else {
                for (int i = nextThumbIndex; i < (nextThumbIndex + imagePerPane); i++) {
                    previous.setDisable(false);
                    grid.getChildren().add(thumbnails.get(i));
                }
            }

            nextThumbIndex += imagePerPane;

            if (nextThumbIndex > thumbnails.size() - 1) {
                next.setDisable(true);
            }
        }
    }

    private void showPrevious() {
        nextThumbIndex -= imagePerPane;

        grid.getChildren().clear();
        for (int i = nextThumbIndex; i < ((nextThumbIndex) + imagePerPane); i++) {
            next.setDisable(false);
            grid.getChildren().add(thumbnails.get(i));
        }
        if (nextThumbIndex == 0) {
            previous.setDisable(true);
            nextThumbIndex = imagePerPane;
        }
    }
}
