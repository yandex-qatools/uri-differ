package ru.lanwen.diff.uri.core.converters;

import ch.lambdaj.function.convert.Converter;
import difflib.Delta;
import ru.lanwen.diff.uri.core.formatted.FormattedDelta;

import static jersey.repackaged.com.google.common.base.Joiner.on;

/**
 * User: lanwen
 */
public class DeltaFormatter implements Converter<Delta, FormattedDelta> {
    public static DeltaFormatter formatDeltas(String delimeter) {
        return new DeltaFormatter(delimeter);
    }

    private DeltaFormatter(String delimeter) {
        this.delimeter = delimeter;
    }

    private String delimeter = "";

    public static final String DELETE_PATTERN = "[-%s]";
    public static final String INSERT_PATTERN = "[+%s]";
    public static final String CHANGE_PATTERN = "[%s->%s]";

    @Override
    public FormattedDelta convert(Delta from) {
        String original = on(delimeter).join(from.getOriginal().getLines());
        String revised = on(delimeter).join(from.getRevised().getLines());


        return new FormattedDelta(
                wrap(from.getType(), original, revised),
                from.getOriginal().getPosition(),
                from.getOriginal().size(),
                from.getType()
        );
    }

    protected String wrap(Delta.TYPE type, String original, String revised) {
        switch (type) {
            case DELETE:
                return String.format(DELETE_PATTERN, original);
            case INSERT:
                return String.format(INSERT_PATTERN, revised);
            case CHANGE:
                return String.format(CHANGE_PATTERN, original, revised);
            default:
                return "";
        }
    }
}
