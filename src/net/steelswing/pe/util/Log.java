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

    public static void info(Object msg) {
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.out.println(date + " INFO]: " + msg.toString());
    }

    public static void debug(Object msg) {
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.out.println(date + " DEBUG]: " + msg.toString());
    }

    public static void error(Object msg) {
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " ERROR]: " + msg.toString());
    }

    public static void warn(Object msg, Exception e) {
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " ERROR]: " + msg.toString());
        e.printStackTrace();
    }

    public static void warn(Object msg) {
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " WARN]: " + msg.toString());
    }

    public static void error(Object msg, OpenALException e) {
        Date d = new Date();
        String date = "[" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
        System.err.println(date + " WARN]: " + msg.toString());
        e.printStackTrace();
    }
}
