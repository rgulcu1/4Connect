public class demoConnect4 {

    public static void main(String[] args) {

        Connect4 connect4 = new Connect4();

        //connect4.playHumanvsAi(Constant.Heuristic.HEURISTIC1,4);
        //connect4.playHumanvsHuman();
        connect4.playAivsAi(Constant.Heuristic.HEURISTIC1 , Constant.Heuristic.HEURISTIC1 ,4 ,4);

       // MainFrame mainFrame = new MainFrame("4connect");


    }
}
