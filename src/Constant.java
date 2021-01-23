public final class Constant {



    public enum Heuristic{
        HEURISTIC1,
        HEURISTIC2,
        HEURISTIC3;
    }


    public static String[][] INITIAL_BOARD = {{" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "}};

    public static final Integer ROW_SIZE = 6;

    public static final Integer COL_SIZE = 7;

    public static final Integer WIN_SCORE =  100000;

    public static final Player player1 = new Player("B" , "Player 1");

    public static final Player player2 = new Player("R" , "Player 2");
}

