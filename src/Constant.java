public final class Constant {

    public enum Player{
        Player1("B"),
        Player2("R");

        public final String label;

        Player(String label) {
            this.label=label;
        }
    }

    public static final String[][] BOARD = {{" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "}};
}
