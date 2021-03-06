package addImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Picture;
import imageGetters.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import javafx.concurrent.Task;
import repository.AuthenticationTwitter;
import repository.StorePicturesCom;

/**
 *
 * @author T
 */
public class AddImageLogic {

    private ArrayList<Picture> pictureList = new ArrayList<>();
    private List<JsonArray> jsonArrayList = new ArrayList<>();
    private InstagramGetter instaGetter = null;
    private InstagramParser instaParser = null;
    private TwitterGetter twitterGetter = null;
    private TwitterParser twitterParser = null;
    private AuthenticationTwitter Auth = null;
    public String failedMsg = "Fant ingen bilder";
    private String tag = null;
    private int picturesFound = 0;
    private int twitterPicFound = 0;
    private int instagramPicFound = 0;
    private JsonPrimitive fromSource = null;
    private final int pictureLimit = 100;
    private int picCountTmp = 0;
    private String minTagID = null;
    String bearerToken = null;

    public String getMinTagID() {
        return minTagID;
    }

    /**
     * Takes a tag, finds pictures from Instagram and Twitter, returns amount of
     * pictures found.
     *
     * @param tag (String)
     * @return picturesFound (Integer)
     * @throws IOException
     */
    private int getPictures(String tag) throws IOException {
        initiate();
        String instaUrl = instaGetter.toUrl(tag);
        String twitterUrl = twitterGetter.toUrl(tag);
        if (instaUrl == null || twitterUrl == null) {
            failedMsg = "Ugyldig tag";
        }
        bearerToken = Auth.requestBearerToken();
        instagramPicFound = getSizeAndAdd(instaGetter.findPictures(instaUrl), "Instagram");
        if (bearerToken != null) {
            twitterPicFound = getSizeAndAdd(twitterGetter.findPictures(twitterUrl, bearerToken), "Twitter");
        }
        minTagID = instaGetter.getMinID();
        picturesFound = getMore();
        return picturesFound;
    }

    private void initiate() {
        instaGetter = new InstagramGetter();
        instaParser = new InstagramParser();
        twitterGetter = new TwitterGetter();
        twitterParser = new TwitterParser();
        Auth = new AuthenticationTwitter();
    }

    /**
     * Finds the size and adds the source(Twitter/Instagram) of the JsonArray,
     * returns size of array.
     *
     * @param jsonList (JsonArray)
     * @param source (String)
     * @return jsonList size (Integer)
     */
    private int getSizeAndAdd(JsonArray jsonList, String source) {
        if (jsonList.size() != 0) {
            fromSource = new JsonPrimitive(source);
            jsonList.add(fromSource);
            jsonArrayList.add(jsonList);
            picturesFound += jsonList.size() - 1;
        }
        return jsonList.size();
    }

    /**
     * Makes sure we get all or enough pictures, returns amount of pictures
     * found.
     *
     * @return picturesFound (Integer)
     * @throws IOException
     */
    private int getMore() throws IOException {
        if (instagramPicFound != 0 && picturesFound <= pictureLimit) {
            String next_url = instaGetter.getNextUrl();
            if (next_url != null) {
                instagramPicFound = getSizeAndAdd(instaGetter.findPictures(next_url), "Instagram");
            }
        }
        if (twitterPicFound != 0 && picturesFound <= pictureLimit) {
            String next_url = twitterGetter.getNextUrl();
            if (next_url != null && bearerToken != null) {
                twitterPicFound = getSizeAndAdd(twitterGetter.findPictures(next_url, bearerToken), "Twitter");
            }
        }
        if (picturesFound <= pictureLimit && (instagramPicFound + twitterPicFound) != picCountTmp) {
            picCountTmp = instagramPicFound + twitterPicFound;
            return getMore();
        }
        return picturesFound;
    }

    /**
     * Decides where the JsonElements source, finds the url and adds to list.
     *
     * @param j (JsonElement)
     * @param source (String)
     */
    private void addPictureToList(JsonElement j, String source) {
        switch (source) {
            case "Instagram": {
                Picture picture = instaParser.addToList(j);
                picture.setTag(tag);
                pictureList.add(picture);
                break;
            }
            case "Twitter": {
                Picture picture = twitterParser.addToList(j);
                picture.setTag(tag);
                pictureList.add(picture);
                break;
            }
        }
    }

    /**
     * Sends the list to server for storage.
     *
     * @return boolean
     * @throws IOException
     */
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

    /**
     * Takes a tag, starts a new task which finds pictures from Instagram and
     * Twitter, and updates GUI.
     *
     * @param tag (string)
     * @return Task
     */
    public Task findPicturesTask(final String tag) {
        this.tag = tag;
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int size = getPictures(tag);
                updateMessage(getMinTagID());
                if (size > pictureLimit) {
                    int tmp = size - pictureLimit;
                    size = size - tmp;
                }
                if (size != 0) {
                    int i = 1;
                    AddImageGUI.addingToList = true;
                    for (int t = 0; t < jsonArrayList.size(); t++) {
                        String source = jsonArrayList.get(t).get(jsonArrayList.get(t).size() - 1).getAsString();
                        for (int j = 0; j < jsonArrayList.get(t).size() - 1; j++) {
                            JsonElement json = jsonArrayList.get(t).get(j);
                            Thread.sleep(10);
                            addPictureToList(json, source);
                            updateProgress(i, size);
                            if (i >= size) {
                                updateProgress(size, size);
                                break;
                            }
                            i++;
                        }
                    }

                    if (!exportList()) {
                        AddImageGUI.addingToList = false;
                        failedMsg = "Klarte ikke legge bilder inn pÃ¥ server";
                        failed();
                        return false;
                    } else {
                        picturesFound = 0;
                        AddImageGUI.addingToList = false;
                        return true;
                    }
                } else {
                    AddImageGUI.addingToList = false;
                    failedMsg = "Fant ingen bilder";
                    failed();
                    return false;
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
