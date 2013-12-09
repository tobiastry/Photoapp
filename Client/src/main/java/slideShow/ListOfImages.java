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

    private ArrayList<ImageView> imageViewList;
    private RetrievePicturesCom com;
    private volatile boolean isRunning = true;
    private ArrayList<Picture> newImageList;
    private ArrayList<Picture> oldImageList;

    /**
     * ArrayList with Imageview Objects
     *
     * @param list
     */
    public ListOfImages(ArrayList<ImageView> list, ArrayList<Picture> oldImageList) {
        this.imageViewList = list;
        this.oldImageList = oldImageList;
        com = new RetrievePicturesCom();
    }

    //    
    /**
     * Extract image path from server, creates ImageViews and adds to ArrayList
     * If individual paths from server are incorrect, a "Image not found" image
     * is used instead However, this does not work when server does not return
     * any value, then relevant ImageView remains blank. Also, for some other
     * cases, replacement image does not work. I think this is due to path
     * access rights
     *
     * @return
     */
    public Task getImageViewList() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                newImageList = com.getImageList();
                /*
                 * Checking if Imagelist on server is different from imageList
                 * on client.
                 */
                if (newImageList.size() == oldImageList.size()) {
                    for (int i = 0; i < newImageList.size(); i++) {
                        if (!newImageList.get(i).getLargeUrl().equals(oldImageList.get(i).getLargeUrl())) {
                            break;
                        }else if(i == newImageList.size()-1){
                            System.out.println("No changes in image list from server");
                            return true;
                        }
                    }
                }
                imageViewList.clear();
                System.out.println("Gathering new Imges. Number of images on server: " + newImageList.size());
                for (int i = 0; i < newImageList.size(); i++) {

                    if (!isRunning) {
                        System.out.println("Ending task");
                        return true;
                    }

                    try {
                        imageViewList.add(new ImageView(new Image(newImageList.get(i).getLargeUrl())));
                        if (i % 10 == 0 && i != 0) {
                            updateMessage("Generate new slideshow " + i);
                        }
                    } catch (Exception e) {
                        //If path is wrong, it returns image below
                        imageViewList.add(new ImageView(new Image("http://cdn.panasonic.com/images/imageNotFound400.jpg")));
                    }
                }
                updateMessage("Generate new slideshow " + newImageList.size() + " SISTE");
                oldImageList.clear();
                oldImageList.addAll(newImageList);
                return true;
            }
        };
    }

    public void setIsRunning(boolean state) {
        isRunning = state;
    }
}