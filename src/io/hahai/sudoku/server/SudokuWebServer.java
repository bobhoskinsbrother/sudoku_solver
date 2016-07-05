package io.hahai.sudoku.server;

import io.hahai.sudoku.grid.Grid;
import org.eclipse.jetty.server.Server;

import java.net.URL;

import static io.hahai.sudoku.server.builder.HttpServerBuilder.ServletsBuilder.sb;
import static io.hahai.sudoku.server.builder.HttpServerBuilder.builder;

public final class SudokuWebServer {

    public static void main(String[] args) throws Exception {
        Grid grid = new Grid(Grid.defaultGameReader());
        Server server = builder().port(9900)
                .addToContext("/v1/sudoku/", sb().addServlet("/grid", new PlaySudokuServlet(grid)))
                .addToContext("/", sb().addDefault("/*", resolve("web_resources/"))).build();
        server.start();
    }

    private static String resolve(String path) {
        final URL resource = SudokuWebServer.class.getClassLoader().getResource(path);
        if (resource == null) { throw new IllegalArgumentException("File root: " + path + " not found on classpath"); }
        return resource.toExternalForm();

    }

}
