package bd.edu.seu.fall2018ai.assignment1.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainUiController implements Initializable {
    //@FXML private Canvas canvas;
    @FXML private Label statusLabel;
    @FXML private Pane drawingPane;
    @FXML private ScrollPane scrollPane;

//    private GraphicsContext gc;
    private final double MAJOR_TICK = 100;
    private final double MINOR_TICK = 20;

    private void drawGridOnPane() {
        Rectangle backgroundRectangle = new Rectangle(0, 0, drawingPane.getWidth(), drawingPane.getHeight());
        Line line = new Line(0, 0, drawingPane.getWidth(), drawingPane.getHeight());
        drawingPane.getChildren().addAll(line);
    }
/*
    private void drawGrid() {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.GRAY);
        for (int i = 0; i <= canvas.getWidth() / MINOR_TICK; i++)
            gc.strokeLine(i * MINOR_TICK, 0, i * MINOR_TICK, canvas.getHeight());

        gc.setStroke(Color.CRIMSON);
        for (int i = 0; i <= canvas.getWidth() / MAJOR_TICK; i++)
            gc.strokeLine(i * MAJOR_TICK, 0, i * MAJOR_TICK, canvas.getHeight());

        gc.setStroke(Color.GRAY);
        for (int i = 0; i <= canvas.getWidth() / MINOR_TICK; i++)
            gc.strokeLine(0,i * MINOR_TICK, canvas.getWidth(), i * MINOR_TICK);

        gc.setStroke(Color.INDIGO);
        for (int i = 0; i <= canvas.getWidth() / MAJOR_TICK; i++)
            gc.strokeLine(0, i * MAJOR_TICK, canvas.getWidth(), i * MAJOR_TICK);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }
*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        gc = canvas.getGraphicsContext2D();
        scrollPane.setStyle("-fx-focus-color: transparent;");
        statusLabel.setText("Started the application");
        drawGridOnPane();
//        drawGrid();
    }
}
