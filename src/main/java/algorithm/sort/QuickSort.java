package algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author ShaoJiale
 * Date: 2020/2/29
 */
public class QuickSort {
    public static void sort(int[] arr, int left, int right) {
        if (left >= right) return;
        int i = left;
        int j = right;
        int key = arr[i];
        while (i < j) {
            while (i < j && arr[j] >= key) j--;
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && arr[i] <= key) i++;
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = key;
        sort(arr, i, right - 1);
        sort(arr, left + 1, i);
    }

    @Test
    public void test() {
        int[] arr = {9, 8, 7, 6, 5, 3, 123, 1};
        int[] tmp = new int[arr.length];
        System.arraycopy(arr, 0, tmp, 0, arr.length);
        sort(arr, 0, arr.length - 1);
        Arrays.sort(tmp);
        Assert.assertArrayEquals(tmp, arr);
    }
}
