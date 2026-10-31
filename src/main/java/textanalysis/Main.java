
package textanalysis;

import textanalysis.experiment.BenchmarkRunner;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("benchmark")) {
            try {
                BenchmarkRunner.runAll(Path.of("results"));
                System.out.println("Experiments completed successfully.");
                System.out.println("CSV files saved in the results folder.");
            } catch (Exception e) {
                System.err.println("Error running experiments: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Text Analysis project");
            System.out.println("To run benchmarks, use the argument: benchmark");
        }
    }
}
