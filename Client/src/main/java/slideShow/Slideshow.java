package slideShow;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.LoginWindow;
import model.Picture;

/**
 *
 * @author Morten
 */
public class Slideshow extends Application {

    private StackPane root;
    private SequentialTransition slideshow;
    private ImageTransition imageTrans;
    private ArrayList<ImageView> imageList;
    private ArrayList<Picture> oldImageList;
    private ListOfImages imageViewSetter;
    private CheckNewDelay checkNewDelay;
    private UpdatePictureListTimer updatePicture;
    private Task retrieveImagesTask, checkDelayTask, pictureTimerTask;
    private Thread retrieveImagesThread, checkDelayThread, updatePictureTimerThread;
    private boolean startup = true;
    private Button quit, menu;
    private HBox box;
    private double delayDiffFactor = 1.0;
    private int delay;
    private Stage stage;
    private double yPos, xPos;
    private FadeTransition fadeOut = null;
    private Timeline timeline = null;

    @Override
    public void start(Stage stage1) throws Exception {
        this.stage = stage1;
        root = new StackPane();
        slideshow = new SequentialTransition();
        imageTrans = new ImageTransition();
        imageList = new ArrayList();
        oldImageList = new ArrayList();
        updatePicture = new UpdatePictureListTimer();
        pictureTimerTask = updatePicture.timerTask();
        checkNewDelay = new CheckNewDelay(imageTrans.getFadeTime() / 1000);
        checkDelayTask = checkNewDelay.checkNewDelay();

        delay = imageTrans.getDelay();

        initiateRetrieveImagesThread();
        initiateCheckDelayThread();
        initiateUpdatePictureTimerThread();

        menu = new Button();
        menu.setText("Admin Menu");
        menu.setMaxSize(200, 50);
        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                LoginWindow login = new LoginWindow(getSlideshowObject());
                login.generateStage();
            }
        });

        quit = new Button();
        quit.setText("Quit Slideshow");
        quit.setMaxSize(200, 50);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        box = new HBox(1000);
        box.setPadding(new Insets(15, 15, 15, 15));
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.getChildren().add(quit);
        box.getChildren().add(menu);

        box.setStyle("../stylesheets/Menu.css");

        /*
         * Listener on mouse movement for buttons
         */
        root.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                root.setCursor(Cursor.DEFAULT);
                box.toFront();
                FadeTransition fadeIn = new FadeTransition(Duration.millis(1), box);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                if (box.getOpacity() <= 0.8) {
                    fadeIn.play();
                } else {
                    //Do nothing
                }
                if (timeline != null) {
                    timeline.stop();
                }
                if (fadeOut != null) {
                    fadeOut.stop();
                }
                timeline = new Timeline(
                        new KeyFrame(Duration.seconds(3),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        root.setCursor(Cursor.NONE);
                                        fadeOut = new FadeTransition(Duration.millis(500), box);
                                        fadeOut.setFromValue(1.0);
                                        fadeOut.setToValue(0.0);
                                        fadeOut.play();
                                    }
                                }));
                timeline.play();

            }
        });

        root.getChildren().add(box);

        /*
         * Initiates stage and sets it visible
         */
        stage = new SlideShowWindow();
        stage.setScene(new Scene(root, 800, 600, Color.BLACK));
        stage.getScene().getStylesheets().add(this.getClass().getResource("/stylesheets/Slideshow.css").toExternalForm());

        //Toggle Fullscreen
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && !event.isConsumed()) {
                    event.consume();
                    stage.setFullScreen(!stage.isFullScreen());
                }
            }
        });

        //Moving the window
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xPos = event.getX();
                yPos = event.getY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!stage.isFullScreen()) {
                    stage.getScene().getWindow().setX(event.getScreenX() - xPos);
                    stage.getScene().getWindow().setY(event.getScreenY() - yPos);
                }
            }
        });

        stage.show();

        startup = false;
    }

    /*
     * Creates a new slideshow with updated preferrences
     */
    public void initiateNewSlideshow() {
        Duration timestamp = slideshow.getCurrentTime();
        System.out.println(timestamp);
        imageTrans.setNewDelay();
        slideshow.stop();
        root.getChildren().clear();
        slideshow.getChildren().clear();

        root.getChildren().add(box);

        for (int i = 0; i < imageList.size(); i++) {
            imageList.get(i).setOpacity(0);
            root.getChildren().add(imageList.get(i));
            slideshow.getChildren().add(imageTrans.getFullTransition(imageList.get(i)));
        }

        slideshow.setCycleCount(Timeline.INDEFINITE);
        double tempDuration = (timestamp.toMillis() * delayDiffFactor);
        slideshow.playFrom(new Duration(tempDuration));
        delay = imageTrans.getDelay();
        delayDiffFactor = 1.0;
        System.out.println("initated new slideshow with " + imageList.size() + " images");
    }

    public void initiateCheckDelayThread() {
        checkDelayThread = new Thread(checkDelayTask);
        checkDelayThread.setDaemon(true);
        checkDelayThread.start();

        /*
         * Listening on ready signal from Task: checkDelay
         */
        checkDelayTask.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                delayDiffFactor = Double.parseDouble(checkDelayTask.messageProperty().getValue().split(" ")[4]);
                initiateNewSlideshow();
            }
        });
    }

    public void initiateRetrieveImagesThread() {
        System.out.println("Initiating new retreiveImagesThread");
        if (!startup) {
            imageViewSetter.setIsRunning(false);
            try {
                retrieveImagesThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Slideshow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //imageList.clear();
        imageViewSetter = new ListOfImages(imageList, oldImageList);
        retrieveImagesTask = imageViewSetter.getImageViewList();
        retrieveImagesThread = new Thread(retrieveImagesTask);
        retrieveImagesThread.setDaemon(true);
        retrieveImagesThread.start();

        /*
         * Listening on ready signal from Task: retrieveImages
         */
        retrieveImagesTask.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                double timeStamp = slideshow.getCurrentTime().toMillis();
                int fadeTime = imageTrans.getFadeTime() * 2;
                double bilderVist = timeStamp / (delay + fadeTime);
                String[] split = newValue.split(" ");
                int numberOfImagesGenerated = Integer.parseInt(split[3]);
                if (numberOfImagesGenerated > bilderVist || split.length == 5) {
                    initiateNewSlideshow();
                }
            }
        });
    }

    public void initiateUpdatePictureTimerThread() {
        updatePictureTimerThread = new Thread(pictureTimerTask);
        updatePictureTimerThread.setDaemon(true);
        updatePictureTimerThread.start();

        pictureTimerTask.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!retrieveImagesThread.isAlive()) {
                    initiateRetrieveImagesThread();
                }
            }
        });
    }

    public Slideshow getSlideshowObject() {
        return this;
    }

    /*
     * Main function
     */
    public static void main(String[] args) {
        launch(args);
    }
}
