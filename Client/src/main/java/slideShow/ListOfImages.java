package slideShow;

import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import repository.RetrievePicturesCom;

public class ListOfImages {

    ArrayList<ImageView> list;
    RetrievePicturesCom com;
    Slideshow slideshow;

    public ListOfImages(ArrayList<ImageView> list, Slideshow slideshow) {
        this.list = list;
        com = new RetrievePicturesCom();
        this.slideshow = slideshow;
    }

    //Creates and ArrayList of ImageViews from server    
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
                        //Incase of bad link, the image below will be used instead
                        list.add(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg")));
                    }
                }
                return true;
            }
        };
    }
}