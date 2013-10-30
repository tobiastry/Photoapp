package getImage;

import model.Picture;
import imageGetters.InstagramGetter;
import imageGetters.TwitterGetter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class AddPictureLogic {
	private List<Picture> pictureList = new ArrayList<Picture>();
	private TwitterGetter twitGetter;
	private  InstagramGetter instaGetter;
	private UpdateThread thread = new UpdateThread();
	
	public AddPictureLogic(){
//		thread.start();
		twitGetter = new TwitterGetter(getThread());
		instaGetter = new InstagramGetter(getThread());
//		getPictures("nofilter");
	}
	
	public int getPictures(String tag) throws IOException {
//		getThread().start();
		twitGetter.getPictures(tag);
		instaGetter.getPictures(tag);
//		addPicturesToList();
		return 0;
	}

	public void addPicturesToList(){
		twitGetter.addToList();
		instaGetter.addToList();
	}

	public List<Picture> getPictureList() {
		return thread.getList();
	}

	public UpdateThread getThread() {
		return thread;
	}

	public void setThread(UpdateThread thread) {
		this.thread = thread;
	}


}




