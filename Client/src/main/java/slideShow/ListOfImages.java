package slideShow;

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

    public ListOfImages(ArrayList<ImageView> list) {
        this.list = list;
        com = new RetrievePicturesCom();
    }

    //Lager en ImageView liste utifra string liste over, som brukes i slideshow    
    public Task getImageViewList() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                ArrayList<String> imageList = com.getLargeImageList();
                for (int i = 0; i < imageList.size(); i++) {
                    try {
                        System.out.println("Creating ImageView #" + i);
                        list.add(new ImageView(new Image(imageList.get(i))));
                        if (i%10 == 0 && i!=0) {
                            updateMessage("Generate new slideshow "+i);
                        }
                    } catch (Exception e) {
                        //Dersom link eller path ikke stemmer, så viser programmet et placeholder bilde.
                        list.add(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg")));
                    }
                }
                updateMessage("Generate new slideshow SISTE");
                return true;
            }
        };
    }
}