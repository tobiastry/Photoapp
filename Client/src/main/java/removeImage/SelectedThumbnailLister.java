package removeImage;

import java.util.ArrayList;
import model.Picture;

/**
 *
 * @author Johan LG
 */
public class SelectedThumbnailLister {

    private ArrayList<Thumbnail> thumbnails;
    private ArrayList<Picture> images;
    
    public SelectedThumbnailLister(ArrayList<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
        images = new ArrayList();
    }
    
    public ArrayList<Picture> ListSelectedThumbnails(){
        images.clear();
        for (Thumbnail thumbnail : thumbnails) {
            if(thumbnail.isSelected()){
                images.add(thumbnail.getPicture());
            }
        }
        return images;
    }

}
