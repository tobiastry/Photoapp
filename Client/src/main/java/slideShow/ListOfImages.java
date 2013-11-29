package slideShow;

import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Picture;
import repository.RetrievePicturesCom;

/**
 *
 * @author mhovdan
 */
public class ListOfImages {

    private ArrayList<ImageView> list;
    private RetrievePicturesCom com;
    private volatile boolean isRunning = true;
    private ArrayList<Picture> imageList;

    /**
     * ArrayList with Imageview Objects
     * @param list
     */
    public ListOfImages(ArrayList<ImageView> list) {
        this.list = list;
        com = new RetrievePicturesCom();
    }

    //    
    /**
     * Extract image path from server, creates ImageViews and adds to ArrayList
     * If individual paths from server are incorrect, a "Image not found" image is used instead
     * However, this does not work when server does not return any value, then relevant ImageView remains blank.
     * Also, for some other cases, replacement image does not work. I think this is due to path access rights
     * @return
     */
    public Task getImageViewList() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                imageList = com.getImageList();
                for (int i = 0; i < imageList.size(); i++) {
                   
                    if(!isRunning){
                        System.out.println("Ending task");
                        return true;
                    }
                    
                    try {
                        System.out.println("Creating ImageView #" + i);
                        list.add(new ImageView(new Image(imageList.get(i).getLargeUrl())));
                        if (i%10 == 0 && i!=0) {
                            updateMessage("Generate new slideshow "+i);
                        }
                    } catch (Exception e) {
                        //If path is wrong, it returns image below
                        list.add(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg")));
                    }
                }
                updateMessage("Generate new slideshow SISTE");
                return true;
            }
        };
    }
    
    public void setIsRunning(boolean state){
        isRunning = state;
    }
}