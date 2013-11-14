package slideShow;

import java.io.IOException;
import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import repository.RetrievePicturesCom;

/**
 *
 * @author mhovdan
 */
public class ListOfImages {

    ArrayList<ImageView> list;
    RetrievePicturesCom com;
    Slideshow slideshow;

    public ListOfImages(ArrayList<ImageView> list, Slideshow slideshow) {
        this.list = list;
        com = new RetrievePicturesCom();
        this.slideshow = slideshow;
    }

    //Gir en liste med stier til bildefiler. Dette er en stubb som vil knyttes til server
    public static ArrayList getImageList() {
        //Temp bilder som er lagt inn
        ArrayList tempList = new ArrayList();
        tempList.add("http://sportige.com/wp-content/uploads/2011/12/Warriors-Cheerleaders.jpg");
        tempList.add("http://www.underconsideration.com/brandnew/archives/warriors_logo.gif");
        tempList.add("http://a.espncdn.com/media/motion/2010/1018/com_101019nba_warriors_preview_1v.jpg");
        tempList.add("Tulle link for å teste tempbilde");
        tempList.add("http://api.ning.com/files/K6cyghYfeIuNpS*n0Tp23PF-lsh3vX0Aq90MQrFEcty*6YzkT3S0Z4qyXw5WvYir4NzVPYHrjm76K-mt2OR00tq3BrAl*fhJ/NBA_Playoffs_Golden_State_Warriors_Wallpapers.png");
        tempList.add("http://sportige.com/wp-content/uploads/2011/12/Thunder-Cheerleaders-e1323953136526.jpg");
        tempList.add("http://sportige.com/wp-content/uploads/2011/12/Rockets-Cheerleaders-e1323951549986.jpg");
        tempList.add("http://sportige.com/wp-content/uploads/2011/12/Bulls-Cheerleaders-e1323950821246.jpg");
        tempList.add("http://sportige.com/wp-content/uploads/2011/12/Bobcats-Cheeleaders-e1323950695383.jpg");
        tempList.add("http://sportige.com/wp-content/uploads/2011/12/Celtics-Cheerleaders-e1323950598999.jpg");

        return tempList;
    }
    //Lager en ImageView liste utifra string liste over, som brukes i slideshow

    public Task getImageViewList() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int teller = 0;
                ArrayList<String> imageList = com.getLargeImageList();
                //ArrayList<String> imageList = getImageList();
                for (int i = 0; i < imageList.size(); i++) {
                    try {
                        teller++;
                        if (teller%10 == 0) {
                            slideshow.initiateNewSlideshow();
                            break;
                        }
                        System.out.println("Creating ImageView #" + teller);
                        list.add(new ImageView(new Image(imageList.get(i))));
                    } catch (Exception e) {
                        //Dersom link eller path ikke stemmer, så viser programmet et placeholder bilde.
                        list.add(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg")));
                    }
                }
                return true;
            }
        };
    }
}