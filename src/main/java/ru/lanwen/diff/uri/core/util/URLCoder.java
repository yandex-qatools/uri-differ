package ru.lanwen.diff.uri.core.util;

import java.io.UnsupportedEncodingException;

/**
 * User: leonsabr
 */
public final class URLCoder {

    private URLCoder() {
    }

    public static String decode(String s) {
        try {
            return java.net.URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding is not supported");
        }
    }

    public static String encode(String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding is not supported");
        }
    }
}
