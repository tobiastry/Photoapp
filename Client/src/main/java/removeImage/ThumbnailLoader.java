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

    private ArrayList<Picture> images;
    private int thumbsLoaded = 0;

    ThumbnailLoader(ArrayList<Thumbnail> thumbnails) {
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
     * Loads a number of images given by number starting from the index from
     *
     * @param thumbnails
     * @param from
     * @param number
     */
    public void loadPictures(final ArrayList<Thumbnail> thumbnails, final int from, final int number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (from + number < imageListSize()) {
                    if (from >= thumbsLoaded) {
                        thumbsLoaded += number;
                        for (int i = from; i < from + number; i++) {
                            thumbnails.get(i).loadImage();
                        }
                    }
                } else {
                    for (int i = from; i < imageListSize(); i++) {
                        thumbnails.get(i).loadImage();
                    }
                    thumbsLoaded += (imageListSize() - from);
                }
            }
        }).start();
    }

    public void addPictures(ArrayList<Thumbnail> thumbs) {
        for (int i = 0; i < thumbs.size(); i++) {
            thumbs.get(i).setPicture(images.get(i));
        }
    }

    public int imageListSize() {
        return images.size();
    }

    public void updateImages() {
        RetrievePicturesCom retriver = new RetrievePicturesCom();
        try {
            images = retriver.getImageList();
        } catch (IOException ex) {
            Logger.getLogger(ThumbnailLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
