package algorithm.tree;

/**
 * @author ShaoJiale
 * Date: 2020/3/5
 */
public class TrieNode {
    private TrieNode[] links;
    private boolean end;
    public final int CHAR_NUMS = 26;

    public TrieNode() {
        links = new TrieNode[CHAR_NUMS];
        end = false;
    }

    public void put(char ch, TrieNode node) {
        links[ch - 'a'] = node;
    }

    public boolean containsKey(char ch) {
        return links[ch - 'a'] != null;
    }

    public TrieNode getNode(char ch) {
        return links[ch - 'a'];
    }

    public void setEnd() {
        this.end = true;
    }

    public boolean isEnd() {
        return this.end;
    }
}
