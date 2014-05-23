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
package org.jsudoku.core.solver;

import org.jsudoku.core.model.Puzzle;

/**
 * The class implements various algorithms for solving the Sudoku puzzle. The following algoritms are implemented:
 * <ul>
 *     <li></li>
 * </ul>
 *
 * @author sokolovic
 */
public class Solver {

    /**
     * Method scans the individual cells in the grid from left to right, top to bottom.
     * It calls <code>calculatePossibleValues()</code> method, and if the possible value
     * returned is a single number, then the number for that cell is confirmed and the cell
     * in the grid is updated with the confirmed number.
     * @param puzzle Puzzle that is being solved.
     * @return <code>true</code> if at least one cell if confirmed in a single pass, and
     * <code>false</code> if no cells get confirmed.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean checkColumnsAndRows(Puzzle puzzle) throws Exception
    {
        boolean changes = false;

        // Check all cells
        for(int row = 0; row < 9; ++row)
        {
            for(int col = 0; col < 9; ++col)
            {
                if(puzzle.getActualAt(row, col) == 0)
                {
                    try
                    {
                        String possibleValues = calculatePossibleValues(row, col, puzzle);
                        puzzle.setPossibleAt(row, col, possibleValues);
                    }
                    catch(Exception exception)
                    {
                        throw new Exception("Invalid move.");
                    }

                    if(puzzle.getPossibleAt(row, col).length() == 1)
                    {
                        // Number is confirmed
                        puzzle.setActualAt(row, col, Integer.parseInt(puzzle.getPossibleAt(row, col)));
                        changes = true;
                    }
                }
            }
        }

        return changes;
    }

    /**
     * Method calculates the possible values for a cell. It first scans its column, followed by
     * its row, and then the minigrid it is in. If, after scanning, the possible value is an empty
     * string, method raises an exception indicating that an error has occurred on some previous
     * moves made.
     * @param row Row index of the cell to be scanned.
     * @param col Column index of the cell to be scanned.
     * @param puzzle Puzzle that is being solved.
     * @return String containing the list of possible values for a specified field.
     * @throws Exception if an error has occurred on some previously made moves.
     */
    private String calculatePossibleValues(int row, int col, Puzzle puzzle) throws Exception
    {
        // Get the current possible values for the cell
        String str;
        if(puzzle.getPossibleAt(row, col).equals(""))
        {
            str = "123456789";
        }
        else
        {
            str = puzzle.getPossibleAt(row, col);
        }

        int r, c;

        // Step 1: Check by column
        for(r = 0; r < 9; ++r)
        {
            if(puzzle.getActualAt(r, col) != 0)
            {
                // That means there is an actual value in it
                str = str.replace(String.valueOf(puzzle.getActualAt(r, col)), "");
            }
        }

        // Step 2: Check by column
        for(c = 0; c < 9; ++c)
        {
            if(puzzle.getActualAt(row, c) != 0)
            {
                // That means there is an actual value in it
                str = str.replace(String.valueOf(puzzle.getActualAt(c, row)), "");
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
                if(puzzle.getActualAt(rr, cc) != 0)
                {
                    // That means there is an actual value in it
                    str = str.replace(String.valueOf(puzzle.getActualAt(rr, cc)), "");
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

}
