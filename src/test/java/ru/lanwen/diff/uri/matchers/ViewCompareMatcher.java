package ru.lanwen.diff.uri.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import ru.lanwen.diff.uri.core.UriDiff;
import ru.lanwen.diff.uri.core.filters.UriDiffFilter;
import ru.lanwen.diff.uri.core.view.DefaultUrlDiffView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static ru.lanwen.diff.uri.UriDiffer.diff;

/**
 * User: lanwen
 */
public class ViewCompareMatcher extends TypeSafeDiagnosingMatcher<URI> {

    private URI expectedUri;
    private String expectedReport = "";

    private List<UriDiffFilter> filters = new ArrayList<>();

    public ViewCompareMatcher(URI expectedUri) {
        this.expectedUri = expectedUri;
    }

    public ViewCompareMatcher produce(String expectedReport) {
        this.expectedReport = expectedReport;
        return this;
    }

    public ViewCompareMatcher filtered(UriDiffFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
        return this;
    }



    @Factory
    public static ViewCompareMatcher comparingTo(URI expectedUri) {
        return new ViewCompareMatcher(expectedUri);
    }

    @Override
    protected boolean matchesSafely(URI actualUri, Description description) {
        description.appendText("with ").appendValue(actualUri)
                .appendText(" should produce:\n\t")
                .appendValue(expectedReport);

        UriDiff changes = diff().expected(expectedUri).actual(actualUri)
                .filter(filters)
                .changes();
        String report = changes.report(new DefaultUrlDiffView());

        description.appendText(", but was:\n\t").appendValue(report);
        description.appendText("\n").appendValueList("filtered with: \n\t", "\n\t", "", filters);
        return equalTo(expectedReport).matches(report);
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("when compare: ").appendValue(expectedUri);
    }
}
