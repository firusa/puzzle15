package se.flyingpenguin;

import java.util.Arrays;
import java.util.Random;

public class Puzzle15 {

    public static int[][] tilesGrid = new int[4][4];

    //    cheat
/*
    private static int zr;
    private static int zc;

    private static void getZ() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tilesGrid[i][j] == 0) {
                    zr = i;
                    zc = j;
                    break;
                }
            }
        }
    }

    private static void print() {
        System.out.println(Arrays.deepToString(tilesGrid));
    }

    public static void moveTile(int clickRow, int clickColumn) {
        tilesGrid[zr][zc] = tilesGrid[clickRow][clickColumn]; // copying clicked tile value on the tile that was on position 0
        tilesGrid[clickRow][clickColumn] = 0; //click tile after moving this tile becomes 0 tile
        zr = clickRow;
        zc = clickColumn;
        System.out.println(isSolved());
    }
*/
    //  cheat end

    public static void newGame() {
        do {
            tilesGrid = generateRandomArray();
        } while (!isSolvable());
//  cheat

      //  getZ();
        //print();

//  cheat end

    }

    private static int[][] generateRandomArray() {
        int[][] cookedArray = new int[4][4];
        Random randomizer = new Random();
        for (int i = 1; i <= 15; i++) {
            int randomRow = randomizer.nextInt(4);
            int randomColumn = randomizer.nextInt(4);
            if (cookedArray[randomRow][randomColumn] == 0) {// if that index/spot is available, meaning filled with 0 then put int I there
                cookedArray[randomRow][randomColumn] = i;
            } else {
                i--;
            }
        }
        return cookedArray;
    }

    public static boolean isSolved() {
        if (tilesGrid[3][3] != 0) { // if blank tile is not in the solved position ==> not solved
            return false;
        }
        for (int i = 0; i < 15; i++) {
            if (tilesGrid[i / 4][i % 4] != i + 1) {//translate 1d loop to 2d array indexes to make sure e.g. 5 is on spot [1,0]
                return false;
            }
        }
        return true;
    }

    private static boolean isSolvable() {
        int countInversions = 0;//number of inversions is even means puzzle is solvable

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if (tilesGrid[j / 4][j % 4] > tilesGrid[i / 4][i % 4])
                    countInversions++;
            }
        }

        return countInversions % 2 == 0;
    }
// normal

    public static void moveTile(int clickRow, int clickColumn) {
        if (tilesGrid[clickRow][clickColumn] == 0) {//if the content of the tile is 0 we definitely do not move it
            return;
        }

        int moveRow = 0;
        int moveColumn = 0;

        if (clickRow > 0 && tilesGrid[clickRow - 1][clickColumn] == 0) { //check if the content of tile next to clicked one is 0.
            moveRow = -1;
        } else if (clickRow < 3 && tilesGrid[clickRow + 1][clickColumn] == 0) {
            moveRow = 1;
        } else if (clickColumn > 0 && tilesGrid[clickRow][clickColumn - 1] == 0) {
            moveColumn = -1;
        } else if (clickColumn < 3 && tilesGrid[clickRow][clickColumn + 1] == 0) {
            moveColumn = 1;
        } else {
            return;// click wasn't next to 0 tile
        }

        tilesGrid[clickRow + moveRow][clickColumn + moveColumn] = tilesGrid[clickRow][clickColumn]; // copying clicked tile value on the tile that was on position 0
        tilesGrid[clickRow][clickColumn] = 0; //click tile after moving this tile becomes 0 tile
    }


    // normal end


}