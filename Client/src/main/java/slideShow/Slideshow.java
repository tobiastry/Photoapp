package slideShow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.LoginWindow;
import repository.RetrievePicturesCom;

public class Slideshow extends Application {

    private StackPane root = new StackPane();
    private SequentialTransition slideshow;
    private ImageTransition imageTrans;
    private ArrayList<ImageView> bildeListe;
    private ListOfImages imageViewSetter;
    RetrievePicturesCom com;
    private Task retrieveImages;

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Start initiated");
        slideshow = new SequentialTransition();
        imageTrans = new ImageTransition();
        bildeListe = new ArrayList();
        com = new RetrievePicturesCom();
        imageViewSetter = new ListOfImages(bildeListe,this);
        //imageViewSetter.getImageViewList();
        //getImageViewList();
        retrieveImages = imageViewSetter.getImageViewList();
        //getImageViewList();
        retrieveImages.run();

        System.out.println("Gathered list of images");

        final Button quit = new Button();
        quit.setText("Quit Slideshow");
        quit.setLayoutX(500);
        quit.setLayoutY(500);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        final Button menu = new Button();
        menu.setText("Admin Menu");
        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                slideshow.pause();
                LoginWindow login = new LoginWindow(getSlideshowObject());
                login.generateStage();
            }
        });


        final HBox box = new HBox(20);
        box.setAlignment(Pos.BOTTOM_RIGHT);
        box.setOpacity(0.0);
        box.getChildren().add(quit);
        box.getChildren().add(menu);

        this.root.getChildren().add(box);

        this.root.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), box);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                PauseTransition pause = new PauseTransition(Duration.millis(5000));

                FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), box);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                SequentialTransition sequence = new SequentialTransition();
                sequence.getChildren().addAll(fadeIn, pause, fadeOut);
                if (box.getOpacity() > 0.1) {
                    //Do nothing
                } else {
                    sequence.play();
                }
            }
        });
        //getImageViewList();
        //Legger alle bildene inn i slideshow transition

        stage = SlideShowWindow.getSlideShowWindow();
        stage.setScene(new Scene(root, 800, 600, Color.BLACK));
        stage.show();
        
      /*  for (ImageView bilde : bildeListe) {
            bilde.setOpacity(0);
            this.root.getChildren().add(bilde);
        }*/
        //getImageViewList();
    }

    public void initiateNewSlideshow() {
        Duration timestamp = slideshow.getCurrentTime();
        
        //Legger inn overgang for alle bilder i bilde listen
        imageTrans.setNewDelay();
        slideshow.stop();
        root.getChildren().clear();
        slideshow.getChildren().clear();
        
        for(int i = 0; i<bildeListe.size();i++) {
            bildeListe.get(i).setOpacity(0);
            this.root.getChildren().add(bildeListe.get(i));
            slideshow.getChildren().add(imageTrans.getFullOvergang(bildeListe.get(i)));
        }
        slideshow.setCycleCount(Timeline.INDEFINITE);
        slideshow.playFrom(timestamp);
        System.out.println("initated new slideshow");
    }

    public Slideshow getSlideshowObject() {
        return this;
    }

    public void getImageViewList() {
        int teller = 0;
        ArrayList<String> imageList;
        try {
            imageList = com.getLargeImageList();
            for (int i = 0; i < imageList.size(); i++) {
                teller++;
                if (teller % 10 == 0) {
                    initiateNewSlideshow();
                    break;
                }
                System.out.println("Creating ImageView #" + teller);
                bildeListe.add(new ImageView(new Image(imageList.get(i))));
            }
        } catch (Exception e) {
            //Dersom link eller path ikke stemmer, så viser programmet et placeholder bilde.
            bildeListe.add(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg")));
        }
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}