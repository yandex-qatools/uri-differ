package ru.lanwen.diff.uri.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import ru.lanwen.diff.uri.core.UriDiff;

import java.net.URI;

import static ru.lanwen.diff.uri.UriDiffer.diff;

/**
 * User: lanwen
 */
public class HasChangesMatcher extends TypeSafeDiagnosingMatcher<URI> {

    private URI expectedUri;
    private Matcher<UriDiff> matcher;

    public HasChangesMatcher(URI expectedUri) {
        this.expectedUri = expectedUri;
    }

    public HasChangesMatcher expect(Matcher<UriDiff> matcher) {
        this.matcher = matcher;
        return this;
    }


    @Factory
    public static HasChangesMatcher comparingTo(URI expectedUri) {
        return new HasChangesMatcher(expectedUri);
    }

    @Override
    protected boolean matchesSafely(URI actualUri, Description description) {
        UriDiff diff = diff().expected(expectedUri).actual(actualUri).changes();
        matcher.describeMismatch(diff, description);
        return matcher.matches(diff);
    }


    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(matcher);
    }
}
