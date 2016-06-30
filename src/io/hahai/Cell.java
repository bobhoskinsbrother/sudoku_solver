package io.hahai;

public final class Cell {

    private final int index;
    private SudokuGrid.CellState state;
    private int numericValue;

    public Cell(int index, SudokuGrid.CellState state, int numericValue) {
        this.index = index;
        this.state = state;
        this.numericValue = numericValue;
    }

    public Cell(int index) {
        this(index, SudokuGrid.CellState.PLAYABLE, 0);
    }
    public SudokuGrid.CellState getState() {
        return state;
    }
    public int getValue() {
        return numericValue;
    }
    public int getIndex() {
        return index;
    }

}
