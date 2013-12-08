package removeImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Picture;
import model.PictureList;
import repository.RetrievePicturesCom;
import menu.Menu;

/**
 *
 * @author Johan LG
 */
class ThumbnailLoader {

    private int thumbsLoaded = 0;

    /**
     * Loads a number of images given by number starting from the index from
     *
     * @param thumbnails
     * @param from
     * @param number
     */
    public void loadPictures(final ArrayList<SelectableThumbnail> thumbnails, final int from, final int number) {
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

    public void addPictures(ArrayList<SelectableThumbnail> thumbs) {
        for (int i = 0; i < thumbs.size(); i++) {
            thumbs.get(i).setPicture(Menu.getPictureList().get(i));
        }
    }

    public int imageListSize() {
        return Menu.getPictureList().size();
    }
}
