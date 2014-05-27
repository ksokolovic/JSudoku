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
package org.jsudoku.core.generator;

import org.jsudoku.core.model.Puzzle;
import org.jsudoku.core.solver.Solver;

import java.util.Random;

/**
 * The class generates the new Sudoku puzzle based on the given level of difficulty.
 *
 * @author sokolovic
 */
public class Generator {

    /**
     * This method calls the <code>generateNewPuzzle()</code> method to ensure that
     * the puzzle is of the right level of difficulty. It does so by checking
     * the score of the puzzle and then generating a new puzzle if the score
     * does not match the level of difficulty required.
     * @param level Level of difficulty of the puzzle being generated.
     * @return String containing values of the newly generated puzzle.
     */
    public String generatePuzzle(int level)
    {
        int score = 0;
        String result = null;

        do
        {
            result = generateNewPuzzle(level);
            score = puzzle.getTotalScore();
            if(!"".equals(result))
            {
                // Check if the puzzle matches the level of difficulty
                switch(level)
                {
                    // Average for this level is 45
                    case 1:
                        if(score >= 41 && score <= 50)
                        {
                            return result;
                        }
                        break;
                    // Average for this level is 55
                    case 2:
                        if(score >= 51 && score <= 60)
                        {
                            return result;
                        }
                        break;
                    // Average for this level is 65
                    case 3:
                        if(score >= 61 && score <= 70)
                        {
                            return result;
                        }
                        break;
                    // Average for this level is 115
                    case 4:
                        if(score >= 111 && score <= 120)
                        {
                            return result;
                        }
                        break;
                }
            }
        } while(true);
    }

    /**
     * Method generates new Sudoku puzzle based on the selected level of
     * difficulty. It returns a puzzle as a string of numbers.
     * @param level Level of difficulty of the puzzle being generated.
     * @return Sudoku puzzle as string of numbers.
     */
    private String generateNewPuzzle(int level)
    {
        puzzle = new Puzzle();
        solver = new Solver(puzzle);
        int col = 0;
        int row = 0;
        String str = null;
        int numberOfEmptyCells = 0;

        // Initialize entire board
        for(row = 0; row < 9; ++row)
        {
            for(col = 0; col < 9; ++col)
            {
                puzzle.setActualAt(row, col, 0);
                puzzle.setPossibleAt(row, col, "");
            }
        }
        // Clear the stacks
        puzzle.clearActualStack();
        puzzle.clearPossibleStack();

        // Populate the board with numbers by solving an empty grid
        try
        {
            // Use logical methods to setup the grid first
            if(!solver.solve())
            {
                // If it doesn't work, use brute force
                solver.solvePuzzleByBruteForce();
            }
        }
        catch(Exception exception)
        {
            // Just in case an error occured, return an empty string
            return "";
        }

        // Make a backup of an actual array
        puzzle.setActualBackup(puzzle.getActual().clone());

        // Set the number of empty cells based on the selected level of difficulty
        numberOfEmptyCells = getNumberOfEmptyCells(level);

        // Clear the stacks that are used in brute force elimination
        puzzle.clearActualStack();
        puzzle.clearPossibleStack();
        solver.setBruteForceStop(false);

        // Create empty cells
        createEmptyCells(numberOfEmptyCells);

        // Convert the values in the actual array into string
        str = "";
        for(row = 0; row < 9; ++row)
        {
            for(col = 0; col < 9; ++col)
            {
                str += String.valueOf(puzzle.getActualAt(row, col));
            }
        }

        // Ensure that the puzzle has one solution
        do
        {
            puzzle.setTotalScore(0);
            try
            {
                if(!solver.solve())
                {
                    // If puzzle is not solved and this is a level 1 - 3 puzzle
                    if(level < 4)
                    {
                        // Try generation again
                        return "";
                    }
                    else
                    {
                        // Level 4 puzzles don't guarantee single solution
                        // and potentially need guessing
                        solver.solvePuzzleByBruteForce();
                        break;
                    }
                }
                else
                {
                    // Puzzle does indeed have one solution
                    break;
                }
            }
            catch(Exception exception)
            {
                return "";
            }
        } while(true);

        // Return the puzzle as string
        return str;
    }

    /**
     * Method returns a random number of cells to be set as empty based on the
     * level of difficulty of the puzzle.
     * @param level Level of difficulty of the puzzle being generated.
     * @return Random number of cells to be left empty.
     */
    private int getNumberOfEmptyCells(int level)
    {
        switch(level)
        {
            case 1:
                return randomNumber(40, 45);
            case 2:
                return randomNumber(46, 49);
            case 3:
                return randomNumber(50, 53);
            case 4:
                return randomNumber(54, 58);
            default:
                return -1;
        }
    }

    /**
     * Method returns a random integer number within the range specified with
     * min and max value.
     * @param min Minimum value of the random number.
     * @param max Maximum value of the random number.
     * @return Random number within the [min, max] range.
     */
    private int randomNumber(int min, int max)
    {
        Random random = new Random();
        return (int)((max - min + 1) * random.nextDouble()) + min;
    }

    /**
     * Method randomly determines the location of empty cells in the Sudoku grid.
     * It first generates the location of empty cells in the top left half of
     * the grid. It then "reflects" the empty cells onto the bottom half of the
     * grid so that a symmetrical Sudoku puzzle is achieved.
     * @param number Number of cells to be left empty.
     */
    private void createEmptyCells(int number)
    {
        int row, col;
        // Choose random locations for empty cells
        String[] emptyCells = new String[number];
        for(int i = 0; i <= (number / 2); ++i)
        {
            boolean duplicate;
            do
            {
                duplicate = false;
                // Get a cell in the first half of the grid
                do
                {
                    col = randomNumber(0, 8);
                    row = randomNumber(0, 4);
                } while((col > 4) && (row == 4));

                for(int j = 0; j <= i; ++j)
                {
                    // If cell is already selected to be empty
                    if(emptyCells[j] != null &&
                        emptyCells[j].equals(String.valueOf(row) + String.valueOf(col)))
                    {
                        duplicate = true;
                        break;
                    }
                }

                if(!duplicate)
                {
                    // Set the empty cell
                    emptyCells[i] = String.valueOf(row) + String.valueOf(col);
                    puzzle.setActualAt(row, col, 0);
                    puzzle.setPossibleAt(row, col, "");
                    // Reflect the top half of the grid and make it symetrical
                    emptyCells[number - i - 1] = String.valueOf(8 - row) + String.valueOf(8 - col);
                    puzzle.setActualAt(8 - row, 8 - col, 0);
                    puzzle.setPossibleAt(8 - row, 8 - col, "");
                }
            } while(duplicate);
        }
    }

    private Solver solver;          // The solver that we use to create the puzzle in reverse
    private Puzzle puzzle;          // The puzzle generated

}
