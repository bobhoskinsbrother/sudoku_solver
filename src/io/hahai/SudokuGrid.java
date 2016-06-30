package io.hahai;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.hahai.SudokuGrid.CellState.PLAYABLE;
import static io.hahai.SudokuGrid.CellState.PRESET;

public final class SudokuGrid {

    public static final int GRID_DIMENSION = 9;
    public static final int BLOCK_DIMENSION = (int) Math.sqrt(GRID_DIMENSION);


    public enum CellState {PRESET, RESOLVED, PLAYABLE}

    private List<Cell> cells;

    public SudokuGrid(StringReader reader) {
        cells = new ArrayList<>();
        loadCellsFrom(reader);
    }

    public List<Cell> playableCells() {
        return cells.stream().filter(cell -> cell.getState() == PLAYABLE).collect(Collectors.toList());
    }

    private void loadCellsFrom(StringReader reader) {
        int current;
        int count=0;
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
            List<Integer> reply = create();
            List<Integer> column = getColumnForCell(index);
            List<Integer> block = getBlockForCell(index);
            List<Integer> row = getRowForCell(index);
            reply.removeAll(column);
            reply.removeAll(block);
            reply.removeAll(row);
            return reply;
        }
        return new ArrayList<>();
    }

    public boolean playable(int index) {
        return getCell(index).getState() == PLAYABLE;
    }

    public boolean valid() {
        return true;
    }

    List<Integer> getBlockForCell(int blockLocation) {
        List<Integer> reply = new ArrayList<>(GRID_DIMENSION);
        int current = blockNumberStartFrom(blockLocation);
        for (int i = 1; i <= GRID_DIMENSION; i++) {
            final Integer value = cells.get(current).getValue();
            reply.add(value);
            if (((i % BLOCK_DIMENSION) == 0)) {
                current += (GRID_DIMENSION-BLOCK_DIMENSION+1);
            } else {
                current += 1;
            }
        }
        return reply;
    }

    List<Integer> getRowForCell(int index) {
        int rowLocation = rowNumberFrom(index) * GRID_DIMENSION;
        List<Integer> reply = new ArrayList<>(GRID_DIMENSION);
        int count = 0;
        while (count < GRID_DIMENSION) {
            final int i = rowLocation + count;
            reply.add(cells.get(i).getValue());
            count++;
        }
        return reply;
    }

    List<Integer> getColumnForCell(int cellIndexNumber) {
        int columnLocation = columnNumberFrom(cellIndexNumber);
        List<Integer> reply = new ArrayList<>(GRID_DIMENSION);
        int count = 0;
        while (count < GRID_DIMENSION) {
            final int i = columnLocation + (count * GRID_DIMENSION);
            reply.add(cells.get(i).getValue());
            count++;
        }
        return reply;
    }

    int blockNumberStartFrom(int location) {
        int threeByThree = BLOCK_DIMENSION*GRID_DIMENSION;
        return threeByThree * (location / threeByThree) + BLOCK_DIMENSION * ((location % GRID_DIMENSION) / BLOCK_DIMENSION);
    }

    private int columnNumberFrom(int location) {
        return location % GRID_DIMENSION;
    }

    private int rowNumberFrom(int location) {
        return (location / GRID_DIMENSION);
    }

    private Cell getCell(int index) {
        try {
            return cells.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No cell found for index: " + index);
        }
    }

    private List<Integer> create() {
        return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

}
