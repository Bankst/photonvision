/*
 * Copyright (C) Photon Vision.
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

package org.photonvision.calibrator;

import java.util.logging.Level;
import org.opencv.core.Mat;

/*----------------------------------------------------------------------------------------------------------- */
/*----------------------------------------------------------------------------------------------------------- */
/*                                                                                                            */
/*                                     keyframe class                                                         */
/*                                     keyframe class                                                         */
/*                                     keyframe class                                                         */
/*                                                                                                            */
/*----------------------------------------------------------------------------------------------------------- */
/*----------------------------------------------------------------------------------------------------------- */
class keyframe {
    private Mat p2d; // detected ccorners in camera image
    private Mat
            p3d; // target ChArUcoBoard object - perfect without distortion in 3d space but ours is always

    // flat on a wall so Z = 0

    // getters
    Mat p2d() {
        return p2d;
    }

    Mat p3d() {
        return p3d;
    }

    /*----------------------------------------------------------------------------------------------------------- */
    /*----------------------------------------------------------------------------------------------------------- */
    /*                                                                                                            */
    /*                                     keyframe constructor                                                   */
    /*                                     keyframe constructor                                                   */
    /*                                     keyframe constructor                                                   */
    /*                                                                                                            */
    /*----------------------------------------------------------------------------------------------------------- */
    /*----------------------------------------------------------------------------------------------------------- */
    keyframe(Mat p2d, Mat p3d) {
        this.p2d = p2d;
        this.p3d = p3d;
        if (this.p2d.rows() != this.p3d.rows() || this.p2d.cols() != p3d.cols()) {
            Main.LOGGER.log(Level.SEVERE, "size of p2d != p3d");
        }
    }
}
/*----------------------------------------------------------------------------------------------------------- */
/*----------------------------------------------------------------------------------------------------------- */
/*                                                                                                            */
/*                                     End keyframe class                                                     */
/*                                     End keyframe class                                                     */
/*                                     End keyframe class                                                     */
/*                                                                                                            */
/*----------------------------------------------------------------------------------------------------------- */
/*----------------------------------------------------------------------------------------------------------- */
