package slideShow;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author mhovdan
 */
public class ListOfImages {
    //Gir en liste med stier til bildefiler. Dette er en stubb som vil knyttes til server
    public static String[] getImageList()
    {
        //Temp bilder som er lagt inn
        String[] tempList = {
            "http://sportige.com/wp-content/uploads/2011/12/Warriors-Cheerleaders.jpg",
            "http://www.underconsideration.com/brandnew/archives/warriors_logo.gif",
            "http://a.espncdn.com/media/motion/2010/1018/com_101019nba_warriors_preview_1v.jpg",
            "Tulle link for å teste tempbilde",
            "http://api.ning.com/files/K6cyghYfeIuNpS*n0Tp23PF-lsh3vX0Aq90MQrFEcty*6YzkT3S0Z4qyXw5WvYir4NzVPYHrjm76K-mt2OR00tq3BrAl*fhJ/NBA_Playoffs_Golden_State_Warriors_Wallpapers.png",
            "http://sportige.com/wp-content/uploads/2011/12/Thunder-Cheerleaders-e1323953136526.jpg",
            "http://sportige.com/wp-content/uploads/2011/12/Rockets-Cheerleaders-e1323951549986.jpg",
            "http://sportige.com/wp-content/uploads/2011/12/Bulls-Cheerleaders-e1323950821246.jpg",
            "http://sportige.com/wp-content/uploads/2011/12/Bobcats-Cheeleaders-e1323950695383.jpg",
            "http://sportige.com/wp-content/uploads/2011/12/Celtics-Cheerleaders-e1323950598999.jpg"
        };
        return tempList;
    }
    //Lager en ImageView liste utifra string liste over, som brukes i slideshow
    public static ImageView[] getImageViewList()
    {
        ImageView tempImageView[] = new ImageView[getImageList().length];
        for (int i = 0; i<getImageList().length; i++)
        {
            try{
            tempImageView[i] = new ImageView(new Image(getImageList()[i]));
            } catch (Exception e){
                //Dersom link eller path ikke stemmer, så viser programmet et placeholder bilde.
                tempImageView[i] = new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg"));
            }
        }
        
        return tempImageView;
    }
    
}
