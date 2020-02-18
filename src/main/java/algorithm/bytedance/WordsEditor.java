package algorithm.bytedance;

import org.junit.Assert;
import org.junit.Test;

/**
 * 1. 三个同样的字母连在一起，一定是拼写错误，去掉一个的就好啦：比如 helllo -> hello
 * 2. 两对一样的字母（ AABB 型）连在一起，一定是拼写错误，去掉第二对的一个字母就好啦：比如 helloo -> hello
 * 3. 上面的规则优先“从左到右”匹配，即如果是 AABBCC，虽然 AABB 和 BBCC 都是错误拼写，应该优先考虑修复 AABB，结果为 AABCC
 * @author ShaoJiale
 * Date: 2020/2/18
 */
public class WordsEditor {
    public String edit(String word) {
        StringBuilder res = new StringBuilder(word);
        for (int i = 0; i < res.length() - 3; ) {
            if (tripleEqual(res, i)) {
                res.deleteCharAt(i);
            } else if (quadrupleEqual(res, i)) {
                res.deleteCharAt(i + 2);
            } else {
                i++;
            }
        }
        if (tripleEqual(res, res.length() - 3)) {
            res.deleteCharAt(res.length() - 3);
        }
        return res.toString();
    }

    private boolean tripleEqual(StringBuilder word, int i) {
        return word.charAt(i) == word.charAt(i + 1)
                && word.charAt(i + 1) == word.charAt(i + 2);
    }

    private boolean quadrupleEqual(StringBuilder word, int i) {
        return word.charAt(i) == word.charAt(i + 1)
                && word.charAt(i + 2) == word.charAt(i + 3);
    }

    @Test
    public void test() {
        // 已通过牛客网所有测试用例
        Assert.assertEquals("AABCC", edit("AABBCC"));
        Assert.assertEquals("hello", edit("helloo"));
        Assert.assertEquals("hello", edit("helllo"));
    }
}
