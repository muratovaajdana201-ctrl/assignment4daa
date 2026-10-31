package textanalysis.dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LCSTest {

    @Test
    void identicalStrings() {
        assertEquals(
                3,
                LCS.findLCS("abc", "abc")
        );
    }

    @Test
    void completelyDifferentStrings() {
        assertEquals(
                0,
                LCS.findLCS("abc", "xyz")
        );
    }

    @Test
    void emptyString() {

        assertEquals(
                0,
                LCS.findLCS("", "abc")
        );

        assertEquals(
                0,
                LCS.findLCS("abc", "")
        );
    }

    @Test
    void standardCase() {

        assertEquals(
                3,
                LCS.findLCS("abcde", "ace")
        );
    }
}