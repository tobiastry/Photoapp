package slideShow;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author mhovdan
 */
public class SlideShowWindow{
      Stage slideShowVindu = new Stage();
      
    public static Stage getSlideShowWindow(){
        Stage slideShowVindu = new Stage();
        slideShowVindu.initStyle(StageStyle.UNDECORATED);
        slideShowVindu.setFullScreen(true);
        slideShowVindu.setResizable(false);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        slideShowVindu.setX(primaryScreenBounds.getMinX());
        slideShowVindu.setY(primaryScreenBounds.getMinY());
        slideShowVindu.setWidth(primaryScreenBounds.getWidth());
        slideShowVindu.setHeight(primaryScreenBounds.getHeight());
        slideShowVindu.initStyle(StageStyle.UNDECORATED);
        slideShowVindu.setTitle("Ultimate slideshow");
        return slideShowVindu;
    }
}
