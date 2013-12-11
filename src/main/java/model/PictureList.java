package model;

import addImage.Thumbnail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.RetrievePicturesCom;
import repository.TagCom;

/**
 *
 * @author Johan LG
 */
public class PictureList extends ArrayList<Picture> {

    private ArrayList<String> tags = new ArrayList();
    private Map<Picture, Thumbnail> thumbnails = new HashMap<>();
    private RetrievePicturesCom retriver;
    private TagCom tagCom;

    public PictureList() {
        retriver = new RetrievePicturesCom();
        update();

    }

    public void update() {
        ArrayList<Picture> newList = new ArrayList();
        tagCom = new TagCom();
        try {
            newList.addAll(retriver.getImageList());
            clear();
            addAll(newList);
            tags = tagCom.getTags();
        } catch (IOException ex) {
            Logger.getLogger(PictureList.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Picture p : this) {
            if (!thumbnails.containsKey(p)) {
                Thumbnail tn = new Thumbnail();
                tn.setPicture(p);
                thumbnails.put(p, tn);
            }
        }

//        for (int i = 0; i < 200; i++) {
//            String p = "http://d3j5vwomefv46c.cloudfront.net/photos/thumb/415070" + (i / 100) + (i % 100) + ".jpg";
//            add(new Picture(p, p));
//        }

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

    public ArrayList<Thumbnail> getThumbnails(String tag) {
        ArrayList<Thumbnail> thumbs = new ArrayList();
        for (Picture p : getPictures(tag)) {
            thumbs.add(thumbnails.get(p));
        }

        return thumbs;
    }

    public ArrayList<String> getTags() {
        try {
            tags = tagCom.getTags();
        } catch (IOException ex) {
            Logger.getLogger(PictureList.class.getName()).log(Level.SEVERE, null, ex);
        }
//        tags.clear();
//        for (Picture p : this) {
//            String tag = p.getTag();
//            if (!tags.contains(tag)) {
//                tags.add(tag);
//            }
//        }
        return tags;
    }
}
