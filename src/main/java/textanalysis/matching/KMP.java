package textanalysis.matching;

import java.util.ArrayList;
import java.util.List;

public final class KMP {

    private KMP() {
    }

    public static int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (
                    pattern.charAt(i)
                            == pattern.charAt(length)
            ) {

                length++;
                lps[i] = length;
                i++;

            } else if (length > 0) {

                length = lps[length - 1];

            } else {

                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }

    public static List<Integer> search(
            String text,
            String pattern
    ) {
        return searchWithStats(text, pattern).indices();
    }

    public static SearchResult searchWithStats(
            String text,
            String pattern
    ) {

        List<Integer> result = new ArrayList<>();

        long comparisons = 0;

        if (pattern.isEmpty()) {
            for (int i = 0; i <= text.length(); i++) {
                result.add(i);
            }

            return new SearchResult(result, 0, 0);
        }

        if (pattern.length() > text.length()) {

            return new SearchResult(
                    result,
                    0,
                    0
            );
        }

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            comparisons++;

            if (
                    text.charAt(i)
                            == pattern.charAt(j)
            ) {

                i++;
                j++;

                if (j == pattern.length()) {

                    result.add(i - j);

                    j = lps[j - 1];
                }

            } else if (j > 0) {

                j = lps[j - 1];

            } else {

                i++;
            }
        }

        return new SearchResult(
                result,
                comparisons,
                0
        );
    }
}