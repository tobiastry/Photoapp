package slideShow;

import javafx.concurrent.Task;
import static slideShow.CheckNewDelay.getDelay;

/**
 *
 * @author Emil
 */
public class UpdatePictureListTimer {
    
    private boolean loop = true;
    
    public Task timerTask(){
        return new Task(){
            @Override
            protected Object call() throws Exception {
                int teller = 0;
                while(loop){
                    Thread.sleep(30000);
                    updateMessage(Integer.toString(teller));
                    teller++;
                }
                return true;
            }
            
        };
    }
}
