package org.photonvision.calibui;

import org.photonvision.calibui.ui.CalibUI;
import org.photonvision.common.util.TestUtils;

public class Main {

    public static final CalibUI userInterface = new CalibUI();

    public static void main(String[] args) {
        TestUtils.loadLibraries();
        userInterface.launchUI();
    }
}
