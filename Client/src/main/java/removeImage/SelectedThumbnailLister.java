
package removeImage;

import java.util.ArrayList;

/**
 *
 * @author Johan LG
 */
public class SelectedThumbnailLister {

    ArrayList<Thumbnail> thumbnails;
    ArrayList<String> urls;
    
    public SelectedThumbnailLister(ArrayList<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
        urls = new ArrayList<>();
    }
    
    public void ListSelectedThumbnails(){
        urls.clear();
        for (Thumbnail thumbnail : thumbnails) {
            if(thumbnail.isSelected()){
                urls.add(thumbnail.getUrl());
            }
        }
    }
}
