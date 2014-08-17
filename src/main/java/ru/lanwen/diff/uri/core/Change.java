package ru.lanwen.diff.uri.core;

import difflib.Delta;

import java.util.List;

import static jersey.repackaged.com.google.common.base.Joiner.on;

/**
 * User: lanwen
 */
public class Change {
    private List<Delta> diffs;
    private List<String> original;
    private UriPart type;

    public Change(List<Delta> diffs, List<String> original, UriPart type) {
        this.diffs = diffs;
        this.original = original;
        this.type = type;
    }

    public List<Delta> getDeltas() {
        return diffs;
    }


    public UriPart getType() {
        return type;
    }

    public List<String> getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return String.format("%nChange: %s, deltas:%n\t%s", type, on("\n\t").join(diffs));
    }
}
