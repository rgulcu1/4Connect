import java.util.Arrays;

public class Node {

    private Integer weight;

    private String[][] state;

    private Integer depth;

    public Node( String[][] state , Integer weight,Integer depth) {
        this.weight = weight;
        this.state  = new String[state.length][];
        Utils.deepCopyDobuleArray(this.state , state);
        this.depth = depth;
    }

    public String[][] getState() {
        return state;
    }

    public void setState(String[][] state) {
        Utils.deepCopyDobuleArray(this.state,state);
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getDepth() {
        return depth;
    }

}
