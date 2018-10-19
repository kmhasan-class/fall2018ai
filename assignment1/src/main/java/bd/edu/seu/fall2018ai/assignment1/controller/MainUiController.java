package bd.edu.seu.fall2018ai.assignment1.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
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

    private void drawGrid() {
        drawingPane.getChildren().clear();
        statusLabel.setText("Drawing the base grid");
        Rectangle backgroundRectangle = new Rectangle(0, 0, drawingPane.getWidth(), drawingPane.getHeight());
        backgroundRectangle.setFill(Color.LIGHTGRAY);

        for (int i = 0; i <= drawingPane.getWidth() / MINOR_TICK; i++) {
            Line line = new Line(i * MINOR_TICK, 0, i * MINOR_TICK, drawingPane.getHeight());
            line.setStroke(Color.GRAY);
            drawingPane.getChildren().add(line);
        }

        for (int i = 0; i <= drawingPane.getWidth() / MAJOR_TICK; i++) {
            Line line = new Line(i * MAJOR_TICK, 0, i * MAJOR_TICK, drawingPane.getHeight());
            line.setStroke(Color.CRIMSON);
            drawingPane.getChildren().add(line);
        }

        for (int i = 0; i <= drawingPane.getWidth() / MINOR_TICK; i++) {
            Line line = new Line(0, i * MINOR_TICK, drawingPane.getWidth(), i * MINOR_TICK);
            line.setStroke(Color.GRAY);
            drawingPane.getChildren().add(line);
        }

        for (int i = 0; i <= drawingPane.getWidth() / MAJOR_TICK; i++) {
            Line line = new Line(0, i * MAJOR_TICK, drawingPane.getWidth(), i * MAJOR_TICK);
            line.setStroke(Color.INDIGO);
            drawingPane.getChildren().add(line);
        }

    }

    private void drawObjects() {

    }

    private void readFile(File file) {

    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        statusLabel.setText("Reading map");
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Platform.runLater(() -> {
                readFile(file);
                drawGrid();
                drawObjects();
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setStyle("-fx-focus-color: transparent;");
        statusLabel.setText("Started the application");
        Platform.runLater(() -> drawGrid());
    }
}
