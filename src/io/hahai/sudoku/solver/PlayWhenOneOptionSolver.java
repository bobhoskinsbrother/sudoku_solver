package io.hahai.sudoku.solver;

import io.hahai.sudoku.grid.Grid;

import java.util.List;

public final class PlayWhenOneOptionSolver implements Solver {

    @Override public void attemptSolve(Grid grid) {
        int size = grid.size();
        for (int i = 0; i < size; i++) {
            if(grid.cellPlayable(i)) {
                List<Integer> playableOptions = grid.playableOptions(i);
                if(playableOptions.size()==1) {
                    grid.play(i,playableOptions.get(0));
                }
            }
        }
    }
}
