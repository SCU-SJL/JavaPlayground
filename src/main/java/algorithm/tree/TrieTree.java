package algorithm.tree;

/**
 * @author ShaoJiale
 * Date: 2020/3/5
 */
public class TrieTree {
    private TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) {
                node.put(ch, new TrieNode());
            }
            node = node.getNode(ch);
        }
        node.setEnd();
    }

    public boolean contains(String word) {
        TrieNode res = searchPrefix(word);
        return res != null && res.isEnd();
    }

    public TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.containsKey(ch)) {
                node = node.getNode(ch);
            } else {
                return null;
            }
        }
        return node;
    }

    public boolean startWith(String prefix) {
        TrieNode res = searchPrefix(prefix);
        return res != null;
    }
}
