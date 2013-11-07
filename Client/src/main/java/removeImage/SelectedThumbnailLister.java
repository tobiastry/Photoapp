package removeImage;

import java.util.ArrayList;

/**
 *
 * @author Johan LG
 */
public class SelectedThumbnailLister {

    private ArrayList<Thumbnail> thumbnails;
    private ArrayList<String> urls;
    
    public SelectedThumbnailLister(ArrayList<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
        urls = new ArrayList<>();
    }
    
    public ArrayList<String> ListSelectedThumbnails(){
        urls.clear();
        for (Thumbnail thumbnail : thumbnails) {
            if(thumbnail.isSelected()){
                urls.add(thumbnail.getUrl());
            }
        }
        return urls;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }
}
