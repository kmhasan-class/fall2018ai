/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localsearch;

import java.util.Arrays;

/**
 *
 * @author kmhasan
 */
public class LocalSearch {

    final int N = 8;

    public int[] generateRandomBoard() {
        int board[] = new int[N];
        for (int i = 0; i < N; i++) {
            board[i] = (int) (Math.random() * N);
        }
        return board;
    }

    // horrible solution
    public int countConflicts(int board[]) {
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                // horizontal conflict
                if (board[i] == board[j]) {
                    conflicts++;
                }

                // diagonal conflict
                int dx = Math.abs(i - j);
                int dy = Math.abs(board[i] - board[j]);

                if (dx == dy) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    public int[] steepestAscentHillClimbing() {
        int board[] = generateRandomBoard();
        int bestBoard[] = new int[N];
        System.arraycopy(board, 0, bestBoard, 0, N);

        int iteration = 0;

        while (true) {
            iteration++;
            int conflicts = countConflicts(bestBoard);
            System.arraycopy(bestBoard, 0, board, 0, N);
            int bestConflicts = conflicts;
//            System.out.println(iteration + ": " + Arrays.toString(board));
//            System.out.println(conflicts);

            for (int c = 0; c < N; c++) {
                int row = board[c];

                for (int r = 0; r < N; r++) {
                    if (r == row) {
                        continue;
                    }
                    board[c] = r;
                    int newConflicts = countConflicts(board);
                    if (newConflicts < bestConflicts) {
                        bestConflicts = newConflicts;
                        System.arraycopy(board, 0, bestBoard, 0, N);
//                        System.out.println("   " + Arrays.toString(bestBoard));
//                        System.out.println("   " + bestConflicts);
                    }
                }
                board[c] = row;
            }

            // HW: allow for sideways moves for M (100) times
            if (bestConflicts == conflicts || bestConflicts == 0) {
                break;
            }
        }

        return bestBoard;
    }

    public int[] randomNextStateHillClimbing() {
        int board[] = generateRandomBoard();
        int bestBoard[] = new int[N];
        System.arraycopy(board, 0, bestBoard, 0, N);

        int iteration = 0;

        while (true) {
            iteration++;
            int conflicts = countConflicts(bestBoard);
            System.arraycopy(bestBoard, 0, board, 0, N);
            int bestConflicts = conflicts;
//            System.out.println(iteration + ": " + Arrays.toString(board));
//            System.out.println(conflicts);

            //for (int c = 0; c < N; c++) {
            int c = (int) Math.random() * N;
            int row = board[c];

            int r = (int) Math.random() * N;
            board[c] = r;
            int newConflicts = countConflicts(board);
            if (newConflicts < bestConflicts) {
                bestConflicts = newConflicts;
                System.arraycopy(board, 0, bestBoard, 0, N);
//                        System.out.println("   " + Arrays.toString(bestBoard));
//                        System.out.println("   " + bestConflicts);
            }
            // }
            board[c] = row;
            //}

            // HW: allow for sideways moves for M (100) times
            if (bestConflicts == conflicts || bestConflicts == 0) {
                break;
            }
        }

        return bestBoard;
    }

    public int[] simulatedAnnealing() {
        int board[] = generateRandomBoard();
        int bestBoard[] = new int[N];
        System.arraycopy(board, 0, bestBoard, 0, N);

        int iteration = 0;

        double temperature = 100;
        
        while (true) {
            temperature -= 0.001;
            iteration++;
            int conflicts = -countConflicts(bestBoard);
            System.arraycopy(bestBoard, 0, board, 0, N);
            int bestConflicts = conflicts;
            int c = (int) Math.random() * N;
            int row = board[c];

            int r = (int) Math.random() * N;
            board[c] = r;
            int newConflicts = -countConflicts(board);
            
            if (newConflicts > conflicts) {
                // take the move
                // we're going for a better state
            } else {
                double rnd = Math.random();
                double acceptanceProbability = Math.exp((newConflicts - conflicts) / temperature);
                if (rnd <= acceptanceProbability) {
                    // take the move
                    // this is a bad move, but it may lead to something
                    // good
                }
            }

            
            if (bestConflicts == conflicts || bestConflicts == 0) {
                break;
            }
        }

        return bestBoard;
    }

    public LocalSearch() {
        /*
        int board[] = generateRandomBoard();
        System.out.println(Arrays.toString(board));
        System.out.println(countConflicts(board));
         */
        int success = 0;
        int totalTrials = 100000;
        for (int i = 1; i <= totalTrials; i++) {
            int[] board = randomNextStateHillClimbing();
            int conflicts = countConflicts(board);
            System.out.println(conflicts + " " + Arrays.toString(board));
            if (conflicts == 0) {
                success++;
            }
        }

        System.out.println(success + "/" + totalTrials);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new LocalSearch();
    }

}
