package ru.lanwen.diff.uri.core.converters;

import ru.lanwen.diff.uri.core.Change;
import ru.lanwen.diff.uri.core.formatted.FormattedChange;
import ru.lanwen.diff.uri.core.formatted.FormattedDelta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;
import static ru.lanwen.diff.uri.core.converters.DeltaFormatter.formatDeltas;

/**
 * User: lanwen
 */
public class ChangeFormatter implements Function<Change, FormattedChange> {

    public static ChangeFormatter formatChanges() {
        return new ChangeFormatter();
    }

    /**
     * How many times insertion occurs
     */
    private int inserted = 0;

    @Override
    public FormattedChange apply(Change change) {
        List<String> splitted = new ArrayList<>(change.getOriginal());

        change.getDeltas()
                .stream()
                .map(formatDeltas(change.getType().getJoiner()))
                .forEach(delta -> apply(delta, splitted));

        return new FormattedChange(
                splitted.stream().collect(joining(change.getType().getJoiner())),
                change.getType()
        );
    }

    private List<String> apply(FormattedDelta delta, List<String> splitted) {
        if (splitted.size() > delta.getPosition() + inserted) {
            oper(delta, splitted);
        } else {
            splitted.add(delta.getChunk());
        }
        return splitted;
    }

    private void oper(FormattedDelta delta, List<String> splitted) {
        int index = delta.getPosition() + inserted;
        switch (delta.getType()) {
            case INSERT: {
                splitted.add(index, delta.getChunk());
                inserted++;
                break;
            }
            case DELETE:
            case CHANGE: {
                for (int i = index + delta.getLength() - 1; i > index; i--) {
                    splitted.remove(i);
                }
                splitted.set(index, delta.getChunk());
                break;
            }
        }
    }
}
