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
 *     <li>Lone Rangers Technique (Row, Column and Minigrid)</li>
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
            occurence = 0;
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
                        puzzle.setActualAt(rPos, cPos, n);
                        changes = true;
                    }
                }
            }
        }
        return changes;
    }

    /**
     * Method looks into each of the nine rows and scans for lone rangers (from 1
     * to 9). It starts from the first row and iteratively looks for lone rangers
     * that may be present in the row, until the last row.
     * @return <code>true</code> if a lone ranger is found in one of the rows;
     * <code>false</code> otherwise.
     */
    private boolean lookForLoneRangersInRows()
    {
        boolean changes = false;        // Indicator for method return value
        int occurence = 0;              // Number of occurences of the current number
        int cPos = 0;                   // The column position of lone ranger
        int rPos = 0;                   // The row position of lone ranger

        // Check by row
        for(int r = 0; r < 9; ++r)
        {
            for(int n = 1; n <= 9; ++n)
            {
                occurence = 0;
                for(int c = 0; c < 9; ++c)
                {
                    if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).contains(String.valueOf(n))))
                    {
                        occurence += 1;
                        // If multiple occurences, not a lone ranger anymore
                        if(occurence > 1)
                        {
                            break;
                        }
                        cPos = c;
                        rPos = r;
                    }
                }

                if(occurence == 1)
                {
                    // Number is confirmed
                    puzzle.setActualAt(rPos, cPos, n);
                    changes = true;
                }
            }
        }
        return changes;
    }

    /**
     * Method looks into each of the nine columns and scans for lone rangers
     * (from 1 to 9). It starts from the first column, and iteratively looks for
     * lone rangers that may be present in the column, until the last column.
     * @return <code>true</code> if a lone ranger is found in one of the columns;
     * <code>false otherwise</code>.
     */
    private boolean lookForLoneRangersInColumns()
    {
        boolean changes = false;        // Indicator for method return value
        int occurence = 0;              // Number of occurences of the current number
        int cPos = 0;                   // The column position of lone ranger
        int rPos = 0;                   // The row position of lone ranger

        // Check by column
        for(int c = 0; c < 9; ++c)
        {
            for(int n = 1; n <= 9; ++n)
            {
                occurence = 0;
                for(int r = 0; r < 9; ++r)
                {
                    if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).contains(String.valueOf(n))))
                    {
                        occurence += 1;
                        // If multiple occurences, not a lone ranger anymore
                        if(occurence > 1)
                        {
                            break;
                        }
                        cPos = c;
                        rPos = r;
                    }
                }

                if(occurence == 1)
                {
                    // Number is confirmed
                    puzzle.setActualAt(rPos, cPos, n);
                    changes = true;
                }
            }
        }
        return changes;
    }

    /**
     * Method scans through all the cells in the grid and looks for cells with
     * two possible values. Once it finds a cell with two possible values, it
     * searches for the cell's twin in the minigrid that it is in. If there is
     * indeed a pair of twins in the minigrid, the rest of the cells in the
     * minigrid will have their list of possible values modified to remove the
     * values of twins. After this process, if there are cells left with one
     * possible value, those cells are updated with the confirmed number.
     * @return <code>true</code> if there are any changes to the list of possible
     * values for any of the cells in the grid; <code>false</code> otherwise.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean lookForTwinsInMinigrids() throws Exception
    {
        boolean changes = false;

        // Look for twins in each cell
        for(int r = 0; r < 9; ++r)
        {
            for(int c = 0; c < 9; ++c)
            {
                // If two possible values, check for twins
                if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).length() == 2))
                {
                    // Scan the minigrid that the current cell is in
                    int startC = c - (c % 3);
                    int startR = r - (r % 3);

                    for(int rr = startR; rr < startR + 3; ++rr)
                    {
                        for(int cc = startC; cc < startC + 3; ++cc)
                        {
                            // For cells other than the pair of twins
                            if((!((cc == c) && (rr == r))) && (puzzle.getPossibleAt(rr, cc).equals(puzzle.getPossibleAt(r, c))))
                            {
                                // Remove the twins from all other possible values in the mingrid
                                for(int rrr = startR; rrr < startR + 3; ++rrr)
                                {
                                    for(int ccc = startC; ccc < startC + 3; ++ccc)
                                    {
                                        // Only check for empty cells
                                        if((puzzle.getActualAt(rrr, ccc) == 0) && !(puzzle.getPossibleAt(rrr, ccc).equals(puzzle.getPossibleAt(r, c))))
                                        {
                                            // Save the copy of the original possible values (twins)
                                            String original_possible = puzzle.getPossibleAt(rrr, ccc);
                                            // Remove the first twin number from possible values
                                            puzzle.setPossibleAt(rrr, ccc,
                                                puzzle.getPossibleAt(rrr, ccc).replace(puzzle.getPossibleAt(r, c).substring(0, 1), ""));
                                            // Remove the second twin number from possible values
                                            puzzle.setPossibleAt(rrr, ccc,
                                                puzzle.getPossibleAt(rrr, ccc).replace(puzzle.getPossibleAt(r, c).substring(1, 2), ""));

                                            // If the possible values are modified, then set the changes variable
                                            // to true to indicate that the possible values of cells in minigrid
                                            // have been modified
                                            if(!original_possible.equals(puzzle.getPossibleAt(rrr, ccc)))
                                            {
                                                changes = true;
                                            }

                                            // If the possible value reduces to empty string, then the user has
                                            // placed a move that results in a puzzle being not solvable
                                            if(puzzle.getPossibleAt(rrr, ccc).equals(""))
                                            {
                                                throw new Exception("Invalid move.");
                                            }

                                            // If left with one possible value for the current cell, cell is confirmed
                                            if(puzzle.getPossibleAt(rrr, ccc).length() == 1)
                                            {
                                                puzzle.setActualAt(rrr, ccc,
                                                    Integer.parseInt(puzzle.getPossibleAt(rrr, ccc)));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return changes;
    }

    /**
     * Method scans through all the cells in the grid and looks for cells with
     * two possible values. Once it finds a cell with two possible values, it
     * searches for the cell's twin in the row that it is in. If there is indeed
     * a pair of twins in the row, the rest of the cells in the row will have
     * their list of possible values modified to remove the values of twins.
     * After this process, if there are cells left with one possible value,
     * those cells are updated with the confirmed number.
     * @return <code>true</code> if there are any changes to the list of possible
     * values for any of the cells in the grid; <code>false</code> otherwise.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean lookForTwinsInRows() throws Exception
    {
        boolean changes = false;

        // For each row, check each column in the row
        for(int r = 0; r < 9; ++r)
        {
            for(int c = 0; c < 9; ++c)
            {
                // If two possible values, check for twins
                if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).length() == 2))
                {
                    // Scan columns in this row
                    for(int cc = c + 1; cc < 9; ++cc)
                    {
                        if(puzzle.getPossibleAt(r, cc).equals(puzzle.getPossibleAt(r, c)))
                        {
                            // Remove the twins from all other possible values in the ro
                            for(int ccc = 0; ccc < 9; ++ccc)
                            {
                                if((puzzle.getActualAt(r, ccc) == 0) && (ccc != c) && (ccc != cc))
                                {
                                    // Save a copy of the original possible values (twins)
                                    String original_possible = puzzle.getPossibleAt(r, ccc);
                                    // Remove the first twin number from possible values
                                    puzzle.setPossibleAt(r, ccc,
                                        puzzle.getPossibleAt(r, ccc).replace(puzzle.getPossibleAt(r, c).substring(0, 1), ""));
                                    // Remove the second twin number from possible values
                                    puzzle.setPossibleAt(r, ccc,
                                        puzzle.getPossibleAt(r, ccc).replace(puzzle.getPossibleAt(r, c).substring(1, 2), ""));

                                    // If the possible values are modified, then set the changes variable
                                    // to true to indicate that the possible values of cells in the minigrid
                                    // have been modified
                                    if(!original_possible.equals(puzzle.getPossibleAt(r, ccc)))
                                    {
                                        changes = true;
                                    }

                                    // If possible value reduces to empty string, then the user has
                                    // placed a move that results in the puzzle being not solvable
                                    if(puzzle.getPossibleAt(r, ccc).equals(""))
                                    {
                                        throw new Exception("Invalid move.");
                                    }

                                    // If left with one possible value for the current cell, cell is confirmed
                                    if(puzzle.getPossibleAt(r, ccc).length() == 1)
                                    {
                                        puzzle.setActualAt(r, ccc,
                                            Integer.parseInt(puzzle.getPossibleAt(r, ccc)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return changes;
    }

    private final Puzzle puzzle;

}
