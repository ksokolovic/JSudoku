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

import java.util.Random;

/**
 * The class implements various algorithms for solving the Sudoku puzzle. The following algoritms are implemented:
 * <ul>
 *     <li>Elimination Technique (Row, Column and Minigrid Elimination)</li>
 *     <li>Lone Rangers Technique (Row, Column and Minigrid)</li>
 *     <li>Looking for Twins Technique (Row, Column and Minigrid)</li>
 *     <li>Looking for Triplets Technique (Row, Column and Minigrid)</li>
 *     <li>Brute Force Elimination Technique</li>
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
     * Method performes the core of the Sudoku solving by invoking the implemented
     * algorithms in a specific order with an attempt to solve the puzzle.
     * @return <code>true</code> if the puzzle is successfully solved; <code>false</code>
     * otherwise.
     */
    public boolean solve() throws Exception
    {
        boolean changes;
        boolean exitLoop = false;

        try
        {
            do      // Look for Triplets in Columns
            {
                do      // Look for Triplets in Rows
                {
                    do      // Look for Triplets in Minigrids
                    {
                        do      // Look for Twins in Columns
                        {
                            do      // Look for Twins in Rows
                            {
                                do      // Look for Twins in Minigrids
                                {
                                    do      // Look for Lone Ranger in Columns
                                    {
                                        do      // Look for Lone Ranger in Rows
                                        {
                                            do      // Look for Lone Ranger in Minigrids
                                            {
                                                do      // Perform Column, Row and Minigrid Elimination
                                                {
                                                    changes = checkColumnsAndRows();
                                                    if(puzzle.isSolved())
                                                    {
                                                        exitLoop = true;
                                                        break;
                                                    }
                                                } while(changes);

                                                if(exitLoop)
                                                {
                                                    break;
                                                }

                                                // Look for Lone Ranger in Minigrids
                                                changes = lookForLoneRangersInMinigrids();
                                                if(puzzle.isSolved())
                                                {
                                                    exitLoop = true;
                                                    break;
                                                }
                                            } while(changes);

                                            if(exitLoop)
                                            {
                                                break;
                                            }

                                            // Look for Lone Ranger in Rows
                                            changes = lookForLoneRangersInRows();
                                            if(puzzle.isSolved())
                                            {
                                                exitLoop = true;
                                                break;
                                            }
                                        } while(changes);

                                        if(exitLoop)
                                        {
                                            break;
                                        }

                                        // Look for Lone Ranger in Columns
                                        changes = lookForLoneRangersInColumns();
                                        if(puzzle.isSolved())
                                        {
                                            exitLoop = true;
                                            break;
                                        }
                                    } while(changes);

                                    // Look for Twins in Minigrids
                                    changes = lookForTwinsInMinigrids();
                                    if(puzzle.isSolved())
                                    {
                                        exitLoop = true;
                                        break;
                                    }
                                } while(changes);

                                // Look for Twins in Rows
                                changes = lookForTwinsInRows();
                                if(puzzle.isSolved())
                                {
                                    exitLoop = true;
                                    break;
                                }
                            } while(changes);

                            // Look for Twins in Columns
                            changes = lookForTwinsInColumns();
                            if(puzzle.isSolved())
                            {
                                exitLoop = true;
                                break;
                            }
                        } while(changes);

                        // Look for Triplets in Minigrids
                        changes = lookForTripletsInMinigrids();
                        if(puzzle.isSolved())
                        {
                            exitLoop = true;
                            break;
                        }
                    } while(changes);

                    // Look for Triplets in Rows
                    changes = lookForTripletsInRows();
                    if(puzzle.isSolved())
                    {
                        exitLoop = true;
                        break;
                    }
                } while(changes);

                // Look for Triplets in Columns
                changes = lookForTripletsInColumns();
                if(puzzle.isSolved())
                {
                    exitLoop = true;
                    break;
                }
            } while(changes);
        }
        catch(Exception exception)
        {
            throw exception;
        }

        return puzzle.isSolved();
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

                        // Accumulate the total score
                        puzzle.setTotalScore(puzzle.getTotalScore() + 1);
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

                        // Accumulate the total score
                        puzzle.setTotalScore(puzzle.getTotalScore() + 2);
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

                    // Accumulate the total score
                    puzzle.setTotalScore(puzzle.getTotalScore() + 2);
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

                    // Accumulate the total score
                    puzzle.setTotalScore(puzzle.getTotalScore() + 2);
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

                                                // Accumulate the total score
                                                puzzle.setTotalScore(puzzle.getTotalScore() + 3);
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

                                        // Accumulate the total score
                                        puzzle.setTotalScore(puzzle.getTotalScore() + 3);
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
     * searches for the cell's twin in the column that it is in. If there is indeed
     * a pair of twins in the column, the rest of the cells in the column will have
     * their list off possible values modified to remove the values of twins.
     * After this process, if there are cells left with one possible value,
     * those cells are updated with the confirmed number.
     * @return <code>true</code> if there are any changes to the list of possible
     * values for any of the cells in the grid; <code>false</code> otherwise.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean lookForTwinsInColumns() throws Exception
    {
        boolean changes = false;

        // For each column, check each row in a column
        for(int c = 0; c < 9; ++c)
        {
            for(int r = 0; r < 9; ++r)
            {
                // If two possible values, check for twins
                if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).length() == 2))
                {
                    // Scan rows in this column
                    for(int rr = r + 1; rr < 9; ++rr)
                    {
                        if(puzzle.getPossibleAt(rr, c).equals(puzzle.getPossibleAt(r, c)))
                        {
                            // Remove the twins from all the other possible values in the row
                            for(int rrr = 0; rrr < 9; ++rrr)
                            {
                                if((puzzle.getActualAt(rrr, c) == 0) && (rrr != r) && (rrr != rr))
                                {
                                    // Save a copy of the original possible values (twins)
                                    String original_possible = puzzle.getPossibleAt(rrr, c);
                                    // Remove the first twin number from possible values
                                    puzzle.setPossibleAt(rrr, c,
                                        puzzle.getPossibleAt(rrr, c).replace(puzzle.getPossibleAt(r, c).substring(0, 1), ""));
                                    // Remove the second twin number from possible values
                                    puzzle.setPossibleAt(rrr, c,
                                        puzzle.getPossibleAt(rrr, c).replace(puzzle.getPossibleAt(r, c).substring(1, 2), ""));

                                    // If the possible values are modified, then set changes variable to true
                                    // to indicate that the possible values of cells in the minigrid have been
                                    // modified
                                    if(!original_possible.equals(puzzle.getPossibleAt(rrr, c)))
                                    {
                                        changes = true;
                                    }

                                    // If possible value reduces to empty string, then the user has
                                    // placed a move that results in the puzzle being not solvable
                                    if(puzzle.getPossibleAt(rrr, c).equals(""))
                                    {
                                        throw new Exception("Invalid move.");
                                    }

                                    // If left with one possible value for the current cell, cell is confirmed
                                    if(puzzle.getPossibleAt(rrr, c).length() == 1)
                                    {
                                        puzzle.setActualAt(rrr, c,
                                            Integer.parseInt(puzzle.getPossibleAt(rrr, c)));

                                        // Accumulate the total score
                                        puzzle.setTotalScore(puzzle.getTotalScore() + 3);
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
     * three possible values. Once it finds a cell with three possible values, it
     * searches for two other triplets in the minigrid that the cell is in. If
     * there is indeed a set of triplets in the minigrid, the rest of the cells
     * in the minigrid will have their list of possible values modified to remove
     * the values of triplets. After the process, if there are cells left with
     * one possible value, those cells are updated with the confirmed number.
     * @return <code>true</code> if there are any changes to the list of possible
     * values for any of the cells in the grid; <code>false</code> otherwise.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean lookForTripletsInMinigrids() throws Exception
    {
        boolean changes = false;

        // Check each cell
        for(int r = 0; r < 9; ++r)
        {
            for(int c = 0; c < 9; ++c)
            {
                // Three possible values, check for triplets
                if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).length() == 3))
                {
                    // First potential triplet found
                    String tripletsLocation = String.valueOf(r) + String.valueOf(c);

                    // Scan by minigrid
                    int startC = c - (c % 3);
                    int startR = r - (r % 3);

                    for(int rr = startR; rr < startR + 3; ++rr)
                    {
                        for(int cc = startC; cc < startC + 3; ++cc)
                        {
                            if((!((cc == c) && (rr == r))) &&
                                ((puzzle.getPossibleAt(rr, cc).equals(puzzle.getPossibleAt(r, c))) ||
                                    (puzzle.getPossibleAt(rr, cc).length() == 2 &&
                                    puzzle.getPossibleAt(r, c).contains(puzzle.getPossibleAt(rr, cc).substring(0, 1)) &&
                                    puzzle.getPossibleAt(r, c).contains(puzzle.getPossibleAt(rr, cc).substring(1, 2)))))
                            {
                                // Save the coordinate of the triplets
                                tripletsLocation += String.valueOf(rr) + String.valueOf(cc);
                            }
                        }
                    }

                    // Found 3 cells as triplets; remove all from other cells
                    if(tripletsLocation.length() == 6)
                    {
                        // Remove each cell's possible values containing the triplet
                        for(int rrr = startR; rrr < startR + 3; ++rrr)
                        {
                            for(int ccc = startC; ccc < startC + 3; ++ccc)
                            {
                                // Look for the cell that is not part of the 3 cells found
                                // ERROR: This condition never evaluates to True
                                if((puzzle.getActualAt(rrr, ccc) == 0) &&
                                    (rrr != Integer.parseInt(tripletsLocation.substring(0, 1))) &&
                                    (ccc != Integer.parseInt(tripletsLocation.substring(1, 2))) &&
                                    (rrr != Integer.parseInt(tripletsLocation.substring(2, 3))) &&
                                    (ccc != Integer.parseInt(tripletsLocation.substring(3, 4))) &&
                                    (rrr != Integer.parseInt(tripletsLocation.substring(4, 5))) &&
                                    (ccc != Integer.parseInt(tripletsLocation.substring(5, 6))))
                                {
                                    // Save the original possible values
                                    String original_possible = puzzle.getPossibleAt(rrr, ccc);
                                    // Remove the first triplet number from possible values
                                    puzzle.setPossibleAt(rrr, ccc,
                                        puzzle.getPossibleAt(rrr, ccc).replace(puzzle.getPossibleAt(r, c).substring(0, 1), ""));
                                    // Remove the second triplet number from possible values
                                    puzzle.setPossibleAt(rrr, ccc,
                                        puzzle.getPossibleAt(rrr, ccc).replace(puzzle.getPossibleAt(r, c).substring(1, 2), ""));
                                    // Remove the third triplet number from possible values
                                    puzzle.setPossibleAt(rrr, ccc,
                                        puzzle.getPossibleAt(rrr, ccc).replace(puzzle.getPossibleAt(r, c).substring(2, 3), ""));

                                    // If the possible values are modified, then set the changes variable to
                                    // true to indicate that the possible values of cells in the minigrid are
                                    // modified
                                    if(!original_possible.equals(puzzle.getPossibleAt(rrr, ccc)))
                                    {
                                        changes = true;
                                    }

                                    // If possible value reduces to empty string, then the user has
                                    // placed a move that results in the puzzle being not solvable
                                    if(puzzle.getPossibleAt(rrr, ccc).equals(""))
                                    {
                                        throw new Exception("Invalid move.");
                                    }

                                    // If left with one possible value for the current cell, cell is confirmed
                                    if(puzzle.getPossibleAt(rrr, ccc).length() == 1)
                                    {
                                        puzzle.setActualAt(rrr, ccc,
                                            Integer.parseInt(puzzle.getPossibleAt(rrr, ccc)));

                                        // Accumulate the total score
                                        puzzle.setTotalScore(puzzle.getTotalScore() + 4);
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
     * three possible values. Once it finds a cell with three possible values, it
     * searches for two other triplets in the row that the cell is in. If there
     * is indeed a set of triplets in the row, the rest of the cells in the row
     * will have their list of possible values modified to remove the values of
     * triplets. After the process, if there are cells with one possible value,
     * those cells are updated with the confirmed number.
     * @return <code>true</code> if there are any changes to the list of possible
     * values for any of the cells in the grid; <code>false</code> otherwise.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean lookForTripletsInRows() throws Exception
    {
        boolean changes = false;

        // For each row, check each column in the row
        for(int r = 0; r < 9; ++r)
        {
            for(int c = 0; c < 9; ++c)
            {
                // Three possible values; check for triplets
                if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).length() == 3))
                {
                    // First potential triplet found
                    String tripletsLocation = String.valueOf(r) + String.valueOf(c);

                    // Scan columns in this row
                    for(int cc = 0; cc < 9; ++cc)
                    {
                        // Look for other triplets
                        if((cc != c) &&
                            ((puzzle.getPossibleAt(r, cc).equals(puzzle.getPossibleAt(r, c))) ||
                                (puzzle.getPossibleAt(r, cc).length() == 2 &&
                                puzzle.getPossibleAt(r, c).contains(puzzle.getPossibleAt(r, cc).substring(0, 1)) &&
                                puzzle.getPossibleAt(r, c).contains(puzzle.getPossibleAt(r, cc).substring(1, 2)))))
                        {
                            // Save the coorinates of the triplets
                            tripletsLocation += String.valueOf(r) + String.valueOf(cc);
                        }
                    }

                    // Found 3 cells as triplets; remove all from the other cells
                    if(tripletsLocation.length() == 6)
                    {
                        // Remove each cell's possible values containing the triplet
                        for(int ccc = 0; ccc < 9; ++ccc)
                        {
                            if((puzzle.getActualAt(r, ccc) == 0) &&
                                (ccc != Integer.parseInt(tripletsLocation.substring(1, 2))) &&
                                (ccc != Integer.parseInt(tripletsLocation.substring(3, 4))) &&
                                (ccc != Integer.parseInt(tripletsLocation.substring(5, 6))))
                            {
                                // Save the original possible values
                                String original_possible = puzzle.getPossibleAt(r, ccc);

                                // Remove the first triplet number from possible values
                                puzzle.setPossibleAt(r, ccc,
                                    puzzle.getPossibleAt(r, ccc).replace(puzzle.getPossibleAt(r, c).substring(0, 1), ""));
                                // Remove the second triplet number from possible values
                                puzzle.setPossibleAt(r, ccc,
                                    puzzle.getPossibleAt(r, ccc).replace(puzzle.getPossibleAt(r, c).substring(1, 2), ""));
                                // Remove the third triplet number from possible values
                                puzzle.setPossibleAt(r, ccc,
                                    puzzle.getPossibleAt(r, ccc).replace(puzzle.getPossibleAt(r, c).substring(2, 3), ""));

                                // If the possible values are modified, then set the changes variable
                                // to true to indicate that the possible values of cells in the row
                                // are modified
                                if(!original_possible.equals(puzzle.getPossibleAt(r, ccc)))
                                {
                                    changes = true;
                                }

                                // If the possible value reduces to empty string, then the user
                                // has placed a move that results in the puzzle being not solvable
                                if(puzzle.getPossibleAt(r, ccc).equals(""))
                                {
                                    throw new Exception("Invalid move.");
                                }

                                // If left with one possible value for the current cell, cell is confirmed
                                if(puzzle.getPossibleAt(r, ccc).length() == 1)
                                {
                                    puzzle.setActualAt(r, ccc,
                                        Integer.parseInt(puzzle.getPossibleAt(r, ccc)));

                                    // Accumulate the total score
                                    puzzle.setTotalScore(puzzle.getTotalScore() + 4);
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
     * three possible values. Once it finds a cell with three possible values, it
     * searches for two other triplets in the column that the cell is in. If there
     * is indeed a set of triplets, in the column, the rest of the cells in the
     * column will have their list of possible values modified to remove the
     * values of triplets. After the process, if there are cells left with one
     * possible value, those cells are updated with the confirmed number.
     * @return <code>true</code> if there are any changes to the list of possible
     * values for any of the cells in the grid; <code>false</code> otherwise.
     * @throws java.lang.Exception if an invalid move has been made.
     */
    private boolean lookForTripletsInColumns() throws Exception
    {
        boolean changes = false;

        // For each column, check each row in the column
        for(int c = 0; c < 9; ++c)
        {
            for(int r = 0; r < 9; ++r)
            {
                // Three possible values; check for triplets
                if((puzzle.getActualAt(r, c) == 0) && (puzzle.getPossibleAt(r, c).length() == 3))
                {
                    // First potential triplet found
                    String tripletsLocation = String.valueOf(r) + String.valueOf(c);

                    // Scan rows in this column
                    for(int rr = 0; rr < 9; ++rr)
                    {
                        if((rr != r) &&
                            ((puzzle.getPossibleAt(rr, c).equals(puzzle.getPossibleAt(r, c))) ||
                                (puzzle.getPossibleAt(rr, c).length() == 2 &&
                                puzzle.getPossibleAt(r, c).contains(puzzle.getPossibleAt(rr, c).substring(0, 1)) &&
                                puzzle.getPossibleAt(r, c).contains(puzzle.getPossibleAt(rr, c).substring(1, 2)))))
                        {
                            // Save the coordinates of the triplet
                            tripletsLocation += String.valueOf(rr) + String.valueOf(c);
                        }
                    }

                    // Found 3 cells as triplets; remove all from the other cells
                    if(tripletsLocation.length() == 6)
                    {
                        // Remove each cell's possible values containing the triplet
                        for(int rrr = 0; rrr < 9; ++rrr)
                        {
                            if((puzzle.getActualAt(rrr, c) == 0) &&
                                (rrr != Integer.parseInt(tripletsLocation.substring(0, 1))) &&
                                (rrr != Integer.parseInt(tripletsLocation.substring(2, 3))) &&
                                (rrr != Integer.parseInt(tripletsLocation.substring(4, 5))))
                            {
                                // Save the original possible values
                                String original_possible = puzzle.getPossibleAt(rrr, c);

                                // Remove the first triplet number from possible values
                                puzzle.setPossibleAt(rrr, c,
                                    puzzle.getPossibleAt(rrr, c).replace(puzzle.getPossibleAt(r, c).substring(0, 1), ""));
                                // Remove the second triplet number from possible values
                                puzzle.setPossibleAt(rrr, c,
                                    puzzle.getPossibleAt(rrr, c).replace(puzzle.getPossibleAt(r, c).substring(1, 2), ""));
                                // Remove the third triplet number from possible values
                                puzzle.setPossibleAt(rrr, c,
                                    puzzle.getPossibleAt(rrr, c).replace(puzzle.getPossibleAt(r, c).substring(2, 3), ""));

                                // If the possible values are modified, then set the changes variable
                                // to true to indicate that the possible values of cells in the minigrid
                                // are modified
                                if(!original_possible.equals(puzzle.getPossibleAt(rrr, c)))
                                {
                                    changes = true;
                                }

                                // If possible value reduces to empty string, then the user has placed
                                // a move that results in the puzzle being not solvable
                                if(puzzle.getPossibleAt(rrr, c).equals(""))
                                {
                                    throw new Exception("Invalid move.");
                                }

                                // If left with one possible value for the current cell, cell is confirmed
                                if(puzzle.getPossibleAt(rrr, c).length() == 1)
                                {
                                    puzzle.setActualAt(rrr, c,
                                        Integer.parseInt(puzzle.getPossibleAt(rrr, c)));

                                    // Accumulate the total score
                                    puzzle.setTotalScore(puzzle.getTotalScore() + 4);
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
     * A recursive method that attempts to solve a Sudoku puzzle by systematically
     * selecting a possible value from a cell and then applying all the other
     * techiques to solve the puzzle. It calls itself until the puzzle is solved,
     * or if selecting a particular value for a cell causes the puzzle to be unsolvable
     * it backtracks by restoring the previous state of the grid from puzzle's stack.
     */
    public void solvePuzzleByBruteForce()
    {
        int row = 0;
        int col = 0;

        // Accumulate the total score
        puzzle.setTotalScore(puzzle.getTotalScore() + 5);

        // Find out which cell has the smallest number of possible values
        int[] cell = puzzle.findCellWithFewestPossibleValues();
        row = cell[0];
        col = cell[1];

        // Get the possible values for the chosen cell
        String possibleValues = puzzle.getPossibleAt(row, col);

        // Randomize the possible values
        possibleValues = randomizePossibleValues(possibleValues);

        // Push the actual and possible matrices onto the stack
        puzzle.pushActualStack();
        puzzle.pushPossibleStack();

        // Select one value and try
        for(int i = 0; i <= possibleValues.length() - 1; ++i)
        {
            puzzle.setActualAt(row, col,
                Integer.parseInt(possibleValues.substring(i, i + 1)));
            try
            {
                if(solve())
                {
                    // If the puzzle is solved, the recursion can stop now
                    bruteForceStop = true;
                    return;
                }
                else
                {
                    solvePuzzleByBruteForce();
                    if(bruteForceStop)
                    {
                        return;
                    }
                }
            }
            catch(Exception exception)
            {
                // Accumulate the total score
                puzzle.setTotalScore(puzzle.getTotalScore() + 5);
                puzzle.popActualStack();
                puzzle.popPossibleStack();
            }
        }
    }

    /**
     * Method randomizes the list of possible values for a cell.
     * @param str String containing the cell's possible values.
     * @return Randomized list of cell's possible values as string.
     */
    private String randomizePossibleValues(String str)
    {
        char[] s = str.toCharArray();
        int i, j;
        char temp;
        Random random = new Random();

        for(i = 0; i <= str.length() - 1; ++i)
        {
            j = (int)((str.length() - i + 1) * random.nextDouble() + i) % str.length();
            // Swap the chars
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }

        StringBuilder builder = new StringBuilder();
        for(int k = 0; k < s.length; ++k)
        {
            builder.append(s[k]);
        }
        return builder.toString();
    }

    /**
     * Sets the value indicating if the brute force method should stop.
     * @param bruteForceStop <code>true</code> if the brute force method
     *                       should stop; <code>false</code> otherwise.
     */
    public void setBruteForceStop(boolean bruteForceStop)
    {
        this.bruteForceStop = bruteForceStop;
    }

    private final Puzzle puzzle;                        // The puzzle being solved
    private boolean bruteForceStop = false;             // Indicate if the brute force method should stop

}
