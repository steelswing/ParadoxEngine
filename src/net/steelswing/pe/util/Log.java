/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.util;

import java.util.Date;
import org.lwjglx.openal.OpenALException;

/**
 *
 * @author MrJavaCoder
 */
public class Log {

    private static boolean //
            info = true,
            debug = true,
            error = true,
            warn = true;

    public static void info(Object msg) {
        if (!info) {
            return;
        }
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.out.println(date + " INFO]: " + msg.toString());
    }

    public static void debug(Object msg) {
        if (!debug) {
            return;
        }
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.out.println(date + " DEBUG]: " + msg.toString());
    }

    public static void error(Object msg) {
        if (!error) {
            return;
        }
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " ERROR]: " + msg.toString());
    }

    public static void warn(Object msg, Exception e) {
        if (warn) {
            return;
        }
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " ERROR]: " + msg.toString());
        e.printStackTrace();
    }

    public static void warn(Object msg) {
        if (warn) {
            return;
        }
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " WARN]: " + msg.toString());
    }

    public static void error(Object msg, OpenALException e) {
        if (!error) {
            return;
        }
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " WARN]: " + msg.toString());
        e.printStackTrace();
    }

    public static boolean isInfo() {
        return info;
    }

    public static void setInfo(boolean info) {
        Log.info = info;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Log.debug = debug;
    }

    public static boolean isError() {
        return error;
    }

    public static void setError(boolean error) {
        Log.error = error;
    }

    public static boolean isWarn() {
        return warn;
    }

    public static void setWarn(boolean warn) {
        Log.warn = warn;
    }
}
