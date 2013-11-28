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

    //Compines fade in, fade out and pause to a complete transition 
    public SequentialTransition getFullTransition(ImageView imageView) {
        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().addAll(getTransitionStart(imageView), getTransitionPause(), GetTransitionStop(imageView));
        return fullTransition;
    }

    public static SequentialTransition getHalfTransition(ImageView imageView) {
        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().addAll(getTransitionStart(imageView));
        return fullTransition;
    }

    public static SequentialTransition getPause() {
        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().add(getTransitionPause());
        return fullTransition;
    }

    //Fade in, with 2 sec fade time
    public static FadeTransition getTransitionStart(ImageView imageView) {
        FadeTransition TransitionStart = new FadeTransition(Duration.millis(fadeTime), imageView);
        TransitionStart.setFromValue(0);
        TransitionStart.setToValue(1);
        return TransitionStart;
    }
    //Fade out, with 2 sec fade time 
    public static FadeTransition GetTransitionStop(ImageView imageView) {

        FadeTransition TransitionStop = new FadeTransition(Duration.millis(fadeTime), imageView);
        TransitionStop.setFromValue(1);
        TransitionStop.setToValue(0);
        return TransitionStop;
    }
    //Pause between fade in and fade out, to show image. Time given by getTimeBetweenImages()
    public static PauseTransition getTransitionPause() {
        if(getNewDelay){
            delay = getTimeBetweenImages();
            getNewDelay = false;
        }
            
        PauseTransition transistionPause = new PauseTransition(Duration.millis(delay));
        return transistionPause;
    }
    
    public int getFadeTid(){
        return fadeTime;
    }
    
    public void setNewDelay(){
        getNewDelay = true;
    }
    
    public int getDelay(){
        return delay;
    }
    
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
