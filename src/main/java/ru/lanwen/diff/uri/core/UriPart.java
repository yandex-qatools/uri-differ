package ru.lanwen.diff.uri.core;

import static java.lang.String.format;
import static ru.lanwen.diff.uri.core.Delimiters.BY_CHAR_SPLITTER;
import static ru.lanwen.diff.uri.core.Delimiters.HOST_JOINER;
import static ru.lanwen.diff.uri.core.Delimiters.HOST_SPLITTER;
import static ru.lanwen.diff.uri.core.Delimiters.PATH_SPLITTER;
import static ru.lanwen.diff.uri.core.Delimiters.QUERY_SPLITTER;
import static ru.lanwen.diff.uri.core.Delimiters.WITH_DELIMITER;

/**
 * User: lanwen
 */
public enum UriPart {
    SCHEME(BY_CHAR_SPLITTER, BY_CHAR_SPLITTER),
    HOST(HOST_SPLITTER, HOST_JOINER),
    PORT(BY_CHAR_SPLITTER, BY_CHAR_SPLITTER),
    PATH(format(WITH_DELIMITER, PATH_SPLITTER), BY_CHAR_SPLITTER),
    QUERY(QUERY_SPLITTER, QUERY_SPLITTER),
    FRAGMENT(BY_CHAR_SPLITTER, BY_CHAR_SPLITTER);


    private String splitter;
    private String joiner;

    private UriPart(String splitter, String joiner) {
        this.splitter = splitter;
        this.joiner = joiner;
    }

    public String getSplitter() {
        return splitter;
    }

    public String getJoiner() {
        return joiner;
    }
}
