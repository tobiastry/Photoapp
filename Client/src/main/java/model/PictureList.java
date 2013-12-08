package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.RetrievePicturesCom;

/**
 *
 * @author Johan LG
 */
public class PictureList extends ArrayList<Picture> {

    private ArrayList<String> tags = new ArrayList();

    public PictureList() {
        RetrievePicturesCom retriver = new RetrievePicturesCom();
        try {
            addAll(retriver.getImageList());
        } catch (IOException ex) {
            Logger.getLogger(PictureList.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for (int i = 0; i < 200; i++) {
//            String p = "http://d3j5vwomefv46c.cloudfront.net/photos/thumb/415070" + (i / 100) + (i % 100) + ".jpg";
//            add(new Picture(p, p));
//        }

        //for testing:
        for (int i = 0; i < 4; i++) {
            get(i).setTag("tag1");
        }
        for (int i = 4; i < 7; i++) {
            get(i).setTag("tag2");
        }

        for (Picture p : this) {
            String tag = p.getTag();
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
        }
    }

    public ArrayList<Picture> getPictures() {
        return this;
    }

    /**
     * gets an ArrayList of Pictures whith a given tag
     *
     * @param tag
     * @return
     */
    public ArrayList<Picture> getPictures(String tag) {
        ArrayList<Picture> list = new ArrayList();
        for (Picture p : this) {
            if (p.getTag() == null ? tag == null : p.getTag().equals(tag)) {
                list.add(p);
            }
        }
        return list;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void updateImages() {
        RetrievePicturesCom retriver = new RetrievePicturesCom();
        try {
            addAll(retriver.getImageList());
        } catch (IOException ex) {
            Logger.getLogger(PictureList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
