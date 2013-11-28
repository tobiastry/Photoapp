package slideShow;

import java.io.IOException;
import javafx.concurrent.Task;
import repository.DelayCom;

/**
 *
 * @author Emil
 */
public class CheckNewDelay {
    
    private int delay = 5;
    private boolean loop = true;
    
    public Task checkNewDelay(){
        return new Task(){
            @Override
            protected Object call() throws Exception {
                while(loop){
                    Thread.sleep(3000);
                    if(getDelay() != delay){
                        updateMessage("New delay: "+delay);
                    }
                }
                return true;
            }
            
        };
    }
    
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
