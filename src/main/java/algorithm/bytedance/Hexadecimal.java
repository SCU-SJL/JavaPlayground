package algorithm.bytedance;

import algorithm.utils.BaseUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * 计算 36 进制的加法，要求不允许将 36 进制转化为 10 进制计算
 * @author ShaoJiale
 * Date: 2020/2/16
 */
public class Hexadecimal {
    /**
     * 使用 {@link BaseUtils#toHexadecimal(int)}
     * 和 {@link BaseUtils#toDecimal(String)} 完成
     */
    public String defaultHexadecimalAddition(String num1, String num2) {
        return BaseUtils.toHexadecimal(BaseUtils.toDecimal(num1) + BaseUtils.toDecimal(num2));
    }

    /**
     * 模拟笔算过程，使用按位计算和进位完成
     */
    public String hexadecimalAddition(String num1, String num2) {
        if (num1.length() < num2.length()) {
            return hexadecimalAddition(num2, num1);
        }

        StringBuilder res = new StringBuilder();
        int carry = 0;
        int len1 = num1.length();
        int len2 = num2.length();

        for (int i = 0; carry > 0 || i < len1; i++) {
            int addition = i > len2 - 1 ? (i > len1 - 1 ? carry
                    : carry + BaseUtils.getValueOfChar(num1.charAt(len1 - 1 - i)))
                    : carry + BaseUtils.getValueOfChar(num1.charAt(len1 - 1 - i)) + BaseUtils.getValueOfChar(num2.charAt(len2 - 1 - i));
            if (addition > 35) {
                addition %= 36;
                res.append(BaseUtils.getCharOfValue(addition));
                carry = 1;
            } else {
                res.append(BaseUtils.getCharOfValue(addition));
                carry = 0;
            }
        }
        return res.reverse().toString();
    }

    @Test
    public void test() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            String num1 = BaseUtils.toHexadecimal(random.nextInt(100000));
            String num2 = BaseUtils.toHexadecimal(random.nextInt(100000));
            Assert.assertEquals(defaultHexadecimalAddition(num1, num2), hexadecimalAddition(num1, num2));
        }
    }
}
