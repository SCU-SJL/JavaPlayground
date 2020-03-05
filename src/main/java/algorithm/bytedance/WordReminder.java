package algorithm.bytedance;

import algorithm.tree.TrieNode;
import algorithm.tree.TrieTree;

import java.util.LinkedList;
import java.util.List;

/**
 * 模拟搜索引擎的词汇补全，输入一个字符串，提示以其开头的所有结果
 *
 * @author ShaoJiale
 * Date: 2020/3/5
 */
public class WordReminder {
    public List<String> getRelatedWords(String word, TrieTree trieTree) {
        List<String> res = new LinkedList<>();

        TrieNode node = trieTree.searchPrefix(word);
        if (node.isEnd()) {
            res.add(word);
        }

        for (int i = 0; i < node.CHAR_NUMS; i++) {
            char nextCh = (char) ('a' + i);
            TrieNode next = node.getNode(nextCh);
            if (next != null) {
                getRelatedWords(word, i, next, res);
            }
        }

        return res;
    }

    private void getRelatedWords(String word, int index, TrieNode node, List<String> list) {
        if (node.isEnd()) {
            char ch = (char) ('a' + index);
            list.add(word + ch);
        }
        for (int i = 0; i < node.CHAR_NUMS; i++) {
            char currCh = (char) ('a' + index);
            char nextCh = (char) ('a' + i);
            TrieNode next = node.getNode(nextCh);
            if (next != null) {
                getRelatedWords(word + currCh, i, next, list);
            }
        }
    }
}
