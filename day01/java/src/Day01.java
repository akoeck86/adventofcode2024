import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Day01 {

    public static void main(String... args) throws IOException {
        List<Integer> input1 = new ArrayList<>();
        List<Integer> input2 = new ArrayList<>();
        readFile(args[0], input1, input2);

        // Part 1
        quickSort(input1, 0, input1.size() - 1);
        quickSort(input2, 0, input2.size() - 1);
        // Result Part 1
        System.out.println("Result Part 1: " + caluclateTotalDistance(input1, input2));

        // Part 2
        var countMap = createCount(input1, input2);
        System.out.println("Result Part 2: " + calucateSimiltritaryScore(countMap));
    }

    private static void readFile(String path, List<Integer> array1, List<Integer> array2) throws IOException {
        var file = Path.of(path).toFile();
        var reader = new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine()) != null) {
            var parts = line.split("\\s+");
            if(parts.length == 2) {
                array1.add(Integer.parseInt(parts[0]));
                array2.add(Integer.parseInt(parts[1]));
            }
        }
        reader.close();
    }

    private static void quickSort(List<Integer> array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Integer> array, int low, int high) {
        int pivot = array.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array.get(j) <= pivot) {
                i++;
                int temp = array.get(i);
                array.set(i,array.get(j));
                array.set(j, temp);
            }
        }
        int temp = array.get(i + 1);
        array.set(i + 1,array.get(high));
        array.set(high, temp);
        return i + 1;
    }


    private static int caluclateTotalDistance(List<Integer> input1, List<Integer> input2) {
        List<Integer> distances = new ArrayList<>();
        for(int i=0; i<input1.size(); i++) {
            distances.add(Math.abs(input1.get(i) - input2.get(i)));
        }
        return distances.stream().reduce(0, Integer::sum);
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
