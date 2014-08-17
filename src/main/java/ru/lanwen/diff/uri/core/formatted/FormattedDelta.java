package ru.lanwen.diff.uri.core.formatted;

import difflib.Delta;

/**
 * User: lanwen
 */
public class FormattedDelta {
    private Delta.TYPE type;
    private String chunk;
    private int position;
    private int length;

    public FormattedDelta(String chunk, int position, int length, Delta.TYPE type) {
        this.chunk = chunk;
        this.position = position;
        this.length = length;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s:%s[%s]->%s", type, position, length, chunk);
    }

    public String getChunk() {
        return chunk;
    }

    public int getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }

    public Delta.TYPE getType() {
        return type;
    }
}
