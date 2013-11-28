package addImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Picture;
import imageGetters.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javafx.concurrent.Task;
import repository.StorePicturesCom;

public class AddImageLogic {
    //32104414142school

    private ArrayList<Picture> pictureList = new ArrayList<>();
    private List<JsonArray> jsonArrayList = new ArrayList<>();
    private InstagramGetter instaGetter = null;
    private TwitterGetter twitterGetter = null;
    public String failedMsg = "Fant ingen bilder";
    private String tag = null;
    private int picturesFound = 0;
    private int twitterPicFound = 0;
    private int instagramPicFound = 0;
    private JsonPrimitive fromSource = null;
    private final int pictureLimit = 30;

    private int getPictures(String tag) throws IOException {
        instaGetter = new InstagramGetter();
        twitterGetter = new TwitterGetter();
        instagramPicFound = getSizeAndAdd(instaGetter.findPictures(instaGetter.toUrl(tag)), "Instagram");
        twitterPicFound = getSizeAndAdd(twitterGetter.findPictures(twitterGetter.toUrl(tag)), "Twitter");

        picturesFound = getMore();
        return picturesFound;
    }

    private int getSizeAndAdd(JsonArray jsonList, String source) {
        if (jsonList.size() != 0) {
            fromSource = new JsonPrimitive(source);
            jsonList.add(fromSource);
            jsonArrayList.add(jsonList);
            picturesFound += jsonList.size();
        }
        return jsonList.size();
    }

    private int getMore() throws IOException {
        if (instagramPicFound != 0 && picturesFound <= pictureLimit) {
            String next_url = instaGetter.getNextUrl();
            if (next_url != null) {
                instagramPicFound = getSizeAndAdd(instaGetter.findPictures(next_url), "Instagram");
            }
        }
        if (twitterPicFound != 0 && picturesFound <= pictureLimit) {
            String next_url = twitterGetter.getNextUrl();
            if (next_url != null) {
                twitterPicFound = getSizeAndAdd(twitterGetter.findPictures(next_url), "Twitter");
            }
        }
        if (picturesFound <= pictureLimit && (instagramPicFound + twitterPicFound) != 0) {
            return getMore();
        }
        return picturesFound;
    }

    private void addPictureToList(JsonElement j, String source) {
        switch (source) {
            case "Instagram": {
                Picture picture = instaGetter.addToList(j);
                picture.tag = tag;
                pictureList.add(picture);
                System.out.println(picture.largeUrl);
                break;
            }
            case "Twitter": {
                Picture picture = twitterGetter.addToList(j);
                picture.tag = tag;
                pictureList.add(picture);
                System.out.println(picture.largeUrl);
                break;
            }
        }

    }

    private boolean exportList() throws IOException {
        StorePicturesCom store = new StorePicturesCom();
        if (store.storePictures(pictureList) != 200) {
            jsonArrayList.clear();
            pictureList.clear();
            return false;
        } else {
            jsonArrayList.clear();
            pictureList.clear();
            return true;
        }
    }

    public Task findPicturesTask(final String tag) {
        this.tag = tag;
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int size = getPictures(tag);
                int i = 1;
                AddImageGUI.addingToList = true;
                for (int t = 0; t < jsonArrayList.size(); t++) {
                    String source = jsonArrayList.get(t).get(jsonArrayList.get(t).size() - 1).getAsString();
                    for (int j = 0; j < jsonArrayList.get(t).size() - 1; j++) {
                        JsonElement json = jsonArrayList.get(t).get(j);
                        Thread.sleep(50);
                        System.out.println(source);
                        addPictureToList(json, source);
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
                    picturesFound = 0;
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
                picturesFound = 0;
                jsonArrayList.clear();
                pictureList.clear();
                updateMessage(failedMsg);
                updateProgress(0, 0);
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                picturesFound = 0;
                jsonArrayList.clear();
                pictureList.clear();
                updateMessage("");
                updateProgress(0, 0);
            }
        };
    }
}
