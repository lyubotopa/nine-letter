package com.ioncannon.datatype;

import java.util.ArrayList;
import java.util.List;

class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;

    TrieNode() {
        children = new TrieNode[26]; // Assuming lowercase English letters only
        isEndOfWord = false;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word into the trie
    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'A';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    // Search for a word in the trie
    public boolean search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'A';
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return current != null && current.isEndOfWord;
    }

    // Get all stored strings in the trie
    public List<String> getAllStrings() {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        getAllStringsHelper(root, sb, result);
        return result;
    }

    private void getAllStringsHelper(TrieNode node, StringBuilder sb, List<String> result) {
        if (node.isEndOfWord) {
            result.add(sb.toString());
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                sb.append((char)('A' + i));
                getAllStringsHelper(node.children[i], sb, result);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }

}
