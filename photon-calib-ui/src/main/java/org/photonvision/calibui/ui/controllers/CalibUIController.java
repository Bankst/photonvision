package org.photonvision.calibui.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.photonvision.calibui.CVFXUtils;
import org.photonvision.calibui.ui.helpers.ImageListViewCell;
import org.photonvision.calibui.ui.helpers.ImageListViewData;
import org.photonvision.common.logging.LogGroup;
import org.photonvision.common.logging.Logger;
import org.photonvision.vision.frame.provider.FileFrameProvider;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class CalibUIController {

    private final Logger logger = new Logger(CalibUIController.class, LogGroup.General);

    private final FileChooser.ExtensionFilter imageExtFilter = new FileChooser.ExtensionFilter("Image files", ".jpg", ".jpeg", ".png", ".bmp");

    @FXML
    private ListView<ImageListViewData> inputImageListView;
    private Stage stage;

    private final HashMap<String, FileFrameProvider> inputImageProviders = new HashMap<>();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void openImageFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Chessboard/Dotboard Images");
        chooser.setSelectedExtensionFilter(imageExtFilter);

        loadImages(chooser.showOpenMultipleDialog(stage));
    }

    private void loadImages(List<File> imageFiles) {
        for (var imageFile : imageFiles) {
            if (imageFile.exists()) {
                var imageFilename = imageFile.getName();
                try {
                    if (!inputImageProviders.containsKey(imageFilename)) {
                        var ffp = new FileFrameProvider(imageFile.getAbsolutePath(), 69 /* nice */); // todo: idk how to deal with FOV for this - do we even need to?
                        logger.debug("Created FileFrameProvider for image: " + imageFilename);
                        inputImageProviders.put(imageFilename, ffp);
                    } else {
                        logger.info("Skipping already loaded image: " + imageFilename);
                    }
                } catch (Exception ex) {
                    logger.error("Failed to create FileFrameProvider for image!", ex);
                }
            }
        }

        updateInputImageListView();
    }

    private void updateInputImageListView() {
        ObservableList<ImageListViewData> observableItems = FXCollections.observableArrayList();

        for (var inputs : inputImageProviders.entrySet()) {
            var lvImageItem = new ImageListViewData(inputs.getKey(), CVFXUtils.matToImage(inputs.getValue().get().image));
            lvImageItem.image.setFitWidth(160);
            lvImageItem.image.setFitHeight(120);
            observableItems.add(lvImageItem);
        }

        inputImageListView.getItems().clear();
        inputImageListView.setItems(observableItems);
        inputImageListView.getSelectionModel().selectFirst();
    }

    private void removeImageByTitle(String title) {
        inputImageProviders.remove(title);
        inputImageListView.getItems().removeIf(i -> i.title.equals(title));
        updateInputImageListView();
    }

    @FXML
    public void exitApplication() {
        stage.close();
    }

    @FXML
    public void initialize() {
        inputImageListView.setCellFactory(l -> {
            var cell = new ImageListViewCell();
            cell.setOnRemoveClickCallback(this::removeImageByTitle);
            return cell;
        });
        inputImageListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
