package slideShow;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author mhovdan
 */
public class SlideShowWindow extends Stage {

    /**
     * Creates the stage for the slideshow
     */
    public SlideShowWindow() {
        super();
        initStyle(StageStyle.UNDECORATED);
        setFullScreen(true);
        setTitle("Ultimate slideshow");
    }
}
