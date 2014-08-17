package ru.lanwen.diff.uri.core.filters;

import ru.lanwen.diff.uri.core.UriPart;
import ru.lanwen.diff.uri.core.util.UriSplitter;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static jersey.repackaged.com.google.common.base.Joiner.on;
import static ru.lanwen.diff.uri.core.Delimeters.QUERY_NAME_VALUE_SEPARATOR;

/**
 * User: lanwen
 */
public class AnyParamValueFilter implements UriDiffFilter {
    public static final String ANY_OF = "ignoring(`%s`)";
    public static final String ANY_OF_SEPARATOR = "`,`";
    public static final String ANY_VALUE = "(ignored)";


    private String name;
    private List<String> values = new ArrayList<String>();

    public AnyParamValueFilter(String name) {
        this.name = name;
    }

    public static AnyParamValueFilter param(String name) {
        return new AnyParamValueFilter(name);
    }

    public AnyParamValueFilter ignore(String... values) {
        this.values.addAll(asList(values));
        return this;
    }

    @Override
    public URI apply(URI uri) {
        UriBuilder uriBuilder = UriBuilder.fromUri(uri);

        Map<String, List<String>> params = UriSplitter.queryParams(uriBuilder.build());
        if (values.isEmpty()) {
            params.remove(name);
            params.put(name, asList(ANY_VALUE));
            return uriBuilder.replaceQuery(toQuery(params)).build();
        }
        UriSplitter.removeValues(params, name, values);
        if (params.containsKey(name)) {
            params.get(name).add(format(ANY_OF, on(ANY_OF_SEPARATOR).join(values)));
        } else {
            params.put(name, asList(format(ANY_OF, on(ANY_OF_SEPARATOR).join(values))));
        }
        return uriBuilder.replaceQuery(toQuery(params)).build();
    }

    @Override
    public String toString() {
        return format("%s:ignored%s", name, values);
    }


    private String toQuery(Map<String, List<String>> params) {
        List<String> pairs = new ArrayList<>();
        for (String key : params.keySet()) {
            for (String value : params.get(key)) {
                pairs.add(on(QUERY_NAME_VALUE_SEPARATOR).join(asList(key, value)));
            }
        }
        return on(UriPart.QUERY.getJoiner()).join(pairs);
    }
}
