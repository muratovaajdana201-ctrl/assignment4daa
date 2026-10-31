package textanalysis.suffix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class KasaiTest {

    @Test
    void bananaExample() {

        String text = "banana";

        int[] suffixArray =
                {5, 3, 1, 0, 4, 2};

        int[] expected =
                {0, 1, 3, 0, 0, 2};

        assertArrayEquals(
                expected,
                Kasai.buildLCP(
                        text,
                        suffixArray
                )
        );
    }

    @Test
    void shortExample() {

        String text = "aba";

        int[] suffixArray =
                {2, 0, 1};

        int[] expected =
                {0, 1, 0};

        assertArrayEquals(
                expected,
                Kasai.buildLCP(
                        text,
                        suffixArray
                )
        );
    }
}