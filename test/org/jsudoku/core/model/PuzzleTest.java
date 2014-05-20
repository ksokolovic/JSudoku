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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
        // The invalid value is at (2, 3) position: 7 instead of 4
        int[][] puzzle = new int[][] {
            {9, 5, 4, 3, 8, 2, 1, 7, 6},
            {7, 3, 6, 1, 9, 5, 4, 8, 2},
            {8, 1, 2, 7, 6, 7, 3, 5, 9},
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

    /**
     * Method tests various cases for obtaining the value from the puzzle on
     * the given position.
     * @throws Exception if an error occurred during the execution.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetActualAt() throws Exception
    {
        // In the empty puzzle, each value is zero
        for(int row = 0; row < 9; ++row)
        {
            for(int col = 0; col < 9; ++col)
            {
                assertEquals(emptyPuzzle.getActualAt(row, col), 0);
            }
        }

        // Testing against few random values of another puzzle
        assertEquals(unsolvedPuzzle.getActualAt(0, 0), 9);
        assertEquals(unsolvedPuzzle.getActualAt(1, 3), 8);
        assertEquals(unsolvedPuzzle.getActualAt(2, 1), 6);
        assertEquals(unsolvedPuzzle.getActualAt(3, 7), 7);
        assertEquals(unsolvedPuzzle.getActualAt(4, 6), 1);
        assertEquals(unsolvedPuzzle.getActualAt(5, 0), 2);
        assertEquals(unsolvedPuzzle.getActualAt(6, 1), 4);
        assertEquals(unsolvedPuzzle.getActualAt(7, 5), 6);
        assertEquals(unsolvedPuzzle.getActualAt(8, 8), 4);
    }

    /**
     * Method tests invalid cases for obtaining the value from the puzzle on
     * the given position.
     * @throws exception if an error occurred during the execution.
     */
    @Test
    public void testGetActualAtOutOfRange() throws Exception
    {
        try
        {
            unsolvedPuzzle.getActualAt(-1, 0);
            fail("Should throw IndexOutOfBoundsException");
        }
        catch(IndexOutOfBoundsException exception)
        {
            assertTrue(exception.getMessage().contains("Both row and column must be within [0..8] range."));
        }

        try
        {
            unsolvedPuzzle.getActualAt(10, 0);
            fail("Should throw IndexOutOfBoundsException");
        }
        catch(IndexOutOfBoundsException exception)
        {
            assertTrue(exception.getMessage().contains("Both row and column must be within [0..8] range."));
        }

        try
        {
            unsolvedPuzzle.getActualAt(0, -1);
            fail("Should throw IndexOutOfBoundsException");
        }
        catch(IndexOutOfBoundsException exception)
        {
            assertTrue(exception.getMessage().contains("Both row and column must be within [0..8] range."));
        }

        try
        {
            unsolvedPuzzle.getActualAt(0, 10);
            fail("Should throw IndexOutOfBoundsException");
        }
        catch(IndexOutOfBoundsException exception)
        {
            assertTrue(exception.getMessage().contains("Both row and column must be within [0..8] range."));
        }
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

    /**
     * Method tests various cases for checking if the puzzle is solved.
     * @throws Exception if an error occurred during the execution.
     */
    @Test
    public void testIsSolved() throws Exception
    {
        assertFalse(emptyPuzzle.isSolved());
        assertFalse(unsolvedPuzzle.isSolved());
        assertTrue(validSolvedPuzzle.isSolved());
        assertFalse(invalidSolvedPuzzle.isSolved());
    }

    /**
     * Method tests various moves for checking if they are valid on the given puzzle.
     * @throws Exception if an error occurred during the execution.
     */
    @Test
    public void testIsMoveValid() throws Exception
    {
        // Invalid move because of the duplicate in the row
        assertFalse(unsolvedPuzzle.isMoveValid(0, 1, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(0, 7, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(0, 8, 5));

        assertFalse(unsolvedPuzzle.isMoveValid(1, 0, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 4, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 5, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 6, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 7, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 8, 3));

        assertFalse(unsolvedPuzzle.isMoveValid(2, 0, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 2, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 5, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 8, 6));

        assertFalse(unsolvedPuzzle.isMoveValid(3, 0, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 2, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 4, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 5, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 6, 1));

        assertFalse(unsolvedPuzzle.isMoveValid(4, 0, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 1, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 3, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 4, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 5, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 7, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 8, 6));

        assertFalse(unsolvedPuzzle.isMoveValid(5, 2, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 3, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 4, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 6, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 8, 2));

        assertFalse(unsolvedPuzzle.isMoveValid(6, 0, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 3, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 6, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 8, 4));

        assertFalse(unsolvedPuzzle.isMoveValid(7, 0, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 1, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 2, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 3, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 4, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 8, 3));

        assertFalse(unsolvedPuzzle.isMoveValid(8, 0, 7));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 1, 7));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 7, 7));

        // Invalid move because of the duplicate in the column
        assertFalse(unsolvedPuzzle.isMoveValid(1, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 0, 9));

        assertFalse(unsolvedPuzzle.isMoveValid(0, 1, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 1, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 1, 3));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 1, 3));

        assertFalse(unsolvedPuzzle.isMoveValid(2, 2, 8));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 2, 8));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 2, 8));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 2, 8));

        assertFalse(unsolvedPuzzle.isMoveValid(4, 3, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 3, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 3, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 3, 9));

        assertFalse(unsolvedPuzzle.isMoveValid(1, 4, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 4, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 4, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 4, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 4, 6));

        assertFalse(unsolvedPuzzle.isMoveValid(1, 5, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 5, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 5, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 5, 2));

        assertFalse(unsolvedPuzzle.isMoveValid(1, 6, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 6, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 6, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 6, 5));

        assertFalse(unsolvedPuzzle.isMoveValid(0, 7, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 7, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 7, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 7, 1));

        assertFalse(unsolvedPuzzle.isMoveValid(0, 8, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 8, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 8, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 8, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 8, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 8, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 8, 4));

        // Invalid move because of the duplicate in the minigrid
        assertFalse(unsolvedPuzzle.isMoveValid(0, 1, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 0, 9));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 2, 9));

        assertFalse(unsolvedPuzzle.isMoveValid(1, 4, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 5, 6));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 5, 6));

        assertFalse(unsolvedPuzzle.isMoveValid(0, 7, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(0, 8, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 6, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 7, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(1, 8, 5));
        assertFalse(unsolvedPuzzle.isMoveValid(2, 8, 5));

        assertFalse(unsolvedPuzzle.isMoveValid(3, 0, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 2, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 0, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 1, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 2, 2));

        assertFalse(unsolvedPuzzle.isMoveValid(3, 4, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(3, 5, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 3, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 4, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 5, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 3, 1));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 4, 1));

        assertFalse(unsolvedPuzzle.isMoveValid(3, 6, 7));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 7, 7));
        assertFalse(unsolvedPuzzle.isMoveValid(4, 8, 7));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 6, 7));
        assertFalse(unsolvedPuzzle.isMoveValid(5, 8, 7));

        assertFalse(unsolvedPuzzle.isMoveValid(6, 0, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 0, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 1, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 2, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 0, 4));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 1, 4));

        assertFalse(unsolvedPuzzle.isMoveValid(6, 3, 8));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 3, 8));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 4, 8));

        assertFalse(unsolvedPuzzle.isMoveValid(6, 6, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(6, 8, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(7, 8, 2));
        assertFalse(unsolvedPuzzle.isMoveValid(8, 7, 2));

        // Valid move
        assertTrue(unsolvedPuzzle.isMoveValid(0, 1, 7));
        assertTrue(unsolvedPuzzle.isMoveValid(0, 7, 4));
        assertTrue(unsolvedPuzzle.isMoveValid(0, 8, 3));
        assertTrue(unsolvedPuzzle.isMoveValid(1, 0, 5));
        assertTrue(unsolvedPuzzle.isMoveValid(1, 4, 4));
        assertTrue(unsolvedPuzzle.isMoveValid(1, 5, 9));
        assertTrue(unsolvedPuzzle.isMoveValid(1, 6, 7));
        assertTrue(unsolvedPuzzle.isMoveValid(1, 7, 6));
        assertTrue(unsolvedPuzzle.isMoveValid(1, 8, 2));
        assertTrue(unsolvedPuzzle.isMoveValid(2, 0, 4));
        assertTrue(unsolvedPuzzle.isMoveValid(2, 2, 2));
        assertTrue(unsolvedPuzzle.isMoveValid(2, 5, 7));
        assertTrue(unsolvedPuzzle.isMoveValid(2, 8, 1));
        assertTrue(unsolvedPuzzle.isMoveValid(3, 0, 3));
        assertTrue(unsolvedPuzzle.isMoveValid(3, 2, 4));
        assertTrue(unsolvedPuzzle.isMoveValid(3, 4, 8));
        assertTrue(unsolvedPuzzle.isMoveValid(3, 5, 5));
        assertTrue(unsolvedPuzzle.isMoveValid(3, 6, 2));
        assertTrue(unsolvedPuzzle.isMoveValid(4, 0, 8));
        assertTrue(unsolvedPuzzle.isMoveValid(4, 1, 9));
        assertTrue(unsolvedPuzzle.isMoveValid(4, 3, 2));
        assertTrue(unsolvedPuzzle.isMoveValid(4, 4, 7));
        assertTrue(unsolvedPuzzle.isMoveValid(4, 5, 4));
        assertTrue(unsolvedPuzzle.isMoveValid(4, 7, 3));
        assertTrue(unsolvedPuzzle.isMoveValid(4, 8, 5));
        assertTrue(unsolvedPuzzle.isMoveValid(5, 2, 7));
        assertTrue(unsolvedPuzzle.isMoveValid(5, 3, 6));
        assertTrue(unsolvedPuzzle.isMoveValid(5, 4, 3));
        assertTrue(unsolvedPuzzle.isMoveValid(5, 6, 4));
        assertTrue(unsolvedPuzzle.isMoveValid(5, 8, 8));
        assertTrue(unsolvedPuzzle.isMoveValid(6, 0, 1));
        assertTrue(unsolvedPuzzle.isMoveValid(6, 3, 5));
        assertTrue(unsolvedPuzzle.isMoveValid(6, 6, 6));
        assertTrue(unsolvedPuzzle.isMoveValid(6, 8, 7));
        assertTrue(unsolvedPuzzle.isMoveValid(7, 0, 7));
        assertTrue(unsolvedPuzzle.isMoveValid(7, 1, 8));
        assertTrue(unsolvedPuzzle.isMoveValid(7, 2, 5));
        assertTrue(unsolvedPuzzle.isMoveValid(7, 3, 4));
        assertTrue(unsolvedPuzzle.isMoveValid(7, 4, 2));
        assertTrue(unsolvedPuzzle.isMoveValid(7, 8, 9));
        assertTrue(unsolvedPuzzle.isMoveValid(8, 0, 6));
        assertTrue(unsolvedPuzzle.isMoveValid(8, 1, 2));
        assertTrue(unsolvedPuzzle.isMoveValid(8, 7, 5));
    }

    /**
     * Method checks for the <code>toString()</code> method of the <code>Puzzle</code>
     * class.
     * Given the one hardcoded puzzle, the resulting string representation is created
     * by hand and compared to the one that should be returned by the method.
     * @throws Exception if an error occurred during the execution.
     */
    @Test
    public void testToString() throws Exception
    {
        String unsolvedPuzzleStr =
            "\n" +
            "9 0 8 1 6 2 5 0 0 \n" +
            "0 3 1 8 0 0 0 0 0 \n" +
            "0 6 0 3 5 0 9 8 0 \n" +
            "0 1 0 9 0 0 0 7 6 \n" +
            "0 0 6 0 0 0 1 0 0 \n" +
            "2 5 0 0 0 1 0 9 0 \n" +
            "0 4 3 0 9 8 0 2 0 \n" +
            "0 0 0 0 0 6 3 1 0 \n" +
            "0 0 9 7 1 3 8 0 4 ";

        assertNotNull(unsolvedPuzzle.toString());
        assertFalse(unsolvedPuzzle.toString().length() == 0);
        assertEquals(unsolvedPuzzleStr, unsolvedPuzzle.toString());
    }

    Puzzle emptyPuzzle;                 // Puzzle with all empty cells
    Puzzle unsolvedPuzzle;              // Unsolved puzzle with some cells filled with valid values
    Puzzle validSolvedPuzzle;           // Completely correctly solved puzzle
    Puzzle invalidSolvedPuzzle;         // Invalidly solved puzzle
}
