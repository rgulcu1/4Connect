import java.awt.*;

public class Player {

    public final String label;

    public  Constant.Heuristic heuristic;

    public  Integer depth;

    public String name;

    public Color playerColor;

    public String colorName;

    Player(String label , String name , Color color , String colorName) {
        this.label = label;
        this.name = name;
        this.playerColor =color;
        this.colorName = colorName;
    }

    Player(String label, Constant.Heuristic heuristic, Integer depth) {
        this.label = label;
        this.heuristic = heuristic;
        this.depth = depth;
    }


}
