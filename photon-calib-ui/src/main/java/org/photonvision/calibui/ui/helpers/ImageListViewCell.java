package org.photonvision.calibui.ui.helpers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;


public class ImageListViewCell extends ListCell<ImageListViewData> {

    private Consumer<String> removeCallback = s -> {};

    private final Label imageTitle = new Label("Unknown File");
    private final Button deleteButton = new Button("Delete");
    private VBox imageContainer = new VBox();
    private final BorderPane container = new BorderPane();

    public ImageListViewCell() {
        imageTitle.setMaxWidth(Double.MAX_VALUE);
        imageTitle.setAlignment(Pos.CENTER);

        BorderPane.setAlignment(deleteButton, Pos.CENTER);
        deleteButton.setAlignment(Pos.CENTER);

        container.setRight(deleteButton);
        container.setCenter(imageContainer);
    }

    @Override
    protected void updateItem(ImageListViewData item, boolean empty) {
        if (empty) {
            setText("");
            setGraphic(null);
        } else {
            deleteButton.setOnMouseClicked((e) -> removeCallback.accept(item.title));

            imageTitle.setText(item.title);

            imageContainer = new VBox(item.image, imageTitle);
            container.setCenter(imageContainer);
            setGraphic(container);

//            var contextMenu = new ContextMenu();
//            var removeMenuItem = new MenuItem("Remove");
//            removeMenuItem.setOnAction((e) -> {
//                if (removeCallback != null) removeCallback.accept(item.title);
//                e.consume();
//            });
//            contextMenu.getItems().add(removeMenuItem);
//
//            setContextMenu(contextMenu);
        }
    }

    public void setOnRemoveClickCallback(Consumer<String> removeByTitleCallback) {
        removeCallback = removeByTitleCallback;
    }
}
