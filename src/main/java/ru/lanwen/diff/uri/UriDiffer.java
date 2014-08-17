package ru.lanwen.diff.uri;

import difflib.Delta;
import difflib.DiffUtils;
import ru.lanwen.diff.uri.core.Change;
import ru.lanwen.diff.uri.core.UriDiff;
import ru.lanwen.diff.uri.core.filters.UriDiffFilter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.lanwen.diff.uri.core.UriPart.FRAGMENT;
import static ru.lanwen.diff.uri.core.UriPart.HOST;
import static ru.lanwen.diff.uri.core.UriPart.PATH;
import static ru.lanwen.diff.uri.core.UriPart.PORT;
import static ru.lanwen.diff.uri.core.UriPart.QUERY;
import static ru.lanwen.diff.uri.core.UriPart.SCHEME;
import static ru.lanwen.diff.uri.core.util.UriSplitter.splitFragment;
import static ru.lanwen.diff.uri.core.util.UriSplitter.splitHost;
import static ru.lanwen.diff.uri.core.util.UriSplitter.splitPath;
import static ru.lanwen.diff.uri.core.util.UriSplitter.splitPort;
import static ru.lanwen.diff.uri.core.util.UriSplitter.splitQuery;
import static ru.lanwen.diff.uri.core.util.UriSplitter.splitScheme;

/**
 * User: lanwen
 */
public class UriDiffer {
    //TODO filter
    //TODO encoding

    private URI actual = URI.create("/");
    private URI expected = URI.create("/");

    public static UriDiffer diff() {
        return new UriDiffer();
    }

    public UriDiffer actual(String actual) throws URISyntaxException {
        return actual(new URI(actual));
    }

    public UriDiffer expected(String expected) throws URISyntaxException {
        return expected(new URI(expected));
    }

    public UriDiffer actual(URI actual) {
        this.actual = actual;
        return this;
    }

    public UriDiffer expected(URI expected) {
        this.expected = expected;
        return this;
    }


    public UriDiffer filter(List<UriDiffFilter> filters) {
        for (UriDiffFilter filter : filters) {
            expected(filter.apply(expected));
            actual(filter.apply(actual));
        }
        return this;
    }

    public UriDiffer filter(UriDiffFilter... filters) {
        return filter(asList(filters));
    }

    public UriDiff changes() {
        return new UriDiff(expected, actual)
                .addChange(new Change(schemeDeltas(), splitScheme(expected), SCHEME))
                .addChange(new Change(hostDeltas(), splitHost(expected), HOST))
                .addChange(new Change(portDeltas(), splitPort(expected), PORT))
                .addChange(new Change(pathDeltas(), splitPath(expected), PATH))
                .addChange(new Change(queryDeltas(), splitQuery(expected), QUERY))
                .addChange(new Change(fragmentDeltas(), splitFragment(expected), FRAGMENT));
    }

    public List<Delta> schemeDeltas() {
        return (List) DiffUtils.diff(splitScheme(expected), splitScheme(actual)).getDeltas();
    }

    public List<Delta> hostDeltas() {
        return (List) DiffUtils.diff(splitHost(expected), splitHost(actual)).getDeltas();
    }

    public List<Delta> portDeltas() {
        return (List) DiffUtils.diff(splitPort(expected), splitPort(actual)).getDeltas();
    }

    public List<Delta> pathDeltas() {
        return (List) DiffUtils.diff(splitPath(expected), splitPath(actual)).getDeltas();
    }


    public List<Delta> queryDeltas() {
        return (List) DiffUtils.diff(splitQuery(expected), splitQuery(actual)).getDeltas();
    }


    public List<Delta> fragmentDeltas() {
        return (List) DiffUtils.diff(splitFragment(expected), splitFragment(actual)).getDeltas();
    }

}
