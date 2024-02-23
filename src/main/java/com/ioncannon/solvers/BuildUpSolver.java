package com.ioncannon.solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class BuildUpSolver {

    /**
     * Solves the puzzle by building up words from single letter to the maximum length
     * Finds all solutions, but is slowest of them all
     * @param dictionary - a list of all valid words
     * @param maxLetters - the maximum length of the words (9 in the original puzzle)
     * @param singleLetterWords - if the single letter words are not included in the dictionary, specify them here
     */
    public ArrayList<String[]> solve(ArrayList<String> dictionary, ArrayList<String> singleLetterWords, int maxLetters) {

        /* get separate dictionaries by number of letters in a word */
        Map<Integer, ArrayList<String>> wordsByNumOfLetters = new HashMap<Integer, ArrayList<String>>();
        for (int i = 1; i <= maxLetters; i++) {
            wordsByNumOfLetters.put(i, new ArrayList<String>());
        }

        if (singleLetterWords != null) {
            wordsByNumOfLetters.get(1).addAll(singleLetterWords);
        }

        CharSequence singleCharWords = String.join("", wordsByNumOfLetters.get(1));

        for (String word : dictionary) {
            if (word.length() <= maxLetters && StringUtils.containsAny(word, singleCharWords)) {
                wordsByNumOfLetters.get(word.length()).add(word);
            }
        }

        /* find solutions */
        ArrayList<String[]> solutions = new ArrayList<String[]>();
        for (String word : wordsByNumOfLetters.get(1)) {
            solutions.add(new String[]{word});
        }

        for (int wordIndex = 2; wordIndex <= maxLetters; wordIndex++) {
            ArrayList<String[]> prevSolutions = solutions;
            solutions = new ArrayList<String[]>();
            for (String[] solution : prevSolutions) {
                for (String word : wordsByNumOfLetters.get(wordIndex)) {
                    if (hasAllCharactersInOrder(word, solution[wordIndex-2])) {
                        // add new word to solution
                        String[] newSolution = new String[wordIndex];
                        System.arraycopy(solution, 0, newSolution, 0, wordIndex - 1);
                        newSolution[wordIndex - 1] = word;
                        solutions.add(newSolution);
                    }
                }
            }
        }

        return solutions;
    }

    private boolean hasAllCharactersInOrder(String mainString, String subString) {
        int mainIndex = 0;
        int subIndex = 0;

        while (mainIndex < mainString.length() && subIndex < subString.length()) {
            if (mainString.charAt(mainIndex) == subString.charAt(subIndex)) {
                subIndex++;
            }
            mainIndex++;
        }

        return subIndex == subString.length();
    }
}
