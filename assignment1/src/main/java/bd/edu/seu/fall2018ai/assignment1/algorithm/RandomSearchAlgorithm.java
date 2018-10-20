package bd.edu.seu.fall2018ai.assignment1.algorithm;

import bd.edu.seu.fall2018ai.assignment1.model.Action;
import bd.edu.seu.fall2018ai.assignment1.model.Destination2D;
import bd.edu.seu.fall2018ai.assignment1.model.Robot2D;
import bd.edu.seu.fall2018ai.assignment1.model.Shape2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSearchAlgorithm extends SearchAlgorithm {
    @Override
    public List<Action> search(Robot2D source,
                               Destination2D destination,
                               List<Shape2D> obstacleList) {
        List<Action> actionList = new ArrayList<>();

        final int MAX_ACTIONS = 100;
        Random random = new Random();
        for (int i = 0; i < MAX_ACTIONS; i++) {
            Robot2D currentState = new Robot2D(source.getCenter(), )
            Action randomAction = Action.values()[random.nextInt(Action.values().length)];

        }

        return actionList;
    }
}
