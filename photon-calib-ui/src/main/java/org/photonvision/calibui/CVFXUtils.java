package org.photonvision.calibui;

import javafx.scene.image.Image;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.photonvision.vision.opencv.CVMat;

import java.io.ByteArrayInputStream;

public class CVFXUtils {
    private CVFXUtils() {}

    public static Image matToImage(CVMat mat) {
        MatOfByte buf = new MatOfByte();
        Imgcodecs.imencode(".png", mat.getMat(), buf);
        return new Image(new ByteArrayInputStream(buf.toArray()));
    }
}

