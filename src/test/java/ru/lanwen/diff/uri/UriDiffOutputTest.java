package ru.lanwen.diff.uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.lanwen.diff.uri.matchers.HasChangesMatcher;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static ru.lanwen.diff.uri.matchers.UriDiffMatchers.changes;
import static ru.lanwen.diff.uri.matchers.ViewCompareMatcher.comparingTo;

/**
 * User: lanwen
 */
@RunWith(Parameterized.class)
public class UriDiffOutputTest {


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        List<Object[]> data = new ArrayList<Object[]>();

        data.add(new Object[]{"httpf://ya.ru", "httpfs://ya.ru", "httpf[+s]://ya.ru"});
        data.add(new Object[]{"httpfd://ya.ru", "http://ya.ru", "http[-fd]://ya.ru"});
        data.add(new Object[]{"httpfd://ya.ru", "httpgt://ya.ru", "http[fd->gt]://ya.ru"});
        data.add(new Object[]{"httpf://ya.ru", "http://ya.ru", "http[-f]://ya.ru"});
        data.add(new Object[]{"httpf://ya.ru", "httpg://ya.ru", "http[f->g]://ya.ru"});
        data.add(new Object[]{"htspf://ya.ru", "httpg://ya.ru", "ht[s->t]p[f->g]://ya.ru"});
        data.add(new Object[]{"htspf://ya.ru", "httpg://ya.ru/h", "ht[s->t]p[f->g]://ya.ru[+/h]"});
        data.add(new Object[]{"http://ya.ru", "http://yandex.ru", "http://[ya->yandex].ru"});
        data.add(new Object[]{"http://yandex.ru", "http://yandex.com.tr", "http://yandex.[ru->com.tr]"});
        data.add(new Object[]{"http://yandex.com", "http://yandex.com.tr", "http://yandex.com.[+tr]"});
        data.add(new Object[]{"http://yandex.com.tr", "http://yandex.com", "http://yandex.com.[-tr]"});
        data.add(new Object[]{"http://yandex.com.tr.fr", "http://yandex.com", "http://yandex.com.[-tr.fr]"});
        data.add(new Object[]{"http://ya.ru", "http://ya.ru:80", "http://ya.ru:[+80]"});
        data.add(new Object[]{"http://ya.ru:8980", "http://ya.ru:8090", "http://ya.ru:8[+0]9[-8]0"});
        data.add(new Object[]{"http://ya.ru:8980", "http://ya.ru:8000090", "http://ya.ru:8[+0000]9[-8]0"});
        data.add(new Object[]{"http://ya.ru/", "http://ya.ru", "http://ya.ru[-/]"});
        data.add(new Object[]{"http://ya.ru/", "http://ya.ru//", "http://ya.ru/[+/]"});
        data.add(new Object[]{"http://ya.ru/path", "http://ya.ru/path/", "http://ya.ru/path[+/]"});
        data.add(new Object[]{"http://ya.ru/path/", "http://ya.ru/path", "http://ya.ru/path[-/]"});
        data.add(new Object[]{"http://ya.ru/path/path/path", "http://ya.ru/path/pat2/path", "http://ya.ru/path/[path->pat2]/path"});
        data.add(new Object[]{"http://ya.ru/path/path/path", "http://ya.ru/path/", "http://ya.ru/path/[-path/path]"});
        data.add(new Object[]{"http://ya.ru/?query", "http://ya.ru/?queyu", "http://ya.ru/?[query->queyu]"});
        data.add(new Object[]{"http://ya.ru/?query", "http://ya.ru/?que", "http://ya.ru/?[query->que]"});
        data.add(new Object[]{"http://ya.ru/?query=12", "http://ya.ru/?query=133", "http://ya.ru/?[query=12->query=133]"});
        data.add(new Object[]{"http://ya.ru/?query=12&err=log", "http://ya.ru/?query=133&err=log2", "http://ya.ru/?[err=log&query=12->err=log2&query=133]"});
        data.add(new Object[]{"http://ya.ru/?query=12&err=log", "http://ya.ru/?query=12&err=log2", "http://ya.ru/?[err=log->err=log2]&query=12"});
        data.add(new Object[]{"http://ya.ru/?q=1#neo", "http://ya.ru/?q=1#neo2", "http://ya.ru/?q=1#neo[+2]"});
        data.add(new Object[]{"http://ya.ru/?q=1#neo", "http://ya.ru/?q=1#neo212", "http://ya.ru/?q=1#neo[+212]"});
        data.add(new Object[]{"http://ya.ru/?q=1#123neo", "http://ya.ru/?q=1#neo", "http://ya.ru/?q=1#[-123]neo"});
        data.add(new Object[]{"http://ya.ru/?q=1#noo", "http://ya.ru/?q=1#neo", "http://ya.ru/?q=1#n[+e]o[-o]"});
        data.add(new Object[]{"http://ya.ru/?q=1#n00oo", "http://ya.ru/?q=1#neo", "http://ya.ru/?q=1#n[00->e]oo[-o]"});
        data.add(new Object[]{UriDiffTest.URL_EXP, UriDiffTest.URL_ACT,
                "http://disk.yandex.com.tr/?[auth=1->auth=1?retpath=http://mail.yandex.com.tr/neo2/#disk]&auth=2&[-retpath=http://mail.yandex.com.tr/neo2/#disk]"});
        return data;
    }

    public UriDiffOutputTest(String expected, String actual, String report) {
        this.expectedUri = expected;
        this.actualUri = actual;
        this.report = report;
    }

    private String expectedUri;
    private String actualUri;
    private String report;

    @Test
    public void changeUri() throws Exception {
        URI expected = URI.create(expectedUri);
        URI actual = URI.create(actualUri);
        assertThat(actual, comparingTo(expected).produce(report));
    }

    @Test
    public void diffsUri() throws Exception {
        URI expected = URI.create(expectedUri);
        URI actual = URI.create(actualUri);
        assertThat(actual, HasChangesMatcher.comparingTo(expected).expect(changes(hasSize(greaterThan(0)))));
    }
}
