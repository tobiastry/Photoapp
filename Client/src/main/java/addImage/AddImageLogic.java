package addImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Picture;
import imageGetters.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javafx.concurrent.Task;
import repository.StorePicturesCom;

public class AddImageLogic {

    private ArrayList<Picture> pictureList = new ArrayList<>();
    private List<JsonArray> jsonArrayList = new ArrayList<>();
    private InstagramGetter instaGetter = null;
    private TwitterGetter twitterGetter = null;
    private String failedMsg = null;
    private int picturesFound = 0;

    private int getPictures(String tag) throws IOException {
        instaGetter = new InstagramGetter();
        twitterGetter = new TwitterGetter();
        getSizeAndAdd(instaGetter.findPictures(instaGetter.toUrl(tag)));
        getSizeAndAdd(twitterGetter.findPictures(twitterGetter.toUrl(tag)));
        picturesFound += addMore();
        return picturesFound;
    }

    private int getSizeAndAdd(JsonArray jsonList) {
        if(jsonList.size()!=0){
            jsonArrayList.add(jsonList);
            picturesFound+=jsonList.size();
        }
        return jsonList.size();
    }

    private int addMore() throws IOException {
        if (picturesFound <= 90 && 
                getSizeAndAdd(instaGetter.findPictures(instaGetter.getNextUrl())) != 0 && 
                getSizeAndAdd(twitterGetter.findPictures(twitterGetter.getNextUrl())) != 0) {
            return addMore();
        }
        return picturesFound;

    }

    private void addPictureToList(JsonElement j, int t) {
        if (t == 0) {
            pictureList.add(instaGetter.addToList(j));
        }
        if (t == 1) {
            pictureList.add(instaGetter.addToList(j));
        }
        if (t == 2) {
            pictureList.add(twitterGetter.addToList(j));
        }
    }

    private boolean exportList() throws IOException {
        StorePicturesCom store = new StorePicturesCom();
        if (store.storePictures(pictureList) != 200) {
            jsonArrayList.clear();
            pictureList.clear();
            return false;
        }
        jsonArrayList.clear();
        pictureList.clear();
        return true;
    }

    public Task findPicturesTask(final String tag) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int size = getPictures(tag);
                int i = 1;
                AddImageGUI.addingToList = true;
                for (int t = 0; t < jsonArrayList.size(); t++) {
                    for (JsonElement j : jsonArrayList.get(t)) {
                        Thread.sleep(50);
                        addPictureToList(j, t);
                        updateProgress(i, size);
                        updateMessage(i + "/" + size);
                        i++;
                    }
                }
                if (!exportList()) {
                    AddImageGUI.addingToList = false;
                    failedMsg = "Klarte ikke legge bilder inn på server";
                    return false;
                } else {
                    AddImageGUI.addingToList = false;
                    return true;
                }
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("-Ferdig-");

            }

            @Override
            protected void running() {
                super.running();
                updateMessage("Laster ned bilder");
            }

            @Override
            protected void failed() {
                super.failed();
                updateMessage(failedMsg);
                updateProgress(0, 0);
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                updateMessage("");
                updateProgress(0, 0);
            }
        };
    }
}
