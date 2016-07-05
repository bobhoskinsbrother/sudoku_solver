package io.hahai.sudoku.grid;

import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public final class GridTest {

    @Test public void canCreate1To5List() {
        String grid ="";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
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
        final Grid unit = new Grid(reader);
        List<Integer> reply = unit.create1ToNList(2);
        assertThat(reply.size(), is(2));
        assertThat(reply.get(0), is(1));
        assertThat(reply.get(1), is(2));
    }

    @Test(expected = IllegalArgumentException.class) public void cannotCreate1To0List() {
        String grid ="";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        unit.create1ToNList(0);
    }

    @Test(expected = IllegalArgumentException.class) public void cannotCreate1ToMinus1List() {
        String grid ="";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        unit.create1ToNList(-1);
    }

    @Test public void canInitialiseValidGrid9x9() {
        String grid =
                " 3 1 4 2 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(true));
    }

    @Test public void canInitialiseValidGrid9x9NoSpaces() {
        String grid =
                "314268759" +
                "582179436" +
                "679345128" +
                "127834965" +
                "456921387" +
                "893657214" +
                "238496571" +
                "965712843" +
                "741583692";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(true));
    }

    @Test public void canInitialiseValidGrid9x9SpacesForBlocks() {
        String grid =
                "314 268 759" +
                "582 179 436" +
                "679 345 128" +
                "           " +
                "127 834 965" +
                "456 921 387" +
                "893 657 214" +
                "           " +
                "238 496 571" +
                "965 712 843" +
                "741 583 692";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(true));
    }

    @Test public void canInitialiseValidGrid9x9SpacesAndLines() {
        String grid =
                "┌───────┬───────┬───────┐" +
                "│ 3 1 4 │ 2 6 8 │ 7 5 9 │" +
                "│ 5 8 2 │ 1 7 9 │ 4 3 6 │" +
                "│ 6 7 9 │ 3 4 5 │ 1 2 8 │" +
                "├───────┼───────┼───────┤" +
                "│ 1 2 7 │ 8 3 4 │ 9 6 5 │" +
                "│ 4 5 6 │ 9 2 1 │ 3 8 7 │" +
                "│ 8 9 3 │ 6 5 7 │ 2 1 4 │" +
                "├───────┼───────┼───────┤" +
                "│ 2 3 8 │ 4 9 6 │ 5 7 1 │" +
                "│ 9 6 5 │ 7 1 2 │ 8 4 3 │" +
                "│ 7 4 1 │ 5 8 3 │ 6 9 2 │" +
                "└───────┴───────┴───────┘";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(true));
    }

    @Test public void canInitialiseValidGrid9x9ButIsInvalidDueToRepeatInRow() {
        String grid =
                " . 9 4 2 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 . 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(false));
    }

    @Test public void canInitialiseValidGrid9x9ButIsInvalidDueToRepeatInColumn() {
        String grid =
                " 1 . 4 2 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 . 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(false));
    }

    @Test
    public void canPlayWhenValueIsNotALegalMoveButWillFlagAsInvalid() {
        String grid =
                " . 1 4 2 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        unit.play(0, 1);
        assertThat(unit.playableCells().get(0).isValid(), is(false));
        unit.play(0, 3);
        assertThat(unit.playableCells().get(0).isValid(), is(true));
    }

    @Test
    public void canValidate2ValuesCorrectly() {
        String grid =
                " . 1 4 2 6 8 7 5 ." +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        unit.play(0, 1);
        unit.play(8, 9);
        assertThat(unit.playableCells().get(0).isValid(), is(false));
        assertThat(unit.playableCells().get(1).isValid(), is(true));
        unit.play(0, 3);
        assertThat(unit.playableCells().get(0).isValid(), is(true));
        assertThat(unit.playableCells().get(1).isValid(), is(true));
        unit.play(0, 3);
    }

    @Test public void canPlayWhenValueIsInOptions() {
        String grid =
                " . 1 4 2 6 8 7 5 ." +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 . 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " . 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        unit.play(0, 9);
        unit.play(0, 3);
    }

    @Test public void gameSetIsNotPlayable() {
        String grid =
                " . 1 4 2 6 8 7 5 ." +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 . 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " . 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.cellPlayable(1), is(false));
    }


    @Test public void remainsPlayableAfterPlayed() {
        String grid =
                " . 1 4 2 6 8 7 5 ." +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 . 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " . 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.cellPlayable(0), is(true));
        unit.play(0,9);
        assertThat(unit.cellPlayable(0), is(true));
    }

    @Test public void gridIsCompleteWhenFullyFilledIn() {
        String grid =
                " . 1 4 2 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridCompleteAndCorrect(), is(false));
        unit.play(0, 3);
        assertThat(unit.isGridCompleteAndCorrect(), is(true));
    }

    @Test public void canInitialiseValidGrid9x9ButIsInvalidDueToRepeatInBlock() {
        String grid =
                " 3 1 4 2 6 8 7 5 9" +
                " 5 9 2 1 7 . 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 . 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(false));
    }

    @Test public void canInitialiseValidGrid4x4() {
        String grid =
                " 1 2 3 4" +
                " 4 3 2 1" +
                " 3 4 1 2" +
                " 2 1 4 3"
                ;
        final StringReader reader = new StringReader(grid);
        Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(true));
    }

    @Test public void cannotInitialiseANotSquareGrid() {
        String grid = " . 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 . 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 . 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 ";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        assertThat(unit.isGridInitialisedCorrectly(), is(false));
    }

    @Test public void canVerifyPlayableOptionsOnHorizontal() {
        String grid =
                " . 1 4 . 6 8 7 5 9" +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ."
                ;
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);

        assertThat(unit.cellPlayable(0), is(true));
        assertThat(unit.cellPlayable(1), is(false));
        assertThat(unit.cellPlayable(3), is(true));

        List<Integer> playableOptions = unit.playableOptions(3);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(2, 3));

        playableOptions = unit.playableOptions(0);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(2, 3));
    }

    @Test public void canVerifyPlayableOptionsOnVertical() {
        String grid =
                " . . . . . . . . ." +
                " . 2 . . . . . . ." +
                " . 1 . . . . . . ." +
                " . 4 . . . . . . ." +
                " . 5 . . . . . . ." +
                " . 6 . . . . . . ." +
                " . 7 . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ."
                ;
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);

        assertThat(unit.cellPlayable(1), is(true));

        List<Integer> playableOptions = unit.playableOptions(1);
        assertThat(playableOptions.size(), is(3));
        assertThat(playableOptions, containsInAnyOrder(3, 8, 9));

        playableOptions = unit.playableOptions(64);
        assertThat(playableOptions.size(), is(3));
        assertThat(playableOptions, containsInAnyOrder(3, 8, 9));
    }

    @Test public void canVerifyPlayableOptionsOnBlock() {
        String grid =
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . 1 2 3 . . ." +
                " . . . . 6 . . . ." +
                " . . . 8 . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ."
                ;
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);

        assertThat(unit.cellPlayable(39), is(true));
        assertThat(unit.cellPlayable(41), is(true));

        List<Integer> playableOptions = unit.playableOptions(39);
        assertThat(playableOptions.size(), is(4));
        assertThat(playableOptions, containsInAnyOrder(4, 5, 7, 9));

        playableOptions = unit.playableOptions(41);
        assertThat(playableOptions.size(), is(4));
        assertThat(playableOptions, containsInAnyOrder(4, 5, 7, 9));
    }

    @Test public void canVerifyPlayableOptionsOnEveryDimension() {
        String grid =
                " . 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 . 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 . 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);

        assertThat(unit.cellPlayable(0), is(true));
        assertThat(unit.cellPlayable(1), is(false));
        assertThat(unit.cellPlayable(3), is(true));

        final List<Integer> playableOptions = unit.playableOptions(3);
        assertThat(playableOptions.size(), is(1));
        assertThat(playableOptions, containsInAnyOrder(2));
    }

    @Test public void playableCells() {
        String grid =
                " . 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 . 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 . 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        List<Cell> playable = unit.playableCells();
        assertThat(playable.size(), is(4));
    }

    @Test public void canGetColumn1() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getColumnForCell(1));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{1, 8, 7, 2, 5, 9, 3, 6, 4}));
    }

    @Test public void canGetColumn3() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getColumnForCell(3));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{0, 1, 3, 8, 9, 6, 4, 7, 5}));
    }

    @Test public void canGetColumn10() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getColumnForCell(10));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{1, 8, 7, 2, 5, 9, 3, 6, 4}));
    }

    @Test public void canGetRowForCell0() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getRowForCell(0));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 0, 6, 8, 7, 5, 9}));
    }

    @Test public void canGetRowForCell9() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getRowForCell(9));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{5, 8, 2, 1, 7, 9, 4, 3, 6}));
    }

    @Test public void canGetRowForCell28() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getRowForCell(28));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{1, 2, 7, 8, 3, 4, 9, 6, 5}));
    }

    @Test public void canGetBlockForCell0() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(0));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 5, 8, 2, 6, 7, 9}));
    }

    @Test public void canGetBlockForCell1() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(1));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 5, 8, 2, 6, 7, 9}));
    }

    @Test public void canGetBlockForCell10() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(10));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{3, 1, 4, 5, 8, 2, 6, 7, 9}));
    }

    @Test public void canGetBlockForCell35() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(35));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{9,6,5,3,8,7,2,1,4}));
    }

    @Test public void canGetBlockForCell72() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
        final ArrayList<Integer> reply = new ArrayList<>(unit.getBlockForCell(72));
        Integer[] actual = reply.toArray(new Integer[reply.size()]);
        assertThat(actual, is(new Integer[]{2,3,8,9,6,5,7,4,1}));
    }

    @Test public void blockStartForMultipleIndices() {
        String grid =
                " 3 1 4 . 6 8 7 5 9" +
                " 5 8 2 1 7 9 4 3 6" +
                " 6 7 9 3 4 5 1 2 8" +
                " 1 2 7 8 3 4 9 6 5" +
                " 4 5 6 9 2 1 3 8 7" +
                " 8 9 3 6 5 7 2 1 4" +
                " 2 3 8 4 9 6 5 7 1" +
                " 9 6 5 7 1 2 8 4 3" +
                " 7 4 1 5 8 3 6 9 2";
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);
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

    @Test public void optionsDecrementWhenCellsPlayed() {
        String grid =
                " . . . . . . . . ." +
                " . 2 . . . . . . ." +
                " . 1 . . . . . . ." +
                " . 4 . . . . . . ." +
                " . 5 . . . . . . ." +
                " . 6 . . . . . . ." +
                " . 7 . . . . . . ." +
                " . . . . . . . . ." +
                " . . . . . . . . ."
                ;
        final StringReader reader = new StringReader(grid);
        final Grid unit = new Grid(reader);

        assertThat(unit.cellPlayable(1), is(true));

        List<Integer> playableOptions = unit.playableOptions(1);
        assertThat(playableOptions.size(), is(3));
        assertThat(playableOptions, containsInAnyOrder(3, 8, 9));

        playableOptions = unit.playableOptions(64);
        assertThat(playableOptions.size(), is(3));
        assertThat(playableOptions, containsInAnyOrder(3, 8, 9));
        playableOptions = unit.playableOptions(73);
        assertThat(playableOptions.size(), is(3));
        assertThat(playableOptions, containsInAnyOrder(3, 8, 9));
        unit.play(1, 3);

        playableOptions = unit.playableOptions(64);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(8, 9));

        playableOptions = unit.playableOptions(73);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(8, 9));

        unit.play(1, 8);

        playableOptions = unit.playableOptions(64);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(3, 9));

        playableOptions = unit.playableOptions(73);
        assertThat(playableOptions.size(), is(2));
        assertThat(playableOptions, containsInAnyOrder(3, 9));

        unit.play(64, 9);

        playableOptions = unit.playableOptions(73);
        assertThat(playableOptions.size(), is(1));
        assertThat(playableOptions, containsInAnyOrder(3));
    }

}
