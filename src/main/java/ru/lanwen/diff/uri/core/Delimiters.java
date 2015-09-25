package ru.lanwen.diff.uri.core;

/**
 * User: lanwen
 */
public final class Delimiters {

    public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
    public static final String BY_CHAR_SPLITTER = "";
    public static final String HOST_SPLITTER = "\\.";
    public static final String PATH_SPLITTER = "/";
    public static final String QUERY_SPLITTER = "&";
    public static final String HOST_JOINER = ".";
    public static final String QUERY_NAME_VALUE_SEPARATOR = "=";

    private Delimiters() {
        throw new UnsupportedOperationException();
    }

}
