
//
// Title: (testsokoban)
// Files: (none)
// Course: (cs200 fall 2018)
//
// Author: ROSALIE CAI
// Email: RCAI25 @wisc.edu
// Lecturer's Name: MARC RENAULT
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: (NONE)
// Online Sources: (NONE)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
/**
 * This file contains testing methods for the Sokoban project. These methods are intended to provide
 * an example of a way to incrementally test your code, and to provide example method calls for the
 * Sokoban methods
 *
 * Toward these objectives, the expectation is that part of the grade for the Sokoban project is to
 * write some tests and write header comments summarizing the tests that have been written. Specific
 * places are noted with FIXME but add any other comments you feel would be useful.
 */

import java.util.Arrays;

/**
 * This class contains a few methods for testing methods in the Sokoban class as they are developed.
 * These methods are all private as they are only intended for use within this class.
 * 
 * @author Marc Renault
 * @author Rosalie Cai
 *
 */
public class TestSokoban {

    /**
     * This is the main method that runs the various tests. Uncomment the tests when you are ready
     * for them to run.
     * 
     * @param args (unused)
     */
    public static void main(String[] args) {
        // Milestone 1
        testCheckLevel();
        // Milestone 2
        testInitBoard();
        testCheckWin();
        testCalcDelta();
        testCheckDelta();
        // Milestone 3
        testTogglePos();
        testShiftBox();
        testDoMove();
        testProcessMove();
    }

    /**
     * test for Sokoban.checkLevel
     */
    private static void testCheckLevel() {
        int numTests = 4;
        int passed = numTests;
        int res;
        // Test checkLevel test 1
        if ((res = Sokoban.checkLevel(-1, Config.LEVELS, Config.GOALS)) != 0) {
            System.out.println(
                "FAILED: Sokoban.checkLevel Test 1. Expected 0, but value returned " + res);
            passed--;
        }

        // Test checkLevel test 2
        char[][][] lvl = new char[2][8][];
        if ((res = Sokoban.checkLevel(1, lvl, Config.GOALS)) != -1) {
            System.out.println(
                "FAILED: Sokoban.checkLevel Test 2. Expected -1, but value returned " + res);
            passed--;
        }

        // Test checkLevel test 3
        int[][] goo = new int[3][3];

        if ((res = Sokoban.checkLevel(1, Config.LEVELS, goo)) != -2) {
            System.out.println(
                "FAILED: Sokoban.checkLevel Test 3. Expected -2, but value returned " + res);
            passed--;
        }

        // Test checkLevel test 4
        char[][][] lev = new char[2][8][9];
        lev[0][0][0] = lev[0][0][1] = Config.BOX_CHAR;
        if ((res = Sokoban.checkLevel(1, lev, Config.GOALS)) != -3) {
            System.out.println(
                "FAILED: Sokoban.checkLevel Test 4. Expected -3, but value returned " + res);
            passed--;
        }

        System.out.println("testCheckLevel: Passed " + passed + " of " + numTests + " tests.");
    }

    /**
     * Returns true if the arrays are the same size and have the same contents.
     */
    private static boolean compBoards(char[][] a, char[][] b) {
        if (a == null || b == null)
            return false;
        if (a.length != b.length)
            return false;
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * test for Sokoban.initBoard
     */
    private static void testInitBoard() {
        int numTests = 3;
        int passed = numTests;

        // Test 1
        int[] pTest1 = new int[2];
        char[][] bTest1 = Sokoban.initBoard(0, Config.LEVELS, Config.GOALS, pTest1);
        if (!Arrays.equals(pTest1, new int[] {4, 4})) {
            System.out.println(
                "FAILED: Sokoban.initBoard Test 1. Expected initial position: {4, 4} , but value after call "
                    + Arrays.toString(pTest1));
            passed--;
        }
        char[][] bCompTest1 = new char[][] {
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.GOAL_CHAR, Config.BOX_CHAR,
                Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.WORKER_CHAR}};

        if (!compBoards(bTest1, bCompTest1)) {
            System.out.println("FAILED: Sokoban.initBoard Test 1. Board not as expected!");
            System.out.println("Generated:");
            Sokoban.printBoard(bTest1);
            System.out.println("Expected:");
            Sokoban.printBoard(bCompTest1);
            passed--;

        }
        // End of Test 1;

        // test 3
        int[] pTest2 = new int[Config.GOALS[1].length];
        {
            for (int i = 0; i < Config.GOALS[1].length; i++) {
                pTest2[i] = Config.GOALS[1][i];
            }
        }
        char[][] bTest2 = Sokoban.initBoard(1, Config.LEVELS, Config.GOALS, pTest2);
        char[][] bCompTest2 = new char[][] {
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.WALL_CHAR,
                Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.WALL_CHAR,
                Config.BOX_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR,
                Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.BOX_CHAR},
            {Config.EMPTY_CHAR, Config.WALL_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.BOX_CHAR, Config.EMPTY_CHAR, Config.BOX_CHAR, Config.EMPTY_CHAR},
            {Config.WALL_CHAR, Config.WALL_CHAR, Config.EMPTY_CHAR, Config.WALL_CHAR,
                Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.EMPTY_CHAR,
                Config.WALL_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR,
                Config.WALL_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.WALL_CHAR,
                Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.EMPTY_CHAR,
                Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR,
                Config.WALL_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.GOAL_CHAR,
                Config.GOAL_CHAR},
            {Config.EMPTY_CHAR, Config.BOX_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.BOX_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.GOAL_CHAR,
                Config.GOAL_CHAR},
            {Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR,
                Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR,
                Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WORKER_CHAR, Config.WALL_CHAR,
                Config.WALL_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.GOAL_CHAR,
                Config.GOAL_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.WALL_CHAR,
                Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR,
                Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR,
                Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR, Config.WALL_CHAR,
                Config.WALL_CHAR}};
        if (!compBoards(bTest2, bCompTest2)) {
            System.out.println("FAILED: Sokoban.initBoard Test 2. Board not as expected!");
            System.out.println("Generated:");
            Sokoban.printBoard(bTest2);
            System.out.println("Expected:");
            Sokoban.printBoard(bCompTest2);
            passed--;
        }

        System.out.println("testInitBoard: Passed " + passed + " of " + numTests + " tests.");
    }

    /**
     * test for Sokoban.checkWin
     */
    private static void testCheckWin() {
        // test box goal
        char[][] test1 =
            {{Config.BOX_GOAL_CHAR, Config.EMPTY_CHAR}, {Config.WORKER_CHAR, Config.EMPTY_CHAR}};
        if (Sokoban.checkWin(test1) == true) {
            System.out.println("Test checkwin test 1 passed");
        } else {
            System.out.println("Test checkwin test 1 failed");
        }

        // test goal
        char[][] test2 =
            {{Config.GOAL_CHAR, Config.EMPTY_CHAR}, {Config.WORKER_CHAR, Config.EMPTY_CHAR}};
        if (Sokoban.checkWin(test2) == false) {
            System.out.println("Test checkwin test 2 passed");
        } else {
            System.out.println("Test checkwin test 2 failed");
        }

        // test work goal
        char[][] test3 =
            {{Config.WORK_GOAL_CHAR, Config.EMPTY_CHAR}, {Config.WORKER_CHAR, Config.EMPTY_CHAR}};
        if (Sokoban.checkWin(test3) == false) {
            System.out.println("Test checkwin test 3 passed");
        } else {
            System.out.println("Test checkwin test 3 failed");
        }
    }

    /**
     * test for CalcDelta
     */
    private static void testCalcDelta() {
        // test up
        String up = "81";
        if (Sokoban.calcDelta(up)[0] != -1 || Sokoban.calcDelta(up)[1] != 0) {
            System.out.println("up failed");
        }

        // test down
        String down = "2";
        if (Sokoban.calcDelta(down)[0] != 1 || Sokoban.calcDelta(up)[1] != 0) {
            System.out.println("down failed");
        }

        // test left
        String left = "77";
        if (Sokoban.calcDelta(left)[0] != 0 || Sokoban.calcDelta(left)[1] != 0) {
            System.out.println("left failed");
        }

        // test right
        String right = "41";
        if (Sokoban.calcDelta(right)[0] != 0 || Sokoban.calcDelta(right)[1] != -1) {
            System.out.println("right failed");
        }

    }

    /**
     * test for CheckDelta
     */
    private static void testCheckDelta() {
        // test checkDelta test1
        int[] pos = null;
        if (Sokoban.checkDelta(Config.LEVELS[0], pos, new int[] {4, 4},
            new char[] {Config.WORKER_CHAR}) != -1) {
            System.out.println("Check delta test valid1 failed");
        }
        int[] pos2 = new int[] {1, 1, 1};
        if (Sokoban.checkDelta(Config.LEVELS[0], pos2, new int[] {4, 4},
            new char[] {Config.WORKER_CHAR}) != -1) {
            System.out.println("Check delta test valid2 failed");
        }
        int[] pos3 = new int[] {99, 0};
        if (Sokoban.checkDelta(Config.LEVELS[0], pos3, new int[] {4, 4},
            new char[] {Config.WORKER_CHAR}) != -1) {
            System.out.println("Check delta test valid3 failed");
        }
        int[] pos4 = new int[] {0, 99};
        if (Sokoban.checkDelta(Config.LEVELS[0], pos4, new int[] {4, 4},
            new char[] {Config.WORKER_CHAR}) != -1) {
            System.out.println("Check delta test valid4 failed");
        }

        // test checkDelta test2
        char[] valid = new char[] {Config.WORK_GOAL_CHAR, Config.WORKER_CHAR};
        if (Sokoban.checkDelta(Config.LEVELS[0], new int[] {4, 3}, new int[] {1, 1}, valid) != -2) {
            System.out.println("Check delta test valid failed");
        }

        // test checkDelta test3
        int[] delta11 = null;
        if (Sokoban.checkDelta(Config.LEVELS[0], new int[] {4, 4}, delta11,
            new char[] {Config.WORKER_CHAR}) != -3) {
            System.out.println("Check delta test 3.1 failed");
        }

        int[] delta12 = {2, 2, 2};
        if (Sokoban.checkDelta(Config.LEVELS[0], new int[] {4, 4}, delta12,
            new char[] {Config.WORKER_CHAR}) != -3) {
            System.out.println("Check delta test 3.2 failed");
        }

        // test checkDelta test4
        int[] delta13 = {99, 2};
        if (Sokoban.checkDelta(Config.LEVELS[0], new int[] {4, 4}, delta13,
            new char[] {Config.WORKER_CHAR}) != -4) {
            System.out.println("Check delta test 4.1 failed");
        }

        int[] delta14 = {0, -1};
        if (Sokoban.checkDelta(Config.LEVELS[1], new int[] {7, 10}, delta14,
            new char[] {Config.WORKER_CHAR}) != -4) {
            System.out.println("Check delta test 4.2 failed");
        }

    }

    /**
     * test for Socoban.togglePos
     */
    private static void testTogglePos() {
        char[][] board1 = new char[][] {{Config.EMPTY_CHAR, Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR}};
        Sokoban.togglePos(board1, new int[] {1, 1}, Config.WORKER_CHAR, Config.BOX_CHAR,
            Config.BOX_GOAL_CHAR);
        if (board1[1][1] != Config.BOX_GOAL_CHAR) {
            System.out.println("testTogglePos test 1 failed");
        } else {
            System.out.println("testTogglePos test 1 passed");
        }
    }

    /**
     * test for Socoban.shiftBox
     */
    private static void testShiftBox() {
        char[][] board = new char[][] {{Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.BOX_CHAR}};
        Sokoban.shiftBox(board, new int[] {1, 3}, new int[] {0, -1});
        if (board[1][2] == Config.BOX_CHAR && board[1][3] == Config.EMPTY_CHAR) {
            System.out.println("testShiftBox passed");
        } else {
            System.out.println("testShiftBox failed" + board[1][2] + " " + board[1][3]);
        }
    }

    /**
     * test for Socoban.doMOve
     */
    private static void testDoMove() {
        // test 1
        char[][] board1 = new char[][] {{Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WORKER_CHAR, Config.BOX_CHAR}};
        if (Sokoban.doMove(board1, new int[] {1, 2}, new int[] {0, -1}) != -4) {
            System.out.println("testDoMove 1 failed");
        } else {
            System.out.println("testDoMove 1 passed");
        }

        // test 2
        char[][] board2 = new char[][] {{Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WORKER_CHAR, Config.BOX_CHAR,
                Config.EMPTY_CHAR}};
        int[] posWorker = new int[] {1, 2};
        if (Sokoban.doMove(board2, posWorker, new int[] {0, 1}) != 1) {
            System.out.println("testDoMove 2 failed");
        } else {
            System.out.println("testDoMove 2 passed");
        }

        if (board2[1][2] != Config.EMPTY_CHAR || board2[1][4] != Config.BOX_CHAR) {
            System.out.println("testDoMove 2 failed");
        } else {
            System.out.println("testDoMove 2 passed");
        }

        if (posWorker[0] != 1 || posWorker[1] != 3) {
            System.out.println("testDoMove 2 failed");
        } else {
            System.out.println("testDoMove 2 passed");
        }
    }

    /**
     * test for Socoban.processMove
     */
    private static void testProcessMove() {
        // test 1
        int[] delta1 = {0, 0};
        char[][] board1 = new char[][] {{Config.EMPTY_CHAR, Config.EMPTY_CHAR, Config.EMPTY_CHAR},
            {Config.EMPTY_CHAR, Config.WALL_CHAR, Config.WORKER_CHAR, Config.BOX_CHAR,
                Config.EMPTY_CHAR}};
        int[] posWorker = new int[] {1, 2};
        if (Sokoban.processMove(board1, posWorker, delta1) != 0) {
            System.out.println("processmove test1 failed");
        } else {
            System.out.println("processmove 1 passed");
        }

        // test 2
        int[] delta2 = {-1, -2};
        Sokoban.processMove(board1, posWorker, delta2);
        if (posWorker[0] != 0 || posWorker[1] != 0) {
            System.out.println("processmove test2 failed");
        } else {
            System.out.println("processmove 2 passed");
        }
    }

}
