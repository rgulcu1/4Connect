
import java.util.Arrays;
import java.util.Scanner;

public class Connect4 {


    Node boardNode = new Node(Constant.INITIAL_BOARD);


    public void playHumanvsHuman(){

        while (true) {

            Utils.printBoard(boardNode.getState());
            playerMove(Constant.player1);
            Utils.printBoard(boardNode.getState());
            playerMove(Constant.player2);
        }

    }

    public void playHumanvsAi(Constant.Heuristic heuristicFunction , Integer depthLevel){

        Constant.player2.depth = depthLevel;
        Constant.player2.heuristic =heuristicFunction;


        while(true){
            Utils.printBoard(boardNode.getState());
            playerMove(Constant.player1);
            Utils.printBoard(boardNode.getState());
            generateAiDecision(Constant.player2);
        }
    }

    public void playAivsAi(Constant.Heuristic heuristicFunction1 , Constant.Heuristic heuristicFunction2 , Integer depthLevel1 , Integer depthLevel2){

        Constant.player1.depth = depthLevel1;
        Constant.player1.heuristic =heuristicFunction1;

        Constant.player2.depth = depthLevel2;
        Constant.player2.heuristic =heuristicFunction2;

        while(true){
            Utils.printBoard(boardNode.getState());
            generateAiDecision(Constant.player1);
            Utils.printBoard(boardNode.getState());
            generateAiDecision(Constant.player2);
        }
    }

   private void generateAiDecision(Player player){
       Integer[] aiMove = maxPlay(boardNode , player.depth,Integer.MIN_VALUE , Integer.MAX_VALUE , player);
       System.out.println(Arrays.toString(aiMove));
       move(boardNode.getState(),aiMove[0],player);

       checkGameOver(player);
   }

   public  Integer[] maxPlay(Node node , Integer depth, Integer alfa , Integer beta , Player player){

       Integer score = node.getScore(player);

       if( node.isGameOver(depth , score)) return new Integer[] {null ,score };

       Integer[] max = {null ,Integer.MIN_VALUE};

       for (int i = 0; i <Constant.COL_SIZE ; i++) {

           Node childNode = new Node(node.getState());

           if(move(childNode.getState(),i,player) == -1) continue;

           Integer[] minPlay =  minPlay(childNode,depth-1, alfa , beta , Utils.reversePlayer(player));

           if (max[0] == null || minPlay[1] > max[1]) {
               max[0] = i;
               max[1] = minPlay[1];
               alfa = minPlay[1];
           }

           if (alfa >= beta) return max;
       }
       return max;
   }

   private Integer[] minPlay(Node node , Integer depth, Integer alfa , Integer beta , Player player){

       Integer score = node.getScore(Utils.reversePlayer(player));

       if( node.isGameOver(depth , score)) return new Integer[] {null ,score };

       Integer[] min = {null ,Integer.MAX_VALUE};

       for (int i = 0; i <Constant.COL_SIZE ; i++) {

           Node childNode = new Node(node.getState());

           if(move(childNode.getState(),i,player) == -1) continue;

           Integer[] maxPlay =  maxPlay(childNode,depth-1, alfa , beta , Utils.reversePlayer(player));

           if (min[0] == null || maxPlay[1] < min[1]) {
               min[0] = i;
               min[1] = maxPlay[1];
               beta = maxPlay[1];
           }

           if (alfa >= beta) return min;
       }
       return min;

   }


    private void checkGameOver(Player player){

       if(boardNode.getScore(player) == Constant.WIN_SCORE){
            Utils.printBoard(boardNode.getState());
            System.out.println(player.name + " winss!");
            System.exit(1);
        }

        if(boardNode.isBoardFull()){
            Utils.printBoard(boardNode.getState());
            System.out.println("Tie!!");
            System.exit(1);
        }
    }

    private void playerMove(Player player) {

        final Scanner scan = new Scanner(System.in);
        Integer colChoice;
        Integer row;

        System.out.print("\n" + player.name + " please enter a column:");
        while (true) {

            colChoice = scan.nextInt();
            if (colChoice > 7 || colChoice < 1) {
                System.err.println("Please Enter a valid column number!!!");
                continue;
            }
            row = move(boardNode.getState(), colChoice - 1, player);
            if (row != -1) {
                break;
            }
            System.err.println("Selected column is full!!!");
        }

        checkGameOver(player);
    }

    private Integer move(String[][] state , Integer colChoice , Player player){

        if(!state[0][colChoice].equals(" ")){
            return -1;
        }

        int i;
        for (i = 5; i >=0 ; i--) {
            if(state[i][colChoice].equals(" ")) break;
        }

        state[i][colChoice] = player.label;
        return i;
    }


}
