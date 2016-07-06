Sudoku
======

This is a simple example in Java for a game of sudoku.
The web front end has only been put on as an example - it's not threadsafe, and allows multiple players to edit the same game.

This idea is to model the majority of the grid in Java, and then begin to write a bunch of solvers (perhaps even something constraint based)

To build:

Install ant, then:
```ant```

then

```bash
cd dist
unzip sudoku_solver.zip
cd sudoku_solver
./run_sudoku.sh```
```