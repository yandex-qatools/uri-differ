package ru.lanwen.diff.uri.core.formatted;

import ru.lanwen.diff.uri.core.UriPart;

/**
 * User: lanwen
 */
public class FormattedChange {

    private String formatted;
    private UriPart type;

    public FormattedChange(String formatted, UriPart type) {
        this.formatted = formatted;
        this.type = type;
    }

    public String getFormatted() {
        return formatted;
    }

    public UriPart getType() {
        return type;
    }


    @Override
    public String toString() {
        return String.format("<%s: %s>", type, formatted);
    }

}
