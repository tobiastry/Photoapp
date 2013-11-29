package removeImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Picture;
import repository.RetrievePicturesCom;

/**
 *
 * @author Johan LG
 */
class ThumbnailLoader {

    //private final ArrayList<Thumbnail> thumbnails;
    private ArrayList<Picture> images;
    private int thumbsLoaded = 0;

    ThumbnailLoader(ArrayList<Thumbnail> thumbnails) {
        //this.thumbnails = thumbnails;
        RetrievePicturesCom retriver = new RetrievePicturesCom();
        try {
            images = retriver.getImageList();
        } catch (IOException ex) {
            Logger.getLogger(ThumbnailLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
//for testing if server is down:
        /*for (int i = 0; i < 200; i++) {
         String p = "http://d3j5vwomefv46c.cloudfront.net/photos/thumb/415070" + (i / 100) + (i % 100) + ".jpg";
         urls.add(p);
         }*/
    }

    /**
     * Loads a number of images given by numberOfImages starting from the index
     * fromIndex
     *
     * @param fromIndex
     * @param numberOfImages
     */
    public void loadPictures(final ArrayList<Thumbnail> thumbnails, final int from, final int number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (from + number < imageListSize()) {
                    if (from + 1 > thumbsLoaded) {
                        thumbsLoaded += number;
                        for (int i = from; i < from + number; i++) {
                            if (images.get(i).getThumbUrl().endsWith(".jpg")) {
                                thumbnails.get(i).loadImage(images.get(i));
                            }
                        }
                    }
                } else {
                    for (int i = from; i < imageListSize() - 1; i++) {
                        if (images.get(i).getThumbUrl().endsWith(".jpg")) {
                            thumbnails.get(i).loadImage(images.get(i));
                        }
                    }
                    thumbsLoaded += (imageListSize() - from);
                }
            }
        }).start();
    }

    public int imageListSize() {
        return images.size();
    }
    
    public void updateImages(){
        RetrievePicturesCom retriver = new RetrievePicturesCom();
        try {
            images = retriver.getImageList();
        } catch (IOException ex) {
            Logger.getLogger(ThumbnailLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
