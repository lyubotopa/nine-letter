package com.ioncannon.solvers;

import com.ioncannon.datatype.Trie;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrieSolver {

    /**
     * Solves the using a custom prefix tree implementation
     * @param dictionary - a list of all valid words
     * @param maxLetters - the maximum length of the words (9 in the original puzzle)
     * @param singleLetterWords - if the single letter words are not included in the dictionary, specify them here
     */
    public ArrayList<String[]> solve(ArrayList<String> dictionary, ArrayList<String> singleLetterWords, int maxLetters) {

        /* get separate dictionaries by number of letters in a word */
        Map<Integer, Trie> wordsByNumOfLetters = new HashMap<Integer, Trie>();
        for (int i = 1; i <= maxLetters; i++) {
            wordsByNumOfLetters.put(i, new Trie());
        }

        if (singleLetterWords != null) {
            for (String w : singleLetterWords){
                wordsByNumOfLetters.get(1).insert(w);
            }
        }

        CharSequence singleCharWords = String.join("", wordsByNumOfLetters.get(1).getAllStrings());

        for (String word : dictionary) {
            if (word.length() <= maxLetters && StringUtils.containsAny(word, singleCharWords)) {
                wordsByNumOfLetters.get(word.length()).insert(word);
            }
        }

        /* find solutions */
        ArrayList<String[]> solutions = new ArrayList<String[]>();
        for (String word : wordsByNumOfLetters.get(maxLetters).getAllStrings()) {
            solutions.add(new String[]{word});
        }

        for (int wordIndex = maxLetters-1; wordIndex > 0; wordIndex--) {
            ArrayList<String[]> prevSolutions = solutions;
            solutions = new ArrayList<String[]>();
            for (String[] solution : prevSolutions) {
                findNextWord: {
                    String currentWord = solution[maxLetters - wordIndex - 1];
                    String[] shortVersions = new String[currentWord.length()];
                    for (int i = 0; i < shortVersions.length; i++) {
                        shortVersions[i] = currentWord.substring(0, i) + currentWord.substring(i + 1);
                    }

                    for (String shortWord : wordsByNumOfLetters.get(wordIndex).getAllStrings()) {
                        for (String version : shortVersions) {
                            if (version.contentEquals(shortWord)) {
                                String[] newSolution = new String[maxLetters - wordIndex + 1];
                                System.arraycopy(solution, 0, newSolution, 0, maxLetters - wordIndex);
                                newSolution[maxLetters - wordIndex] = version;
                                solutions.add(newSolution);
                                break findNextWord;
                            }
                        }
                    }
                }
            }
        }

        return solutions;
    }
}
