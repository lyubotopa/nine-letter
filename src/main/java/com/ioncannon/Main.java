package com.ioncannon;

import com.ioncannon.solvers.RecursionSolver;
import com.ioncannon.solvers.TopDownSolver;
import com.ioncannon.utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        ArrayList<String> dictionary = fileReader.readRemoteFile("https://raw.githubusercontent.com/nikiiv/" +
                "JavaCodingTestOne/master/scrabble-words.txt");

        long beforeSolving = System.currentTimeMillis();
        //TopDownSolver solver = new TopDownSolver();
        //BuildUpSolver solver = BuildUpSolver();
        RecursionSolver solver = new RecursionSolver();
        ArrayList<String[]> solutions = solver.solve(dictionary, new ArrayList<>(Arrays.asList("A", "I")), 9);
        long afterSolving = System.currentTimeMillis();

        System.out.printf("Solving took %d milliseconds%n", afterSolving-beforeSolving);
        System.out.println(solutions.size() + " solutions found");

        for (String[] solution : solutions) {
            System.out.println("\n");
            for (int i=solution.length-1; i>=0; i--) {
                System.out.println(solution[i]);
            }
        }
    }
}