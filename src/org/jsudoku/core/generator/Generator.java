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

    private String generateNewPuzzle(int level)
    {
        // TODO
        return null;
    }

    private Solver solver;          // The solver that we use to create the puzzle in reverse
    private Puzzle puzzle;          // The puzzle generated

}
