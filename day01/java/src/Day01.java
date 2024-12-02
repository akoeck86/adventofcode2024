import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Day01 {

    public static void main(String... args) throws IOException {
        List<Integer> input1 = new ArrayList<>();
        List<Integer> input2 = new ArrayList<>();
        readFile(args[0], input1, input2);

        // Part 1
        part1(input1, input2);

        // Part 2
        var countMap = createCount(input1, input2);
        System.out.println("Result Part 2: " + calucateSimiltritaryScore(countMap));
    }

    private static void readFile(String path, List<Integer> array1, List<Integer> array2) throws IOException {
        var file = Path.of(path).toFile();
        var reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            var parts = line.split("\\s+");
            if (parts.length == 2) {
                array1.add(Integer.parseInt(parts[0]));
                array2.add(Integer.parseInt(parts[1]));
            }
        }
        reader.close();
    }

    private static void part1(List<Integer> input1, List<Integer> input2) {
        var sortedInput1 = input1.stream().sorted().toList();
        var sortedInput2 = input2.stream().sorted().toList();
        var distance = IntStream.range(0, sortedInput1.size())
                .map(index -> Math.abs(sortedInput1.get(index) - sortedInput2.get(index))).reduce(Integer::sum).orElse(0);
        System.out.println("Result Part 1: " + distance);
    }


    // Part 2
    private static Map<Integer, Long> createCount(List<Integer> input1, List<Integer> input2) {
        Map<Integer, Long> countMap = new HashMap<>();
        input1.forEach(n -> countMap.put(n, countInList(input2, n)));
        return countMap;
    }

    private static long countInList(List<Integer> list, Integer number) {
        return list.stream().filter(n -> Objects.equals(n, number)).count();
    }

    private static long calucateSimiltritaryScore(Map<Integer, Long> input) {
        List<Long> values = new ArrayList<>();
        input.forEach((number, count) -> values.add(number * count));
        return values.stream().reduce(0L, Long::sum);
    }
}
