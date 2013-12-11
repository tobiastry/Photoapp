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
    private static int delay = 0;

    public ImageTransition(){
       delay = getTimeBetweenImages();
    }
    
    // 
    /**
     * Combines fade in, fade out and pause to a complete transition
     * @param imageView
     * @return A full image transition
     */
    public SequentialTransition getFullTransition(ImageView imageView) {
        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().addAll(getTransitionStart(imageView), getTransitionPause(), getTransitionStop(imageView));
        return fullTransition;
    }
    
        /**
       * Combines fades in, fade out and pause to create an individual transition
       * for loading screen where pause time is not dependent on server
       * configuration
       * @param imageView
       * @return
       */
    public static SequentialTransition getLoadingScreenTransition(ImageView imageView) {
        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().addAll(getTransitionStart(imageView), new PauseTransition(Duration.millis(20000)), getTransitionStop(imageView));
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
        FadeTransition transitionStart = new FadeTransition(Duration.millis(fadeTime), imageView);
        transitionStart.setFromValue(0);
        transitionStart.setToValue(1);
        return transitionStart;
    }
    /**
     * Fade out, with fade time from fadeTime
     * @param imageView
     * @return FadeTransition for the fade out of images
     */
    public static FadeTransition getTransitionStop(ImageView imageView) {
        FadeTransition transitionStop = new FadeTransition(Duration.millis(fadeTime), imageView);
        transitionStop.setFromValue(1);
        transitionStop.setToValue(0);
        return transitionStop;
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
    public int getFadeTime(){
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
            if(delay == 0){
                delay = 10000;
            }
            return delay;
        }
        return d * 1000;
    }
}