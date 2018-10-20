package bd.edu.seu.fall2018ai.assignment1.algorithm;

import bd.edu.seu.fall2018ai.assignment1.model.Action;
import bd.edu.seu.fall2018ai.assignment1.model.Destination2D;
import bd.edu.seu.fall2018ai.assignment1.model.Robot2D;
import bd.edu.seu.fall2018ai.assignment1.model.Shape2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnotherRandomSearchAlgorithm extends SearchAlgorithm {
    @Override
    public List<Action> search(Robot2D source,
                               Destination2D destination,
                               List<Shape2D> obstacleList,
                               double mapWidth, double mapHeight) {
        List<Action> actionList = new ArrayList<>();

        final int MAX_ACTIONS = 100;
        Random random = new Random();

        for (int i = 0; i < MAX_ACTIONS; i++) {
            Robot2D currentState = new Robot2D(source.getCenter(), source.getRadius(), source.getOrientation());
            Action randomAction = Action.values()[random.nextInt(Action.values().length - 1)];
            source.applyAction(randomAction);

            boolean collides = false;

            if (source.getCenter().getSquaredDistance(destination.getCenter()) < destination.getRadius() * destination.getRadius())
                break;

            if (source.getCenter().getX() - source.getRadius() < 0 ||
                    source.getCenter().getY() - source.getRadius() < 0 ||
                    source.getCenter().getX() + source.getRadius() >= mapWidth ||
                    source.getCenter().getY() + source.getRadius() >= mapHeight)
                collides = true;

            if (!collides)
                for (Shape2D shape2D : obstacleList) {
                    if (shape2D.collidesWith(source)) {
                        collides = true;
                        break;
                    }
                }

            if (collides) {
                source = currentState;
            } else {
                actionList.add(randomAction);
            }
        }

        return actionList;
    }
}
