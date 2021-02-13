package org.photonvision.calibui.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.photonvision.calibui.ui.controllers.CalibUIController;

import java.io.IOException;

public class CalibUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/calibui.fxml"));
        BorderPane pane = loader.load();

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);

        CalibUIController controller = new CalibUIController();
        loader.setController(controller);
        controller.setStage(stage);

        stage.show();
    }

    public void launchUI() {
        launch();
    }
}
