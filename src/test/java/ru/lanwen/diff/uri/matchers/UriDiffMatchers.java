package ru.lanwen.diff.uri.matchers;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import ru.lanwen.diff.uri.core.UriDiff;
import ru.lanwen.diff.uri.core.UriPart;
import ru.lanwen.diff.uri.core.Change;

import java.util.Collection;
import java.util.List;

/**
 * User: lanwen
 */
public class UriDiffMatchers {

    public static Matcher<UriDiff> changes(Matcher<? super Collection<? super Change>> matcher) {
        return new FeatureMatcher<UriDiff, List<Change>>(matcher, "changes", "actual changes") {
            @Override
            protected List<Change> featureValueOf(UriDiff uriDiff) {
                return uriDiff.getChanges();
            }
        };
    }

    public static Matcher<Change> changeType(Matcher<UriPart> matcher) {
        return new FeatureMatcher<Change, UriPart>(matcher, "expected type", "actual type") {
            @Override
            protected UriPart featureValueOf(Change change) {
                return change.getType();
            }
        };
    }
}
