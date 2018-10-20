package bd.edu.seu.fall2018ai.assignment1.model;

public enum Action {
    MOVE_FORWARD("↑"),
    ROTATE_CCW("↺"),
    ROTATE_CW("↻");

    private String displayName;

    Action(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
