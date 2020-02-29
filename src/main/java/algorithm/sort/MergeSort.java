package algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author ShaoJiale
 * Date: 2020/2/29
 */
public class MergeSort {
    public static void sort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int middle = (left + right) / 2;
            sort(arr, left, middle, temp);
            sort(arr, middle + 1, right, temp);
            merge(arr, left, middle, right, temp);
        }
    }

    private static void merge(int[] arr, int left, int middle, int right, int[] temp) {
        int start1 = left;
        int start2 = middle + 1;
        int index = 0;
        while (start1 <= middle && start2 <= right) {
            if (arr[start1] <= arr[start2]) {
                temp[index++] = arr[start1++];
            } else {
                temp[index++] = arr[start2++];
            }
        }
        while (start1 <= middle) {
            temp[index++] = arr[start1++];
        }
        while (start2 <= right) {
            temp[index++] = arr[start2++];
        }
        if (index >= 0) System.arraycopy(temp, 0, arr, left, index);
    }

    @Test
    public void test() {
        int[] arr = {9, 8, 7, 6, 5, 3, 123, 1};
        int[] tmp = new int[arr.length];
        System.arraycopy(arr, 0, tmp, 0, arr.length);
        sort(arr, 0, arr.length - 1, new int[arr.length]);
        Arrays.sort(tmp);
        Assert.assertArrayEquals(tmp, arr);
    }
}
