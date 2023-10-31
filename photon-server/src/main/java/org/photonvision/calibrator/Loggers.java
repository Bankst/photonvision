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

import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Helper class for setting up the application loggers.
 *
 * <p>Handle errors to "err"
 *
 * <p>Handle all to "out"
 */
public final class Loggers {
    static { // configured logger hasn't started yet so use system out and err; you can log here but
        // messages disappear probably because of the reset
        System.out.println(
                "Starting class: " + MethodHandles.lookup().lookupClass().getCanonicalName());
        System.err.println(
                "Starting class: " + MethodHandles.lookup().lookupClass().getCanonicalName());
    }

    // default values set here and setupLoggers can override or not
    private static OutputStream errLog = System.err;

    private static String outFormat = "%4$-7s [%3$s %2$s] %5$s %6$s%n";
    private static String outHeader = "";
    private static String outTail = "";
    private static Level outLevel = Level.ALL;

    private static String errFormat =
            "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s %2$s] %5$s %6$s%n";
    private static String errHeader = "NEW BEGINNINGS\n";
    private static String errTail = "THE END\n";
    private static Level errLevel = Level.WARNING;

    private Loggers() {
        throw new UnsupportedOperationException("Run setupLoggers() to change defaults.");
    }

    public static void setupLoggers(
            OutputStream errLog,
            String outFormat,
            String outHeader,
            String outTail,
            Level outLevel,
            String errFormat,
            String errHeader,
            String errTail,
            Level errLevel) {
        Loggers.errLog = errLog;
        Loggers.outFormat = outFormat;
        Loggers.outHeader = outHeader;
        Loggers.outTail = outTail;
        Loggers.outLevel = outLevel;
        Loggers.errFormat = errFormat;
        Loggers.errHeader = errHeader;
        Loggers.errTail = errTail;
        Loggers.errLevel = errLevel;

        setupLoggers();
    }

    public static void setupLoggers() {
        // Set up the global level logger. This handles IO for all loggers.
        LogManager.getLogManager().reset(); // remove all handlers; same as a removeHandler loop
        final Logger globalLogger =
                LogManager.getLogManager().getLogger(""); // handler called null name

        // // Remove the default handlers that stream to System.err
        // for (Handler handler : globalLogger.getHandlers()) {
        //   globalLogger.removeHandler(handler);
        // }

        LogFormatter outFormatter = new LogFormatter(outFormat, outHeader, outTail);
        final StreamHandler stdout =
                new StreamHandler(System.out, outFormatter) {
                    // final StreamHandler stdout = new StreamHandler(System.out, new SimpleFormatter()) {
                    @Override
                    public synchronized void publish(final LogRecord record) {
                        super.publish(record);
                        // For some reason this doesn't happen automatically.
                        // This will ensure we get all of the logs printed to the console immediately instead of
                        // at shutdown
                        flush(); // flush isn't in the default publish
                    }
                };

        // used for all SimpleFormatters [new SimpleFormatter()] so if you want different formats
        // different formatters must be used.
        // System.setProperty("java.util.logging.SimpleFormatter.format",
        // "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s %2$s] %5$s %6$s%n");

        LogFormatter errFormatter = new LogFormatter(errFormat, errHeader, errTail);
        final StreamHandler stderr =
                new StreamHandler(errLog, errFormatter) {
                    @Override
                    public synchronized void publish(final LogRecord record) {
                        super.publish(record);
                        // For some reason this doesn't happen automatically.
                        // This will ensure we get all of the logs printed to the console immediately instead of
                        // at shutdown
                        flush(); // flush isn't in the default publish
                    }
                };

        // 1st stage filters
        // OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL
        stdout.setLevel(outLevel);
        stderr.setLevel(errLevel);

        // 2nd stage filter for out
        Filter outFilter =
                new Filter() {
                    public boolean isLoggable(LogRecord record) {
                        String ClassName = record.getSourceClassName();
                        // System.out.println(">" + ClassName + "<");
                        // don't want to see any java awt or swing messages - might be slightly dangerous,
                        // though. could suppress bad ones
                        if (ClassName.startsWith("java.awt")) return false;
                        if (ClassName.startsWith("java.io")) return false;
                        if (ClassName.startsWith("sun")) return false;
                        if (ClassName.startsWith("javax")) return false;
                        if (Main.outOverride2ndStageClassFilter)
                            return true; // this filter allows all messages it sees
                        // var method = record.getSourceClassName().replaceFirst("team4237.", "");

                        if (Main.classLevels.get(ClassName) != null) // use level if specified by user
                        {
                            if (record.getLevel().intValue() >= Main.classLevels.get(ClassName).intValue())
                                return true;
                            else return false;
                        }
                        return true;
                    }
                };

        // 2nd stage filter for err
        Filter errFilter =
                new Filter() {
                    public boolean isLoggable(LogRecord record) {
                        String ClassName = record.getSourceClassName();
                        // System.out.println(">" + ClassName + "<");
                        // don't want to see any java awt or swing messages - might be slightly dangerous,
                        // though. could suppress bad ones
                        if (ClassName.startsWith("java.awt")) return false;
                        if (ClassName.startsWith("java.io")) return false;
                        if (ClassName.startsWith("sun")) return false;
                        if (ClassName.startsWith("javax")) return false;
                        if (Main.errOverride2ndStageClassFilter)
                            return true; // this filter allows all messages it sees
                        if (Main.classLevels.get(ClassName) != null) // use level if specified by user
                        {
                            if (record.getLevel().intValue() >= Main.classLevels.get(ClassName).intValue())
                                return true;
                            else return false;
                        }
                        // if(record.getMessage().equals("robot driving messages not being sent to roboRIO"))
                        // return false;
                        return true;
                    }
                };

        // record.getSourceClassName().startsWith("org.netbeans.modules.parsing.impl.indexing.LogContext")
        // handler.setFilter(record -> record.getLevel().equals(Level.SEVERE));
        // record.getLevel().intValue() >= Level.FINE.intValue()
        // Throwable t = record.getThrown();

        // if (t != null) {
        //     return true;
        // }

        // && !logRecord.getLevel().equals(CONFIG)
        // && currentFilter.map(f -> f.isLoggable(logRecord)).orElse(true); // Honour existing filter if
        // exists
        // && !filter.isLoggable(record)
        stdout.setFilter(outFilter);
        stderr.setFilter(errFilter);

        globalLogger.addHandler(stdout);
        globalLogger.addHandler(stderr);

        // stdout.setFormatter(new SimpleFormatter()); //log in text, not xml
        // stderr.setFormatter(new SimpleFormatter()); //log in text, not xml

        // first level master filter default isn't ALL
        // set to ALL so ALL messages go to the handlers which have their own level limits
        globalLogger.setLevel(Level.ALL);

        System.out.flush();
        System.err.flush(); // try to force the header out before anything else happens

        globalLogger.config("Logger configuration done."); // Log that we are done setting up the logger
    }
}
