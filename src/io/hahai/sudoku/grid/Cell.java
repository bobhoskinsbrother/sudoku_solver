package io.hahai.sudoku.grid;

public final class Cell {

    private final int index;
    private Grid.CellState state;
    private int numericValue;

    public Cell(int index, Grid.CellState state, int numericValue) {
        this.index = index;
        this.state = state;
        this.numericValue = numericValue;
    }

    public Cell(int index) {
        this(index, Grid.CellState.PLAYABLE, 0);
    }
    public Grid.CellState getState() {
        return state;
    }
    public int getValue() {
        return numericValue;
    }
    public int getIndex() {
        return index;
    }

}
