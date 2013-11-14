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
    
    private static int fadeTid =  1000;
    private static boolean getNewDelay = false;
    /*Delay i millisekunder*/
    private static int delay = 1;

    //Setter sammen fade inn, pause og fade out til en fullhverdig overgang
    public SequentialTransition getFullOvergang(ImageView imageView) {
        SequentialTransition fullOvergang = new SequentialTransition();
        fullOvergang.getChildren().addAll(getOvergangStart(imageView), getOvergangPause(), getOvergangStopp(imageView));
        return fullOvergang;
    }

    public static SequentialTransition getHalvOvergang(ImageView imageView) {
        SequentialTransition fullOvergang = new SequentialTransition();
        fullOvergang.getChildren().addAll(getOvergangStart(imageView));
        return fullOvergang;
    }

    public static SequentialTransition getPause() {
        SequentialTransition fullOvergang = new SequentialTransition();
        fullOvergang.getChildren().add(getOvergangPause());
        return fullOvergang;
    }

    //Fade inn, der fade effekt tar 2 sekunder
    public static FadeTransition getOvergangStart(ImageView imageView) {
        FadeTransition overgangStart = new FadeTransition(Duration.millis(fadeTid), imageView);
        overgangStart.setFromValue(0);
        overgangStart.setToValue(1);
        return overgangStart;
    }
    //Fade ut, der fade effekt varer i 2 sekunder 

    public static FadeTransition getOvergangStopp(ImageView imageView) {

        FadeTransition overgangStopp = new FadeTransition(Duration.millis(fadeTid), imageView);
        overgangStopp.setFromValue(1);
        overgangStopp.setToValue(0);
        return overgangStopp;
    }
    //Pause mellom fade effekter for å vise bildet. Tid gitt av getTidMellomBilder()

    public static PauseTransition getOvergangPause() {
        if(getNewDelay){
            delay = getTidMellomBilder();
            getNewDelay = false;
        }
            
        PauseTransition overgangPause = new PauseTransition(Duration.millis(delay));
        return overgangPause;
    }
    //Returnerer tid mellom bilder i millisec. Fungerer som en stub, da dette skal komme fra server i framtiden.

    public int getFadeTid(){
        return fadeTid;
    }
    
    public void setNewDelay(){
        getNewDelay = true;
    }
    
    public int getDelay(){
        return delay;
    }
    
    public static int getTidMellomBilder() {
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
