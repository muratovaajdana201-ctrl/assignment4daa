package textanalysis.matching;

import java.util.ArrayList;
import java.util.List;

public final class NaiveSearch {

    private NaiveSearch() {
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

        for (
                int i = 0;
                i <= text.length() - pattern.length();
                i++
        ) {

            int j = 0;

            while (j < pattern.length()) {

                comparisons++;

                if (
                        text.charAt(i + j)
                                != pattern.charAt(j)
                ) {
                    break;
                }

                j++;
            }

            if (j == pattern.length()) {
                result.add(i);
            }
        }

        return new SearchResult(
                result,
                comparisons,
                0
        );
    }
}