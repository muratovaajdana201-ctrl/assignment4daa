package textanalysis.experiment;

public record ExperimentResult(
        String algorithm,
        int size,
        String caseType,
        double averageNanoseconds,
        double averageCharacterComparisons,
        double averageHashComparisons
) {
}