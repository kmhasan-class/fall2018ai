package bd.edu.seu.fall2018ai.assignment1.controller;

import bd.edu.seu.fall2018ai.assignment1.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainUiController implements Initializable {
    @FXML private Label statusLabel;
    @FXML private Pane drawingPane;
    @FXML private ScrollPane scrollPane;

    private List<Shape2D> shape2DList;
    private double mapWidth = 800;
    private double mapHeight = 800;
    private Robot2D source;
    private Destination2D destination;

    private final double MAJOR_TICK = 100;
    private final double MINOR_TICK = 20;

    private void drawGrid() {
        drawingPane.getChildren().clear();

        drawingPane.setPrefWidth(mapWidth);
        drawingPane.setPrefHeight(mapHeight);

        drawingPane.setMinWidth(mapWidth);
        drawingPane.setMinHeight(mapHeight);

        drawingPane.setMaxWidth(mapWidth);
        drawingPane.setMaxHeight(mapHeight);

        statusLabel.setText("Drawing the base grid");
        Rectangle backgroundRectangle = new Rectangle(0, 0, mapWidth, mapHeight);
        backgroundRectangle.setFill(Color.LIGHTGRAY);

        for (int i = 0; i <= mapWidth / MINOR_TICK; i++) {
            Line line = new Line(i * MINOR_TICK, 0, i * MINOR_TICK, mapHeight);
            line.setStroke(Color.GRAY);
            drawingPane.getChildren().add(line);
        }

        for (int i = 0; i <= mapWidth / MAJOR_TICK; i++) {
            Line line = new Line(i * MAJOR_TICK, 0, i * MAJOR_TICK, mapHeight);
            line.setStroke(Color.CRIMSON);
            drawingPane.getChildren().add(line);
        }

        for (int i = 0; i <= mapHeight / MINOR_TICK; i++) {
            Line line = new Line(0, i * MINOR_TICK, mapWidth, i * MINOR_TICK);
            line.setStroke(Color.GRAY);
            drawingPane.getChildren().add(line);
        }

        for (int i = 0; i <= mapHeight / MAJOR_TICK; i++) {
            Line line = new Line(0, i * MAJOR_TICK, mapWidth, i * MAJOR_TICK);
            line.setStroke(Color.INDIGO);
            drawingPane.getChildren().add(line);
        }
    }

    private void drawObjects() {
        
        shape2DList.forEach(shape2D -> drawingPane.getChildren().add(shape2D.getJFXShape()));

        drawingPane.getChildren().add(source.getJFXShape());

        drawingPane.getChildren().add(destination.getJFXShape());

    }

    private void readFile(File file) {
        String line = null;
        String tokens[];
        double centerX, centerY, radius, width, height;

        shape2DList = new ArrayList<>();

        try (RandomAccessFile input = new RandomAccessFile(file, "r")) {
            while ((line = input.readLine()) != null && line.startsWith("#"));

            tokens = line.split("\\ ");
            mapWidth = Double.parseDouble(tokens[0]);
            mapHeight = Double.parseDouble(tokens[1]);

            while ((line = input.readLine()) != null && line.startsWith("#"));

            int numObjects = Integer.parseInt(line);
            int count = 0;

            while (count < numObjects) {
                while ((line = input.readLine()) != null && line.startsWith("#"));

                tokens = line.split("\\ ");

                switch (tokens[0]) {
                    case "circle":
                        centerX = Double.parseDouble(tokens[1]);
                        centerY = Double.parseDouble(tokens[2]);
                        radius  = Double.parseDouble(tokens[3]);
                        shape2DList.add(new Circle2D(new Point2D(centerX, centerY), radius));
                        break;
                    case "rectangle":
                        centerX = Double.parseDouble(tokens[1]);
                        centerY = Double.parseDouble(tokens[2]);
                        width  = Double.parseDouble(tokens[3]);
                        height  = Double.parseDouble(tokens[4]);
                        shape2DList.add(new Rectangle2D(new Point2D(centerX, centerY), width, height));
                        break;
                    default:
                        break;
                }
                count++;
            }
            
            // read the source
            while ((line = input.readLine()) != null && line.startsWith("#"));

            tokens = line.split("\\ ");
            centerX = Double.parseDouble(tokens[0]);
            centerY = Double.parseDouble(tokens[1]);
            radius  = Double.parseDouble(tokens[2]);
            source = new Robot2D(new Point2D(centerX, centerY), radius);
            // read the destination

            while ((line = input.readLine()) != null && line.startsWith("#"));

            tokens = line.split("\\ ");

            centerX = Double.parseDouble(tokens[0]);
            centerY = Double.parseDouble(tokens[1]);
            radius  = Double.parseDouble(tokens[2]);
            destination = new Destination2D(new Point2D(centerX, centerY), radius);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                statusLabel.setText("Done reading " + file.getName());
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
