import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    public static void main(String... args) throws IOException {
        List<List<Integer>> reports = new ArrayList<>();
        readFile(args[0], reports);
        long safeReports = countSafeReports(reports);
        System.out.println("Safe reports: " + safeReports);
        long safeReportsWithDamper = countSafeReportsWithDampener(reports);
        System.out.println("Safe reports with dampener: " + safeReportsWithDamper);
    }


    private static void readFile(String path, List<List<Integer>> input) throws IOException {
        var file = Path.of(path).toFile();
        var reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            var parts = line.split("\\s+");
            input.add(Arrays.stream(parts).map(Integer::parseInt).toList());
        }
        reader.close();
    }

    private static long countSafeReports(List<List<Integer>> reports) {
        return reports.stream().filter(Day02::isSafe).count();
    }

    private static long countSafeReportsWithDampener(List<List<Integer>> reports) {
        return reports.stream().filter(Day02::isSafeWithDampener).count();
    }

    private static boolean isSafe(List<Integer> levels) {
        return isMonotonicAndValid(levels);
    }


    public static boolean isSafeWithDampener(List<Integer> levels) {
        if (isMonotonicAndValid(levels)) {
            return true;
        }

        for (int i = 0; i < levels.size(); i++) {
            var modifiedLevels = new ArrayList<>(levels);
            modifiedLevels.remove(i); // Remove the current level
            if (isMonotonicAndValid(modifiedLevels)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMonotonicAndValid(List<Integer> levels) {
        boolean isIncreasing = true, isDecreasing = true;

        for (int i = 0; i < levels.size() - 1; i++) {
            int diff = levels.get(i + 1) - levels.get(i);

            // Check difference is between 1 and 3
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Check monotonicity
            if (diff < 0) {
                isIncreasing = false;
            }
            if (diff > 0) {
                isDecreasing = false;
            }
        }

        return isIncreasing || isDecreasing;
    }

}
