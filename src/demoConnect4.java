public class demoConnect4 {

    public static void main(String[] args) {

        Connect4 connect4 = new Connect4();

        //connect4.playHumanvsAi(Constant.Heuristic.HEURISTIC1,5);
        connect4.playHumanvsHuman(Constant.Heuristic.HEURISTIC1,3);

       // MainFrame mainFrame = new MainFrame("4connect");


    }
}
