import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day04 {

    public static void main(String... args) throws IOException {
        var grid = readFile(args[0]);
        var count = findOccurrences(grid);
        System.out.println("XMAS count: " + count);
        var xmasCount = findXMas(grid);
        System.out.println("X-Mas count: " + xmasCount);
    }


    private static char[][] readFile(String path) throws IOException {
        var file = Path.of(path).toFile();
        var reader = new BufferedReader(new FileReader((file)));

        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        char[][] grid = new char[lines.getFirst().length()][lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                grid[i][j] = lines.get(i).charAt(j);
            }
        }
        return grid;
    }

    // Part 1
    private static int findOccurrences(char[][] grid) {
        var rows = grid.length;
        var cols = grid[0].length;
        var count = 0;

        int[][] directions = {
                {0, 1}, {1, 0}, {1, 1}, {-1, 1},
                {0, -1}, {-1, 0}, {-1, -1}, {1, -1}
        };

        char[] wordArray = "XMAS".toCharArray();

        for (var row = 0; row < rows; row++) {
            for (var col = 0; col < cols; col++) {
                for (int[] direction : directions) {
                    if (checkDirection(grid, row, col, wordArray, direction, rows, cols)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private static boolean checkDirection(char[][] grid, int row, int col, char[] word, int[] direction, int rows, int cols) {
        var wordLength = word.length;

        for (var i = 0; i < wordLength; i++) {
            var newRow = row + i * direction[0];
            var newCol = col + i * direction[1];

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols || grid[newRow][newCol] != word[i]) {
                return false;
            }
        }

        return true;
    }

    // Part 2
    public static int findXMas(char[][] grid) {
        var rows = grid.length;
        var cols = grid[0].length;
        var count = 0;

        for (var row = 1; row < rows - 1; row++) {
            for (var col = 1; col < cols - 1; col++) {
                if (grid[row][col] == 'A') {
                    count += checkXMas(grid, row, col, rows, cols);
                }
            }
        }

        return count;
    }

    public static int checkXMas(char[][] grid, int row, int col, int rows, int cols) {
        int patterns = 0;

        int[][] diagonals = {
                {-1, -1, 1, 1},
                {-1, 1, 1, -1}
        };


        var mTopRow = row + diagonals[0][0];
        var mTopCol = col + diagonals[0][1];
        var sBottomRow = row + diagonals[0][2];
        var sBottomCol = col + diagonals[0][3];
        var sTopRow = row + diagonals[1][0];
        var sTopCol = col + diagonals[1][1];
        var mBottomRow = row + diagonals[1][2];
        var mBottomCol = col + diagonals[1][3];

        if (isInBounds(mTopRow, mTopCol, rows, cols) && isInBounds(sBottomRow, sBottomCol, rows, cols) && isInBounds(sTopRow, sTopCol, rows, cols) && isInBounds(mBottomRow, mBottomCol, rows, cols)) {
            var mTop = grid[mTopRow][mTopCol];
            var sBottom = grid[sBottomRow][sBottomCol];
            var sTop = grid[sTopRow][sTopCol];
            var mBottom = grid[mBottomRow][mBottomCol];

            if ((mTop == 'M' && sBottom == 'S') && (sTop == 'S' && mBottom == 'M')) {
                patterns++;
            }
        }

        return patterns;
    }

    public static boolean isInBounds(int row, int col, int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
