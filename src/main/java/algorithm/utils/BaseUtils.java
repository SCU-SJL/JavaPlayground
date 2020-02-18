package algorithm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * Utils of different base.
 *
 * @author ShaoJiale
 * Date: 2020/2/16
 */
public class BaseUtils {

    public static int binaryToDecimal(int[] binary) {
        return convertToBinaryWithBase(binary, null, 2);
    }

    public static int hexToDecimal(String hex) {
        return convertToBinaryWithBase(null, hex, 16);
    }

    public static int hexadecimalToDecimal(String hexadecimal) {
        return convertToBinaryWithBase(null, hexadecimal, 36);
    }

    public static int[] decimalToBinary(int num) {
        Stack<Integer> stack = new Stack<>();
        while (num > 0) {
            stack.push(num % 2);
            num /= 2;
        }
        int[] res = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            res[i++] = stack.pop();
        }
        return res;
    }

    public static String decimalToHex(int decimal) {
        return convertToHexOrHexadecimalWithBase(decimal, 16);
    }

    public static String decimalToHexadecimal(int decimal) {
        return convertToHexOrHexadecimalWithBase(decimal, 36);
    }

    public static int getValueOfChar(char ch) {
        if (ch < '0' || ch > 'z') {
            throw new IllegalArgumentException("Found illegal character: '" + ch + "'");
        }
        return ch <= '9' ? ch - '0' : (ch > 'Z' ? ch - 'a' + 10 : ch - 'A' + 10);
    }

    public static char getCharOfValue(int val) {
        if (val > 35 || val < 0) {
            throw new IllegalArgumentException("Found illegal number: '" + val + "'");
        }
        return val < 10 ? (char) (val + '0') : (char) (val - 10 + 'A');
    }

    private static int convertToBinaryWithBase(int[] binary, String hexOrHexadecimal, int base) {
        int res = 0;
        int exponent = 1;
        if (binary != null) {
            for (int i = binary.length - 1; i >= 0; i--) {
                res += binary[i] * exponent;
                exponent *= base;
            }
        } else {
            for (int i = hexOrHexadecimal.length() - 1; i >= 0; i--) {
                res += getValueOfChar(hexOrHexadecimal.charAt(i)) * exponent;
                exponent *= base;
            }
        }
        return res;
    }

    private static String convertToHexOrHexadecimalWithBase(int decimal, int base) {
        Stack<Character> stack = new Stack<>();
        while (decimal > 0) {
            stack.push(getCharOfValue(decimal % base));
            decimal /= base;
        }
        StringBuilder hexadecimal = new StringBuilder();
        while (!stack.isEmpty()) {
            hexadecimal.append(stack.pop());
        }
        return hexadecimal.toString();
    }

    @Test
    public void test() {
        Assert.assertEquals(65535, hexToDecimal("ffff"));
        Assert.assertEquals("FFFF", decimalToHex(65535));
    }
}
