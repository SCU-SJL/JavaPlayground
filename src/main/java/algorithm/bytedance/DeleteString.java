package algorithm.bytedance;

import org.junit.Test;

import java.util.*;

/**
 * 编写一个方法，删除 ArrayList<String> 中以 "1_" 开头的元素
 * @author ShaoJiale
 * Date: 2020/2/25
 */
public class DeleteString {
    public void delete(List<String> list) {
        List<String> res = new LinkedList<>(list);
        res.removeIf(str -> str.startsWith("1_"));
        list.clear();
        list.addAll(res);
    }

    public void delete(ArrayList<String> list) {
        int left = 0, right = list.size() - 1;
        while (left < right){
            String tmp = list.get(left);
            if (tmp.startsWith("1_")) {
                String str = list.get(right);
                list.set(right, tmp);
                list.set(left, str);
                list.remove(right);
                right--;
            } else {
                left++;
            }
        }
    }
    @Test
    public void test() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1___");
        list.add("1_aa");
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("1_abc");
        list.add("1_123");
        list.add("1____");
        delete(list);
        System.out.println(list);
    }
}