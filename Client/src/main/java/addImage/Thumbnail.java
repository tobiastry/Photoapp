package addImage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.Picture;

/**
 *
 * @author Johan LG
 */
public class Thumbnail extends StackPane {

    private ImageView imageView;
    private Image image;
    private Picture picture;
    private boolean loaded;

    public Thumbnail() {
        setPrefSize(150, 150);
        Rectangle frame = new Rectangle(152, 152);
        getChildren().add(frame);
        imageView = new ImageView();
        loaded = false;

        getChildren().addAll(imageView);
    }

    /**
     * Method for loading the Image from a given url
     *
     * @param pic
     */
    public void loadImage(/*Picture picture*/) {
        //this.setPicture(picture);
        if (!loaded) {
            image = new Image(picture.getThumbUrl());
            imageView.setImage(image);
            loaded = true;
        }
    }

    /**
     * @return the picture
     */
    public Picture getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}