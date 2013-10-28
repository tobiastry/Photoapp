package getImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import getImage.AddPictureLogic;
import model.Picture;
import getImage.UpdateThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class AddPictureGUI extends GridPane{
	Text textCounter;
	private AddPictureLogic logic = new AddPictureLogic();
	private List<Picture> pictureList = new ArrayList<Picture>();
	private  UpdateThread thread = logic.getThread();
	private Label statusLabel = new Label("");
	private Label progressLabel = new Label("");
	private ProgressBar progressBar = new ProgressBar(0);
	final TextField searchField = new TextField();

	public AddPictureGUI()  {
		setAlignment(Pos.CENTER);
		setHgap(12);
		setVgap(12);

		Label searchLabel = new Label("S�k bilder etter tag:");
		add(searchLabel, 0, 0);


		add(searchField, 1, 0);

		Button searchButton = new Button("Legg til");
		add(searchButton, 2, 0);
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setProgressText("0");
				setProgress(0);
				setStatusText("Searching");
				startScheduledExecutorService();
//				try {
//					logic.getPictures(searchField.getText());
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				logic.addPicturesToList();

			}
		});

		add(statusLabel, 0, 4);

		HBox progressLabelHbox = new HBox();
		progressLabelHbox.setAlignment(Pos.CENTER_RIGHT);
		progressLabelHbox.getChildren().add(progressLabel);
		add(progressLabelHbox, 2, 6);

		HBox pbHBox = new HBox();
		pbHBox.setAlignment(Pos.CENTER);
		progressBar.setPrefWidth(300);
		pbHBox.getChildren().add(progressBar);
		add(pbHBox, 0, 5, 3, 1);

	}



	public void setStatusText(String text) {
		statusLabel.setText(text);
	}

	public void setProgressText(String text) {
		progressLabel.setText(""+text);
	}


	public void setProgress(double progress) {
		progressBar.setProgress(progress);
	}


	public void startScheduledExecutorService() {

        final ScheduledExecutorService scheduler 
        = Executors.newScheduledThreadPool(1);

        scheduler.scheduleWithFixedDelay(
                        new Runnable(){

                                int counter = 0;

                                @Override
                                public void run() {
                                        counter++;
                                        if(counter==1){

                                                Platform.runLater(new Runnable(){
                                                        @Override
                                                        public void run() {
                                                                setStatusText("Searching");
                                                                try {
                                                                        logic.getPictures(searchField.getText());
                                                                } catch (IOException e) {
                                                                        // TODO Auto-generated catch block
                                                                        e.printStackTrace();
                                                                }
                                                                progressLabel.setText("Found: "+String.valueOf(thread.size()));
                                                        }
                                                });

                                                Platform.runLater(new Runnable(){
                                                        @Override
                                                        public void run() {
                                                                setStatusText("Adding Pictures");
                                                                logic.addPicturesToList();
                                                             
                                                                progressLabel.setText("Found: "+String.valueOf(logic.getPictureList().size()));
                                                                setProgress((double)logic.getPictureList().size());
                                                        }
                                                });
                                        }
                                        else{
                                                scheduler.shutdown();
                                                Platform.runLater(new Runnable(){
                                                        @Override
                                                        public void run() {
                                                                statusLabel.setText("-Finished-");
                                                        }
                                                });
                                        }

                                }

                        }, 
                        1, 
                        3, 
                        TimeUnit.SECONDS);
}
}