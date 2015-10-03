package ru.lanwen.diff.uri;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import ru.lanwen.diff.uri.core.UriDiff;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.lanwen.diff.uri.core.UriPart.*;
import static ru.lanwen.diff.uri.matchers.UriDiffMatchers.changeType;
import static ru.lanwen.diff.uri.matchers.UriDiffMatchers.changes;

/**
 * User: lanwen
 */
public class UriDiffTest {

    public static final String URL_ACT = "http://disk.yandex.com.tr/?auth=1?retpath=http%3A%2F%2Fmail.yandex.com.tr%2Fneo2%2F%23disk&auth=2";
    public static final String URL_EXP = "http://disk.yandex.com.tr/?auth=1&retpath=http%3A%2F%2Fmail.yandex.com.tr%2Fneo2%2F%23disk&auth=2";

    @Test
    public void defaultValue() {
        assertThat(UriDiffer.DEFAULT_URI, equalTo(URI.create("/")));
    }

    @Test
    public void construction() throws Exception {
        String uri = "http://ya.ru";
        UriDiffer differ = UriDiffer.diff().actual(uri).expected(uri);
        UriDiff diff = differ.changes();

        assertThat(diff.getChanges(), empty());
        assertThat(diff.getOriginal(), equalTo(URI.create(uri)));
        assertThat(diff.getRevised(), equalTo(URI.create(uri)));
        assertThat(diff.toString(), not(isEmptyOrNullString()));
        assertThat(diff.report(), not(isEmptyOrNullString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructionBadActual() throws Exception {
        UriDiffer.diff().actual(StringUtils.SPACE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructionBadExpected() throws Exception {
        UriDiffer.diff().expected(StringUtils.SPACE);
    }

    @Test
    public void schemeChange() throws Exception {
        String expected = "httpf://ya.ru";
        String actual = "hotps://ya.ru";
        UriDiffer differ = UriDiffer.diff().actual(actual).expected(expected);
        UriDiff diff = differ.changes();
        assertThat(differ.schemeDeltas(), hasSize(3));
        assertThat(diff.hasChanges(), equalTo(Boolean.TRUE));
        assertThat(diff, changes(hasSize(1)));
        assertThat(diff, changes(hasItem(changeType(equalTo(SCHEME)))));
    }


    @Test
    public void hostChange() throws Exception {
        String expected = "https://yandex.ru";
        String actual = "https://ya.ru";

        UriDiffer differ = UriDiffer.diff().actual(actual).expected(expected);
        UriDiff diff = differ.changes();
        assertThat(differ.hostDeltas().size(), equalTo(1));
        assertThat(diff.hasChanges(), equalTo(Boolean.TRUE));
        assertThat(diff, changes(hasSize(1)));
        assertThat(diff, changes(hasItem(changeType(equalTo(HOST)))));
    }

    @Test
    public void pathChange() throws Exception {
        String expected = "https://yandex.ru/ff/";
        String actual = "https://yandex.ru/ff";

        UriDiffer differ = UriDiffer.diff().actual(actual).expected(expected);
        UriDiff diff = differ.changes();

        assertThat(differ.pathDeltas().size(), equalTo(1));
        assertThat(diff.hasChanges(), equalTo(Boolean.TRUE));
        assertThat(diff, changes(hasSize(1)));
        assertThat(diff, changes(hasItem(changeType(equalTo(PATH)))));
    }

    @Test
    public void portChange() throws Exception {
        String expected = "https://yandex.ru:8080/ff/";
        String actual = "https://yandex.ru/ff/";

        UriDiffer differ = UriDiffer.diff().actual(actual).expected(expected);
        UriDiff diff = differ.changes();

        assertThat(differ.portDeltas().size(), equalTo(1));
        assertThat(diff.hasChanges(), equalTo(Boolean.TRUE));
        assertThat(diff, changes(hasSize(1)));
        assertThat(diff, changes(hasItem(changeType(equalTo(PORT)))));
    }

    @Test
    public void queryChange() throws Exception {
        String expected = "https://yandex.ru/ff/?q=e&q2&q=4";
        String actual = "https://yandex.ru/ff/?q=1&q2&q=5";

        UriDiffer differ = UriDiffer.diff().actual(actual).expected(expected);
        UriDiff diff = differ.changes();

        assertThat(differ.queryDeltas().size(), equalTo(1));
        assertThat(diff.hasChanges(), equalTo(Boolean.TRUE));
        assertThat(diff, changes(hasSize(1)));
        assertThat(diff, changes(hasItem(changeType(equalTo(QUERY)))));
    }


    @Test
    public void queryChangeEncoded() throws Exception {
        UriDiffer differ = UriDiffer.diff().actual(URL_ACT).expected(URL_EXP);
        UriDiff diff = differ.changes();

        assertThat(differ.queryDeltas().size(), equalTo(2));
        assertThat(diff.hasChanges(), equalTo(Boolean.TRUE));
        assertThat(diff, changes(hasSize(1)));
        assertThat(diff, changes(hasItem(changeType(equalTo(QUERY)))));
    }

    @Test
    public void fragmentChange() throws Exception {
        String expected = "https://yandex.ru/ff/?q=1&q2&q=5#index";
        String actual = "https://yandex.ru/ff/?q=1&q2&q=5";

        UriDiffer differ = UriDiffer.diff().actual(actual).expected(expected);
        UriDiff diff = differ.changes();

        assertThat(differ.fragmentDeltas().size(), equalTo(1));
        assertThat(diff.hasChanges(), equalTo(Boolean.TRUE));
        assertThat(diff, changes(hasSize(1)));
        assertThat(diff, changes(hasItem(changeType(equalTo(FRAGMENT)))));
    }
}
