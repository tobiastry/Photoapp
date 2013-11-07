package getImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import getImage.AddPictureGUI;
import model.Picture;
import imageGetters.*;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javafx.concurrent.Task;

public class AddPictureLogic {
        private ArrayList<Picture> pictureList = new ArrayList<Picture>();
        private InstagramGetter instaGetter;
        private TwitterGetter twitterGetter;
        private JsonArray jsonPictures;
        private List<JsonArray> testlist = new ArrayList<>();
        
        public AddPictureLogic(){
                instaGetter = new InstagramGetter();
                twitterGetter = new TwitterGetter();
        }

        public int getPictures(String tag) throws IOException {
                String url = instaGetter.instagramUrl(tag);
                testlist.add(instaGetter.findPictures(url));
                url = instaGetter.getNextUrl();
                System.out.println("Test "+url);
                testlist.add(instaGetter.findPictures(url));
            
                testlist.add(twitterGetter.findPictures(tag));
                return testlist.get(0).size()+testlist.get(1).size()+testlist.get(2).size();
        }
        
        public void addPictureToList(JsonElement j, int t){
            if(t==0){
                pictureList.add(instaGetter.addToList(j));
            }
            if(t==1){
                pictureList.add(instaGetter.addToList(j));
            }
            if(t==2){
                pictureList.add(twitterGetter.addToList(j));
            }
        }
        
        public List<Picture> getPictureList() {
                return pictureList;
        }
        
        public void exportList() {
                pictureList.clear();
        }

        public Task addPicturesToList(final String tag) {
                return new Task() {
                        @Override
                        protected Object call() throws Exception {
                                int size=getPictures(tag);
                                  int i=1;
                                AddPictureGUI.addingToList=true;
                                for(int t=0;t<testlist.size();t++){
                                    for(JsonElement j : testlist.get(t)) {
                                        Thread.sleep(50);
                                        addPictureToList(j,t);
                                        updateProgress(i, size);
                                        updateMessage(i+1+"/"+size);
                                        i++;
                                    }
                                }
                                testlist.clear();
                                exportList();
                                AddPictureGUI.addingToList=false;
                                return true;
                        }
                        @Override protected void succeeded() {
         super.succeeded();
         updateMessage("-Ferdig-");
        
         }
                        @Override protected void running() {
         super.running();
         updateMessage("Laster ned bilder");
         }
                       @Override protected void failed() {
         super.failed();
         updateMessage("Fant ingen bilder!");
         updateProgress(0, 0);
         }
                };
        }
}
