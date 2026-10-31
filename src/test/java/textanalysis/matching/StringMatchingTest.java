package textanalysis.matching;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringMatchingTest {

    @Test
    void beginning() {
        assertAllSame(
                "abcdef",
                "abc",
                List.of(0)
        );
    }

    @Test
    void middle() {
        assertAllSame(
                "xxabcxx",
                "abc",
                List.of(2)
        );
    }

    @Test
    void end() {
        assertAllSame(
                "xxabc",
                "abc",
                List.of(2)
        );
    }

    @Test
    void multipleOccurrences() {
        assertAllSame(
                "abababa",
                "aba",
                List.of(0, 2, 4)
        );
    }

    @Test
    void absentPattern() {
        assertAllSame(
                "abcdef",
                "xyz",
                List.of()
        );
    }

    @Test
    void patternLongerThanText() {
        assertAllSame(
                "abc",
                "abcdef",
                List.of()
        );
    }

    @Test
    void emptyPattern() {
        assertAllSame(
                "abc",
                "",
                List.of(0, 1, 2, 3)
        );
    }

    private void assertAllSame(
            String text,
            String pattern,
            List<Integer> expected
    ) {

        List<Integer> naive =
                NaiveSearch.search(text, pattern);

        List<Integer> kmp =
                KMP.search(text, pattern);

        List<Integer> rk =
                RabinKarp.search(text, pattern);

        assertEquals(expected, naive);
        assertEquals(expected, kmp);
        assertEquals(expected, rk);

        assertEquals(naive, kmp);
        assertEquals(kmp, rk);
    }
}