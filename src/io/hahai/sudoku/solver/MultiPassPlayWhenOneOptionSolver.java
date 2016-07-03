package io.hahai.sudoku.solver;

import io.hahai.sudoku.grid.Grid;

public final class MultiPassPlayWhenOneOptionSolver implements Solver {

    private final PlayWhenOneOptionSolver solver;

    public MultiPassPlayWhenOneOptionSolver() {
        solver = new PlayWhenOneOptionSolver();
    }

    @Override public void attemptSolve(Grid grid) {
        int dimension = grid.dimension();
        for (int i = 0; i < dimension; i++) {
            solver.attemptSolve(grid);
            if(grid.isGridCompleteAndCorrect()) {
                break;
            }
        }
    }
}
