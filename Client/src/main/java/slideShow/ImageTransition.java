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
    
    //Setter sammen fade inn, pause og fade out til en fullhverdig overgang
    public static SequentialTransition getFullOvergang(ImageView imageView){
        SequentialTransition fullOvergang = new SequentialTransition();
        fullOvergang.getChildren().addAll(getOvergangStart(imageView), getOvergangPause(), getOvergangStopp(imageView));
        return fullOvergang;
    }
    public static SequentialTransition getHalvOvergang(ImageView imageView){
    SequentialTransition fullOvergang = new SequentialTransition();
    fullOvergang.getChildren().addAll(getOvergangStart(imageView));
    return fullOvergang;
    }
    public static SequentialTransition getPause(){
    SequentialTransition fullOvergang = new SequentialTransition();
    fullOvergang.getChildren().add(getOvergangPause());
    return fullOvergang;
    }
    
    //Fade inn, der fade effekt tar 2 sekunder
    public static FadeTransition getOvergangStart(ImageView imageView){

        FadeTransition overgangStart = new FadeTransition(Duration.millis(2000), imageView);
        overgangStart.setFromValue(0);
        overgangStart.setToValue(1);
        return overgangStart;
    }
    //Fade ut, der fade effekt varer i 2 sekunder 
    public static FadeTransition getOvergangStopp(ImageView imageView){

        FadeTransition overgangStopp = new FadeTransition(Duration.millis(2000), imageView);
        overgangStopp.setFromValue(1);
        overgangStopp.setToValue(0);
        return overgangStopp;
    }
    //Pause mellom fade effekter for å vise bildet. Tid gitt av getTidMellomBilder()
    public static PauseTransition getOvergangPause(){

        PauseTransition overgangPause = new PauseTransition(Duration.millis(getTidMellomBilder()));
        return overgangPause;
    }
    //Returnerer tid mellom bilder i millisec. Fungerer som en stub, da dette skal komme fra server i framtiden.
    public static int getTidMellomBilder(){
        DelayCom delay = new DelayCom();
        int d;
        try {
            d = delay.getDelay();
        } catch (IOException ex) {
            //Delay in seconds if delay can't be retrieved    
            return 5;
        }
        return d*1000;
    }
}
