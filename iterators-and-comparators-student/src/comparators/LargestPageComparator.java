/*
 * Copyright 2023 Marc Liberatore.
 */

package comparators;

import java.util.Comparator;

/**
 * A comparator to determine which of two WebPageRecords represents a longer web
 * page. The page with the larger `length` attribute is comes before the other.
 * If there is a tie, break it using the length of the first line -- again,
 * larger comes first.
 * If there is still a tie, break it by comparing which URL comes
 * lexicographically first.
 * Any remaining ties mean the two WebPageRecords should be considered equal.
 */
public class LargestPageComparator implements Comparator<WebPageRecord> {
    @Override
    public int compare(WebPageRecord x, WebPageRecord y) {
        // No need to overcomplicate
        if (x.length > y.length) {
            return -1;
        } else if (x.length < y.length) {
            return 1;
        } else if (x.firstLine.length() > y.firstLine.length()) {
            return -1;
        } else if (x.firstLine.length() < y.firstLine.length()) {
            return 1;
        } else if (x.URL.compareTo(y.URL) < 0) {
            return -1;
        } else if (x.URL.compareTo(y.URL) > 0) {
            return 1;
        }

        return 0;
    }
}
