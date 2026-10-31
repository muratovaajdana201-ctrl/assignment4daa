
package textanalysis.suffix;

import java.util.Arrays;
import java.util.Comparator;

public final class SimpleSuffixArray {

    private SimpleSuffixArray() {
    }

    public static int[] build(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        int n = text.length();
        int[] suffixArray = new int[n];
        int[] rank = new int[n];

        if (n == 0) {
            return suffixArray;
        }

        for (int i = 0; i < n; i++) {
            suffixArray[i] = i;
            rank[i] = text.charAt(i);
        }

        int[] nextRank = new int[n];

        for (int length = 1; length < n; length *= 2) {
            final int offset = length;
            final int[] currentRank = rank;

            Integer[] suffixes = new Integer[n];

            for (int i = 0; i < n; i++) {
                suffixes[i] = i;
            }

            Arrays.sort(suffixes, Comparator
                    .comparingInt((Integer i) -> currentRank[i])
                    .thenComparingInt(i ->
                            i + offset < n
                                    ? currentRank[i + offset]
                                    : -1));

            for (int i = 0; i < n; i++) {
                suffixArray[i] = suffixes[i];
            }

            nextRank[suffixArray[0]] = 0;

            for (int i = 1; i < n; i++) {
                int previous = suffixArray[i - 1];
                int current = suffixArray[i];

                boolean different =
                        rank[previous] != rank[current];

                int previousSecond =
                        previous + length < n
                                ? rank[previous + length]
                                : -1;

                int currentSecond =
                        current + length < n
                                ? rank[current + length]
                                : -1;

                if (different || previousSecond != currentSecond) {
                    nextRank[current] = nextRank[previous] + 1;
                } else {
                    nextRank[current] = nextRank[previous];
                }
            }

            int[] temp = rank;
            rank = nextRank;
            nextRank = temp;

            if (rank[suffixArray[n - 1]] == n - 1) {
                break;
            }

            if (length > n / 2) {
                break;
            }
        }

        return suffixArray;
    }
}
