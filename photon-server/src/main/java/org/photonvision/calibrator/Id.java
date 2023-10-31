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

import java.util.Date;

class Id {
    // prints the class and date, for example:
    static String __FILE__(Class<?> c) {
        try {
            String classFileName = c.getSimpleName() + ".class"; // simple name like MyClass.class
            Date d = new Date(c.getResource(classFileName).openConnection().getLastModified()); // date
            classFileName = classFileName + " was compiled on " + d;
            return classFileName;
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Id threw an error\n";
    }

    // String className = c.getResource(classFileName).getPath(); // complete name with path
    // (optional)-probably not useful on roboRIO

    // prints the line number
    static String __LINE__() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String returnLine = "";
        for (int i = 0; i < ste.length; i++)
            if (ste[i].getMethodName().equals("__LINE__")) {
                returnLine = returnLine + ste[i + 1].getFileName() + '@' + ste[i + 1].getLineNumber();
                break;
            }
        return returnLine;
    }
}
