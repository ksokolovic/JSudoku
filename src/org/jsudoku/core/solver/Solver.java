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

    private final Puzzle puzzle;

}
