package slideShow;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
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

public class Slideshow extends Application {

    class SlideShow {

        StackPane root = new StackPane();
        ImageView[] bildeListe;

        public SlideShow() {
            this.bildeListe = ListOfImages.getImageViewList();

        }

        public StackPane getRoot() {
            return root;
        }

        //Selve metoden starter under
        public void start() {
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
                    LoginWindow login = new LoginWindow();
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

            SequentialTransition slideshow = new SequentialTransition();
            //Legger alle bildene inn i slideshow transistion
            for (ImageView bilde : bildeListe) {

                bilde.setOpacity(0);
                this.root.getChildren().add(bilde);


            }
            for (int i = 0; i < 5; i++) //Looper slideshow x antall ganger. Kan ikke loope kontinuerlig, da program aldri starter
            {
                //Legger inn overgang for alle bilder i bilde listen
                for (ImageView bilde : bildeListe) {
                    SequentialTransition sequentialTransition = ImageTransition.getFullOvergang(bilde);
                    slideshow.getChildren().add(sequentialTransition);
                }
            }

            //Del under er for å vise et avsluttende bilde som ikke går vekk i slutten av slideshow, etter x antall looper
            ImageView endImageView = new ImageView(new Image("http://www.bpafreedrinkingbottles.com.au/images/photos/gallery/eos.gif"));
            endImageView.setOpacity(0);
            this.root.getChildren().add(endImageView);
            SequentialTransition sequentialTransition = ImageTransition.getHalvOvergang(endImageView);
            slideshow.getChildren().add(sequentialTransition);


            slideshow.play();


        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage slideshowVindu) throws Exception {
        SlideShow SlideShow = new SlideShow();
        Scene scene = new Scene(SlideShow.getRoot(), 800, 800, Color.BLANCHEDALMOND);
        slideshowVindu = SlideShowWindow.getSlideShowWindow();
        slideshowVindu.setScene(scene);
        slideshowVindu.show();

        SlideShow.start();

    }
}