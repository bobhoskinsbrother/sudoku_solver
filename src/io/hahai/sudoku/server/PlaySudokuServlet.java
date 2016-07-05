package io.hahai.sudoku.server;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import io.hahai.sudoku.grid.Cell;
import io.hahai.sudoku.grid.Grid;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

public final class PlaySudokuServlet extends HttpServlet {

    private final Grid grid;

    public PlaySudokuServlet(Grid grid) {
        this.grid = grid;
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonArray reply = new JsonArray();
        grid.accept(cell -> {
            JsonObject jsonCell = new JsonObject();
            jsonCell.set("val", cell.getValue());
            jsonCell.set("state", cell.getState().name().toLowerCase());
            reply.add(jsonCell);
        });
        resp.setContentType("application/json");
        resp.setStatus(SC_OK);
        PrintWriter writer = resp.getWriter();
        reply.writeTo(writer);
        writer.close();
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStreamReader reader = new InputStreamReader(req.getInputStream());
        JsonObject requestObject = JsonObject.readFrom(reader);
        int index = requestObject.getInt("index", -1);
        int value = requestObject.getInt("value", -1);
        if (index != -1 && value != -1) {
            JsonObject reply = new JsonObject();
            List<Cell> cells = grid.play(index, value);
            resp.setStatus(SC_OK);
            reply.add("cells", serializeCells(cells));
            boolean gridCompleteAndCorrect = grid.isGridCompleteAndCorrect();
            reply.add("complete", gridCompleteAndCorrect);
            if (gridCompleteAndCorrect) {
                grid.resetWith(Grid.defaultGameReader());
            }
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            reply.writeTo(writer);
            writer.close();
        }
    }

    private JsonArray serializeCells(List<Cell> cells) {
        JsonArray array = new JsonArray();
        cells.forEach(cell -> {
            JsonObject jsonCell = new JsonObject();
            jsonCell.set("index", cell.getIndex());
            jsonCell.set("val", cell.getValue());
            jsonCell.set("state", cell.getState().name().toLowerCase());
            jsonCell.set("valid", cell.isValid());
            array.add(jsonCell);
        });
        return array;
    }
}
