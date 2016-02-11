package ru.lanwen.diff.uri;

import org.junit.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.lanwen.diff.uri.core.filters.AnyParamValueFilter.param;
import static ru.lanwen.diff.uri.core.filters.SchemeFilter.scheme;
import static ru.lanwen.diff.uri.matchers.ViewCompareMatcher.comparingTo;

/**
 * User: lanwen
 */
public class UriDiffFilterTest {

    @Test
    public void ignoringTwoParams() throws Exception {
        String exp = "http://yandex.ru/?query=2";
        String act = "http://yandex.ru/?query=1&query=4&e=3";
        String rep = "http://yandex.ru/?e=(ignored)&[+query=4]&query=ignoring(`1`,`2`)";

        URI expected = URI.create(exp);
        URI actual = URI.create(act);
        assertThat(actual, comparingTo(expected)
                .filtered(param("query").ignore("1", "2"), param("e").ignore())
                .produce(rep));
    }

    @Test
    public void testName() throws Exception {
        String exp = "http://yandex.ru/?query=4";
        String act = "http://yandex.ru/?query=3";
        String rep = "http://yandex.ru/?e=(ignored)&[query=4->query=3]&query=ignoring(`2`)";

        URI expected = URI.create(exp);
        URI actual = URI.create(act);
        assertThat(actual, comparingTo(expected)
                .filtered(param("query").ignore("2"), param("e").ignore())
                .produce(rep));
    }

    @Test
    public void testIgnoreScheme() throws Exception {
        String exp = "https://ya.ru/";
        String act = "http://ya.ru/";
        String rep = "http.https://ya.ru/";

        URI expected = URI.create(exp);
        URI actual = URI.create(act);
        assertThat(actual, comparingTo(expected)
                .filtered(scheme("http", "https"))
                .produce(rep));
    }
}
