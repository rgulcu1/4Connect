import java.util.Arrays;

public class demoConnect4 {

    public static void main(String[] args) {

        Connect4 connect4 = new Connect4();

        //connect4.playHumanvsAi(Constant.Heuristic.HEURISTIC2,4);
        //connect4.playHumanvsHuman();
        connect4.playAivsAi(Constant.Heuristic.HEURISTIC3 , Constant.Heuristic.HEURISTIC2 ,8 ,8);

        Node node = new Node(Constant.INITIAL_BOARD);

      //  Constant.player2.heuristic = Constant.Heuristic.HEURISTIC3;
       // Constant.player2.depth = 4;
        //System.out.println(Arrays.toString(connect4.maxPlay(node,Constant.player2.depth,Integer.MIN_VALUE , Integer.MAX_VALUE , Constant.player2)));
       // System.out.println(node.getScore(Constant.player2));
       // MainFrame mainFrame = new MainFrame("4connect");


    }
}
