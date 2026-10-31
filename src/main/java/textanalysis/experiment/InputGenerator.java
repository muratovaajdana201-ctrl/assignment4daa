package textanalysis.experiment;

import java.util.Random;

public final class InputGenerator {

    public static final long SEED = 42L;

    public static final String ALPHABET =
            "abcdefghijklmnopqrstuvwxyz";

    private InputGenerator() {
    }

    public static String randomString(
            int length,
            Random random
    ) {

        StringBuilder sb =
                new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            sb.append(
                    ALPHABET.charAt(
                            random.nextInt(
                                    ALPHABET.length()
                            )
                    )
            );
        }

        return sb.toString();
    }
}