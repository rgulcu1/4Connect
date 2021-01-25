import java.awt.*;

public final class Constant {



    public enum Heuristic{
        HEURISTIC1,
        HEURISTIC2,
        HEURISTIC3;
    }

    public enum GameType{
        AIvsAI,
        HUMANvsHUMAN,
        HUMANvsAI
    }


    public static String[][] INITIAL_BOARD = {{" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "},
                                            {" "," "," "," "," "," "," "}};

    public static Integer[][] CELL_WEIGHTS = {{3,4,5,7,5,4,3},
            {4,6,8,10,8,6,4},
            {5,8,11,13,11,8,5},
            {5,8,11,13,11,8,5},
            {4,6,8,10,8,6,4},
            {3,4,5,7,5,4,3}};

    public static final Integer ROW_SIZE = 6;

    public static final Integer COL_SIZE = 7;

    public static final Integer WIN_SCORE =  10000000;

    public static final Player player1 = new Player("B" , "Player 1", Color.ORANGE , "Yellow");

    public static final Player player2 = new Player("R" , "Player 2" , Color.RED , "Red");
}

