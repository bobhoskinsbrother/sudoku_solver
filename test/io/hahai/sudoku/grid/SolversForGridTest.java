package io.hahai.sudoku.grid;

import io.hahai.sudoku.solver.MultiPassPlayWhenOneOptionSolver;
import io.hahai.sudoku.solver.PlayWhenOneOptionSolver;
import org.junit.Test;

import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class SolversForGridTest {

    @Test public void attemptInitialSolveWithA_PlayWhenOneOptionSolver_NoMultipleOptionsForCells() {
        String grid =
                " . 1 4 2 6 8 7 5 . " +
                " 5 8 2 1 7 9 4 3 6 " +
                " 6 7 9 3 4 5 1 2 8 " +
                " 1 2 7 8 3 4 9 6 5 " +
                " 4 5 6 9 2 1 3 8 7 " +
                " 8 9 3 6 5 7 2 1 4 " +
                " 2 3 8 4 9 6 5 7 1 " +
                " 9 6 5 7 1 2 8 4 3 " +
                " 7 4 1 5 8 3 6 9 2 ";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        unit.applySolvers(new PlayWhenOneOptionSolver());
        assertThat(unit.isGridCompleteAndCorrect(), is(true));
        assertThat(unit.getValueAt(0), is(3));
        assertThat(unit.getValueAt(8), is(9));
    }

    @Test public void willSolveOnlyCellsItCanWithA_PlayWhenOneOptionSolver_WithMultipleOptionsForCells() {
        String grid =
                " . 1 4 2 6 8 7 5 . " +
                " 5 8 2 1 7 9 4 3 6 " +
                " 6 7 . 3 4 5 1 2 8 " +
                " 1 2 7 8 3 4 9 6 5 " +
                " 4 5 6 9 2 1 3 8 7 " +
                " 8 9 3 6 5 7 2 1 4 " +
                " 2 3 8 4 9 6 5 7 1 " +
                " . 6 5 7 1 2 8 4 3 " +
                " 7 4 1 5 8 3 6 9 2 ";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        unit.applySolvers(new PlayWhenOneOptionSolver());
        assertThat(unit.isGridCompleteAndCorrect(), is(false));
        assertThat(unit.getValueAt(0), is(0));
        assertThat(unit.getValueAt(8), is(9));
        assertThat(unit.getValueAt(20), is(9));
        assertThat(unit.getValueAt(63), is(9));
    }

    @Test public void willSolveGridWithA_MultiPassPlayWhenOneOptionSolver_WithMultipleOptionsForOneCell() {
        String grid =
                " . 1 4 2 6 8 7 . . " +
                " 5 8 2 1 7 9 4 3 6 " +
                " 6 7 . 3 4 5 1 2 8 " +
                " 1 2 7 8 3 4 9 6 5 " +
                " 4 5 6 9 2 1 3 8 7 " +
                " 8 9 3 6 5 7 2 1 4 " +
                " 2 3 8 4 9 6 5 7 1 " +
                " . 6 5 7 1 2 8 4 3 " +
                " 7 4 1 5 8 3 6 9 2 ";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        unit.applySolvers(new MultiPassPlayWhenOneOptionSolver());
        assertThat(unit.isGridCompleteAndCorrect(), is(true));
        assertThat(unit.getValueAt(0), is(3));
        assertThat(unit.getValueAt(7), is(5));
        assertThat(unit.getValueAt(8), is(9));
        assertThat(unit.getValueAt(20), is(9));
        assertThat(unit.getValueAt(63), is(9));
    }

}
