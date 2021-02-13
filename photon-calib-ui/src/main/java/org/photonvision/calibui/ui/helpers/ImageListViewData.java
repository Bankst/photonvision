package org.photonvision.calibui.ui.helpers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageListViewData {
    public final String title;
    public final ImageView image = new ImageView();

    public ImageListViewData(String title, Image image) {
        this.title = title;
        this.image.setImage(image);
    }
}
