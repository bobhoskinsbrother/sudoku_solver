package io.hahai.sudoku.grid;

import io.hahai.sudoku.solver.Solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Grid {

    private final int gridDimension;
    private final int blockDimension;

    public enum CellState {PRESET, PLAYABLE}

    private List<Cell> cells;

    public Grid(Reader reader) throws IllegalArgumentException {
        resetWith(reader);
        gridDimension = (int) Math.sqrt(cells.size());
        blockDimension = (int) Math.sqrt(gridDimension);
    }

    public static Reader defaultGameReader() {
        return new BufferedReader(new InputStreamReader(Grid.class.getClassLoader().getResourceAsStream("game.sudoku")));
    }

    public void resetWith(Reader reader) {
        cells = new CopyOnWriteArrayList<>();
        loadCellsFrom(reader);
    }

    public int dimension() {
        return gridDimension;
    }

    public List<Cell> playableCells() {
        return cells.stream().filter(cell -> cell.getState() == CellState.PLAYABLE).collect(Collectors.toList());
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
            if (isGridCompleteAndCorrect()) {
                break;
            }
        }
    }

    public List<Cell> play(int cellIndex, int value) {
        clearCell(cellIndex);
        setValueOfCell(cellIndex, playedCell(cellIndex, value));
        return validateGridAndFlagRepetitionsIgnoringZeroes();
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

    public boolean isGridInitialisedCorrectly() {
        boolean isSquare = gridDimension == (blockDimension * blockDimension) && cells.size() == (gridDimension * gridDimension);
        return isSquare && !hasRepetitionInGridIgnoreZeroes();
    }

    public boolean isGridCompleteAndCorrect() {
        List<Cell> validated = validateGridAndFlagRepetitionsIgnoringZeroes();
        return !hasZeroedCellsInGrid() && isAllValid(validated);
    }

    public void accept(SudokuCellVisitor visitor) {
        cells.forEach(visitor::visit);
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
            Cell cell = cells.get(i);
            reply.add(cell.getValue());
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

    List<Integer> create1ToNList(int n) throws IllegalArgumentException {
        int one = 1;
        if (n < one) {
            throw new IllegalArgumentException("cannot be less than one");
        }
        return IntStream.rangeClosed(one, n).boxed().collect(Collectors.toList());
    }

    int blockNumberStartFrom(int cellIndex) {
        int blockRowSize = blockDimension * gridDimension;
        return blockRowSize * (cellIndex / blockRowSize) + blockDimension * ((cellIndex % gridDimension) / blockDimension);
    }

    private List<Cell> validateGridAndFlagRepetitionsIgnoringZeroes() {
        List<Cell> playableCells = playableCells();
        playableCells.forEach(cell -> {
            if (cellHasRepetitionIgnoreZeroes(cell) && !isCellEmpty(cell)) {
                cell.invalid();
            } else {
                cell.valid();
            }
        });
        return playableCells;
    }

    private boolean isCellEmpty(Cell cell) {
        return cell.getValue() == 0;
    }

    private boolean isAllValid(List<Cell> validated) {return validated.stream().allMatch(Cell::isValid);}

    private boolean hasRepetitionInGridIgnoreZeroes() {
        for (Cell cell : cells) {
            if (cellHasRepetitionIgnoreZeroes(cell)) {
                return true;
            }
        }
        return false;
    }

    private boolean cellHasRepetitionIgnoreZeroes(Cell cell) {
        List<Integer> row = getRowForCell(cell.getIndex());
        List<Integer> block = getBlockForCell(cell.getIndex());
        List<Integer> column = getColumnForCell(cell.getIndex());
        return hasRepetitionIgnoreZeroes(row, cell) || hasRepetitionIgnoreZeroes(block, cell) || hasRepetitionIgnoreZeroes(column, cell);
    }

    private boolean hasRepetitionIgnoreZeroes(List<Integer> cellValues, Cell cell) {
        cellValues.removeAll(Collections.singletonList(0));

        Set<Integer> allItems = new HashSet<>();
        Set<Integer> duplicates = cellValues.stream().filter(n -> !allItems.add(n)) //Set.add() returns false if the item was already in the set.
                .collect(Collectors.toSet());

        return duplicates.contains(cell.getValue());
    }

    private boolean hasZeroedCellsInGrid() {
        return cells.stream().anyMatch(cell -> cell.getValue() == 0);
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

    private Cell playedCell(int cellIndex, int value) {return new Cell(cellIndex, CellState.PLAYABLE, value);}

    private void setValueOfCell(int cellIndex, Cell element) {cells.set(cellIndex, element);}

    private void clearCell(int cellIndex) {
        setValueOfCell(cellIndex, emptyPlayableCell(cellIndex));
    }

    private Cell emptyPlayableCell(int cellIndex) {return new Cell(cellIndex);}

    private void loadCellsFrom(Reader reader) throws IllegalArgumentException {
        int current;
        int count = 0;
        try {
            while ((current = reader.read()) != -1) {
                char c = (char) current;
                if ('.' == c) {
                    cells.add(emptyPlayableCell(count));
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

}
