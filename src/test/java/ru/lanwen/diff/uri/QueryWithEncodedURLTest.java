package ru.lanwen.diff.uri;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * User: leonsabr
 */
public class QueryWithEncodedURLTest {
    public static final String URL_EXP = "https://www.yandex.com/yandsearch?text=procter%26gamble";
    public static final String URL_ACT = "https://www.yandex.com/yandsearch?text=m%26m%27s";

    @Test
    public void parameterValueWithAmpersandIsParsedCorrectly() throws Exception {
        UriDiffer differ = UriDiffer.diff().actual(URL_ACT).expected(URL_EXP);
        assertThat(differ.queryDeltas().size(), equalTo(1));
        assertThat((String) differ.queryDeltas().get(0).getRevised().getLines().get(0), equalTo("text=m&m's"));
    }

}
