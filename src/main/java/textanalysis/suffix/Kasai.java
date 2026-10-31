package textanalysis.suffix;

public final class Kasai {

    private Kasai() {
    }

    public static int[] buildLCP(
            String text,
            int[] suffixArray
    ) {

        int n = text.length();

        if (suffixArray.length != n) {

            throw new IllegalArgumentException(
                    "Suffix array length must equal text length."
            );
        }

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {

            rank[suffixArray[i]] = i;
        }

        int[] lcp = new int[n];

        int k = 0;

        for (int i = 0; i < n; i++) {

            int r = rank[i];

            if (r == 0) {

                k = 0;
                continue;
            }

            int j = suffixArray[r - 1];

            while (
                    i + k < n
                            && j + k < n
                            && text.charAt(i + k)
                            == text.charAt(j + k)
            ) {

                k++;
            }

            lcp[r] = k;

            if (k > 0) {
                k--;
            }
        }

        return lcp;
    }
}