package ru.lanwen.diff.uri.core.filters;

import java.net.URI;

/**
 * User: lanwen
 */
public interface UriDiffFilter {
    URI apply(URI uri);
}
