package bd.edu.seu.fall2018ai.assignment1.algorithm;

import bd.edu.seu.fall2018ai.assignment1.model.Action;
import bd.edu.seu.fall2018ai.assignment1.model.Destination2D;
import bd.edu.seu.fall2018ai.assignment1.model.Robot2D;
import bd.edu.seu.fall2018ai.assignment1.model.Shape2D;

import java.util.List;

public abstract class SearchAlgorithm {
    public abstract List<Action> search(Robot2D source,
                                        Destination2D destination,
                                        List<Shape2D> obstacleList,
                                        double mapWidth, double mapHeight);
}
