package ru.lanwen.diff.uri.core.view;

import ru.lanwen.diff.uri.core.UriDiff;
import ru.lanwen.diff.uri.core.util.UriPrinter;

import static ru.lanwen.diff.uri.core.converters.ChangeFormatter.formatChanges;

/**
 * User: lanwen
 */
public class DefaultUrlDiffView implements ReportView {

    private DefaultUrlDiffView() {
    }

    public static DefaultUrlDiffView withDefaultView() {
        return new DefaultUrlDiffView();
    }

    @Override
    public String report(UriDiff diff) {
        UriPrinter uriPrinter = new UriPrinter(diff.getOriginal());

        diff.getChanges().stream()
                .map(formatChanges())
                .forEach(uriPrinter::applyChange);

        return uriPrinter.toString();
    }

}
