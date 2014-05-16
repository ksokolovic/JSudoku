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

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test class to test the behavior of the <code>Puzzle</code> model class.
 *
 * @author sokolovic
 */
public class PuzzleTest extends TestCase {
    /**
     * Helper method to initialize various puzzles used for testing before each test is executed.
     * @throws Exception if an error occurred during the execution.
     */
    @Before
    public void setUp() throws Exception
    {
        initializeEmptyPuzzle();
        initializeUnsolvedPuzzle();
        initializeValidSolvedPuzzle();
        initializeInvalidSolvedPuzzle();
    }

    /**
     * Initializes an empty (all zero values) puzzle.
     */
    private void initializeEmptyPuzzle()
    {
        emptyPuzzle = new Puzzle();
    }

    /**
     * Initializes a puzzle which is not yet solved but is valid (no invalid moves have been made).
     */
    private void initializeUnsolvedPuzzle()
    {
        int[][] puzzle = new int[][] {
            {9, 0, 8, 1, 6, 2, 5, 0, 0},
            {0, 3, 1, 8, 0, 0, 0, 0, 0},
            {0, 6, 0, 3, 5, 0, 9, 8, 0},
            {0, 1, 0, 9, 0, 0, 0, 7, 6},
            {0, 0, 6, 0, 0, 0, 1, 0, 0},
            {2, 5, 0, 0, 0, 1, 0, 9, 0},
            {0, 4, 3, 0, 9, 8, 0, 2, 0},
            {0, 0, 0, 0, 0, 6, 3, 1, 0},
            {0, 0, 9, 7, 1, 3, 8, 0, 4}
        };
        unsolvedPuzzle = new Puzzle(puzzle);
    }

    /**
     * Initializes a correctly solved puzzle.
     */
    private void initializeValidSolvedPuzzle()
    {
        int[][] puzzle = new int[][] {
            {9, 5, 4, 3, 8, 2, 1, 7, 6},
            {7, 3, 6, 1, 9, 5, 4, 8, 2},
            {8, 1, 2, 4, 6, 7, 3, 5, 9},
            {1, 8, 3, 9, 2, 6, 5, 4, 7},
            {6, 4, 5, 8, 7, 3, 9, 2, 1},
            {2, 9, 7, 5, 4, 1, 8, 6, 3},
            {5, 7, 9, 2, 1, 4, 6, 3, 8},
            {4, 6, 8, 7, 3, 9, 2, 1, 5},
            {3, 2, 1, 6, 5, 8, 7, 9, 4}
        };
        validSolvedPuzzle = new Puzzle(puzzle);
    }

    /**
     * Initializes an incorrectly solved puzzle (invalid moves have been made).
     */
    private void initializeInvalidSolvedPuzzle()
    {
        int[][] puzzle = new int[][] {
            {9, 5, 4, 3, 8, 2, 1, 7, 6},
            {7, 3, 6, 1, 9, 5, 4, 8, 2},
            {8, 1, 2, 4, 6, 7, 3, 5, 9},
            {1, 8, 3, 9, 2, 6, 5, 4, 7},
            {6, 4, 5, 8, 7, 3, 9, 2, 1},
            {2, 9, 7, 5, 4, 1, 8, 6, 3},
            {5, 7, 9, 2, 1, 4, 6, 3, 8},
            {4, 6, 8, 7, 3, 9, 2, 1, 5},
            {3, 2, 1, 6, 5, 8, 7, 9, 4}
        };
        invalidSolvedPuzzle = new Puzzle(puzzle);
    }

    /**
     * Method tests the initialized puzzle instances.
     * @throws Exception if an error occurred during the execution.
     */
    @Test
    public void testPuzzleInstances() throws Exception
    {
        // Assert that proper class instances are initialized
        assertTrue(emptyPuzzle instanceof Puzzle);
        assertTrue(unsolvedPuzzle instanceof Puzzle);
        assertTrue(validSolvedPuzzle instanceof Puzzle);
        assertTrue(invalidSolvedPuzzle instanceof Puzzle);

        // Assert that initialized puzzles are not null
        assertNotNull(emptyPuzzle);
        assertNotNull(unsolvedPuzzle);
        assertNotNull(validSolvedPuzzle);
        assertNotNull(invalidSolvedPuzzle);
    }

    @Test
    public void testGetActualAt() throws Exception
    {

    }

    @Test
    public void testSetActualAt() throws Exception
    {

    }

    @Test
    public void testGetPossibleAt() throws Exception
    {

    }

    @Test
    public void testSetPossibleAt() throws Exception
    {

    }

    @Test
    public void testIsSolved() throws Exception
    {

    }

    @Test
    public void testIsMoveValid() throws Exception
    {

    }

    @Test
    public void testToString() throws Exception
    {

    }

    Puzzle emptyPuzzle;                 // Puzzle with all empty cells
    Puzzle unsolvedPuzzle;              // Unsolved puzzle with some cells filled with valid values
    Puzzle validSolvedPuzzle;           // Completely correctly solved puzzle
    Puzzle invalidSolvedPuzzle;         // Invalidly solved puzzle
}
