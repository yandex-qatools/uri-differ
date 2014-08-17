package ru.lanwen.diff.uri.core.filters;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jersey.repackaged.com.google.common.base.Joiner.on;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

/**
 * User: lanwen
 */
public class SchemeFilter implements UriDiffFilter {
    private List<String> schemeCanBe = new ArrayList<>();

    public SchemeFilter(List<String> schemeCanBe) {
        this.schemeCanBe = schemeCanBe;
    }

    public static SchemeFilter scheme(List<String> schemeCanBe) {
        return new SchemeFilter(schemeCanBe);
    }

    public static SchemeFilter scheme(String... schemes) {
        return scheme(Arrays.asList(schemes));
    }

    @Override
    public URI apply(URI uri) {
        UriBuilder urlBuilder = UriBuilder.fromUri(uri);
        if (schemeCanBe.isEmpty() || schemeCanBe.contains(urlBuilder.build().getScheme())) {
            String scheme = defaultIfEmpty(on(".").join(schemeCanBe), "any-scheme");
            return urlBuilder.scheme(scheme).build();
        }

        return uri;
    }

    @Override
    public String toString() {
        return String.format("Ignoring:%s", on(",").join(schemeCanBe));
    }
}
