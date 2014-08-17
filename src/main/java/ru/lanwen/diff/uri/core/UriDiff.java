package ru.lanwen.diff.uri.core;

import ru.lanwen.diff.uri.core.view.ReportView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static jersey.repackaged.com.google.common.base.Joiner.on;

/**
 * User: lanwen
 */
public class UriDiff {
    private URI original;
    private URI revised;
    private List<Change> changes = new ArrayList<Change>();

    public UriDiff(URI original, URI revised) {
        this.original = original;
        this.revised = revised;
    }

    public List<Change> getChanges() {
        return changes;
    }

    public boolean hasChanges() {
        return !changes.isEmpty();
    }

    public UriDiff addChange(Change change) {
        if (!change.getDeltas().isEmpty()) {
            changes.add(change);
        }
        return this;
    }

    public URI getOriginal() {
        return original;
    }

    public URI getRevised() {
        return revised;
    }

    @Override
    public String toString() {
        return String.format("Url <%s>%nchanged to <%s>:%n%s", original, revised, on("\n").join(changes));
    }


    public String report(ReportView view) {
        return view.report(this);
    }
}
