package slideShow;

import java.io.IOException;
import javafx.concurrent.Task;
import repository.DelayCom;

/**
 *
 * @author Emil
 */
public class CheckNewDelay {
    
    private int delay = getDelay();
    private boolean loop = true;
    private int fadeTime;
    
    /*
     * Checks to see if the delay has been changed every 3 seconds.
     * If it has, it runs updateMessage(), which Slideshow listens to.
     */
    
    public CheckNewDelay(int fadeTimeInSec){
            fadeTime = fadeTimeInSec*2;
}
    
    public Task checkNewDelay(){
        return new Task(){
            @Override
            protected Object call() throws Exception {
                while(loop){
                    Thread.sleep(3000);
                    int newDelay = getDelay();
                    if(newDelay != delay){
                        double diffFactor = ((double)newDelay+fadeTime)/((double)delay+fadeTime);
                        delay = newDelay;
                        updateMessage("New delay: "+delay+" DiffFactor: "+diffFactor);
                        
                    }
                }
                return true;
            }
            
        };
    }
    
    /*
     * Retrieves delay from server.
     */
     public static int getDelay() {
        DelayCom tempDelay = new DelayCom();
        int d;
        try {
            d = tempDelay.getDelay();
        } catch (IOException ex) {
            //Delay in seconds if delay can't be retrieved    
            return 5;
        }
        return d;
    }
}
