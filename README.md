# Assignment 4: Text Analysis

## 1. Project Overview

The goal of this assignment is to implement, test, and compare algorithms for text analysis. The project covers dynamic programming, string matching, and suffix-array processing.

The project includes:

- Longest Common Subsequence (LCS).
- Levenshtein Edit Distance.
- Naive String Search.
- Knuth-Morris-Pratt (KMP).
- Rabin-Karp.
- Kasai algorithm for building the Longest Common Prefix (LCP) array.
- Benchmark experiments and performance graphs.

The algorithms are implemented in Java. JUnit tests are used to check their correctness. Benchmark results are saved as CSV files, and a Python script generates graphs from these results.

## 2. Implementation and Decisions

### Longest Common Subsequence (LCS)

- LCS is implemented using dynamic programming.
- A two-dimensional table stores the LCS length for prefixes of the two input strings.
- If the current characters are equal, the value is the diagonal cell plus one.
- Otherwise, the algorithm takes the maximum of the cell above and the cell on the left.
- The last table cell contains the length of the longest common subsequence.

### Levenshtein Edit Distance

- Edit Distance uses dynamic programming to calculate the minimum number of operations needed to transform one string into another.
- The allowed operations are insertion, deletion, and substitution.
- The first row and column represent transformations involving an empty string.
- If two current characters are equal, no additional operation is required.
- Otherwise, the algorithm chooses the minimum cost among insertion, deletion, and substitution.

### Naive String Search

- The algorithm checks every possible starting position in the text.
- It compares the pattern characters with the corresponding text characters.
- Every position where the full pattern matches is added to the result.
- This algorithm is simple, but it may repeat many character comparisons.

### Knuth-Morris-Pratt (KMP)

- KMP uses an LPS array during searching.
- LPS means Longest Proper Prefix which is also a Suffix.
- The LPS array is calculated before the search starts.
- When a mismatch occurs, the array helps determine how far the pattern can move without repeating unnecessary comparisons.
- This improves the worst-case running time compared with the naive approach.

### Rabin-Karp

- Rabin-Karp compares the hash of the pattern with the hash of each text window.
- A rolling hash updates the window hash when the window moves by one character.
- The implementation uses base 31 and modulus 1,000,000,007.
- When the hashes match, the algorithm compares the actual characters to confirm a match.
- This verification is necessary because different strings can have the same hash value.

### Kasai Algorithm

- Kasai builds an LCP array from a string and its suffix array.
- LCP means Longest Common Prefix.
- Each LCP value represents the common-prefix length of two neighbouring suffixes in suffix-array order.
- The algorithm first builds a rank array that records the position of each suffix in the suffix array.
- It reuses information from previous comparisons to avoid starting every comparison from zero.
- The suffix array is constructed separately before the Kasai benchmark starts.

### Suffix Array Construction

- The project includes a simple suffix-array construction method.
- It uses prefix doubling and rank values to sort suffixes.
- In each iteration, the algorithm compares ranks for increasingly longer prefixes.
- The suffix-array construction is used to prepare input for Kasai and is not included in the Kasai timing measurements.
- The implemented prefix-doubling approach uses sorting in each iteration, giving an approximate time complexity of O(n log² n).

### Empty Pattern Behaviour

- Searching for an empty pattern returns all positions from 0 to the length of the text, inclusive.
- For example, searching for an empty pattern in the text "abc" returns [0, 1, 2, 3].
- Naive Search, KMP, and Rabin-Karp use the same behaviour.
- This behaviour is checked by the string-matching tests.

## 3. Time and Space Complexity

Here, n is the text length and m is the pattern length. For LCS and Edit Distance, n and m are the lengths of the two input strings. For string matching, n is the text length and m is the pattern length.

The space complexity of string-matching algorithms excludes the list of returned match positions, which can require O(k) space for k matches.

| Algorithm | Main operation | Best-case time | Worst-case time | Auxiliary space |
|---|---|---|---|---|
| LCS | Dynamic programming table | O(nm) | O(nm) | O(nm) |
| Edit Distance | Dynamic programming table | O(nm) | O(nm) | O(nm) |
| Naive Search | Compare pattern at each possible position | O(n) | O(nm) | O(1) |
| KMP | LPS preprocessing and pattern search | O(n + m) | O(n + m) | O(m) |
| Rabin-Karp | Rolling hash and character verification | Expected O(n + m) | O(nm) | O(1) |
| Kasai | Rank array and LCP construction | O(n) | O(n) | O(n) |
| Suffix Array Construction | Prefix doubling and sorting | O(n log² n) | O(n log² n) | O(n) |

### Complexity Justification

- LCS: The algorithm fills an n × m dynamic programming table. Each cell requires constant time.
- Edit Distance: Each cell is calculated using insertion, deletion, and substitution values. The table contains n × m cells.
- Naive Search: In the best case, comparisons fail immediately at most positions. In the worst case, up to m characters are compared at each of approximately n positions.
- KMP: The LPS array is built in O(m), and searching takes O(n). Previously matched characters are not unnecessarily compared again.
- Rabin-Karp: Rolling hash updates take constant time per window. Expected time is O(n + m), while repeated hash matches and character verification can produce O(nm) worst-case time.
- Kasai: The rank and LCP arrays are constructed in linear time when the suffix array is already available.
- Suffix Array Construction: The prefix-doubling implementation sorts suffix ranks in each iteration. There are O(log n) iterations, each requiring O(n log n) sorting time.

The suffix-array construction is separate from Kasai's algorithm and is excluded from the Kasai benchmark.

## 4. Testing

JUnit tests are included for the main algorithms and important edge cases.

### LCS Tests

- Identical strings.
- Completely different strings.
- An empty string.
- A standard example with a known LCS length.

### Edit Distance Tests

- Identical strings.
- An empty string.
- Character substitution.
- Character insertion.
- Character deletion.

### String Matching Tests

- A match at the beginning of the text.
- A match in the middle.
- A match at the end.
- Multiple occurrences.
- A pattern that is absent.
- A pattern longer than the text.
- An empty pattern.

The tests check that Naive Search, KMP, and Rabin-Karp return the same expected positions for the same input.

### Kasai Tests

- A short string with a known suffix array.
- A second short example with a known LCP array.
- Comparison of the calculated LCP values with expected values.

The tests can be run through the Maven test lifecycle in IntelliJ IDEA.

## 5. Experimental Setup

The experiments use randomly generated strings with a fixed random seed of 42. The alphabet for generated strings is:

abcdefghijklmnopqrstuvwxyz

The tested input sizes are:

- LCS and Edit Distance: 100, 500, and 1,000 characters for each input string.
- String matching: text lengths of 10,000, 50,000, and 100,000 characters.
- Kasai: string lengths of 1,000, 5,000, and 10,000 characters.

### String Matching Experiments

- Both present and absent patterns are tested.
- The same text and pattern are used for Naive Search, KMP, and Rabin-Karp within each comparison.
- The present pattern is selected from the middle of the generated text.
- The absent pattern contains { characters, which are outside the generated lowercase alphabet.
- The pattern length is 20 characters.

### Timing Method

- Execution time is measured with System.nanoTime().
- Each experiment uses three warm-up runs followed by five measured runs.
- The average time is calculated from the five measured runs.
- Input generation and CSV file writing are outside the measured algorithm time.
- For KMP and Rabin-Karp, the measured search call includes preprocessing performed inside the search method.
- For Kasai, suffix-array construction is completed before timing the LCP calculation.
- The string-matching CSV results include average character-comparison and hash-comparison counters where implemented.

Java execution time can be affected by JVM optimisation, garbage collection, and other running processes. The benchmark results should therefore be interpreted as measurements from a particular environment rather than exact theoretical running times.

## 6. Experimental Results and Graphs

The benchmark program creates the following CSV files:

- results/lcs_edit_distance.csv
- results/string_matching.csv
- results/kasai.csv

The Python plotting script reads these files and generates three graphs.

### LCS and Edit Distance

The following table shows the average execution time for LCS and Edit Distance.

| Algorithm | Input size | Average time (ns) |
|---|---:|---:|
| LCS | 100 | 2,041,920 |
| Edit Distance | 100 | 2,790,600 |
| LCS | 500 | 8,553,560 |
| Edit Distance | 500 | 9,334,700 |
| LCS | 1,000 | 11,225,620 |
| Edit Distance | 1,000 | 12,233,860 |

![LCS and Edit Distance Performance](results/lcs_edit_distance.png)

Both algorithms use dynamic programming and have O(nm) time complexity. Their execution times are expected to increase as the input strings become longer because more table cells must be processed.

### String Matching

The following table shows the average execution time for present and absent patterns.

| Algorithm | Text length | Pattern case | Average time (ns) |
|---|---:|---|---:|
| Naive | 10,000 | Present | 2,549,880 |
| KMP | 10,000 | Present | 1,466,260 |
| Rabin-Karp | 10,000 | Present | 2,913,000 |
| Naive | 10,000 | Absent | 1,240,460 |
| KMP | 10,000 | Absent | 890,380 |
| Rabin-Karp | 10,000 | Absent | 1,654,160 |
| Naive | 50,000 | Present | 3,442,480 |
| KMP | 50,000 | Present | 1,829,640 |
| Rabin-Karp | 50,000 | Present | 3,922,900 |
| Naive | 50,000 | Absent | 393,360 |
| KMP | 50,000 | Absent | 498,100 |
| Rabin-Karp | 50,000 | Absent | 3,478,140 |
| Naive | 100,000 | Present | 811,840 |
| KMP | 100,000 | Present | 638,060 |
| Rabin-Karp | 100,000 | Present | 2,235,120 |
| Naive | 100,000 | Absent | 816,460 |
| KMP | 100,000 | Absent | 643,480 |
| Rabin-Karp | 100,000 | Absent | 2,221,720 |

![String Matching Performance](results/string_matching.png)

Naive Search may repeat many character comparisons. KMP uses the LPS array to avoid unnecessary repeated comparisons. Rabin-Karp uses rolling hashes and verifies characters when hash values match.

The actual running times depend on the input data, implementation details, and the computer used for the experiment.

### Kasai Algorithm

The following table shows the average execution time of Kasai for different string lengths.

| Algorithm | Input size | Average time (ns) |
|---|---:|---:|
| Kasai | 1,000 | 538,820 |
| Kasai | 5,000 | 1,483,920 |
| Kasai | 10,000 | 842,260 |

![Kasai LCP Algorithm Performance](results/kasai.png)

Kasai has O(n) time complexity when the suffix array is already available. Suffix-array construction is excluded from the measured Kasai execution time.

The graphs are generated from the CSV results. They should be regenerated after running the benchmarks again.



## 7. Theoretical and Experimental Comparison

Theoretical complexity explains how algorithms should scale as inputs grow. Experiments show how they behave on actual generated inputs.

- LCS and Edit Distance both use dynamic programming tables, so their running times increase with the product of the input lengths.
- Naive Search is easy to implement but can repeat many character comparisons.
- KMP uses the LPS array to avoid unnecessary repeated comparisons and has O(n + m) total time complexity.
- Rabin-Karp updates the hash as the text window moves, but hash matches still require character verification.
- Kasai constructs the LCP array in linear time when the suffix array is already available.
- Suffix-array construction is a separate operation and is not included in the Kasai timing measurements.

The measured results may not increase perfectly with each input size. JVM optimisation, garbage collection, and other processes can affect individual measurements. The graphs should be considered experimental evidence and compared with the theoretical complexity of each algorithm.

## 8. Conclusion

In this assignment, I implemented and tested dynamic programming algorithms, three string-matching algorithms, and the Kasai algorithm.

LCS and Edit Distance solve their problems by storing intermediate results in tables. Naive Search is straightforward but can repeat comparisons. KMP improves searching by using the LPS array, while Rabin-Karp uses rolling hashes and verifies possible matches. Kasai efficiently builds the LCP array when the suffix array is available.

The benchmark experiments provide a practical comparison of algorithm performance at different input sizes. They also show why theoretical complexity and measured execution time should be considered together.

Each algorithm has its own advantages, and the best choice depends on the input data and the requirements of the problem.

## 9. How to Run

1. Open the project in IntelliJ IDEA.
2. Reload the Maven project.
3. Run the Maven test lifecycle.
4. Run textanalysis.Main with the program argument benchmark.
5. From the project root, run python scripts/plot_results.py.
6. Check that the CSV files and PNG graphs have been created in the results directory.

The results directory should contain the benchmark CSV files and the generated graphs. The graphs are referenced directly in this README so they can also be displayed when viewing the project on GitHub.
