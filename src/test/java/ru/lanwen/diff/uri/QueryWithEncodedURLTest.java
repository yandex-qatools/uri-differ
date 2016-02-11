package ru.lanwen.diff.uri;

import org.junit.Test;

import static ru.lanwen.diff.uri.core.util.URLCoder.encode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static ru.lanwen.diff.uri.matchers.UriDiffMatchers.changes;

/**
 * User: leonsabr
 */
public class QueryWithEncodedURLTest {

    @Test
    public void parameterValueWithAmpersandIsParsedCorrectly() throws Exception {
        String actual = "https://www.yandex.com/yandsearch?text=m%26m%27s";
        String expected = "https://www.yandex.com/yandsearch?text=procter%26gamble";
        
        UriDiffer differ = UriDiffer.diff().actual(actual).expected(expected);
        assertThat(differ.queryDeltas(), hasSize(1));
        assertThat((String) differ.queryDeltas().get(0).getRevised().getLines().get(0), equalTo("text=m&m's"));
    }

    @Test
    public void shouldNormallyEncodeDecodeBrackets() throws Exception {
        String encode = "/?y=" +  encode("{\"");
        UriDiffer differ = UriDiffer.diff().expected("/?y=1").actual(encode);

        assertThat(differ.changes(), changes(hasSize(1)));
        assertThat(differ.queryDeltas(), hasSize(1));
        assertThat((String) differ.queryDeltas().get(0).getRevised().getLines().get(0), equalTo("y={\""));
    }
}
