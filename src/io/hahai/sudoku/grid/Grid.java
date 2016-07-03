package io.hahai.sudoku.grid;

import io.hahai.sudoku.solver.Solver;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Grid {

    private final int gridDimension;
    private final int blockDimension;


    public enum CellState {PRESET, PLAYABLE}

    private List<Cell> cells;

    public Grid(Reader reader) throws IllegalArgumentException {
        cells = new ArrayList<>();
        loadCellsFrom(reader);
        gridDimension = (int) Math.sqrt(cells.size());
        blockDimension = (int) Math.sqrt(gridDimension);
    }

    public int dimension() {
        return gridDimension;
    }

    public List<Cell> playableCells() {
        return playableCellsInCollection(cells);
    }

    public int size() {
        return cells.size();
    }

    public int getValueAt(int index) {
        return cells.get(index).getValue();
    }

    public void applySolvers(Solver... solvers) {
        for (Solver solver : solvers) {
            solver.attemptSolve(this);
            if(isGridCompleteAndCorrect()) {
                break;
            }
        }
    }

    public void play(int cellIndex, int value) throws IllegalArgumentException {
        if (!playableOptions(cellIndex).contains(value)) {
            throw new IllegalArgumentException("Cannot play this value for this cell");
        }
        cells.set(cellIndex, new Cell(cellIndex, CellState.PLAYABLE, value));
    }

    private List<Cell> playableCellsInCollection(List<Cell> allCells) {
        return allCells.stream().filter(cell -> cell.getState() == CellState.PLAYABLE).collect(Collectors.toList());
    }

    private void loadCellsFrom(Reader reader) throws IllegalArgumentException {
        int current;
        int count = 0;
        try {
            while ((current = reader.read()) != -1) {
                char c = (char) current;
                if ('.' == c) {
                    cells.add(new Cell(count));
                    count++;
                } else if (Character.isDigit(c)) {
                    cells.add(new Cell(count, CellState.PRESET, Character.getNumericValue(c)));
                    count++;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Integer> playableOptions(int cellIndex) {
        if (cellPlayable(cellIndex)) {
            List<Integer> reply = create1ToNList(gridDimension);
            reply.removeAll(getColumnForCell(cellIndex));
            reply.removeAll(getBlockForCell(cellIndex));
            reply.removeAll(getRowForCell(cellIndex));
            return reply;
        }
        return new ArrayList<>();
    }

    public boolean cellPlayable(int cellIndex) {
        CellState state = getCell(cellIndex).getState();
        return state == CellState.PLAYABLE;
    }

    public boolean isValidGrid() {
        boolean isSquare = gridDimension == (blockDimension * blockDimension) && cells.size() == (gridDimension * gridDimension);
        return isSquare && !hasRepetitionInGrid();
    }

    public boolean isGridCompleteAndCorrect() {
        return !hasZeroedCellsInGrid() && !hasRepetitionInGrid();
    }

    private boolean hasRepetitionInGrid() {
        for (Cell cell : cells) {
            List<Integer> row = getRowForCell(cell.getIndex());
            List<Integer> block = getBlockForCell(cell.getIndex());
            List<Integer> column = getColumnForCell(cell.getIndex());
            if (hasRepetition(row) || hasRepetition(block) || hasRepetition(column)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRepetition(List<Integer> cellValues) {
        cellValues.removeAll(Collections.singletonList(0));
        return cellValues.size() != new HashSet<>(cellValues).size();
    }

    private boolean hasZeroedCellsInGrid() {
        return cells.stream().anyMatch(cell -> cell.getValue() == 0);
    }

    List<Integer> getBlockForCell(int cellIndex) {
        List<Integer> reply = new ArrayList<>(gridDimension);
        int current = blockNumberStartFrom(cellIndex);
        for (int i = 1; i <= gridDimension; i++) {
            final Integer value = cells.get(current).getValue();
            reply.add(value);
            if (((i % blockDimension) == 0)) {
                current += (gridDimension - blockDimension + 1);
            } else {
                current += 1;
            }
        }
        return reply;
    }

    List<Integer> getRowForCell(int cellIndex) {
        int rowLocation = rowNumberFrom(cellIndex) * gridDimension;
        List<Integer> reply = new ArrayList<>(gridDimension);
        int count = 0;
        while (count < gridDimension) {
            final int i = rowLocation + count;
            reply.add(cells.get(i).getValue());
            count++;
        }
        return reply;
    }

    List<Integer> getColumnForCell(int cellIndex) {
        int columnLocation = columnNumberFrom(cellIndex);
        List<Integer> reply = new ArrayList<>(gridDimension);
        int count = 0;
        while (count < gridDimension) {
            final int i = columnLocation + (count * gridDimension);
            reply.add(cells.get(i).getValue());
            count++;
        }
        return reply;
    }

    int blockNumberStartFrom(int cellIndex) {
        int blockRowSize = blockDimension * gridDimension;
        return blockRowSize * (cellIndex / blockRowSize) + blockDimension * ((cellIndex % gridDimension) / blockDimension);
    }

    private int columnNumberFrom(int cellIndex) {
        return cellIndex % gridDimension;
    }

    private int rowNumberFrom(int cellIndex) {
        return (cellIndex / gridDimension);
    }

    private Cell getCell(int cellIndex) throws IllegalArgumentException {
        try {
            return cells.get(cellIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No cell found for cell index: " + cellIndex);
        }
    }

    List<Integer> create1ToNList(int n) throws IllegalArgumentException {
        int one = 1;
        if (n < one) {
            throw new IllegalArgumentException("cannot be less than one");
        }
        return IntStream.rangeClosed(one, n).boxed().collect(Collectors.toList());
    }

}
