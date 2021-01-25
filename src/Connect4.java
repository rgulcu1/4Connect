
import java.util.Scanner;

public class Connect4 {


    Node boardNode = new Node(Constant.INITIAL_BOARD);


   public void generateAiDecision(Player player){
       Integer[] aiMove = maxPlay(boardNode , player.depth,Integer.MIN_VALUE , Integer.MAX_VALUE , player);
       move(boardNode.getState(),aiMove[0],player);
   }

   private   Integer[] maxPlay(Node node , Integer depth, Integer alfa , Integer beta , Player player){

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

   public Integer move(String[][] state , Integer colChoice , Player player){

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
