/*
 * Copyright (c) 2014  Kemal Sokolović <kemal DOT sokolovic AT gmail DOT com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jsudoku.core.model;

/**
 * Class models a 9x9 Sudoku puzzle. A Sudoku puzzle is made up of 9x9 grid filled with numbers in [1..9] range.
 * The aim of the Sudoku game is to place a number from 1 to 9 into each of the cells, such that each number
 * must appear at least once in each row and each column in the grid. Additionally, each minigrid (nine smaller 3x3
 * grids) must contain all the numbers 1 through 9.
 *
 * @author sokolovic
 */
public class Puzzle {

    /**
     * No-arg contructor to initialize an instance of the <code>Puzzle</code> class.
     */
    public Puzzle()
    {
        initializeActual();
        initializePossible();
    }

    /**
     * Initializes a matrix of puzzle actual values to all zero values.
     */
    private void initializeActual()
    {
        actual = new int[9][9];
        for(int i = 0; i < 9; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                actual[i][j] = 0;
            }
        }
    }

    /**
     * Initializes a matrix of possible values for each cell to all empty strings.
     */
    private void initializePossible()
    {
        possible = new String[9][9];
        for(int i = 0; i < 9; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                possible[i][j] = "";
            }
        }
    }

    /**
     * Returns the actual puzzle value from the position specified.
     * @param row Row index of the value to be returned.
     * @param col Column index of the value to be returned.
     * @return The actual puzzle value from the position specified.
     */
    public int getActualAt(int row, int col)
    {
        return actual[row][col];
    }

    /**
     * Sets the actual puzzle value on the position specified to the value specified.
     * @param row Row index of the value to be set.
     * @param col Column index of the value to be set.
     * @param value Value to be set on the position specified.
     */
    public void setActualAt(int row, int col, int value)
    {
        actual[row][col] = value;
    }

    /**
     * Returns the possible values for the cell specified by its position.
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @return The possible values for the cell.
     */
    public String getPossibleAt(int row, int col)
    {
        return possible[row][col];
    }

    /**
     * Sets the possible values for the cell specified by its position.
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @param value Possible values to be set for the cell.
     */
    public void setPossibleAt(int row, int col, String value)
    {
        possible[row][col] = value;
    }

    /**
     * Checks if this puzzle is solved.
     * @return <code>true</code> if this puzzle is solved; otherwise <code>false</code>.
     */
    public boolean isSolved()
    {
        String pattern;             // Pattern that each row, column and minigrid has to follow when the puzzle is solved
        int row, col;

        // Check row by row
        for(row = 0; row < 9; ++row)
        {
            pattern = "123456789";
            for(col = 0; col < 9; ++col)
            {
                // Remove each actual number in the current row from the pattern
                pattern = pattern.replace(String.valueOf(actual[row][col]), "");
            }

            // If there are numbers left in the pattern, then the row is not complete
            // meaning that the puzzle is not solved
            if(pattern.length() > 0)
            {
                return false;
            }
        }

        // Check column by column
        for(col = 0; col < 9; ++col)
        {
            pattern = "123456789";
            for(row = 0; row < 9; ++row)
            {
                // Remove each actual number in the current column from the pattern
                pattern = pattern.replace(String.valueOf(actual[row][col]), "");
            }

            // If there are numbers left in the pattern, then the column is not complete
            // meaning that the puzzle is not solved
            if(pattern.length() > 0)
            {
                return false;
            }
        }

        // Check minigrid by minigrid
        for(row = 0; row < 9; row += 3)
        {
            for(col = 0; col < 9; col += 3)
            {
                pattern = "123456789";
                for(int mRow = 0; mRow < 3; ++mRow)
                {
                    for(int mCol = 0; mCol < 3; ++mCol)
                    {
                        // Remove each actual number in the current minigrid from the pattern
                        pattern = pattern.replace(String.valueOf(actual[row + mRow][col + mCol]), "");
                    }
                }

                // If there are numbers left in the pattern, then the minigrid is not complete
                // meaning that the puzzle is not solved
                if(pattern.length() > 0)
                {
                    return false;
                }
            }
        }

        // If all the previous checks didn't return false, it means that the puzzle is solved
        return true;
    }

    private int[][] actual;                         // A matrix holding the actual values of the puzzle
    private String[][] possible;                    // A matrix used to keep track of the possible values for each cell
}
