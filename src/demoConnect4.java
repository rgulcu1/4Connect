public class demoConnect4 {

    public static void main(String[] args) {
        //System.out.println((char)27 +"[32m testing bold[0m");
        Connect4 connect4 = new Connect4();

        connect4.playHumanvsAi(Constant.Heuristic.HEURISTIC1,10);


    }
}
