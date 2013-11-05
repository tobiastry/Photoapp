package removeImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import repository.RetrievePicturesCom;

/**
 *
 * @author Johan LG
 */
class ThumbnailLoader {

    private final ArrayList<Thumbnail> thumbnails;
    private ArrayList<String> urls;
    private int from = 0;

    ThumbnailLoader(ArrayList<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
        urls = new ArrayList<>();
        RetrievePicturesCom retriver = new RetrievePicturesCom();
        try {
            urls = retriver.getImageList();
        } catch (IOException ex) {
            Logger.getLogger(ThumbnailLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
//temp:
//        for (int i = 0; i < 100; i++) {
//           String p = "http://d3j5vwomefv46c.cloudfront.net/photos/thumb/4150708" + (i / 10) + (i % 10) + ".jpg";
//            urls.add(p);
//        }
    }

    
    //Loads pictures inn to 'thumbnails' from 'urls' starting at 'from'
    //Use loadPictures(24) to load the second page if a page has 24 thumbnails
    public void loadPictures(int from) {
        this.from = from;
        ImageListLoaderTask tl = new ImageListLoaderTask();
        Thread t = new Thread(tl);
        t.start();
    }

    public void loadPictures() {
        ImageListLoaderTask tl = new ImageListLoaderTask();
        Thread t = new Thread(tl);
        t.start();
    }
    
    public int imageListSize(){
        return urls.size();
    }

    private class ImageListLoaderTask extends Task {
        @Override
        protected Object call() throws Exception {
            for (int i = 0; i < thumbnails.size(); i++) {
                thumbnails.get(i).loadImage(urls.get(i + from));
            }
            return null;
        }
    }
}