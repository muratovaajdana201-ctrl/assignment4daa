package textanalysis.matching;

import java.util.List;

public record SearchResult(
        List<Integer> indices,
        long characterComparisons,
        long hashComparisons
) {
}