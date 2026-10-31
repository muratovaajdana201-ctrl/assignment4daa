
package textanalysis.experiment;

import textanalysis.dp.EditDistance;
import textanalysis.dp.LCS;
import textanalysis.matching.KMP;
import textanalysis.matching.NaiveSearch;
import textanalysis.matching.RabinKarp;
import textanalysis.matching.SearchResult;
import textanalysis.suffix.Kasai;
import textanalysis.suffix.SimpleSuffixArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BenchmarkRunner {

    private static final int RUNS = 5;

    private BenchmarkRunner() {
    }

    public static void runAll(Path resultsDirectory) throws IOException {
        Files.createDirectories(resultsDirectory);

        runLcsAndEditDistance(resultsDirectory.resolve("lcs_edit_distance.csv"));
        runStringMatching(resultsDirectory.resolve("string_matching.csv"));
        runKasai(resultsDirectory.resolve("kasai.csv"));
    }

    private static void runLcsAndEditDistance(Path output) throws IOException {
        int[] sizes = {100, 500, 1000};
        Random random = new Random(42);
        List<String> lines = new ArrayList<>();

        lines.add("algorithm,size,caseType,averageNanoseconds,averageCharacterComparisons,averageHashComparisons");

        for (int size : sizes) {
            String a = randomString(size, random);
            String b = randomString(size, random);

            double lcsTime = averageTime(() -> LCS.findLCS(a, b));
            lines.add(csv("LCS", size, "random", lcsTime, 0, 0));

            double editTime = averageTime(() -> EditDistance.distance(a, b));
            lines.add(csv("EditDistance", size, "random", editTime, 0, 0));
        }

        Files.write(output, lines);
    }

    private static void runStringMatching(Path output) throws IOException {
        int[] sizes = {10_000, 50_000, 100_000};
        Random random = new Random(42);
        List<String> lines = new ArrayList<>();

        lines.add("algorithm,size,caseType,averageNanoseconds,averageCharacterComparisons,averageHashComparisons");

        for (int size : sizes) {
            String text = randomString(size, random);
            String presentPattern = text.substring(size / 2, size / 2 + 20);
            String absentPattern = "{".repeat(20);

            addMatchingRow(lines, "Naive", size, "present",
                    text, presentPattern, NaiveSearch::searchWithStats);

            addMatchingRow(lines, "KMP", size, "present",
                    text, presentPattern, KMP::searchWithStats);

            addMatchingRow(lines, "RabinKarp", size, "present",
                    text, presentPattern, RabinKarp::searchWithStats);

            addMatchingRow(lines, "Naive", size, "absent",
                    text, absentPattern, NaiveSearch::searchWithStats);

            addMatchingRow(lines, "KMP", size, "absent",
                    text, absentPattern, KMP::searchWithStats);

            addMatchingRow(lines, "RabinKarp", size, "absent",
                    text, absentPattern, RabinKarp::searchWithStats);
        }

        Files.write(output, lines);
    }

    private static void addMatchingRow(
            List<String> lines,
            String algorithm,
            int size,
            String caseType,
            String text,
            String pattern,
            SearchFunction function
    ) {
        for (int i = 0; i < 3; i++) {
            function.search(text, pattern);
        }

        long totalTime = 0;
        long totalChars = 0;
        long totalHashes = 0;




        for (int run = 0; run < RUNS; run++) {
            long start = System.nanoTime();
            SearchResult result = function.search(text, pattern);
            long end = System.nanoTime();

            totalTime += end - start;
            totalChars += result.characterComparisons();
            totalHashes += result.hashComparisons();
        }

        lines.add(csv(
                algorithm,
                size,
                caseType,
                (double) totalTime / RUNS,
                (double) totalChars / RUNS,
                (double) totalHashes / RUNS
        ));
    }

    private static void runKasai(Path output) throws IOException {
        int[] sizes = {1000, 5000, 10000};
        Random random = new Random(42);
        List<String> lines = new ArrayList<>();

        lines.add("algorithm,size,caseType,averageNanoseconds,averageCharacterComparisons,averageHashComparisons");

        for (int size : sizes) {
            String text = randomString(size, random);
            int[] suffixArray = SimpleSuffixArray.build(text);

            for (int i = 0; i < 3; i++) {
                Kasai.buildLCP(text, suffixArray);
            }
            long totalTime = 0;

            for (int run = 0; run < RUNS; run++) {
                long start = System.nanoTime();
                Kasai.buildLCP(text, suffixArray);
                long end = System.nanoTime();

                totalTime += end - start;
            }

            lines.add(csv(
                    "Kasai",
                    size,
                    "random",
                    (double) totalTime / RUNS,
                    0,
                    0
            ));
        }

        Files.write(output, lines);
    }

    private static double averageTime(Runnable operation) {

        for (int i = 0; i < 3; i++) {
            operation.run();
        }

        long total = 0;

        for (int run = 0; run < RUNS; run++) {
            long start = System.nanoTime();
            operation.run();
            long end = System.nanoTime();

            total += end - start;
        }

        return (double) total / RUNS;

    }

    private static String randomString(int length, Random random) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return result.toString();
    }

    private static String csv(
            String algorithm,
            int size,
            String caseType,
            double time,
            double chars,
            double hashes
    ) {
        return String.join(",",
                algorithm,
                String.valueOf(size),
                caseType,
                String.valueOf(time),
                String.valueOf(chars),
                String.valueOf(hashes)
        );
    }

    @FunctionalInterface
    private interface SearchFunction {
        SearchResult search(String text, String pattern);
    }
}
