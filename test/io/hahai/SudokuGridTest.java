package io.hahai;

import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class SudokuGridTest {

    @Test public void canCreate1To5List() {
        String grid ="";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        List<Integer> reply = unit.create1ToNList(5);
        assertThat(reply.size(), is(5));
        assertThat(reply.get(0), is(1));
        assertThat(reply.get(1), is(2));
        assertThat(reply.get(2), is(3));
        assertThat(reply.get(3), is(4));
        assertThat(reply.get(4), is(5));
    }

    @Test public void canCreate1To2List() {
        String grid ="";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        List<Integer> reply = unit.create1ToNList(2);
        assertThat(reply.size(), is(2));
        assertThat(reply.get(0), is(1));
        assertThat(reply.get(1), is(2));
    }

    @Test(expected = IllegalArgumentException.class) public void cannotCreate1To0List() {
        String grid ="";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        unit.create1ToNList(0);
    }

    @Test(expected = IllegalArgumentException.class) public void cannotCreate1ToMinus1List() {
        String grid ="";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        unit.create1ToNList(-1);
    }

    @Test public void canInitialiseGrid() {
        String grid =
                " - 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 - 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 - 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        assertThat(unit.valid(), is(true));
    }

    @Test public void canVerifyPlayableOptionsOnHorizontal() {
        String grid =
                " - 1 4 - 6 8 7 5 9" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -"
                ;
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);

        assertThat(unit.playable(0), is(true));
        assertThat(unit.playable(1), is(false));
        assertThat(unit.playable(3), is(true));

        List<Integer> playableOptions = unit.playableOptions(3);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(2,3));

        playableOptions = unit.playableOptions(0);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(2,3));
    }

    @Test public void canVerifyPlayableOptionsOnVertical() {
        String grid =
                " - - - - - - - - -" +
                " - 2 - - - - - - -" +
                " - 1 - - - - - - -" +
                " - 4 - - - - - - -" +
                " - 5 - - - - - - -" +
                " - 6 - - - - - - -" +
                " - 7 - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -"
                ;
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);

        assertThat(unit.playable(1), is(true));

        List<Integer> playableOptions = unit.playableOptions(1);
        assertThat(playableOptions.size(), is(3));
        assertThat(playableOptions, containsInAnyOrder(3,8,9));

        playableOptions = unit.playableOptions(64);
        assertThat(playableOptions.size(), is(3));
        assertThat(playableOptions, containsInAnyOrder(3,8,9));
    }

    @Test public void canVerifyPlayableOptionsOnBlock() {
        String grid =
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - 1 2 3 - - -" +
                " - - - - 6 - - - -" +
                " - - - 8 - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -" +
                " - - - - - - - - -"
                ;
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);

        assertThat(unit.playable(39), is(true));
        assertThat(unit.playable(41), is(true));

        List<Integer> playableOptions = unit.playableOptions(39);
        assertThat(playableOptions.size(), is(4));
        assertThat(playableOptions, containsInAnyOrder(4,5,7,9));

        playableOptions = unit.playableOptions(41);
        assertThat(playableOptions.size(), is(4));
        assertThat(playableOptions, containsInAnyOrder(4,5,7,9));
    }

    @Test public void canVerifyPlayableOptionsOnEveryDimension() {
        String grid =
                " - 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 - 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 - 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);

        assertThat(unit.playable(0), is(true));
        assertThat(unit.playable(1), is(false));
        assertThat(unit.playable(3), is(true));

        final List<Integer> playableOptions = unit.playableOptions(3);
        assertThat(playableOptions.size(), is(1));
        assertThat(playableOptions, containsInAnyOrder(2));
    }

    @Test public void playableCells() {
        String grid =
                " - 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 - 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 - 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        List<Cell> playable = unit.playableCells();
        assertThat(playable.size(), is(4));
    }

    @Test public void canGetColumn1() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getColumnForCell(1));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{1, 8, 7, 2, 5, 9, 3, 6, 4}));
    }

    @Test public void canGetColumn3() {
        String grid = " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getColumnForCell(3));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{0, 1, 3, 8, 9, 6, 4, 7, 5}));
    }

    @Test public void canGetColumn10() {
        String grid = " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getColumnForCell(10));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{1, 8, 7, 2, 5, 9, 3, 6, 4}));
    }

    @Test public void canGetRowForCell0() {
        String grid = " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getRowForCell(0));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 0, 6, 8, 7, 5, 9}));
    }

    @Test public void canGetRowForCell9() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getRowForCell(9));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{5, 8, 2, 1, 7, 9, 4, 3, 6}));
    }

    @Test public void canGetRowForCell28() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getRowForCell(28));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{1, 2, 7, 8, 3, 4, 9, 6, 5}));
    }

    @Test public void canGetBlockForCell0() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(0));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 5, 8, 2, 6, 7, 9}));
    }

    @Test public void canGetBlockForCell1() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(1));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 5, 8, 2, 6, 7, 9}));
    }

    @Test public void canGetBlockForCell10() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(10));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 5, 8, 2, 6, 7, 9}));
    }

    @Test public void canGetBlockForCell35() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(35));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{9,6,5,3,8,7,2,1,4}));
    }

    @Test public void canGetBlockForCell72() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(72));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{2,3,8,9,6,5,7,4,1}));
    }

    @Test public void blockStartForMultipleIndices() {
        String grid =
                " 3 1 4 - 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final SudokuGrid unit = new SudokuGrid(reader);
        assertThat(unit.blockNumberStartFrom(0), is(0));
        assertThat(unit.blockNumberStartFrom(10), is(0));
        assertThat(unit.blockNumberStartFrom(20), is(0));

        assertThat(unit.blockNumberStartFrom(4), is(3));
        assertThat(unit.blockNumberStartFrom(13), is(3));
        assertThat(unit.blockNumberStartFrom(23), is(3));

        assertThat(unit.blockNumberStartFrom(6), is(6));
        assertThat(unit.blockNumberStartFrom(16), is(6));
        assertThat(unit.blockNumberStartFrom(26), is(6));

        assertThat(unit.blockNumberStartFrom(27), is(27));
        assertThat(unit.blockNumberStartFrom(37), is(27));
        assertThat(unit.blockNumberStartFrom(47), is(27));

        assertThat(unit.blockNumberStartFrom(30), is(30));
        assertThat(unit.blockNumberStartFrom(40), is(30));
        assertThat(unit.blockNumberStartFrom(50), is(30));

        assertThat(unit.blockNumberStartFrom(33), is(33));
        assertThat(unit.blockNumberStartFrom(43), is(33));
        assertThat(unit.blockNumberStartFrom(53), is(33));
    }

}
