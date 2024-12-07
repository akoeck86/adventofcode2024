import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day03 {

    public static void main(String... args) throws IOException {
        var input = readFile(args[0]);
        System.out.println("Result: " + multiply(input));
        System.out.println("Result 2: " + multiplyWithDisableAndEnable(input));
    }

    private static String readFile(String path) throws IOException {
        var file = Path.of(path).toFile();
        var reader = new BufferedReader(new FileReader((file)));

        String line;
        var buffer = new StringBuilder();
        while((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    private static Long multiply(String input) {
        final var regex = "mul\\((\\d+),(\\d+)\\)";
        final var pattern = Pattern.compile(regex);
        final var matcher = pattern.matcher(input);

        List<Integer> results = new ArrayList<>();

        while(matcher.find()) {
            int num1 = Integer.parseInt(matcher.group(1));
            int num2 = Integer.parseInt(matcher.group(2));
            results.add(num1 * num2);
        }
        return results.stream().reduce(0, Integer::sum).longValue();
    }

    private static Long multiplyWithDisableAndEnable(String input) {
        final var mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        final var mulMatcher = mulPattern.matcher(input);
        final var doPattern = Pattern.compile("do\\(\\)");
        final var doMatcher = doPattern.matcher(input);
        final var dontPattern = Pattern.compile("don't\\(\\)");
        final var dontMatcher = dontPattern.matcher(input);

        var isEnabled = true;
        long totalSum = 0;
        int currentPosition = 0;

        while(currentPosition < input.length()) {
            if(dontMatcher.find(currentPosition) && dontMatcher.start() == currentPosition) {
                isEnabled = false;
                currentPosition=dontMatcher.end();
            } else if(doMatcher.find(currentPosition) && doMatcher.start() == currentPosition) {
                isEnabled = true;
                currentPosition=doMatcher.end();
            } else if (mulMatcher.find(currentPosition) && mulMatcher.start() == currentPosition) {
                if(isEnabled) {
                    int num1 = Integer.parseInt(mulMatcher.group(1));
                    int num2 = Integer.parseInt(mulMatcher.group(2));
                    totalSum += (long) num1 * num2;
                }
                currentPosition = mulMatcher.end();
            } else {
                currentPosition++;
            }
        }
        return totalSum;
    }

}
