package algorithm.bytedance;

/**
 * 寻找矩阵中的最长上升路径的长度
 *
 * @author ShaoJiale
 * Date: 2020/3/2
 */
public class LongestPath {
    private int rows;
    private int cols;
    private int[][] cache;
    private int[] rowDirs = {0, 1, 0, -1};
    private int[] colDirs = {1, 0, -1, 0};

    public int findPath(int[][] matrix) {
        rows = matrix.length;
        cols = matrix[0].length;
        cache = new int[rows][cols];
        int res = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res = Math.max(res, dfs(i, j, matrix));
            }
        }
        return res;
    }

    private int dfs(int row, int col, int[][] matrix) {
        if (cache[row][col] > 0) {
            return cache[row][col];
        }
        for (int i = 0; i < 4; i++) {
            int nextRow = row + rowDirs[i];
            int nextCol = col + colDirs[i];
            if (overBorder(nextRow, nextCol) || matrix[nextRow][nextCol] <= matrix[row][col]) {
                continue;
            }
            cache[row][col] = Math.max(cache[row][col], dfs(nextRow, nextCol, matrix));
        }
        return ++cache[row][col];
    }

    private boolean overBorder(int row, int col) {
        return row < 0 || row >= rows || col < 0 || col >= cols;
    }
}
