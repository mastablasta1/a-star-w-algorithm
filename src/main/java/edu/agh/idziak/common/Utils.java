package edu.agh.idziak.common;

import org.slf4j.helpers.MessageFormatter;

/**
 * Created by Tomasz on 13.08.2016.
 */
public class Utils {
    public static String slf4jFormat(String pattern, Object... objects) {
        return MessageFormatter.arrayFormat(pattern, objects).getMessage();
    }
}
