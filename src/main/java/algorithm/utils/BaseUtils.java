package algorithm.utils;

import java.util.Stack;

/**
 * @author ShaoJiale
 * Date: 2020/2/16
 */
public class BaseUtils {
    public static int[] toBinary(int num) {
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

    public static int toDecimal(int[] binary) {
        int res = 0;
        int exponent = 0;
        for (int i = binary.length - 1; i >= 0; i--) {
            if (exponent == 0) {
                res += binary[i];
                exponent = 2;
            } else {
                res += binary[i] * exponent;
                exponent *= 2;
            }
        }
        return res;
    }

   public static int toDecimal(String hexadecimal) {
        int res = 0;
        int exponent = 0;
        for (int i = hexadecimal.length() - 1; i >= 0; i--) {
            if (exponent == 0) {
                res += getValueOfChar(hexadecimal.charAt(i));
                exponent = 36;
            } else {
                res += getValueOfChar(hexadecimal.charAt(i)) * exponent;
                exponent *= 36;
            }
        }
        return res;
   }

   public static String toHexadecimal(int decimal) {
        Stack<Character> stack = new Stack<>();
        while (decimal > 0) {
            stack.push(getCharOfValue(decimal % 36));
            decimal /= 36;
        }
        StringBuilder hexadecimal = new StringBuilder();
        while (!stack.isEmpty()) {
            hexadecimal.append(stack.pop());
        }
        return hexadecimal.toString();
   }

   public static int getValueOfChar(char ch) {
        if (ch < '0' || ch > 'z') {
            throw new IllegalArgumentException("Found illegal character: '" + ch + "'" );
        }
        return ch <= '9' ? ch - '0' : (ch > 'Z' ? ch - 'a' + 10 : ch - 'A' + 10);
   }

   public static char getCharOfValue(int val) {
        if (val > 35 || val < 0) {
            throw new IllegalArgumentException("Found illegal number: '" + val + "'");
        }
        return val < 10 ? (char)(val + '0') : (char)(val - 10 + 'A');
   }
}
