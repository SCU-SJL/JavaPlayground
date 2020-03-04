package algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

/**
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 * 返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 *
 * @author ShaoJiale
 * Date: 2020/3/4
 */
public class RottenOranges {
    private static class RottenOrange {
        private int x;
        private int y;

        public RottenOrange(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private LinkedList<RottenOrange> queue = new LinkedList<>();
    private int rows;
    private int cols;

    public int orangesRotting(int[][] grid) {
        if (grid.length < 1 || grid[0].length < 1) {
            return -1;
        }

        rows = grid.length;
        cols = grid[0].length;
        if (rows == 1 && cols == 1 && grid[0][0] == 0) {
            return 0;
        }
        int res = 0;
        int sum = 0;
        int rottenCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] > 0) sum++;
            }
        }

        while (sum > rottenCount) {
            res++;
            int preCount = rottenCount;
            rottenCount = 0;

            while (!queue.isEmpty()) {
                RottenOrange orange = queue.poll();
                grid[orange.x][orange.y] = 2;
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 2) {
                        infect(i, j, grid);
                        grid[i][j] = 3;
                    }
                    if (grid[i][j] > 1) {
                        rottenCount++;
                    }
                }
            }
            if (rottenCount == sum) {
                break;
            }
            if (rottenCount == preCount) {
                return -1;
            }
        }
        return res - 1;
    }

    private void infect(int row, int col, int[][] grid) {
        doInfect(row - 1, col, grid);
        doInfect(row + 1, col, grid);
        doInfect(row, col - 1, grid);
        doInfect(row, col + 1, grid);
    }

    private void doInfect(int row, int col, int[][] grid) {
        if (!(row < 0 || col < 0 || row >= rows || col >= cols) && grid[row][col] > 0) {
            queue.offer(new RottenOrange(row, col));
        }
    }

    @Test
    public void test() {
        int[][] grid = {{2, 1, 1}, {0, 1, 0}, {1, 0, 1}};
        Assert.assertEquals(-1, orangesRotting(grid));
    }
}
