package bd.edu.seu.fall2018ai.assignment1.controller;

import bd.edu.seu.fall2018ai.assignment1.algorithm.RandomSearchAlgorithm;
import bd.edu.seu.fall2018ai.assignment1.algorithm.SearchAlgorithm;
import bd.edu.seu.fall2018ai.assignment1.model.*;
import bd.edu.seu.fall2018ai.assignment1.util.SubclassLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.*;

public class MainUiController implements Initializable {
    private class State {
        int iteration;
        double x;
        double y;
        double theta;
        String action;

        public State(int iteration, double x, double y, double theta, String action) {
            this.iteration = iteration;
            this.x = x;
            this.y = y;
            this.theta = theta;
            this.action = action;
        }
    }

    @FXML private Label statusLabel;
    @FXML private Pane drawingPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Button readMapButton;
    @FXML private Button searchButton;
    @FXML private TableView<State> stateTableView;
    @FXML private TableColumn<State, String> actionColumn;
    @FXML private TableColumn<State, String> iterationColumn;
    @FXML private TableColumn<State, String> xColumn;
    @FXML private TableColumn<State, String> yColumn;
    @FXML private TableColumn<State, String> thetaColumn;
    @FXML private ComboBox<Class> searchClassComboBox;

    private final ObservableList<State> stateTableList = FXCollections.observableArrayList();
    private final ObservableList<Class> searchClassList = FXCollections.observableArrayList();

    private List<Shape2D> shape2DList;
    private double mapWidth = 800;
    private double mapHeight = 800;
    private Robot2D source;
    private Destination2D destination;
    private SearchAlgorithm searchAlgorithm;

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
    public void handleReadMapAction(ActionEvent event) {
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
        searchButton.setDisable(false);
    }

    int iterationCount;
    @FXML
    public void handleSearchAction(ActionEvent event) {
        iterationCount = 0;

        Robot2D initialState = new Robot2D(source.getCenter(), source.getRadius(), source.getOrientation());

        List<Action> actionList = searchAlgorithm.search(source, destination, shape2DList, mapWidth, mapHeight);
        stateTableList.clear();
        drawingPane.getChildren().remove(source.getJFXShape());

        Iterator<Action> iterator = actionList.iterator();
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), keyEvent -> {
            drawingPane.getChildren().remove(initialState.getJFXShape());
            Action action = iterator.next();
            stateTableList.add(new State(++iterationCount, initialState.getCenter().getX(), initialState.getCenter().getY(), initialState.getOrientation(), action.toString()));
            Point2D lastPoint = new Point2D(initialState.getCenter().getX(), initialState.getCenter().getY());
            initialState.applyAction(action);
            drawingPane.getChildren().add(initialState.getJFXShape());
            Point2D newPoint = new Point2D(initialState.getCenter().getX(), initialState.getCenter().getY());
            Platform.runLater(() -> {
                stateTableView.requestFocus();
                stateTableView.getSelectionModel().select(stateTableList.size() - 1);
                stateTableView.getFocusModel().focus(stateTableList.size() - 1);
                drawingPane.getChildren().add(new Line(lastPoint.getX(), lastPoint.getY(), newPoint.getX(), newPoint.getY()));
            });

        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(actionList.size());
        timeline.playFromStart();
    }

    @FXML
    public void handleClassSelectionAction(ActionEvent actionEvent) {
        try {
            searchAlgorithm = (SearchAlgorithm) searchClassComboBox.getSelectionModel().getSelectedItem().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setStyle("-fx-focus-color: transparent;");
        statusLabel.setText("Started the application");
        stateTableView.setItems(stateTableList);
        iterationColumn.setCellValueFactory(data -> Bindings.format("%5.0f", new SimpleDoubleProperty(data.getValue().iteration)));
        xColumn.setCellValueFactory(data -> Bindings.format("%7.2f", new SimpleDoubleProperty(data.getValue().x)));
        yColumn.setCellValueFactory(data -> Bindings.format("%7.2f", new SimpleDoubleProperty(data.getValue().y)));
        thetaColumn.setCellValueFactory(data -> Bindings.format("%5.2f", new SimpleDoubleProperty(Math.toDegrees(data.getValue().theta))));
        actionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().action));

        Platform.runLater(() -> drawGrid());

        searchAlgorithm = new RandomSearchAlgorithm();
        searchClassComboBox.setItems(searchClassList);

        try {
            Arrays.stream(SubclassLoader.getClasses("bd.edu.seu.fall2018ai.assignment1.algorithm"))
                    .filter(aClass -> !aClass.getName().equals("bd.edu.seu.fall2018ai.assignment1.algorithm.SearchAlgorithm"))
                    .forEach(aClass -> searchClassList.add(aClass));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
