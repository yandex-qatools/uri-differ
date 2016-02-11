package ru.lanwen.diff.uri.core.util;

import ru.lanwen.diff.uri.core.formatted.FormattedChange;

import java.net.URI;

/**
 * User: lanwen
 */
public class UriPrinter {
    private String scheme;
    private String host;
    private String port;
    private String path;
    private String query;
    private String fragment;

    public UriPrinter(URI uri) {
        scheme = uri.getScheme();
        host = uri.getHost();
        port = String.valueOf(uri.getPort());
        path = uri.getPath();
        query = uri.getQuery();
        fragment = uri.getFragment();
    }

    public UriPrinter withScheme(String formatted) {
        scheme = formatted;
        return this;
    }

    public UriPrinter withHost(String formatted) {
        host = formatted;
        return this;
    }

    public UriPrinter withPort(String formatted) {
        port = formatted;
        return this;
    }

    public UriPrinter withPath(String formatted) {
        path = formatted;
        return this;
    }

    public UriPrinter withQuery(String formatted) {
        query = formatted;
        return this;
    }

    public UriPrinter withFragment(String formatted) {
        fragment = formatted;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (scheme != null) {
            sb.append(scheme);
            sb.append(':');
        }
        if (host != null) {
            sb.append("//");
            sb.append(host);
            if (!"-1".equals(port)) {
                sb.append(':');
                sb.append(port);
            }
//                } else if (authority != null) {
//                    sb.append("//");
//                    sb.append(authority);
//                }
        }
        if (path != null)
            sb.append(path);
        if (query != null) {
            sb.append('?');
            sb.append(query);
        }
        if (fragment != null) {
            sb.append('#');
            sb.append(fragment);
        }
        return sb.toString();
    }

    public UriPrinter applyChange(FormattedChange change) {
        switch (change.getType()) {
            case SCHEME: {
                return this.withScheme(change.getFormatted());
            }
            case HOST: {
                return this.withHost(change.getFormatted());
            }
            case PORT: {
                return this.withPort(change.getFormatted());
            }
            case PATH: {
                return this.withPath(change.getFormatted());
            }
            case QUERY: {
                return this.withQuery(change.getFormatted());
            }
            case FRAGMENT: {
                return this.withFragment(change.getFormatted());
            }
            default: {
                return this;
            }
        }
    }


}