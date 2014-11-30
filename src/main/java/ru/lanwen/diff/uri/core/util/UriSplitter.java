package ru.lanwen.diff.uri.core.util;

import ch.lambdaj.collection.LambdaList;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.collection.LambdaCollections.with;
import static java.lang.String.valueOf;
import static java.util.Collections.sort;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isIn;
import static ru.lanwen.diff.uri.core.Delimeters.QUERY_NAME_VALUE_SEPARATOR;
import static ru.lanwen.diff.uri.core.UriPart.FRAGMENT;
import static ru.lanwen.diff.uri.core.UriPart.HOST;
import static ru.lanwen.diff.uri.core.UriPart.PATH;
import static ru.lanwen.diff.uri.core.UriPart.PORT;
import static ru.lanwen.diff.uri.core.UriPart.QUERY;
import static ru.lanwen.diff.uri.core.UriPart.SCHEME;
import static ru.lanwen.diff.uri.core.util.Decoder.decode;

/**
 * User: lanwen
 */
public class UriSplitter {

    public static List<String> splitScheme(URI uri) {
        return splitBy(SCHEME.getSplitter(), uri.getScheme());
    }

    public static List<String> splitHost(URI uri) {
        return splitBy(HOST.getSplitter(), uri.getHost());
    }


    public static List<String> splitPort(URI uri) {
        return splitBy(PORT.getSplitter(), valueOf(uri.getPort()).replace("-1", ""));
    }

    public static List<String> splitPath(URI uri) {
        return splitBy(PATH.getSplitter(), uri.getPath());
    }

    public static List<String> splitQuery(URI uri) {
        List<String> queries = splitBy(QUERY.getSplitter(), uri.getRawQuery());
        for (int i = 0; i < queries.size(); i++) {
            queries.set(i, decode(queries.get(i)));
        }
        sort(queries);
        return queries;
    }


    public static List<String> splitFragment(URI uri) {
        return splitBy(FRAGMENT.getSplitter(), uri.getFragment());
    }


    public static List<String> splitBy(String regex, String what) {
        return with(defaultIfEmpty(what, "").split(regex)).remove(equalTo(""));
    }


    public static Map<String, List<String>> queryParams(URI uri) {
        Map<String, List<String>> params = new HashMap<>();
        List<String> splitted = splitQuery(uri);

        for (String pair : splitted) {
            String name = StringUtils.substringBefore(pair, QUERY_NAME_VALUE_SEPARATOR);
            String value = StringUtils.substringAfter(pair, QUERY_NAME_VALUE_SEPARATOR);
            if (params.get(name) != null) {
                params.get(name).add(value);
            } else {
                List<String> paramList = new LinkedList<>();
                paramList.add(value);
                params.put(name, paramList);
            }
        }

        return params;
    }

    public static void removeValues(Map<String, List<String>> map, String name, List<String> values) {
        if (map.containsKey(name)) {
            LambdaList<String> cleared = with(map.get(name)).clone().remove(isIn(values));
            map.put(name, cleared);
        }
    }

}
