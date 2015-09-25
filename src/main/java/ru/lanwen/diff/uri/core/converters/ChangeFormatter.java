package ru.lanwen.diff.uri.core.converters;

import ch.lambdaj.collection.LambdaList;
import ch.lambdaj.function.convert.Converter;
import ru.lanwen.diff.uri.core.Change;
import ru.lanwen.diff.uri.core.formatted.FormattedChange;
import ru.lanwen.diff.uri.core.formatted.FormattedDelta;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.collection.LambdaCollections.with;
import static jersey.repackaged.com.google.common.base.Joiner.on;
import static ru.lanwen.diff.uri.core.converters.DeltaFormatter.formatDeltas;

/**
 * User: lanwen
 */
public class ChangeFormatter implements Converter<Change, FormattedChange> {

    public static ChangeFormatter formatChanges() {
        return new ChangeFormatter();
    }

    /**
     * How many times insertion occurs
     */
    private int inserted = 0;

    @Override
    public FormattedChange convert(Change change) {
        List<String> splitted = new ArrayList<String>(change.getOriginal());

        LambdaList<FormattedDelta> formattedDeltas =
                with(change.getDeltas()).convert(formatDeltas(change.getType().getJoiner()));
        for (FormattedDelta formattedDelta : formattedDeltas) {
            apply(formattedDelta, splitted);
        }
        return new FormattedChange(on(change.getType().getJoiner()).join(splitted), change.getType());
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
