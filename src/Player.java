public class Player {

    public final String label;

    public  Constant.Heuristic heuristic;

    public  Integer depth;

    public String name;

    Player(String label , String name) {
        this.label = label;
        this.name = name;
    }

    Player(String label, Constant.Heuristic heuristic, Integer depth) {
        this.label = label;
        this.heuristic = heuristic;
        this.depth = depth;
    }


}
