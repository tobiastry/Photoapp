package slideShow;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import repository.DelayCom;

/**
 *
 * @author mhovdan
 */
public class ImageTransition {
    
    private static int fadeTime =  1000;
    private static boolean getNewDelay = false;
    /*Delay in milliseconds*/
    private static int delay = 1;

    // 
    /**
     * Combines fade in, fade out and pause to a complete transition
     * @param imageView
     * @return A full image transition
     */
    public SequentialTransition getFullTransition(ImageView imageView) {
        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().addAll(getTransitionStart(imageView), getTransitionPause(), GetTransitionStop(imageView));
        return fullTransition;
    }

    /**
     *
     * @return
     */
    public static SequentialTransition getPause() {
        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().add(getTransitionPause());
        return fullTransition;
    }

    /**
     * 
     * @param imageView
     * @return FadeTransition for the fade in of images
     */
    public static FadeTransition getTransitionStart(ImageView imageView) {
        FadeTransition TransitionStart = new FadeTransition(Duration.millis(fadeTime), imageView);
        TransitionStart.setFromValue(0);
        TransitionStart.setToValue(1);
        return TransitionStart;
    }
    /**
     * Fade out, with fade time from fadeTime
     * @param imageView
     * @return FadeTransition for the fade out of images
     */
    public static FadeTransition GetTransitionStop(ImageView imageView) {

        FadeTransition TransitionStop = new FadeTransition(Duration.millis(fadeTime), imageView);
        TransitionStop.setFromValue(1);
        TransitionStop.setToValue(0);
        return TransitionStop;
    }
    //
    /**
     * Pause between fade in and fade out, to show image. Time given by getTimeBetweenImages()
     * @return 
     */
    public static PauseTransition getTransitionPause() {
        if(getNewDelay){
            delay = getTimeBetweenImages();
            getNewDelay = false;
        }
            
        PauseTransition transistionPause = new PauseTransition(Duration.millis(delay));
        return transistionPause;
    }
    
    /**
     * Self explanatory
     * @return
     */
    public int getFadeTid(){
        return fadeTime;
    }
    
    /**
     * Self explanatory
     */
    public void setNewDelay(){
        getNewDelay = true;
    }
    
    /**
     * Self explanatory
     * @return
     */
    public int getDelay(){
        return delay;
    }
    
    /**
     * Connects to server and returns the current delay. 
     * If it can not connect to the server it returns 5 sec delay
     */
    public static int getTimeBetweenImages() {
        DelayCom tempDelay = new DelayCom();
        int d;
        try {
            d = tempDelay.getDelay();
        } catch (IOException ex) {
            //Delay in seconds if delay can't be retrieved    
            return 5;
        }
        return d * 1000;
    }
}
