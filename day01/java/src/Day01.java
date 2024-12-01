import java.io.*;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Day01 {

    public static void main(String... args) throws IOException {
        List<Integer> array1 = new ArrayList<>();
        List<Integer> array2 = new ArrayList<>();
        readFile(args[0], array1, array2);
        quickSort(array1, 0, array1.size() - 1);
        quickSort(array2, 0, array2.size() - 1);

        List<Integer> distances = new ArrayList<>();
        for(int i=0; i<array1.size(); i++) {
            distances.add(Math.abs(array1.get(i) - array2.get(i)));
        }
        System.out.println(distances.stream().reduce(0, Integer::sum).longValue());
    }

    private static void readFile(String path, List<Integer> array1, List<Integer> array2) throws IOException {
        var file = Path.of(path).toFile();
        var reader = new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine()) != null) {
            var parts = line.split("   ");
            if(parts.length == 2) {
                array1.add(Integer.parseInt(parts[0]));
                array2.add(Integer.parseInt(parts[1]));
            }
        }
    reader.close();
    }

    private static void quickSort(List<Integer> array, int low, int high) {
        if (low < high) {
            // Pivot-Index berechnen
            int pivotIndex = partition(array, low, high);

            // Rekursiv für linke und rechte Partition sortieren
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Integer> array, int low, int high) {
        int pivot = array.get(high); // Pivot als letztes Element wählen
        int i = low - 1; // Index für kleinere Elemente

        for (int j = low; j < high; j++) {
            if (array.get(j) <= pivot) {
                i++; // Index erhöhen
                // Elemente tauschen
                int temp = array.get(i);
                array.set(i,array.get(j));
                array.set(j, temp);
            }
        }

        // Pivot an die richtige Position bringen
        int temp = array.get(i + 1);
        array.set(i + 1,array.get(high));
        array.set(high, temp);

        return i + 1; // Rückgabe des Pivot-Indexes
    }
}
