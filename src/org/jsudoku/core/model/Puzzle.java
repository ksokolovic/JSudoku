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

import java.util.Stack;

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
     * This constructor initializes an empty puzzle with all zero values.
     */
    public Puzzle()
    {
        initializeActual();
        initializePossible();
    }

    /**
     * A constructor to initialize an instance of the <code>Puzzle</code> class.
     * This constructor initializes a puzzle with the values passed as a two-dimensional array
     * of numbers.
     * @param puzzle Actual values of the puzzle to initialize.
     */
    public Puzzle(int[][] puzzle)
    {
        initializeActual(puzzle);
        initializePossible();
    }

    /**
     * Initializes a matrix of puzzle actual values to all zero values.
     */
    private void initializeActual()
    {
        actual = new int[9][9];
        for(int row = 0; row < 9; ++row)
        {
            for(int col = 0; col < 9; ++col)
            {
                actual[row][col] = 0;
            }
        }
    }

    /**
     * Initializes a matrix of puzzle actual values to the values passed as a two-dimensional array
     * of number.
     * @param puzzle Actual values of the puzzle to initialize.
     */
    private void initializeActual(int[][] puzzle)
    {
        actual = new int[9][9];
        for(int row = 0; row < 9; ++row)
        {
            for(int col = 0; col < 9; ++col)
            {
                actual[row][col] = puzzle[row][col];
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
     * Method checks if both the row and column indexes are valid.
     * @param row Row index to check.
     * @param col Column index to check.
     * @return <code>true</code> if they are valid; <code>false</code> otherwise.
     */
    private boolean checkIndexRange(int row, int col)
    {
        if(row < 0 || row > 8 || col < 0 || col > 8)
        {
            return false;
        }
        return true;
    }

    /**
     * Method checks if the given value is the valid value for the sudoku puzzle.
     * The zero-value is also valid in the given check, since it denotes none value.
     * @param value Value to check.
     * @return <code>true</code> if the given value is valid; otherwise <code>false</code>.
     */
    private boolean checkValueRange(int value)
    {
        if(value < 0 || value > 9)
        {
            return false;
        }
        return true;
    }

    /**
     * Method checks if the given string value is the valid value to set as
     * possible to the puzzle cell. The string value must contain only digits
     * 0 through 9 in order to be valid; additionally, an empty string is
     * also valid.
     * @param value Value to check.
     * @return <code>true</code> if the given value is valid; otherwise <code>false</code>.
     */
    private boolean checkPossibleValue(String value)
    {
        String pattern = "[0-9]*";          // Only 0 through 9 digits or empty string allowed
        return value.matches(pattern);
    }

    /**
     * Returns the actual puzzle value from the position specified.
     * @param row Row index of the value to be returned.
     * @param col Column index of the value to be returned.
     * @return The actual puzzle value from the position specified.
     * @throws java.lang.IndexOutOfBoundsException if the specified row or column
     * index are out of the range.
     */
    public int getActualAt(int row, int col) throws IndexOutOfBoundsException
    {
        if(!checkIndexRange(row, col))
        {
            throw new IndexOutOfBoundsException("Both row and column must be within [0..8] range.");
        }
        return actual[row][col];
    }

    /**
     * Sets the actual puzzle value on the position specified to the value specified.
     * @param row Row index of the value to be set.
     * @param col Column index of the value to be set.
     * @param value Value to be set on the position specified.
     * @throws java.lang.IndexOutOfBoundsException if the specified row or column
     * index are out of the range.
     * @throws java.lang.IllegalArgumentException if the specified value is out of the range.
     */
    public void setActualAt(int row, int col, int value) throws IndexOutOfBoundsException, IllegalArgumentException
    {
        if(!checkIndexRange(row, col))
        {
            throw new IndexOutOfBoundsException("Both row and column must be within [0..8] range.");
        }
        if(!checkValueRange(value))
        {
            throw new IllegalArgumentException("Value must be within [0..9] range.");
        }
        actual[row][col] = value;
    }

    /**
     * Returns the possible values for the cell specified by its position.
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @return The possible values for the cell.
     * @throws java.lang.IndexOutOfBoundsException if the specified row or column
     * index are out of the range.
     */
    public String getPossibleAt(int row, int col) throws IndexOutOfBoundsException
    {
        if(!checkIndexRange(row, col))
        {
            throw new IndexOutOfBoundsException("Both row and column must be within [0..8] range.");
        }
        return possible[row][col];
    }

    /**
     * Sets the possible values for the cell specified by its position.
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @param value Possible values to be set for the cell.
     * @throws java.lang.IndexOutOfBoundsException if the specified row or column
     * index are out of the range.
     * @throws java.lang.IllegalArgumentException if the specified possible value
     * is not in the correct format.
     */
    public void setPossibleAt(int row, int col, String value) throws IndexOutOfBoundsException, IllegalArgumentException
    {
        if(!checkIndexRange(row, col))
        {
            throw new IndexOutOfBoundsException("Both row and column must be within [0..8] range.");
        }
        if(!checkPossibleValue(value))
        {
            throw new IllegalArgumentException("Possible values must be passed as string containing digits within [0..9] range.");
        }
        possible[row][col] = value;
    }

    /**
     * Method calculates the possible values for a cell. It first scans its column, followed by
     * its row, and then the minigrid it is in. If, after scanning, the possible value is an empty
     * string, method raises an exception indicating that an error has occurred on some previous
     * moves made.
     * @param row Row index of the cell to be scanned.
     * @param col Column index of the cell to be scanned.
     * @return String containing the list of possible values for a specified field.
     * @throws Exception if an error has occurred on some previously made moves.
     */
    public String calculatePossibleValues(int row, int col) throws Exception
    {
        // Get the current possible values for the cell
        String str;
        if(getPossibleAt(row, col).equals(""))
        {
            str = "123456789";
        }
        else
        {
            str = getPossibleAt(row, col);
        }

        int r, c;

        // Step 1: Check by column
        for(r = 0; r < 9; ++r)
        {
            if(getActualAt(r, col) != 0)
            {
                // That means there is an actual value in it
                str = str.replace(String.valueOf(getActualAt(r, col)), "");
            }
        }

        // Step 2: Check by column
        for(c = 0; c < 9; ++c)
        {
            if(getActualAt(row, c) != 0)
            {
                // That means there is an actual value in it
                str = str.replace(String.valueOf(getActualAt(c, row)), "");
            }
        }

        // Step 3: Check within the minigrid
        int startC, startR;
        startC = col - (col % 3);
        startR = row - (row % 3);
        for(int rr = startR; rr < startR + 3; ++rr)
        {
            for(int cc = startC; cc < startC + 3; ++cc)
            {
                if(getActualAt(rr, cc) != 0)
                {
                    // That means there is an actual value in it
                    str = str.replace(String.valueOf(getActualAt(rr, cc)), "");
                }
            }
        }

        // If the possible value is an empty string, throw an exception because it means
        // that invalid moves have been made
        if(str.equals(""))
        {
            throw new Exception("Invalid move.");
        }

        return str;
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

    /**
     * Checks if the move of placing the <code>value</code> in the cell with specified position is valid or not.
     * @param row Row index of the cell.
     * @param col Column index of the cell.
     * @param value Value to be placed in the cell specified.
     * @return <code>true</code> if the given move is valid; otherwise <code>false</code>.
     */
    public boolean isMoveValid(int row, int col, int value)
    {
        // Scan through the given row
        for(int c = 0; c < 9; ++c)
        {
            // Check if the row already contains the given value
            if(actual[row][c] == value)
            {
                // Duplicate in row
                return false;
            }
        }

        // Scan through the given column
        for(int r = 0; r < 9; ++r)
        {
            // Check if the column already contains the given value
            if(actual[r][col] == value)
            {
                // Duplicate in column
                return false;
            }
        }

        // Scan through the minigrid
        int startC = col - (col % 3);
        int startR = row - (row % 3);

        for(int r = 0; r < 3; ++r)
        {
            for(int c = 0; c < 3; ++c)
            {
                // Check if the minigrid already contains the given value
                if(actual[startR + r][startC + c] == value)
                {
                    // Duplicate in minigrid
                    return false;
                }
            }
        }

        // If all the previous checks didn't return false, it means that the given move is valid
        return true;
    }

    /**
     * Finds the cell with the least number of possible values.
     * @return Integer array with two elements: the element at index 0 represents
     * cell's row index, and the element at index 1 represents cell's column index.
     */
    public int[] findCellWithFewestPossibleValues()
    {
        int[] position = new int[2];
        int min = 10;

        for(int row = 0; row < 9; ++row)
        {
            for(int col = 0; col < 9; ++col)
            {
                if((actual[row][col] == 0) && (possible[row][col].length() < min))
                {
                    min = possible[row][col].length();
                    position[0] = row;
                    position[1] = col;
                }
            }
        }
        return position;
    }

    /**
     * Pushes the current actual matrix onto the stack so that it can be
     * restored if necessary with <code>popActualStack()</code> method.
     */
    public void pushActualStack()
    {
        actualStack.push(actual.clone());
    }

    /**
     * Pops the last actual matrix from the stack.
     */
    public void popActualStack()
    {
        actual = actualStack.pop();
    }

    /**
     * Pushes the current possible matrix onto the stack so that it can be
     * restored if necessary with <code>popPossibleStack()</code> method.
     */
    public void pushPossibleStack()
    {
        possibleStack.push(possible.clone());
    }

    /**
     * Pops the last possible matrix from the stack.
     */
    public void popPossibleStack()
    {
        possible = possibleStack.pop();
    }

    /**
     * Returns the string representation of the puzzle grid.
     * @return String representation of the puzzle.
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for(int row = 0; row < 9; ++row)
        {
            builder.append("\n");
            for(int col = 0; col < 9; ++col)
            {
                builder.append(actual[row][col]).append(" ");
            }
        }
        return builder.toString();
    }

    private int[][] actual;                         // A matrix holding the actual values of the puzzle
    private int[][] actualBackup;                   // Backup copy of the actual matrix
    private String[][] possible;                    // A matrix used to keep track of the possible values for each cell
    private Stack<int[][]> actualStack;             // Stack object used to strore the actual array before a cell is fixed with a value
    private Stack<String[][]> possibleStack;        // Stack object used to store the possible array before a cell is fixed with a value

}
