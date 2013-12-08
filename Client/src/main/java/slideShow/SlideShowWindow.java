package slideShow;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author mhovdan
 */
public class SlideShowWindow{
      Stage slideShowVindu = new Stage();
      
    /**
     * Measures the screen program is started on, and sets window size accordingly
     * It also removes menu bars and resize options
     * @return This is the main stage window, used for the slideshow
     */
    public static Stage getSlideShowWindow(){
        Stage slideShowVindu = new Stage();
        slideShowVindu.initStyle(StageStyle.UNDECORATED);
        slideShowVindu.setFullScreen(true);
        slideShowVindu.setTitle("Ultimate slideshow");
        return slideShowVindu;
    }
}
