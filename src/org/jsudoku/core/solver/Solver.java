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
 *     <li>Elimination Technique (Row, Column and Minigrid Elimination)</li>
 * </ul>
 *
 * @author sokolovic
 */
public class Solver {

    /**
     * Constructor to initialize the <code>Solver</code> instance with the
     * instance of the puzzle to solve here.
     * @param puzzle
     */
    public Solver(Puzzle puzzle)
    {
        this.puzzle = puzzle;
    }

    /**
     * Method scans the individual cells in the grid from left to right, top to bottom.
     * It calls <code>calculatePossibleValues()</code> method, and if the possible value
     * returned is a single number, then the number for that cell is confirmed and the cell
     * in the grid is updated with the confirmed number.
     * @return <code>true</code> if at least one cell if confirmed in a single pass, and
     * <code>false</code> if no cells get confirmed.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean checkColumnsAndRows() throws Exception
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
                        String possibleValues = puzzle.calculatePossibleValues(row, col);
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
     * Method looks into each of the nine minigrids and scans for lone rangers
     * (from 1 to 9). If a lone ranger is found, the number is confirmed and the
     * cell in the grid is updated with the confirmed number.
     * @return <code>true</code> if a lone ranger is found in one of the minigrids;
     * <code>false</code> otherwise.
     */
    private boolean lookForLoneRangersInMinigrids()
    {
        boolean changes = false;        // Indicator for method return value
        boolean nextMiniGrid;           // Should we move to next minigrid
        int occurence = 0;              // Number of occurences of the current number
        int cPos = 0;                   // The column position of lone ranger
        int rPos = 0;                   // The row position of lone ranger

        // Check for each number, from 1 to 9
        for(int n = 1; n <= 9; ++n)
        {
            // Check the 9 minigrids
            for(int r = 0; r < 9; r += 3)
            {
                for(int c = 0; c < 9; c += 3)
                {
                    nextMiniGrid = false;
                    // Check within the minigrid
                    for(int rr = 0; rr < 3; ++rr)
                    {
                        for(int cc = 0; cc < 3; ++cc)
                        {
                            if((puzzle.getActualAt(r + rr, c + cc) == 0) && (puzzle.getPossibleAt(r + rr, c + cc).contains(String.valueOf(n))))
                            {
                                occurence += 1;
                                cPos = c + cc;
                                rPos = r + rr;
                                if(occurence > 1)
                                {
                                    nextMiniGrid = true;
                                    break;
                                }
                            }
                        }
                        if(nextMiniGrid)
                        {
                            break;
                        }
                    }

                    if(!nextMiniGrid && (occurence == 1))
                    {
                        // That means the number is confirmed
                        puzzle.setActualAt(cPos, rPos, n);
                        changes = true;
                    }
                }
            }
        }
        return changes;
    }

    private final Puzzle puzzle;

}
