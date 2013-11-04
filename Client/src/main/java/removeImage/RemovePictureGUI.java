package removeImage;

import java.util.ArrayList;
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

    final ArrayList<Thumbnail> thumbnails;
    final ThumbnailLoader pl;

    public RemovePictureGUI() {
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
        FlowPane grid = new FlowPane();
        grid.setPadding(new Insets(8));
        grid.setVgap(gap);
        grid.setHgap(gap);

        grid.setAlignment(Pos.CENTER);

        scroll.setContent(grid);
        add(scroll, 0, 0, 4, 1);

        Button mark = new Button("Merk alle");
        Button unmark = new Button("Avmerk alle");
        Button delete = new Button("Slett merkede");
        Button privious = new Button("< Forrige");
        Button next = new Button("Neste >");
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
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        privious.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
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
        pageMarkBox.getChildren().addAll(privious, markPage, unmarkPage, next);
        pageMarkBox.setAlignment(Pos.CENTER);
        add(pageMarkBox, 0, 1, 3, 1);

        HBox markBox = new HBox(gap);
        markBox.getChildren().addAll(mark, unmark);
        markBox.setAlignment(Pos.CENTER);
        add(markBox, 0, 2, 3, 1);

        setHalignment(delete, HPos.RIGHT);
        add(delete, 2, 2);

        thumbnails = new ArrayList<>();
        pl = new ThumbnailLoader(thumbnails);
        //temp
        for (int i = 0; i < 24; i++) {
            Thumbnail tn = new Thumbnail();
            thumbnails.add(tn);
        }
        grid.getChildren().addAll(thumbnails);
        pl.loadPictures(0);
    }
}
