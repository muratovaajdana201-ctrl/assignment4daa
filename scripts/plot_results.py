
import csv
from pathlib import Path

import matplotlib.pyplot as plt

ROOT = Path(__file__).resolve().parent.parent
RESULTS = ROOT / "results"
RESULTS.mkdir(exist_ok=True)


def read_csv(filename):
    path = RESULTS / filename

    if not path.exists():
        raise FileNotFoundError(
            f"{path} not found. Run the Java benchmarks first."
        )

    with path.open("r", newline="", encoding="utf-8") as file:
        return list(csv.DictReader(file))


def plot_lcs_edit_distance():
    rows = read_csv("lcs_edit_distance.csv")
    plt.figure()

    for algorithm in ("LCS", "EditDistance"):
        selected = [
            row for row in rows if row["algorithm"] == algorithm
        ]
        selected.sort(key=lambda row: int(row["size"]))

        if selected:
            sizes = [int(row["size"]) for row in selected]
            times = [
                float(row["averageNanoseconds"]) / 1_000_000
                for row in selected
            ]
            plt.plot(sizes, times, marker="o", label=algorithm)

    plt.xlabel("Input size")
    plt.ylabel("Average time (milliseconds)")
    plt.title("LCS and Edit Distance Performance")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(RESULTS / "lcs_edit_distance.png")
    plt.close()


def plot_string_matching():
    rows = read_csv("string_matching.csv")
    plt.figure()

    for algorithm in ("Naive", "KMP", "RabinKarp"):
        for case_type in ("present", "absent"):
            selected = [
                row for row in rows
                if row["algorithm"] == algorithm
                   and row["caseType"] == case_type
            ]
            selected.sort(key=lambda row: int(row["size"]))

            if selected:
                sizes = [int(row["size"]) for row in selected]
                times = [
                    float(row["averageNanoseconds"]) / 1_000_000
                    for row in selected
                ]
                plt.plot(
                    sizes,
                    times,
                    marker="o",
                    label=f"{algorithm} ({case_type})"
                )

    plt.xlabel("Text size")
    plt.ylabel("Average time (milliseconds)")
    plt.title("String Matching Performance")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(RESULTS / "string_matching.png")
    plt.close()


def plot_kasai():
    rows = read_csv("kasai.csv")
    selected = [row for row in rows if row["algorithm"] == "Kasai"]
    selected.sort(key=lambda row: int(row["size"]))

    sizes = [int(row["size"]) for row in selected]
    times = [
        float(row["averageNanoseconds"]) / 1_000_000
        for row in selected
    ]

    plt.figure()
    plt.plot(sizes, times, marker="o", label="Kasai")
    plt.xlabel("Text size")
    plt.ylabel("Average time (milliseconds)")
    plt.title("Kasai LCP Algorithm Performance")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(RESULTS / "kasai.png")
    plt.close()


def main():
    plot_lcs_edit_distance()
    plot_string_matching()
    plot_kasai()
    print("All graphs were saved in the results folder.")


if __name__ == "__main__":
    main()
