package textanalysis.dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditDistanceTest {

    @Test
    void identicalStrings() {

        assertEquals(
                0,
                EditDistance.distance("abc", "abc")
        );
    }

    @Test
    void oneEmptyString() {

        assertEquals(
                3,
                EditDistance.distance("", "abc")
        );

        assertEquals(
                3,
                EditDistance.distance("abc", "")
        );
    }

    @Test
    void substitution() {

        assertEquals(
                1,
                EditDistance.distance("cat", "cut")
        );
    }

    @Test
    void insertion() {

        assertEquals(
                1,
                EditDistance.distance("cat", "cats")
        );
    }

    @Test
    void deletion() {

        assertEquals(
                1,
                EditDistance.distance("cats", "cat")
        );
    }
}