package ru.lanwen.diff.uri.core.filters;

import org.junit.Test;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static ru.lanwen.diff.uri.core.filters.AnyParamValueFilter.param;
import static ru.lanwen.diff.uri.core.util.URLCoder.encode;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class AnyParamValueFilterTest {

    @Test // #8
    public void shouldNotDecodeQueryTwiceWithParamFilter() throws Exception {
        String templatedQueryPart = "{\"";
        String encode = "/?y=" + encode(templatedQueryPart);
        URI filtered = param("z").apply(URI.create(encode));
        assertThat(filtered.toString(), containsString(encode(templatedQueryPart)));
    }

}
