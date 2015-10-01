package ru.lanwen.diff.uri;

import difflib.Delta;
import difflib.DiffUtils;
import org.apache.commons.lang3.Validate;
import ru.lanwen.diff.uri.core.Change;
import ru.lanwen.diff.uri.core.UriDiff;
import ru.lanwen.diff.uri.core.filters.UriDiffFilter;

import java.net.URI;
import java.util.Collection;
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

    /*package*/ static final URI DEFAULT_URI = URI.create("/");

    private URI actual = DEFAULT_URI;
    private URI expected = DEFAULT_URI;

    public static UriDiffer diff() {
        return new UriDiffer();
    }

    public UriDiffer actual(String actual) {
        return actual(URI.create(actual));
    }

    public UriDiffer expected(String expected) {
        return expected(URI.create(expected));
    }

    public UriDiffer actual(URI actual) {
        this.actual = actual;
        return this;
    }

    public UriDiffer expected(URI expected) {
        this.expected = expected;
        return this;
    }


    public UriDiffer filter(Collection<UriDiffFilter> filters) {
        Validate.notNull(filters, "Collection with filters must be defined");
        for (UriDiffFilter filter : filters) {
            expected(filter.apply(expected));
            actual(filter.apply(actual));
        }
        return this;
    }

    public UriDiffer filter(UriDiffFilter... filters) {
        Validate.notNull(filters, "Filter(s) must be defined");
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
        return diffDeltas(splitScheme(expected), splitScheme(actual));
    }

    public List<Delta> hostDeltas() {
        return diffDeltas(splitHost(expected), splitHost(actual));
    }

    public List<Delta> portDeltas() {
        return diffDeltas(splitPort(expected), splitPort(actual));
    }

    public List<Delta> pathDeltas() {
        return diffDeltas(splitPath(expected), splitPath(actual));
    }

    public List<Delta> queryDeltas() {
        return diffDeltas(splitQuery(expected), splitQuery(actual));
    }

    public List<Delta> fragmentDeltas() {
        return diffDeltas(splitFragment(expected), splitFragment(actual));
    }


    @SuppressWarnings("unchecked")
    private List<Delta> diffDeltas(List<String> original, List<String> revised) {
        return (List) DiffUtils.diff(original, revised).getDeltas();
    }

}
