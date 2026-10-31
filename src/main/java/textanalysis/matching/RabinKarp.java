package textanalysis.matching;

import java.util.ArrayList;
import java.util.List;

public final class RabinKarp {

    private static final long BASE = 31L;
    private static final long MOD = 1_000_000_007L;

    private RabinKarp() {
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

        long characterComparisons = 0;
        long hashComparisons = 0;

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

        int m = pattern.length();

        long patternHash = hash(pattern);

        long windowHash =
                hash(text.substring(0, m));

        long highestPower = 1;

        for (int i = 1; i < m; i++) {

            highestPower =
                    (highestPower * BASE) % MOD;
        }

        for (
                int i = 0;
                i <= text.length() - m;
                i++
        ) {

            hashComparisons++;

            if (windowHash == patternHash) {

                boolean equal = true;

                for (int j = 0; j < m; j++) {

                    characterComparisons++;

                    if (
                            text.charAt(i + j)
                                    != pattern.charAt(j)
                    ) {

                        equal = false;
                        break;
                    }
                }

                if (equal) {
                    result.add(i);
                }
            }

            if (i < text.length() - m) {

                long outgoing =
                        (text.charAt(i) * highestPower)
                                % MOD;

                windowHash =
                        (windowHash - outgoing) % MOD;

                if (windowHash < 0) {
                    windowHash += MOD;
                }

                windowHash =
                        (windowHash * BASE
                                + text.charAt(i + m))
                                % MOD;
            }
        }

        return new SearchResult(
                result,
                characterComparisons,
                hashComparisons
        );
    }

    private static long hash(String s) {

        long h = 0;

        for (int i = 0; i < s.length(); i++) {

            h =
                    (h * BASE + s.charAt(i))
                            % MOD;
        }

        return h;
    }
}