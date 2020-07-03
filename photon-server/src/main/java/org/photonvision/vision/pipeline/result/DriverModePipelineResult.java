<<<<<<< HEAD:photon-server/src/main/java/org/photonvision/vision/pipeline/DriverModePipelineResult.java
/*
 * Copyright (C) 2020 Photon Vision.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.photonvision.vision.pipeline;
=======
package org.photonvision.vision.pipeline.result;
>>>>>>> 673ecb3... rename camera config to USBCameraConfiguration, add name:photon-server/src/main/java/org/photonvision/vision/pipeline/result/DriverModePipelineResult.java

import java.util.List;
import org.photonvision.vision.frame.Frame;

public class DriverModePipelineResult extends CVPipelineResult {
    public DriverModePipelineResult(double latencyMillis, Frame outputFrame) {
        super(latencyMillis, List.of(), outputFrame);
    }
}
