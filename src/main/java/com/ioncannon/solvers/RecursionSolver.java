package com.ioncannon.solvers;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class RecursionSolver {

    /**
     * Solves the puzzle by starting from the max length and searching for smaller words
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

        CharSequence singleCharWords = "AI";
        Set<String> allWords = new TreeSet<>();
        ArrayList<String> maxLetterWords = new ArrayList<>();
        for (String word : dictionary) {
            if (StringUtils.containsAny(word, singleCharWords)) {
                if (word.length() < maxLetters) {
                    allWords.add(word);
                } else if (word.length() == maxLetters) {
                    maxLetterWords.add(word);
                }
            }
        }

        /* find solutions */
        ArrayList<String[]> solutions = new ArrayList<>();
        for (String maxLetterWord:maxLetterWords) {
            String[] possibleSolution = new String[maxLetters];
            possibleSolution[0] = maxLetterWord;
            possibleSolution = findNextWord(possibleSolution, allWords);
            if (!ArrayUtils.contains(possibleSolution, null)) {
                solutions.add(possibleSolution);
            }
        }

        return solutions;
    }

    private String[] findNextWord(String[] previousState, Set<String> allWords) {
        int lastWordIndex = (int) Arrays.stream(previousState).filter(Objects::nonNull).count() - 1;
        String lastWord = previousState[lastWordIndex];
        if (lastWord.length() <= 2) {
            if (lastWord.contains("A")) {
                previousState[previousState.length-1] = "A";
            }
            if (lastWord.contains("I")) {
                previousState[previousState.length-1] = "I";
            }
        } else {
            for (int i = 0; i < lastWord.length(); i++) {
                 String newWord = lastWord.substring(0, i) + lastWord.substring(i + 1);
                 if (allWords.contains(newWord)) {
                     previousState[lastWordIndex + 1] = newWord;
                     return findNextWord(previousState, allWords);
                 }
            }
        }
        return previousState;
    }
}
