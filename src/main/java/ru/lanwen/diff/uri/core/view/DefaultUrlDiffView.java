package ru.lanwen.diff.uri.core.view;

import ch.lambdaj.collection.LambdaList;
import ru.lanwen.diff.uri.core.UriDiff;
import ru.lanwen.diff.uri.core.formatted.FormattedChange;
import ru.lanwen.diff.uri.core.util.UriPrinter;

import static ch.lambdaj.collection.LambdaCollections.with;
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
        LambdaList<FormattedChange> changes = with(diff.getChanges()).convert(formatChanges());
        UriPrinter uriPrinter = new UriPrinter(diff.getOriginal());
        for (FormattedChange change : changes) {
            uriPrinter.applyChange(change);
        }
        return uriPrinter.toString();
    }

}
