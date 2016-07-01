package io.hahai;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.hahai.SudokuGrid.CellState.PLAYABLE;
import static io.hahai.SudokuGrid.CellState.PRESET;

public final class SudokuGrid {

    private final int gridDimension;
    private final int blockDimension;

    public enum CellState {PRESET, PLAYABLE }

    private List<Cell> cells;

    public SudokuGrid(StringReader reader) throws IllegalArgumentException {
        cells = new ArrayList<>();
        loadCellsFrom(reader);
        gridDimension = (int) Math.sqrt(cells.size());
        blockDimension = (int) Math.sqrt(gridDimension);
    }

    public void play(int index, int value) throws IllegalArgumentException {
        if(!playableOptions(index).contains(value)) {
            throw new IllegalArgumentException("Cannot play this value for this cell");
        }
        cells.set(index, new Cell(index, PLAYABLE, value));
    }

    public List<Cell> playableCells() {
        return cells.stream().filter(cell -> cell.getState() == PLAYABLE).collect(Collectors.toList());
    }

    private void loadCellsFrom(StringReader reader) throws IllegalArgumentException {
        int current;
        int count = 0;
        try {
            while ((current = reader.read()) != -1) {
                char c = (char) current;
                if (' ' == c)
                    continue;
                if ('-' == c) {
                    cells.add(new Cell(count));
                    continue;
                }
                cells.add(new Cell(count, PRESET, Character.getNumericValue(c)));
                count++;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Integer> playableOptions(int index) {
        if (playable(index)) {
            List<Integer> reply = create1ToNList(gridDimension);
            reply.removeAll(getColumnForCell(index));
            reply.removeAll(getBlockForCell(index));
            reply.removeAll(getRowForCell(index));
            return reply;
        }
        return new ArrayList<>();
    }

    public boolean playable(int index) {
        CellState state = getCell(index).getState();
        return state == PLAYABLE;
    }

    public boolean gridValid() {
        boolean isSquare = gridDimension == (blockDimension * blockDimension) && cells.size() == (gridDimension * gridDimension);
        return isSquare && !hasRepetitionInGrid();
    }

    public boolean gridComplete() {
        return !hasZeroedCellsInGrid() &&
               !hasRepetitionInGrid();
    }

    private boolean hasRepetitionInGrid() {
        for (Cell cell : cells) {
            List<Integer> row = getRowForCell(cell.getIndex());
            List<Integer> block = getBlockForCell(cell.getIndex());
            List<Integer> column = getColumnForCell(cell.getIndex());
            if(hasRepetition(row) || hasRepetition(block) || hasRepetition(column)) {
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

    List<Integer> getBlockForCell(int blockLocation) {
        List<Integer> reply = new ArrayList<>(gridDimension);
        int current = blockNumberStartFrom(blockLocation);
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

    List<Integer> getRowForCell(int index) {
        int rowLocation = rowNumberFrom(index) * gridDimension;
        List<Integer> reply = new ArrayList<>(gridDimension);
        int count = 0;
        while (count < gridDimension) {
            final int i = rowLocation + count;
            reply.add(cells.get(i).getValue());
            count++;
        }
        return reply;
    }

    List<Integer> getColumnForCell(int cellIndexNumber) {
        int columnLocation = columnNumberFrom(cellIndexNumber);
        List<Integer> reply = new ArrayList<>(gridDimension);
        int count = 0;
        while (count < gridDimension) {
            final int i = columnLocation + (count * gridDimension);
            reply.add(cells.get(i).getValue());
            count++;
        }
        return reply;
    }

    int blockNumberStartFrom(int location) {
        int blockRowSize = blockDimension * gridDimension;
        return blockRowSize * (location / blockRowSize) + blockDimension * ((location % gridDimension) / blockDimension);
    }

    private int columnNumberFrom(int location) {
        return location % gridDimension;
    }

    private int rowNumberFrom(int location) {
        return (location / gridDimension);
    }

    private Cell getCell(int index) throws IllegalArgumentException {
        try {
            return cells.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No cell found for index: " + index);
        }
    }

    List<Integer> create1ToNList(int gridDimension) throws IllegalArgumentException {
        int start = 1;
        if (gridDimension < start) {
            throw new IllegalArgumentException("cannot be less than start");
        }
        return IntStream.rangeClosed(start, gridDimension).boxed().collect(Collectors.toList());
    }

}
